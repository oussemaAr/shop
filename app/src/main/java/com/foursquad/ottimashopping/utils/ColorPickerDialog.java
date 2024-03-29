/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.foursquad.ottimashopping.utils;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.adapters.ColorPickerAdapter;


public class ColorPickerDialog extends DialogFragment implements ColorPickerAdapter.ItemClickListener, View.OnClickListener {

    private ColorPickerAdapter colorPickerAdapter;
    private OnCloseListener mOnCloseListener;

    public ColorPickerDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.color_picker_dialog, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        Button close = rootView.findViewById(R.id.close);
        close.setOnClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        colorPickerAdapter = new ColorPickerAdapter(getActivity(), Constant.colorArray, 1);
        colorPickerAdapter.setClickListener(this);
        recyclerView.setAdapter(colorPickerAdapter);
        getDialog().setCancelable(false);
        return rootView;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        this.mOnCloseListener.onClose();
    }

    @Override
    public void onItemClick(View view, int position) {
        Constant.colorArray.get(position).isSelected = !Constant.colorArray.get(position).isSelected;
        colorPickerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        getDialog().dismiss();
    }

    public void setmOnCloseListener(OnCloseListener mOnCloseListener) {
        this.mOnCloseListener = mOnCloseListener;
    }

    public interface OnCloseListener {
        void onClose();
    }
}
