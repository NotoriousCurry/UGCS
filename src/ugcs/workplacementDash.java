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


public class workplacementDash implements Initializable {

    
    @FXML
    Button logout;
    @FXML
    Button prevr;
    @FXML
    Button studentinfo;
    @FXML
    Button placementinfo;
     @FXML
    Button sendemail;
  


    @Override
    public void initialize(URL url, ResourceBundle rb) {

     

        studentinfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "dashS.png", "calendarS.png", "ManageStudentmarks.fxml", "COOP Students");
            }
        });

        placementinfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
              handleTransitionButton(e, "dashS.png", "calendarS.png", "jobinfo.fxml", "Placement Dashboard");

            }
        });
        
         sendemail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
//HOW TO SEND EMAIL
            }
        });

    }


  

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);
    }



}
