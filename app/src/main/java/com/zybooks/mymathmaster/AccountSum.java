package com.zybooks.mymathmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.zybooks.mymathmaster.model.MathCategory;
import com.zybooks.mymathmaster.repo.MathCategoryRepository;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AccountSum extends AppCompatActivity {
    private MathCategoryRepository mathCategoryRepository;
    private ExecutorService executor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sum);

        setTitle("Account");
        mathCategoryRepository = new MathCategoryRepository(getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
        fetchDataAndUpdateUI();
    }

    private void fetchDataAndUpdateUI() {
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Fetch the data
            MathCategory mathCategory = mathCategoryRepository.getMathCategoryById(1); // Assuming ID is 1 for simplicity

            // Update the UI
            handler.post(() -> {
                if (mathCategory != null) {
                    updateUIWithMathCategory(mathCategory);
                }
            });
        });
    }

    private void updateUIWithMathCategory(MathCategory mathCategory) {
        // Get references to your TextViews
        TextView correctAddition = findViewById(R.id.correctAddition);
        TextView incorrectAddition = findViewById(R.id.incorrectAddition);
        TextView overallPerformanceAddition = findViewById(R.id.overallPerformanceAddition);
        TextView correctSubtraction = findViewById(R.id.correctSubtraction);
        TextView incorrectSubtraction = findViewById(R.id.incorrectSubtraction);
        TextView overallPerformanceSubtraction = findViewById(R.id.overallPerformanceSubtraction);
        TextView correctMultiplication = findViewById(R.id.correctMultiplication);
        TextView incorrectMultiplication = findViewById(R.id.incorrectMultiplication);
        TextView overallPerformanceMultiplication = findViewById(R.id.overallPerformanceMultiplication);
        TextView correctDivision = findViewById(R.id.correctDivision);
        TextView incorrectDivision = findViewById(R.id.incorrectDivision);
        TextView overallPerformanceDivision = findViewById(R.id.overallPerformanceDivision);
        TextView correctTotal = findViewById(R.id.correctTotal);
        TextView incorrectTotal = findViewById(R.id.incorrectTotal);
        TextView overallPerformanceTotal = findViewById(R.id.overallPerformanceTotal);

        // Update the TextViews
        correctAddition.setText(String.valueOf(mathCategory.correctAddition));
        incorrectAddition.setText(String.valueOf(mathCategory.incorrectAddition));
        correctSubtraction.setText(String.valueOf(mathCategory.correctSubtraction));
        incorrectSubtraction.setText(String.valueOf(mathCategory.incorrectSubtraction));
        correctMultiplication.setText(String.valueOf(mathCategory.correctMultiplication));
        incorrectMultiplication.setText(String.valueOf(mathCategory.incorrectMultiplication));
        correctDivision.setText(String.valueOf(mathCategory.correctDivision));
        incorrectDivision.setText(String.valueOf(mathCategory.incorrectDivision));

        //Calculate totals
        int totalCorrect = mathCategory.correctAddition + mathCategory.correctSubtraction + mathCategory.correctMultiplication + mathCategory.correctDivision;
        int totalIncorrect = mathCategory.incorrectAddition + mathCategory.incorrectSubtraction + mathCategory.incorrectMultiplication + mathCategory.incorrectDivision;

        // Set total values to TextViews
        correctTotal.setText(String.valueOf(totalCorrect));
        incorrectTotal.setText(String.valueOf(totalIncorrect));

        // Calculate and update overall performance
        int totalQuestions = totalCorrect + totalIncorrect;
        int totalAddition = mathCategory.correctAddition + mathCategory.incorrectAddition;
        int totalSubtraction = mathCategory.correctSubtraction + mathCategory.incorrectSubtraction;
        int totalMultiplication = mathCategory.correctMultiplication + mathCategory.incorrectMultiplication;
        int totalDivision = mathCategory.correctDivision + mathCategory.incorrectDivision;

        double overallPercentageTotal = totalQuestions > 0 ? (double) totalCorrect / totalQuestions * 100 : 0;
        overallPerformanceTotal.setText(String.format(Locale.getDefault(), "%.1f%%", overallPercentageTotal));

        double overallPercentageAddition = totalAddition > 0 ? (double) mathCategory.correctAddition / totalAddition * 100 : 0;
        overallPerformanceAddition.setText(String.format(Locale.getDefault(), "%.1f%%", overallPercentageAddition));

        double overallPercentageSubtraction = totalSubtraction > 0 ? (double) mathCategory.correctSubtraction / totalSubtraction * 100 : 0;
        overallPerformanceSubtraction.setText(String.format(Locale.getDefault(), "%.1f%%", overallPercentageSubtraction));

        double overallPercentageMultiplication = totalMultiplication > 0 ? (double) mathCategory.correctMultiplication / totalMultiplication * 100 : 0;
        overallPerformanceMultiplication.setText(String.format(Locale.getDefault(), "%.1f%%", overallPercentageMultiplication));

        double overallPercentageDivision = totalDivision > 0 ? (double) mathCategory.correctDivision / totalDivision * 100 : 0;
        overallPerformanceDivision.setText(String.format(Locale.getDefault(), "%.1f%%", overallPercentageDivision));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }
}