package com.example.arafathossain.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

public class ImageEditorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Bitmap bitmap;
    float angle = 0;
    Uri uri;
    private int width;
    private int height;
    private float wth;
    private float htw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);
        Button crop = (Button) findViewById(R.id.crop);
        Button resize = (Button) findViewById(R.id.resize);
        Button rotate = (Button) findViewById(R.id.rotate);
        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);
        imageView = (ImageView) findViewById(R.id.imageView);
        try {
            uri = getIntent().getData();
            if (uri == null) bitmap = (Bitmap) getIntent().getExtras().get("bitmap");
            else bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            wth = (float) width / height;
            htw = (float) height / width;
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        crop.setOnClickListener(this);
        resize.setOnClickListener(this);
        rotate.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void showCropDialog() {
        final CropImageView cropImageView = new CropImageView(this);
        cropImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setGuidelines(2);
        cropImageView.setAspectRatio(5, 5);
        cropImageView.setImageBitmap(bitmap);
        cropImageView.setPadding(10, 10, 10, 10);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(cropImageView);
        builder.setPositiveButton("Crop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bitmap = Bitmap.createScaledBitmap(cropImageView.getCroppedImage(), bitmap.getWidth(), bitmap.getHeight(), true);
                imageView.setImageBitmap(bitmap);
                imageView.invalidate();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showResizeDialog() {
        View view = getLayoutInflater().inflate(R.layout.image_resize_layout, null);
        final EditText iw = (EditText) view.findViewById(R.id.imageWidth);
        final EditText ih = (EditText) view.findViewById(R.id.imageHeight);
        final CheckBox cp = (CheckBox) view.findViewById(R.id.cp);
        cp.setChecked(true);
        iw.setText(String.valueOf(bitmap.getWidth()));
        ih.setText(String.valueOf(bitmap.getHeight()));
        iw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (iw.hasFocus()) {
                    if (s != null && !s.toString().isEmpty() && cp.isChecked()) {
                        float f = Float.parseFloat(s.toString()) * htw;
                        ih.setText(String.valueOf((int) f));
                    } else if (s == null || s.toString().isEmpty()) {
                        if (cp.isChecked()) {
                            iw.setText("0");
                            ih.setText("0");
                        } else iw.setText("0");
                    }
                    Log.d("start before", start + " " + before);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ih.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ih.hasFocus()) {
                    if (s != null && !s.toString().isEmpty() && cp.isChecked()) {
                        float f = Float.parseFloat(s.toString()) * wth;
                        iw.setText(String.valueOf((int) f));
                    } else if (s == null || s.toString().isEmpty()) {
                        if (cp.isChecked()) {
                            iw.setText("0");
                            ih.setText("0");
                        } else ih.setText("0");
                    } else if (s.toString().charAt(0) == '0') {
                        if (cp.isChecked()) {
                            iw.setText(s.toString().substring(1, s.length()));
                            ih.setText(s.toString().substring(1, s.length()));
                        } else ih.setText(s.toString().substring(1, s.length()));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (iw.hasFocus()) {
                        float f = Float.parseFloat(iw.getText().toString()) * wth;
                        ih.setText(String.valueOf((int) f));
                    } else {
                        float f = Float.parseFloat(ih.getText().toString()) * wth;
                        iw.setText(String.valueOf((int) f));
                    }
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Matrix matrix = new Matrix();
                matrix.setScale(Float.parseFloat(iw.getText().toString()) / width, Float.parseFloat(ih.getText().toString()) / height);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
                width = Integer.parseInt(iw.getText().toString());
                height = Integer.parseInt(ih.getText().toString());
                wth = (float) width / height;
                htw = (float) height / width;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void rotateBitmap(float angle) {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crop:
                showCropDialog();
                break;
            case R.id.resize:
                showResizeDialog();
                break;
            case R.id.rotate:
                angle += 90;
                if (angle > 270) angle = 90;
                Log.d("angle", String.valueOf(angle));
                rotateBitmap(angle);
                break;
            case R.id.save:
                saveImage();
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    private void saveImage() {
        String fileName = "image_"+ Calendar.getInstance().getTimeInMillis()+".jpg";
        File file = new File(uri.getPath());
        if (file.exists()) {
            file.delete();
            Log.d("deleteFile","exists");
        }
        String profileDir = "profile_"+getIntent().getStringExtra("profileId");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),profileDir);
        }
        else {
            file = new File(getFilesDir(),profileDir);
        }
        file.mkdirs();
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(file,fileName));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this,"Save Complete",Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("fileName",fileName);
            setResult(RESULT_OK,intent);
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

