/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Alert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import ugcs.Model.Consultation;
import ugcs.Queries.ConsultationQueries;
import ugcs.Queries.UgcQueries;

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
        int consTod = 0;
        int consTmrw = 0;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today = df.format(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        String tmrw = df.format(date);

        for (Consultation c : cons) {
            System.out.println(c.getDate1());
            if (today.equals(c.getDate1())) {
                consTod = consTod + 1;
            }
            if (tmrw.equals(c.getDate1())) {
                consTmrw = consTmrw + 1;
            }
        }

        String x = Integer.toString(consTod);
        String y = Integer.toString(consTmrw);

        updateTemp(x, y);

    }

    private void updateTemp(String tod, String tmrw) {
        String fName = "temp.txt";
        String line = null;
        String name = "";
        String pass = "";
        String type = "";
        String cTod = tod;
        String cTmrw = tmrw;

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            name = sect[0];
            pass = sect[1];
            type = sect[2];

            bReader.close();
            System.out.println(type);

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }

        try {
            FileWriter fileWriter = new FileWriter(fName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(name + "," + pass + "," + type + "," + cTod + "," + cTmrw);

            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing file '" + fName + "'");
            ex.printStackTrace();
        }
    }

    public String readTod() {
        String fName = "temp.txt";
        String line = null;
        String tod = "";

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            tod = sect[3];

            bReader.close();
            System.out.println(tod);

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }
        return tod;
    }

    public String readTmrw() {
        String fName = "temp.txt";
        String line = null;
        String tmrw = "";

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            tmrw = sect[4];

            bReader.close();
            System.out.println(tmrw);

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }
        return tmrw;
    }

    // CODE FOR AUTOMATED EMAIL GENERATION
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
