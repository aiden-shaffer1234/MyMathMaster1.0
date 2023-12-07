package com.zybooks.mymathmaster;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private Spinner chooseTimeSpinner;
    private RadioGroup difficultyRadioGroup;
    private Button startGameButton;
    private String difficultyLevel = "Easy";
    private String operationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_setup);

        chooseTimeSpinner = findViewById(R.id.chooseTimeSpinner);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        startGameButton = findViewById(R.id.start_game);

        // Populate the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseTimeSpinner.setAdapter(adapter);

        // Get the operation type from MainActivity
        operationType = getIntent().getStringExtra("OperationType");

        // Set a listener for the difficulty level RadioGroup
        difficultyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            difficultyLevel = ((RadioButton) findViewById(checkedId)).getText().toString();
        });

        // Set a listener for the start game button
        startGameButton.setOnClickListener(v -> onStartGameClicked());
    }

    private void onStartGameClicked() {
        // Get the selected time
        String selectedTime = chooseTimeSpinner.getSelectedItem().toString();

        //for the following activities
        // Start the game activity with selected settings
        Intent intent = new Intent(this, GameLogicActivity.class);
        intent.putExtra("OperationType", operationType);
        intent.putExtra("DifficultyLevel", difficultyLevel);
        intent.putExtra("TimeLimit", selectedTime);
        //after choosing a difficulty it goes back to the home page
        startActivity(intent);
    }
}
