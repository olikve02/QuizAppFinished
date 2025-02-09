package com.example.quizapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class NewImage extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_image);
        ImageView imageView = findViewById(R.id.imageView);
        String imageString = getIntent().getStringExtra("imageUri");
        Uri imageUri = Uri.parse(imageString);
        imageView.setImageURI(imageUri);
        Button leggTilButton = findViewById(R.id.leggTilButton);
        Intent intent = new Intent(this, Menu.class);
        leggTilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText enteredText = findViewById(R.id.editText);
                String name = enteredText.getText().toString();
                MyApplication app = (MyApplication) getApplication();
                app.addImage(name, imageUri);
                startActivity(intent);
                finish();
            }
        });

    }

}
