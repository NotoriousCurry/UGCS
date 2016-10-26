/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ugcs.Database.ConsultationSetup;
import ugcs.Database.StudentCOOPSetup;
import ugcs.Database.StudentFollowSetup;
import ugcs.Database.StudentSetup;
import ugcs.Database.UGCSetup;

/**
 *
 * @author sgaheer
 */
public class UGCS extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StudentSetup.setupDatabase();
        UGCSetup.setupDatabase();
        ConsultationSetup.setupDatabase();
        StudentFollowSetup.setupDatabase();
        StudentCOOPSetup.setupDatabase();

        launch(args);
    }

}
