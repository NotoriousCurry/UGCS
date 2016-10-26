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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import ugcs.Model.Student;
import ugcs.Model.StudentCOOP;
import ugcs.Queries.StudentCOOPQueries;

/**
 *
 * @author Peterrpancakes
 */
public class ManageStudentController implements Initializable {

    private @FXML
    TableView<StudentCOOP> studentcooptable;
    private @FXML
    TableColumn zidcol;
    private @FXML
    TableColumn fnamecol;
    private @FXML
    TableColumn lnamecol;
    private @FXML
    TableColumn addresscol;
    private @FXML
    TableColumn contactcol;
    private @FXML
    TableColumn gendercol;
    private @FXML
    TableColumn emailcol;
    private @FXML
    TableColumn privemailcol;
    private @FXML
    TableColumn notescol;
    @FXML
    Button add;
    @FXML
    Button viewedit;
    @FXML
    Button logout;
    @FXML
    Button prevr;
    @FXML
    Button backhome;

    private static String check = "false";
    private static String string1;

    StudentCOOPQueries scq = new StudentCOOPQueries();
    ObservableList<StudentCOOP> studentcooplist = FXCollections.observableArrayList(scq.getStudentCOOP());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(studentcooplist);
        zidcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("zID")
        );
        fnamecol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("fName")
        );
        lnamecol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("lName")
        );
        gendercol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("gEnder")
        );
        addresscol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("aDdress")
        );
        contactcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, Integer>("cOntact")
        );
        emailcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("eMail")
        );
        privemailcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("wOrkemail")
        );
        notescol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("nOtes")
        );

        studentcooptable.setItems(null);
        studentcooptable.setItems(studentcooplist);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setCheck("false");
                //   StudentAndConsController.setselected(studentcooptable.getSelectionModel().getSelectedItem().getzID());
                //transition to screen with autofilled info...
                handleTransitionButton(e, "conS.png", "loginS.png", "Addviewandeditstudentinfo.fxml", "Student Info");
                System.out.println("false");

            }
        });
        viewedit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (studentcooptable.getSelectionModel().isEmpty() != true) {
                    setCheck("true");
                    setString(studentcooptable.getSelectionModel().getSelectedItem().getZID());
                    handleTransitionButton(e, "conS.png", "loginS.png", "Addviewandeditstudentinfo.fxml", "Student Info");
                    System.out.println("true");
                } else {
                    //alert pls

                }
            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                deleteTemp();
                handleTransitionButton(e, "manageS.png", "loginS.png", "LoginPage.fxml", "Login");
            }
        });

        prevr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "manageS.png", "coopdashS.png", "mystudentsDash.fxml", "Dashboard");
            }
        });
        backhome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "manageS.png", "dashS.png", "Dashboard.fxml", "Dashboard");
            }
        });
    }

    public static String getCheck() {
        return check;

    }

    public static void setCheck(String check) {
        ManageStudentController.check = check;
    }

    public static String getString() {
        return string1;

    }

    public static void setString(String string1) {
        ManageStudentController.string1 = string1;
    }

    private void deleteTemp() {
        try {
            File file = new File("temp.txt");
            if (file.delete()) {
                System.out.println(file.getName() + " is Deleted");
            } else {
                System.out.println("Delete operation failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
