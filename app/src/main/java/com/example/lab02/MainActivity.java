package com.example.lab02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView activityResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        activityResults = findViewById(R.id.txt_activity_results);

        String initialText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (initialText != null && !initialText.trim().isEmpty()) {
            activityResults.setText(initialText);
        }

        var sentenceInputLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String sentence = result.getData()
                                .getStringExtra(SentenceInputActivity.EXTRA_RESULT_SENTENCE);
                        // 👈 Use the returned sentence here
                        Toast.makeText(this, "Sentence Input: " + sentence, Toast.LENGTH_SHORT).show();
                        activityResults.setText(sentence);
                    }
                });

        final Button enterTextBtn = findViewById(R.id.btn_enter_sentence);
        enterTextBtn.setOnClickListener(v -> {
            String sentence = activityResults.getText().toString();
            Intent intent = SentenceInputActivity.newIntent(this, sentence);
            sentenceInputLauncher.launch(intent);
        });

        var wordCounterLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String sentence = result.getData()
                                .getStringExtra(WordCountingActivity.EXTRA_RESULT_SENTENCE);
                        // 👈 Use the returned sentence here
                        Toast.makeText(this, "Word Counter: " + sentence, Toast.LENGTH_SHORT).show();
                        activityResults.setText(sentence);
                    }
                });

        final Button countWordsBtn = findViewById(R.id.btn_count_words);
        countWordsBtn.setOnClickListener(v -> {
            String sentence = activityResults.getText().toString();
            Intent intent = WordCountingActivity.newIntent(this, sentence);
            wordCounterLauncher.launch(intent);
        });

        final Button sendTextBtn = findViewById(R.id.btn_send_text);
        sendTextBtn.setOnClickListener(v -> {
            String sentence = activityResults.getText().toString();

            Intent sendIntent = new Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, sentence)
                    .setType("text/plain");

            startActivity(Intent.createChooser(sendIntent, "Share sentence with"));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}