package com.suhun.gamecollections.game_object;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.suhun.gamecollections.databinding.ActivityGuessAbactivityBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GuessAB {
    public String tag = GuessAB.class.getSimpleName();
    private String answer;
    private int gameLen;
    private int tempWhich;
    private int guessCounter;
    private int guessLimit;

    public GuessAB(){
        gameLen = 4;
        guessLimit = 10;
        setAnswer();
        Log.d(tag, "GameAB was born!Default len is " + gameLen + "default guessAB answer is " + answer);
    }

    private void initGame(ActivityGuessAbactivityBinding binding){
        Log.d(tag, "---init game----");
        this.guessCounter = 0;
        setAnswer();
        binding.userInput.setText("");
        binding.log.setText("");
    }

    public int getGameLen(){
        return gameLen;
    }

    public void setGameLen(int gameLen){
        this.gameLen = gameLen;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(){
        this.answer = createAnswer();
        Log.d(tag, "The create guessAB answer is " + answer);
    }
    private String createAnswer(){
        HashSet<Integer> numSet = new HashSet<Integer>();
        while(numSet.size()<gameLen){
            numSet.add(new Random().nextInt(10));
        }
        List<Integer> numList = new ArrayList<Integer>();
        for(Integer num:numSet){
            numList.add(num);
        }
        Collections.shuffle(numList);
        StringBuffer stringBuffer = new StringBuffer();
        for(Integer num:numList){
            stringBuffer.append(num);
        }
        return stringBuffer.toString();
    }

    public void checkAnswerResult(ActivityGuessAbactivityBinding binding, Resources resources, Context context){
        String userInput = binding.userInput.getText().toString();
        if(userInput.length()!=gameLen){
            binding.userInput.setText("");
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Please input " + gameLen +"digits!!!")
                    .setPositiveButton("Ok", null)
                    .create()
                    .show();

        }else{
            String result = checkAnswer(userInput);
            String message;
            DialogInterface.OnClickListener listenertPositive = null;

            guessCounter++;
            if(guessCounter<guessLimit){
                if(result.equals(gameLen+"A0B")){
                    message = "Perfect!You got it!!!Reset Game!!!";
                    listenertPositive = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            initGame(binding);
                        }
                    };
                }else{
                    message = userInput + ":" + result + "\t " + guessCounter + "times \n";
                    binding.log.append(message);
                    binding.userInput.setText("");
                }
            }else{
                message = "You have already guess " + guessLimit +"times!!!Game over!!!";
                listenertPositive = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initGame(binding);
                    }
                };
            }
            showGuessAlertdialog(context, resources, message, listenertPositive);
        }
    }

    private String checkAnswer(String userInput){
        int a=0,b=0;

        for(int i=0;i<userInput.length();i++){
            if(this.answer.charAt(i)==userInput.charAt(i)){
                a++;
            }else if(this.answer.contains(""+ userInput.charAt(i))){
                b++;
            }
        }
        return String.format("%dA%dB", a, b);
    }

    public void showGuessAlertdialog(Context context, Resources resources , String message, DialogInterface.OnClickListener listenertPositive) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("Ok", listenertPositive)
                .setNeutralButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();

    }

    public void showSettingAlertdialog(Context context, Resources resources, ActivityGuessAbactivityBinding binding){
        String[] items = {"2", "3", "4", "5"};
        int checkedItem = 0;

        for(int i = 0;i<items.length;i++){
            if(Integer.valueOf(items[i])==gameLen){
                checkedItem = i;
                Log.d(tag, "defalut chice is " + checkedItem);
            }
        }
        new AlertDialog.Builder(context)
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(tag, "Setting choice" + which);
                        tempWhich = which;
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameLen = Integer.valueOf(items[tempWhich]);
                        initGame(binding);
                    }
                })
                .setNeutralButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();
    }

    public void showResetAlertDialog(Context context, Resources resources, ActivityGuessAbactivityBinding binding){
        new AlertDialog.Builder(context)
                .setTitle("Restart")
                .setMessage("Are you sure?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initGame(binding);
                    }
                })
                .setNeutralButton("Cancel", null)
                .create()
                .show();;
    }
}
