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
public class ManageStudentmarksController implements Initializable {

    private @FXML
    TableView<StudentCOOP> studentcooptable2;
    private @FXML
    TableColumn zidcol;
    private @FXML
    TableColumn fnamecol;
    private @FXML
    TableColumn lnamecol;
    private @FXML
    TableColumn semcol;
    private @FXML
    TableColumn markcol;
    private @FXML
    TableColumn wamcol;
    private @FXML
    TableColumn subcol;
    private @FXML
    Button add;
    @FXML
    Button viewedit;
    @FXML
    Button logout;
    @FXML
    Button prevr;
    @FXML
    Button backhome;

    private static String string2;

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
        semcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, Integer>("sEmestercompleted")
        );
        markcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, Integer>("mArk")
        );
        wamcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, Integer>("wAm")
        );
        subcol.setCellValueFactory(
                new PropertyValueFactory<StudentCOOP, String>("sUbject")
        );

        studentcooptable2.setItems(null);
        studentcooptable2.setItems(studentcooplist);

        viewedit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (studentcooptable2.getSelectionModel().isEmpty() != true) {

                    setString(studentcooptable2.getSelectionModel().getSelectedItem().getZID());
                    handleTransitionButton(e, "conS.png", "loginS.png", "Addviewandeditstudentgrades.fxml", "Student Grades");
                    System.out.println("openinglad");
                } else {
                    //alert pls

                }
            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                deleteTemp();
                handleTransitionButton(e, "studinfoS.png", "loginS.png", "LoginPage.fxml", "Login");
            }
        });

        prevr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "studinfoS.png", "placementS.png", "workplacementDash.fxml", "Placement Dashboard");
            }
        });
        backhome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "studinfoS.png", "dashS.png", "Dashboard.fxml", "Dashboard");
            }
        });
    }

    public static String getString() {
        return string2;

    }

    public static void setString(String string2) {
        ManageStudentmarksController.string2 = string2;
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
