/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Alert;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import ugcs.Model.Consultation;
import ugcs.Queries.ConsultationQueries;

/**
 *
 * @author sgahe
 */
public class AlertMethods {

    // 12 Hour timer for auto email generation
    private final int TIMER_LENGTH = 43200;

    public AlertMethods() {

    }
    
    public void checkConsToday() {
        ConsultationQueries cq = new ConsultationQueries();
        List<Consultation> cons = cq.getConsultations();
        
    }

    public void startTimer(String file) {
        try {
            new Thread() {
                public void run() {
                    int x = 0;
                    while (x <= 100) {
                        Timer timer = new Timer();
                        timer.schedule(new EmailTask(), TIMER_LENGTH * 1000);
                        timer.cancel();
                    }
                }
            }.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    class EmailTask extends TimerTask {
        public void run() {
            // DO SOMETHING EVERY 12 
        }
    }
}
