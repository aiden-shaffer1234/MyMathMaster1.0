package com.zybooks.mymathmaster;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import android.util.Log;

import com.zybooks.mymathmaster.model.MathCategory;
import com.zybooks.mymathmaster.repo.MathCategoryRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
can we add a fragment of a summary class to this to under the preferences
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private MathCategoryRepository mathCategoryRepository;
        private static final String TAG = "SettingsFragment"; // Tag for log messages

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            SwitchPreferenceCompat themePref = findPreference("dark_theme");
            if (themePref != null) {
                themePref.setOnPreferenceChangeListener((preference, newValue) -> {

                    // Turn on or off night mode
                    if ((Boolean) newValue) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }

                    return true;
                });
            }

            mathCategoryRepository = new MathCategoryRepository(requireContext().getApplicationContext());

            Preference reset = findPreference("reset");
            if (reset != null) {
                reset.setOnPreferenceClickListener(preference -> {
                    Log.d(TAG, "Reset button clicked");
                    resetScoresInDatabase(); //reset the dataBase to all 0's and 00.0%
                    return true;
                });
            }


        }

        private void resetScoresInDatabase() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Log.d(TAG, "Executing database reset operation");

                // Fetch the existing record. Assuming the ID is 1 for simplicity.
                MathCategory mathCategory = mathCategoryRepository.getMathCategoryById(1);

                if (mathCategory != null) {
                    // Reset all scores to zero
                    mathCategory.correctAddition = 0;
                    mathCategory.incorrectAddition = 0;
                    mathCategory.correctSubtraction = 0;
                    mathCategory.incorrectSubtraction = 0;
                    mathCategory.correctMultiplication = 0;
                    mathCategory.incorrectMultiplication = 0;
                    mathCategory.correctDivision = 0;
                    mathCategory.incorrectDivision = 0;

                    // Save the updated MathCategory object to the database
                    mathCategoryRepository.insertOrUpdate(mathCategory);
                } else {
                    Log.d(TAG, "No existing record found to reset");
                }

                Log.d(TAG, "Database reset operation completed");
            });
            executor.shutdown();
        }
    }
}