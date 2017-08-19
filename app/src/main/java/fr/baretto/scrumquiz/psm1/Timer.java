package fr.baretto.scrumquiz.psm1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by mehdi on 19/08/17.
 */

public class Timer {

    private static Timer INSTANCE;

   private Date startDate;
    private Date endDate;

    private Timer(){

    }

    public static Timer getInstance(){
        if(INSTANCE==null){
            INSTANCE = new Timer();
        }
        return INSTANCE;
    }
    public void start(){
        startDate = new Date();
    }

    public void stop(){
        endDate = new Date();
    }

    public void reset(){
        startDate = null;
        endDate = null;
    }

    public String time(){
        long diff = endDate.getTime() - startDate.getTime();//as given

        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        StringBuilder result = new StringBuilder();
        if(minutes<10){
            result.append("0");
        }
        result.append(minutes);
        result.append(":");
        if(seconds<10){
            result.append("0");
        }
        result.append(seconds);
        return result.toString();
    }
}
