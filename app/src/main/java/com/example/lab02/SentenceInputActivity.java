package com.example.lab02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SentenceInputActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT_SENTENCE = "com.example.lab02.EXTRA_RESULT_SENTENCE";
    public static final String EXTRA_INITIAL_SENTENCE = "com.example.lab02.EXTRA_INITIAL_SENTENCE";
    private EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sentence_input);

        String initialText = getIntent().getStringExtra(EXTRA_INITIAL_SENTENCE);
        textInput = findViewById(R.id.input_sentence);
        if (initialText != null && !initialText.trim().isEmpty()) {
            textInput.setText(initialText);
            textInput.setSelection(initialText.length());
        }

        Button submitBtn = findViewById(R.id.btn_submit_sentence);
        submitBtn.setOnClickListener(v -> {
            String sentence = textInput.getText().toString();
            Intent data = new Intent();
            data.putExtra(EXTRA_RESULT_SENTENCE, sentence);
            setResult(RESULT_OK, data);
            finish();
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom code here
                Intent data = new Intent();
                var sentence = textInput.getText().toString();
                data.putExtra(EXTRA_RESULT_SENTENCE, sentence);
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
        Intent intent = new Intent(context, SentenceInputActivity.class);
        if (initialText != null) {
            intent.putExtra(EXTRA_INITIAL_SENTENCE, initialText);
        }
        return intent;
    }
}