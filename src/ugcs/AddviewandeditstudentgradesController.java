/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ugcs.Model.StudentCOOP;
import ugcs.Queries.FileQueries;
import ugcs.Queries.StudentCOOPQueries;

/**
 *
 * @author Peterrpancakes
 */
public class AddviewandeditstudentgradesController implements Initializable {

    private @FXML
    Button logout;
    private @FXML
    Button prevr;
    private @FXML
    Button rDash;
    private @FXML
    TextField ztext;
    private @FXML
    TextField ftext;
    private @FXML
    TextField ltext;
    private @FXML
    TextField subtext;
    private @FXML
    TextField marktext;
    private @FXML
    TextField wamtext;
    private @FXML
    TextField semtext;
    @FXML
    Label FirstName;

    private @FXML
    TextArea notesarea;
    private @FXML
    Button submit1;

    StudentCOOPQueries scq1 = new StudentCOOPQueries();
    FileQueries fq = new FileQueries();
    ObservableList<StudentCOOP> studentcooplist = FXCollections.observableArrayList(scq1.getStudentCOOP());

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FirstName.setText(fq.readName());
        for (StudentCOOP sc : studentcooplist) {
            if (sc.getZID().equals(ManageStudentmarksController.getString())) {

                ztext.setText(sc.getZID());
                ftext.setText(sc.getFName());
                ltext.setText(sc.getLName());
                subtext.setText(sc.getSUbject());
                marktext.setText(sc.getMArk().toString());
                wamtext.setText(sc.getWAm().toString());
                semtext.setText(sc.getSEmestercompleted().toString());
                notesarea.setText(sc.getNOtes());
            }
        }

        //String zID, String fName, String lName, String gEnder, String aDdress, 
        //    Integer cOntact, String eMail, String wOrkemail, String nOtes,
        // String sUbject, Integer sEmestercompleted, Integer mArk, Integer wAm
        submit1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (StudentCOOP sc : studentcooplist) {
                    if (sc.getZID().equals(ztext.getText())) {
                        System.out.println("updating coop marks");
                        StudentCOOP update1 = new StudentCOOP(ztext.getText(), ftext.getText(), ltext.getText(), sc.getGEnder(),
                                sc.getADdress(), sc.getCOntact(), sc.getEMail(), sc.getWOrkemail(),
                                notesarea.getText(), subtext.getText(), Integer.parseInt(semtext.getText()),
                                Double.parseDouble(marktext.getText()), Double.parseDouble(wamtext.getText()));
                        StudentCOOPQueries scq2 = new StudentCOOPQueries();

                        scq2.updateStudentCOOP(update1);
                        System.out.println("student coop updated with grades");
                        handleTransitionButton(e, "conS.png", "calendarS.png", "ManageStudentmarks.fxml", "Dashboard");

                    }
                }
            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                fq.deleteTemp();
                handleTransitionButton(e, "conS.png", "loginS.png", "LoginPage.fxml", "Login");
            }
        });
        
        rDash.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                fq.deleteTemp();
                handleTransitionButton(e, "conS.png", "loginS.png", "mystudentsDash.fxml", "My Students Dashboard");
            }
        });

        prevr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "conS.png", "calendarS.png", "ManageStudentmarks.fxml", "Dashboard");
            }
        });
    }

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);

    }

    private void handleTransitionKey(KeyEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenKey(e, a, b, c, d);

    }

    private void animateLabel(PathTransition pt, Label node) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.animateError(pt, node);
    }
}
