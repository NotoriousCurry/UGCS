/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ugcs.Alert.AlertMethods;
import ugcs.Audio.AudioMethods;

/**
 *
 * @author sgahe
 */
public class DashboardController implements Initializable {

    @FXML
    Button myconsultationbutton;
    @FXML
    Button coopButton;
    @FXML
    Button profilebutton;
    @FXML
    ImageView coopImage;
    @FXML
    Label coopLabel;
    @FXML
    Label todLabel;
    @FXML
    Label tmrwLabel;
    @FXML
    Label todAlert;
    @FXML
    Label tmrwAlert;

    private FadeTransition fadeI = new FadeTransition();
    private FadeTransition fadeL = new FadeTransition();
    private FadeTransition ft = new FadeTransition();
    private FadeTransition ft1 = new FadeTransition();
    private FadeTransition ft2 = new FadeTransition();
    private FadeTransition ft3 = new FadeTransition();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        alert();
        String userType = readType();

        if (userType.equals("COOP")) {
            coopButton.setVisible(true);
            coopLabel.setVisible(true);
            coopImage.setVisible(true);
            animateButton(fadeI, fadeL, coopImage, coopLabel);
        }

        myconsultationbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "dashS.png", "calendarS.png", "CalendarView.fxml", "Consultations");
            }
        });

        coopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("COOP STUFFF PLZ");
            }
        });

    }

    private String readType() {
        String fName = "temp.txt";
        String line = null;
        String type = "";

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            type = sect[2];

            bReader.close();
            System.out.println(type);

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }
        return type;
    }

    private void alert() {
        AlertMethods am = new AlertMethods();
        am.checkConsToday();
        String todCons = am.readTod();
        String tmrwCons = am.readTmrw();

        if (Integer.parseInt(todCons) >= 1) {
            System.out.println("YOU HAVE " + todCons + " APPOINTMENTS TODAY");
            todLabel.setText(todCons);
            todAlert.setVisible(true);
            animAlert(ft, todAlert);
            todLabel.setVisible(true);
            animAlert(ft1, todLabel);
            handleAudio("beep_short_on.wav");
        }

        if (Integer.parseInt(tmrwCons) >= 1) {
            System.out.println("YOU HAVE " + tmrwCons + " APPOINTMENTS TMRW");
            tmrwLabel.setText(tmrwCons);
            tmrwAlert.setVisible(true);
            animAlert(ft2, tmrwAlert);
            tmrwLabel.setVisible(true);
            animAlert(ft3, tmrwLabel);
        }
    }

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);
    }

    private void handleAudio(String file) {
        AudioMethods am = new AudioMethods();

        am.playAudio(file);
    }

    private void animateButton(FadeTransition fadeL, FadeTransition fadeI, ImageView i, Label l) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.animateFade(fadeI, fadeL, i, l);
    }

    private void animAlert(FadeTransition ft, Label l) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.animateAlert(ft, l);
    }

}
