/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ugcs.Model.Student;
import ugcs.Model.StudentCOOP;
import ugcs.Queries.StudentCOOPQueries;

/**
 *
 * @author Peterrpancakes
 */
public class CoopStudentController implements Initializable  {
    
    
    
        @FXML
    TableView<StudentCOOP> studentcooptable;
    @FXML
    TableColumn zidcol;
    @FXML
    TableColumn fnamecol;
    @FXML
    TableColumn lnamecol;
    @FXML
    TableColumn gendercol;
    @FXML
    TableColumn subjectcol;
    @FXML
    TableColumn markcol;
    @FXML
    TableColumn semcol;
    @FXML
    Button addviewedit;
    
    

    
    
    
    StudentCOOPQueries scq = new StudentCOOPQueries();
   ObservableList<StudentCOOP> studentcooplist = FXCollections.observableArrayList(scq.getStudentCOOP());

     @Override
    public void initialize(URL url, ResourceBundle rb) {
    
     zidcol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("zID")
        );
        fnamecol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("fName")
        );
        lnamecol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lName")
        );
        gendercol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("gEnder")
        );
        subjectcol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("sUbject")
        );
         markcol.setCellValueFactory(
                new PropertyValueFactory<Student, Integer>("mArk")
        );
        semcol.setCellValueFactory(
                new PropertyValueFactory<Student, Integer>("sEmestercompleted")
        );
        
         studentcooptable.setItems(null);
        studentcooptable.setItems(studentcooplist);
       

        addviewedit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               
            if(studentcooptable.getSelectionModel().isEmpty() == true){
                //transition to screen with unfilled info...
                
                
            }
            else{
            
              StudentAndConsController.setselected(studentcooptable.getSelectionModel().getSelectedItem().getzID());
            //transition to screen with autofilled info...
            
            }
                
                
                
            }
    
    
    
    
    
});
}
}
