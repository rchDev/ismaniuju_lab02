package com.example.lab02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class WordCountingActivity extends AppCompatActivity {
    public static final String EXTRA_RESULT_SENTENCE = "com.example.lab02.EXTRA_RESULT_SENTENCE";
    public static final String EXTRA_INITIAL_SENTENCE = "com.example.lab02.EXTRA_INITIAL_SENTENCE";

    private String updatedSentence = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_word_counting);

        String initialText = getIntent().getStringExtra(EXTRA_INITIAL_SENTENCE);
        updatedSentence = initialText;

        TextView textView = findViewById(R.id.text_word_counting_results);
        if (initialText != null && !initialText.trim().isEmpty()) {
            textView.setText(initialText);
        }

        Button countWordsBtn = findViewById(R.id.btn_count_words);

        countWordsBtn.setOnClickListener(v -> {
            String sentence = textView.getText().toString();
            var wordCount = sentence.trim().split("\\s+").length;
            updatedSentence = "Sakinyje: \""  + sentence + "\" esti " + wordCount + " žodžiai";
            textView.setText(updatedSentence);
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom code here
                Intent data = new Intent();
                data.putExtra(EXTRA_RESULT_SENTENCE, updatedSentence);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static Intent newIntent(Context context, @Nullable String initialText) {
        Intent intent = new Intent(context, WordCountingActivity.class);
        if (initialText != null) {
            intent.putExtra(EXTRA_INITIAL_SENTENCE, initialText);
        }
        return intent;
    }
}

