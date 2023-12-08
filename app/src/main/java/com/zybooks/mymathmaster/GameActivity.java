package com.zybooks.mymathmaster;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        setTitle(operationType);

        // Set a listener for the difficulty level RadioGroup
        difficultyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            difficultyLevel = ((RadioButton) findViewById(checkedId)).getText().toString();
        });

        // Set a listener for the start game button
        startGameButton.setOnClickListener(v -> onStartGameClicked());
    }
    public void onRadioButtonClicked(View view) {
        int id = view.getId();

        if (id == R.id.easy) {
            difficultyLevel = "Easy";
        } else if (id == R.id.medium) {
            difficultyLevel = "Medium";
        } else if (id == R.id.hard) {
            difficultyLevel = "Hard";
        }
    }

    private void onStartGameClicked() {
        // Get the selected time
        String selectedTime = chooseTimeSpinner.getSelectedItem().toString();

        // Get the selected difficulty level
        int selectedRadioButtonId = difficultyRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String selectedDifficulty = selectedRadioButton.getText().toString();

        // Start GameLogicActivity with selected settings
        Intent intent = new Intent(this, GameLogicActivity.class);
        intent.putExtra("OperationType", operationType);
        intent.putExtra("DifficultyLevel", selectedDifficulty);
        intent.putExtra("TimeLimit", selectedTime);
        startActivity(intent);
    }
}
