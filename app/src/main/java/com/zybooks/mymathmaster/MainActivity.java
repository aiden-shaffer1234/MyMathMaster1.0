package com.zybooks.mymathmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Button additionButton, subtractionButton, multiplicationButton, divisionButton, accountSumButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize buttons
        additionButton = findViewById(R.id.addition);
        subtractionButton = findViewById(R.id.subtraction);
        multiplicationButton = findViewById(R.id.multiplication);
        divisionButton = findViewById(R.id.division);
        accountSumButton = findViewById(R.id.accountSum);

        // Set click listeners for buttons
        additionButton.setOnClickListener(v -> startGameActivity("Addition"));
        subtractionButton.setOnClickListener(v -> startGameActivity("Subtraction"));
        multiplicationButton.setOnClickListener(v -> startGameActivity("Multiplication"));
        divisionButton.setOnClickListener(v -> startGameActivity("Division"));
        accountSumButton.setOnClickListener(v -> accountSummary());

    }
    private void startGameActivity(String operationType) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("OperationType", operationType);
        startActivity(intent);
    }

    private void accountSummary() {
        Intent intent = new Intent(MainActivity.this, AccountSum.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}