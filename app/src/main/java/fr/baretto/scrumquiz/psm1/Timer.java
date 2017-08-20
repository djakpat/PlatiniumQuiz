package fr.baretto.scrumquiz.psm1;

import android.app.Activity;
import android.os.SystemClock;
import android.widget.Chronometer;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by mehdi on 19/08/17.
 */

public class Timer extends Activity{
private final java.util.Timer timer= new java.util.Timer();
    private static Timer INSTANCE;

   private Date startDate;
    private Date endDate;
private long elapsedTime;
    private long currentTime;
    public boolean cont;

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
        cont=true;
        launch();
    }

    public void stop(){
        endDate = new Date();
        cont=false;
    }

    public void reset(){
        startDate = null;
        endDate = null;
        cont=false;
        elapsedTime=0;

    }

    public String time(){
       return  String.format("%d'%d''",
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime))
        );
    }


    private void launch() {
        timer.schedule(new TimerTask(){

            @Override
            public void run() {

                Timer.this.runOnUiThread(new Runnable() {
                    public void run() {
                        if(cont) {
                            Timer.this.currentTime = System.currentTimeMillis();
                            Timer.this.elapsedTime = Timer.this.currentTime - Timer.this.startDate.getTime();

                        }
                    }
                });
            }
        }, 0, 100);
    }
}
