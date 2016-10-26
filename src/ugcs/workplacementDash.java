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
import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javax.mail.*;
import javax.mail.internet.*;
import ugcs.Queries.FileQueries;

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
    @FXML
    Button rDash;
    @FXML
    Label FirstName;

    FileQueries fq = new FileQueries();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FirstName.setText(fq.readName());

        studentinfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "placementS.png", "studinfoS.png", "ManageStudentmarks.fxml", "COOP Students");
            }
        });

        placementinfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Page Coming Soon");
                alert.setHeaderText("Page Coming Soon");
                alert.setHeaderText(null);
                alert.setContentText("This page isnt quite ready for release just yet. \nWe appreciate your patience.");
                alert.showAndWait();
                //handleTransitionButton(e, "placementS.png", "jobS.png", "jobinfo.fxml", "Placement Dashboard");

            }
        });
        rDash.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                handleTransitionButton(e, "placementS.png", "coopdashS.png", "mystudentsDash.fxml", "Placement Dashboard");

            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                fq.deleteTemp();
                handleTransitionButton(e, "placementS.png", "loginS.png", "LoginPage.fxml", "Login");

            }
        });

        sendemail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String ssl = "javax.net.ssl.SSLSocketFactory";
                Properties prop = System.getProperties();
                prop.setProperty("mail.smtp.host", "smtp.gmail.com");
                prop.setProperty("mail.smtp.socketFactory.class", ssl);
                prop.setProperty("mail.smtp.socketFactory.fallback", "false");
                prop.setProperty("mail.smtp.port", "465");
                prop.setProperty("mail.smtp.socketFactory.port", "465");
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.debug", "true");
                prop.put("mail.store.protocol", "pop3");
                prop.put("mail.transport.protocol", "smtp");
                String username = "ugcsunsw@gmail.com";//
                String password = "ugcsApplication";
                try {
                    Session sesh = Session.getDefaultInstance(prop,
                            new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

                    // -- Create a new message --
                    Message msg = new MimeMessage(sesh);

                    // -- Set the FROM and TO fields --
                    msg.setFrom(new InternetAddress("ugcsunsw@gmail.com"));
                    msg.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse("sgaheer@hotmail.com", false));
                    msg.setSubject("CO-OP Preference Form from " + fq.readName());
                    msg.setText("This email contains the preference form for your next placement." + System.lineSeparator()
                            + "Sent from the UGCS Application");
                    msg.setSentDate(new Date());
                    Transport.send(msg);
                    System.out.println("Email Sent");
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);
    }

}
