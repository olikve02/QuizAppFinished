package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        MyApplication app = (MyApplication) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Button goToGallery = findViewById(R.id.goToGallery);
        Intent galleryIntent = new Intent(this, Gallery.class);
        Intent quizIntent = new Intent(this, Quiz.class);
        goToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(galleryIntent);
            }
        });
        Button goToQuiz = findViewById(R.id.goToQuiz);
        goToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app.getImageList().isEmpty()){
                    Toast.makeText(Menu.this, "No images in gallery", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(quizIntent);
                }

            }
        });
    }
}
