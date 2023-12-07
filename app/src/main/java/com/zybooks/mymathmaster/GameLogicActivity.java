package com.zybooks.mymathmaster;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Locale;
import java.security.SecureRandom;

public class GameLogicActivity extends AppCompatActivity {
    private String operationType;
    private String difficultyLevel;
    private String timeLimit;
    private TextView topNumView, bottomNumView, operatorView, timerView, gameProgressView;
    private EditText answerInput;
    private CountDownTimer gameTimer;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int totalQuestions = 0;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);

        // Initialize UI elements
        topNumView = findViewById(R.id.topNumOfQuestion);
        bottomNumView = findViewById(R.id.bottomNumOfQuestion);
        operatorView = findViewById(R.id.operator);
        answerInput = findViewById(R.id.answer);

        // Get intent extras
        operationType = getIntent().getStringExtra("OperationType");
        difficultyLevel = getIntent().getStringExtra("DifficultyLevel");
        timeLimit = getIntent().getStringExtra("TimeLimit");

        // Generate the first problem
        gameProgressView = findViewById(R.id.gameProgress);
        updateGameProgress();
        generateProblem();
        timerView = findViewById(R.id.timerView);
        initializeTimer();
    }

    private void generateProblem() {
        int maxNumber = getMaxNumberBasedOnDifficulty();
        int num1 = secureRandom.nextInt(maxNumber) + 1;
        int num2 = secureRandom.nextInt(maxNumber) + 1;

        // Ensure num1 is always the larger number
        num1 = Math.max(num1, num2);
        num2 = Math.min(num1, num2);


        topNumView.setText(String.valueOf(num1));
        bottomNumView.setText(String.valueOf(num2));
        operatorView.setText(getOperatorSymbol());

        // Reset answer input
        answerInput.setText("");
        totalQuestions++; // Increment the total questions counter
        updateGameProgress(); // Update the game progress display
    }

    private String getOperatorSymbol() {
        switch (operationType) {
            case "Addition":
                return "+";
            case "Subtraction":
                return "-";
            case "Multiplication":
                return "*";
            case "Division":
                return "/";
            default:
                return "";
        }
    }

    private int getMaxNumberBasedOnDifficulty() {
        switch (difficultyLevel) {
            case "Easy":
                return 10;
            case "Medium":
                return 100;
            case "Hard":
                return 1000;
            default:
                return 10;
        }
    }

    private void initializeTimer() {
        long timeInMillis = convertTimeToMillis(timeLimit);
        gameTimer = new CountDownTimer(timeInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update UI with remaining time
                updateTimerDisplay(millisUntilFinished);
            }

            public void onFinish() {
                // Time's up, go to Results Activity
                goToResultsActivity();
            }
        }.start();
    }

    private long convertTimeToMillis(String timeString) {
        String[] timeParts = timeString.split(":");
        int minutes = Integer.parseInt(timeParts[0]);
        int seconds = Integer.parseInt(timeParts[1]);
        return (minutes * 60 + seconds) * 1000;
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        long minutes = (millisUntilFinished / 1000) / 60;
        long seconds = (millisUntilFinished / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerView.setText(timeFormatted);
    }

    // Call this method when the user submits an answer
    public void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerInput.getText().toString());
            int correctAnswer = calculateCorrectAnswer();
            if (userAnswer == correctAnswer) {
                correctAnswers++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                generateProblem(); // Generate a new problem
            } else {
                incorrectAnswers++;
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            }
            updateGameProgress();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGameProgress() {
        String progressText = correctAnswers + "/" + totalQuestions;
        gameProgressView.setText(progressText);
    }

    private int calculateCorrectAnswer() {
        int num1 = Integer.parseInt(topNumView.getText().toString());
        int num2 = Integer.parseInt(bottomNumView.getText().toString());
        switch (operationType) {
            case "Addition":
                return num1 + num2;
            case "Subtraction":
                return num1 - num2;
            case "Multiplication":
                return num1 * num2;
            case "Division":
                return num1 / num2; // Integer division for 'floor' result
            default:
                return 0;
        }
    }

    public void onSubmitClick(View view) {

        checkAnswer();
    }

    public void onSkipClick(View view) {
        incorrectAnswers++;
        generateProblem();
    }

    private void goToResultsActivity() {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("CorrectAnswers", correctAnswers);
        intent.putExtra("IncorrectAnswers", incorrectAnswers);
        startActivity(intent);
        finish();
    }

}