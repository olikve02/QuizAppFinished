package com.example.quizapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class Gallery extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        Button addButton = findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImageWithGallery();
            }
        });
        loadImages();
    }
    @Override
    public void onResume(){
        super.onResume();
        loadImages();
    }
    private static final int PICK_IMAGE_REQUEST = 1;
    public List<Pair<Integer, String>> images;


    public void addImageWithId(int id, String name){
        images.add(new Pair<>(id, name));
    }

    public void addImageWithGallery(){
        //Create intent to open and view images
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //Handle intent
        super.onActivityResult(requestCode, resultCode, data);
        //If intent response is valid, go to new activity for naming of the image
        if(requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null){
            Uri imageUri = data.getData();
            Intent intent = new Intent(this, NewImage.class);
            intent.putExtra("imageUri", imageUri.toString());
            startActivity(intent);

        }
    }
    public void loadImages(){
        LinearLayout imageContainer = findViewById(R.id.imageContainer);
        imageContainer.removeAllViews();
        MyApplication app = (MyApplication) getApplication();
        List<Pair<String, Uri>> imageList = app.getImageList();
        for (Pair<String, Uri> pair : imageList) {
            String name = pair.first;
            Uri imageUri = pair.second;
            //Create new Linear layout to hold the imageview and textview
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            itemLayout.setOrientation(LinearLayout.VERTICAL);

            //Create new imageview
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Create textview
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(name);
            textView.setPadding(16, 0, 0, 0);

            itemLayout.addView(imageView);
            itemLayout.addView(textView);

            imageContainer.addView(itemLayout);
        }
    }



}
