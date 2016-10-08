/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import ugcs.Audio.AudioMethods;

/**
 *
 * @author sgahe
 */
public class DashboardController implements Initializable {

    @FXML
    Button myconsultationbutton;
    @FXML
    Button searchbutton;
    @FXML
    Button profilebutton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myconsultationbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "dashS.png", "calendarS.png", "CalendarView.fxml", "Consultations");
            }
        });
    }

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);
    }

    private void handleAudio(String file) {
        AudioMethods am = new AudioMethods();

        am.playAudio(file);
    }
}
