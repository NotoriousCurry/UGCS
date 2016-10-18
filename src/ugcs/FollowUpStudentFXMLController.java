package ugcs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ugcs.Model.Student;
import ugcs.Queries.StudentFollowQueries;
import ugcs.Queries.StudentQueries;

/**
 * FXML Controller class
 *
 * @author Peterrpancakes
 */
public class FollowUpStudentFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    TableView<Student> studentfollowtable;
    @FXML
    TableColumn zidcol1;
    @FXML
    TableColumn fnamecol1;
    @FXML
    TableColumn lnamecol1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
StudentFollowQueries sfq = new StudentFollowQueries();

        ObservableList<Student> studentlist = FXCollections.observableArrayList(sfq.getStudents());
        System.out.println("sss " + studentlist );
        zidcol1.setCellValueFactory(
                new PropertyValueFactory<Student, String>("zID")
        );
        fnamecol1.setCellValueFactory(
                new PropertyValueFactory<Student, String>("fName")
        );
        lnamecol1.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lName")
        );       
    
    studentfollowtable.setItems(null);
        studentfollowtable.setItems(studentlist);

}
}