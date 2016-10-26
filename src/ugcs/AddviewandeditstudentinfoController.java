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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import ugcs.Model.Student;
import ugcs.Model.StudentCOOP;
import ugcs.Queries.StudentCOOPQueries;

/**
 *
 * @author Peterrpancakes
 */
public class AddviewandeditstudentinfoController implements Initializable  {
    
    
    
    @FXML
    Button submittoadd;
    
      @FXML
    Button logout;
       @FXML
    Button prevr;
    private
     @FXML
    TextField ztext;
    private @FXML
    TextField ftext;
    private @FXML
    TextField ltext;
    private @FXML
    TextField etext;
     private @FXML
    TextField atext;
    private @FXML
    TextField wtext;
    private @FXML
    TextField gtext;
     
     private @FXML
    TextField ctext;
      private @FXML
    TextArea notesarea;
    @FXML
    Button submittoedit;

    
    
    
    StudentCOOPQueries scq = new StudentCOOPQueries();
   ObservableList<StudentCOOP> studentcooplist = FXCollections.observableArrayList(scq.getStudentCOOP());

     @Override
    public void initialize(URL url, ResourceBundle rb) {wtext.setText(null);
        if(ManageStudentController.getCheck().equals("false")){
        
            System.out.println("add screen");
        }
        if(ManageStudentController.getCheck().equals("true")){
            System.out.println("edit/view screen");
            submittoedit.setVisible(true);
            submittoadd.setVisible(false);
            for(StudentCOOP sc : studentcooplist){
                if(sc.getZID().equals(ManageStudentController.getString())){
            
        ztext.setText(sc.getZID());
        ftext.setText(sc.getFName());
        ltext.setText(sc.getLName());
        etext.setText(sc.getEMail());
        atext.setText(sc.getADdress());
        wtext.setText(sc.getWOrkemail());
        gtext.setText(sc.getGEnder());
        ctext.setText(sc.getCOntact().toString());
        notesarea.setText(sc.getNOtes());
        ManageStudentController.setCheck("false");
                }
                }
        }
        //String zID, String fName, String lName, String gEnder, String aDdress, 
          //    Integer cOntact, String eMail, String wOrkemail, String nOtes,
           // String sUbject, Integer sEmestercompleted, Double mArk, Double wAm
        
          submittoedit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 for(StudentCOOP sc : studentcooplist){
                if(sc.getZID().equals(ztext.getText())){
                    System.out.println("updating coop stu");
                StudentCOOP editcoop = new StudentCOOP(ztext.getText(), ftext.getText(),ltext.getText(), gtext.getText(),
                atext.getText(), Integer.parseInt(ctext.getText()), etext.getText(), wtext.getText(), 
                     notesarea.getText(),  sc.getSUbject(), sc.getSEmestercompleted(), sc.getMArk() ,sc.getWAm() );
                    StudentCOOPQueries scq2 = new StudentCOOPQueries();

                scq2.updateStudentCOOP(editcoop);
                    System.out.println("student coop updated");
                    handleTransitionButton(e, "conS.png", "calendarS.png", "ManageStudent2.fxml", "Dashboard");

                }
                 }
            }
        });
          
          submittoadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("adding coop stu");
                StudentCOOP addcoop = new StudentCOOP(ztext.getText(), ftext.getText(),ltext.getText(), gtext.getText(),
                atext.getText(), Integer.parseInt(ctext.getText()), etext.getText(), wtext.getText(), 
                     notesarea.getText(),  null, 0, 0.0 , 0.0 );
                
                scq.insertStudentCOOP(addcoop);
                System.out.println("student coop added");
                handleTransitionButton(e, "conS.png", "calendarS.png", "ManageStudent2.fxml", "Dashboard");

                
            }
        });
        
    
     
        
          logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                deleteTemp();
                handleTransitionButton(e, "conS.png", "loginS.png", "LoginPage.fxml", "Login");
            }
        });

        prevr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "conS.png", "calendarS.png", "ManageStudent2.fxml", "Dashboard");
            }
        });
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