package com.example.quizapp;

import android.app.Application;
import android.net.Uri;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<Pair<String, Uri>> imageList = new ArrayList<>();

    public List<Pair<String, Uri>> getImageList(){
        return imageList;
    }
    public void addImage(String name, Uri imageUri){
        imageList.add(new Pair<>(name, imageUri));

    }
}
