package com.example.numbergame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android .view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int randomNumber;
    private int attempts;
    private final int MaxAttempts =10;
    private int score= 0;
    private TextView tvFeedback;
    private TextView tvAttempts;
    private EditText etGuess;
    private Button btnSubmit;
    private Button btnPlayAgain;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvFeedback= findViewById(R.id.tvFeedback);
        tvAttempts= findViewById(R.id.tvAttempts);
        etGuess= findViewById(R.id.etGuess);
        btnSubmit= findViewById(R.id.btnSubmit);
        btnPlayAgain=findViewById(R.id.btnPlayAgain);

        startNewGame();

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleGuess();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @SuppressLint("SetTextI18n")
    private void startNewGame(){
        Random rand= new Random();
        randomNumber= rand.nextInt(100)+1;
        attempts = MaxAttempts;
        tvAttempts.setText("Attempts left:"+attempts);
        tvFeedback.setText("Enter your guess;");
        etGuess.setText("");
        btnSubmit.setVisibility(View.VISIBLE);
        btnPlayAgain.setVisibility(View.GONE);
    }
    @SuppressLint("SetTextI18n")
    private void handleGuess(){
        String guessString= etGuess.getText().toString();
        if(guessString.isEmpty()){
            Toast.makeText(this,"Please enter a number",Toast.LENGTH_SHORT).show();
            return;
        }
        int guess=Integer.parseInt(guessString);
        attempts--;

        if(guess == randomNumber){
            tvFeedback.setText("Correct! The number was"+randomNumber);
            score++;
            endGame();
        }else if(guess < randomNumber){
            tvFeedback.setText("Too low! Try again.");
        } else{
            tvFeedback.setText("Too high! Try again");
        }
        tvAttempts.setText("Attempts left:"+attempts);

        if(attempts == 0){
            tvFeedback.setText("You've run out of attempts! The number was"+randomNumber);
            endGame();
        }
    }
    private void endGame(){
        btnSubmit.setVisibility(View.GONE);
        btnPlayAgain.setVisibility(View.VISIBLE);
        Toast.makeText(this,"Score:"+score,Toast.LENGTH_LONG).show();
    }
}