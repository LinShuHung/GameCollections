package com.suhun.gamecollections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.suhun.gamecollections.databinding.ActivityGuessAbactivityBinding;
import com.suhun.gamecollections.game_object.GuessAB;

public class GuessABActivity extends AppCompatActivity {
    public String tag = GuessABActivity.class.getSimpleName();
    private ActivityGuessAbactivityBinding binding;
    public GuessAB guessAB = new GuessAB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuessAbactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessAB.checkAnswerResult(binding, getResources(), GuessABActivity.this);
            }
        });

        binding.restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessAB.showResetAlertDialog(GuessABActivity.this, getResources(), binding);
            }
        });
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessAB.showSettingAlertdialog(GuessABActivity.this, getResources(), binding);
            }
        });
    }
}