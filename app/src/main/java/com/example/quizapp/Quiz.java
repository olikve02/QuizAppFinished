package com.example.quizapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    private static final String TAG = "Quiz";
    private ImageView imageView;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private List<Pair<String, Uri>> imagePairs;
    private Pair<String, Uri> correctPair;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        imageView = findViewById(R.id.currentImage);
        answer1Button = findViewById(R.id.answer1);
        answer2Button = findViewById(R.id.answer2);
        answer3Button = findViewById(R.id.answer3);

        MyApplication app = (MyApplication) getApplication();
        imagePairs = app.getImageList();


        setupQuizRound();
    }

    private void setupQuizRound() {
        correctPair = getRandomPair(imagePairs);
        imageView.setImageURI(correctPair.second);

        List<String> incorrectNames = getIncorrectNames(correctPair.first);
        List<String> allNames = new ArrayList<>();
        allNames.add(correctPair.first);
        allNames.addAll(incorrectNames);
        Collections.shuffle(allNames); // Shuffle the names

        answer1Button.setText(allNames.get(0));
        answer2Button.setText(allNames.get(1));
        answer3Button.setText(allNames.get(2));

        answer1Button.setOnClickListener(this::checkAnswer);
        answer2Button.setOnClickListener(this::checkAnswer);
        answer3Button.setOnClickListener(this::checkAnswer);
    }

    private void checkAnswer(View view) {
        Button clickedButton = (Button) view;
        String selectedName = clickedButton.getText().toString();

        if (selectedName.equals(correctPair.first)) {
            // Correct answer
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            setupQuizRound(); // Start a new round
        } else {
            // Incorrect answer
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private Pair<String, Uri> getRandomPair(List<Pair<String, Uri>> list) {
        Random random = new Random();
        int randomInt = random.nextInt(list.size());
        return list.get(randomInt);
    }

    private List<String> getIncorrectNames(String correctName) {
        List<String> incorrectNames = new ArrayList<>();
        List<String> allNames = new ArrayList<>();
        for (Pair<String, Uri> pair : imagePairs) {
            allNames.add(pair.first);
        }


        if (allNames.size() < 3) {
            // Handle the case where there are not enough names
            Log.e(TAG, "Ikke nokk bilder i gallriet.");
            return incorrectNames;
        }

        while (incorrectNames.size() < 2) {
            String randomName = allNames.get(new Random().nextInt(allNames.size()));
            if (!randomName.equals(correctName) && !incorrectNames.contains(randomName)) {
                incorrectNames.add(randomName);
            }
        }
        return incorrectNames;
    }
}