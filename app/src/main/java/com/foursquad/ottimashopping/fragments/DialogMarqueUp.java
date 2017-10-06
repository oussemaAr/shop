package com.foursquad.ottimashopping.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.foursquad.ottimashopping.R;

/**
 * Created by oussemaar on 9/25/17.
 * <In Code I Trust />
 */

public class DialogMarqueUp extends DialogFragment implements View.OnClickListener {

    private OnCloseListener mOnCloseListener;
    private EditText editText;

    public void setmOnCloseListener(OnCloseListener mOnCloseListener) {
        this.mOnCloseListener = mOnCloseListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog, container, false);
        editText = v.findViewById(R.id.marque_up);
        v.findViewById(R.id.valider).setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {
        Log.e("TAG", "onClick: " + editText.getText().toString());
        getDialog().dismiss();
        mOnCloseListener.onClose(editText.getText().toString());
    }

    public interface OnCloseListener {
        void onClose(String value);
    }
}
