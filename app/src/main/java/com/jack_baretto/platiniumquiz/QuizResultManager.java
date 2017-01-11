package com.jack_baretto.platiniumquiz;

/**
 * Created by WORK on 09/01/2017.
 */

public class QuizResultManager {
    int test = 0;

    private static QuizResultManager INSTANCE = null;

   private QuizResultManager(){

    }
    public static QuizResultManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QuizResultManager();
        }
        return INSTANCE;
    }

    public void add(int toAdd){
        test= test +toAdd;
    }

    public int getTest() {
        return test;
    }
}

