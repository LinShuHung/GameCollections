package com.suhun.gamecollections.game_object;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

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

    public GuessAB(){
        gameLen = 4;
        setAnswer();
        Log.d(tag, "GameAB was born!Default len is " + gameLen + "default guessAB answer is " + answer);
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

    private void initGame(){
        this.guessCounter = 0;
        setAnswer();
    }

    public void showSettingAlertdialog(Context context){
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
                        initGame();
                    }
                })
                .setNeutralButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();
    }
}
