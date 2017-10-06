package com.foursquad.ottimashopping.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foursquad.ottimashopping.Main;
import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.adapters.ColorPickerAdapter;
import com.foursquad.ottimashopping.fragments.DialogMarqueUp;
import com.foursquad.ottimashopping.models.Article;
import com.foursquad.ottimashopping.models.MyColor;
import com.foursquad.ottimashopping.utils.CheckView;
import com.foursquad.ottimashopping.utils.ColorPickerDialog;
import com.foursquad.ottimashopping.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddArticle extends AppCompatActivity implements View.OnClickListener, ColorPickerDialog.OnCloseListener, SeekBar.OnSeekBarChangeListener, DialogMarqueUp.OnCloseListener {

    private static final String TAG = AddArticle.class.getSimpleName();

    private ImageView imagePreview;
    private ColorPickerAdapter colorPickerAdapter;
    private ArrayList<MyColor> list;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private File file;
    private StorageReference storageReference;
    private EditText buyPrice;
    private EditText sellPrice;
    private CheckView xs, s, m, l, xl;
    private TextView qte;
    private Spinner type;
    private int value;
    private EditText marque;
    private String marqueUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        FirebaseApp.initializeApp(this);
        initView();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String millisInString = dateFormat.format(new Date());
        storageReference = FirebaseStorage.getInstance().getReference(millisInString);
        Toolbar toolbar = findViewById(R.id.toolbar_parent);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ajouter Article");

        }
        Constant.fillData();
        list = Constant.filterData();
        recyclerView = findViewById(R.id.recycler_color);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        colorPickerAdapter = new ColorPickerAdapter(this, list, 0);
        recyclerView.setAdapter(colorPickerAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Chargement");
        progressDialog.setMessage("Attendez s'il vous plaît");
        findViewById(R.id.cardViewColor).setOnClickListener(this);
        findViewById(R.id.add_btn).setOnClickListener(this);
        imagePreview = findViewById(R.id.image_preview);
        imagePreview.setOnClickListener(this);
    }

    private void initView() {
        buyPrice = findViewById(R.id.buy_price);
        sellPrice = findViewById(R.id.sell_price);
        RadioButton s1 = findViewById(R.id.radio1);
        RadioButton s2 = findViewById(R.id.radio2);
        xs = findViewById(R.id.xs);
        s = findViewById(R.id.s);
        l = findViewById(R.id.l);
        xl = findViewById(R.id.xl);
        m = findViewById(R.id.m);
        SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
        qte = findViewById(R.id.qte);
        type = findViewById(R.id.product_type);
        marque = findViewById(R.id.marque);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddArticle.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cardViewColor:
                recyclerView.invalidate();
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
                colorPickerDialog.show(getFragmentManager(), "tag");
                colorPickerDialog.setmOnCloseListener(this);
                break;
            case R.id.image_preview:
                requestPermission();
                break;
            case R.id.add_btn:
                if (isOkey()) {
                    DialogMarqueUp fragment = new DialogMarqueUp();
                    fragment.setCancelable(false);
                    fragment.setmOnCloseListener(this);
                    fragment.show(getFragmentManager(), "tag");
                }
                break;
        }
    }

    private void uploadImage() {
        Uri uri = Uri.fromFile(file);
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        assert downloadUrl != null;
                        Log.e(TAG, "onSuccess: " + downloadUrl.toString());
                        insertData(downloadUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

    private void insertData(Uri downloadUrl) {
        Article article = new Article();
        article.setMarqueUp(Float.parseFloat(marqueUp));
        article.setTitle(marque.getText().toString());
        article.setImageUri(downloadUrl.toString());
        article.setColors(Constant.filterData());
        article.setSizes(getSelectedSize());
        article.setType(type.getSelectedItem().toString());
        article.setQuantity(value);
        article.setBuyPrice(Float.parseFloat(buyPrice.getText().toString()));
        article.setSellPrice(Float.parseFloat(sellPrice.getText().toString()));
        article.setFinalPrice(article.getMarqueUp() * article.getBuyPrice());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("articles");
        ref.push().setValue(article).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Constant.filterData().clear();
                startActivity(new Intent(AddArticle.this, Main.class));
            }
        });
    }

    private ArrayList<String> getSelectedSize() {
        ArrayList<String> list = new ArrayList<>();
        if (xl.isStatus()) {
            list.add("xl");
        }
        if (xs.isStatus()) {
            list.add("xs");
        }
        if (s.isStatus()) {
            list.add("s");
        }
        if (m.isStatus()) {
            list.add("m");
        }
        if (l.isStatus()) {
            list.add("l");
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath());
        imagePreview.setImageBitmap(bitmap);
    }

    public static Bitmap decodeSampledBitmapFromFile(String path) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > 500) {
            inSampleSize = Math.round((float) height / (float) 500);
        }
        int expectedWidth = width / inSampleSize;
        if (expectedWidth > 500) {
            inSampleSize = Math.round((float) width / (float) 500);
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddArticle.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        } else {
            startCameraIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: Granted");
                    startCameraIntent();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void startCameraIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 101);
    }

    @Override
    public void onClose() {
        list.clear();
        list.addAll(Constant.filterData());
        colorPickerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        String qteString = "Quantité %1$d";
        qte.setText(String.format(Locale.getDefault(), qteString, i));
        value = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private boolean isOkey() {
        if (file == null) {
            Toast.makeText(AddArticle.this, "Ajouter l'image du produit", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(buyPrice.getText())) {
            buyPrice.setError("Donner prix d'achat");
            return false;
        }
        if (TextUtils.isEmpty(sellPrice.getText())) {
            sellPrice.setError("Donner prix de vente");
            return false;
        }
        if (Constant.filterData().size() == 0) {
            Toast.makeText(AddArticle.this, "Selectionnez les couleurs du produit", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(marque.getText())) {
            marque.setError("Donner la marque");
            return false;
        }

        return true;
    }

    @Override
    public void onClose(String value) {
        this.marqueUp = value;
        progressDialog.show();
        uploadImage();
    }
}
