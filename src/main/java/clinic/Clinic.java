/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import java.io.IOException;

import DBAccess.ClinicDBAccess;
import clinic.common.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author olemf
 */
public class Clinic extends Application {
    
   @Override
    public void start(Stage primaryStage) throws IOException {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MAIN_WINDOW));
        
        Parent root = loader.load();
        
        MainWindowController main = loader.<MainWindowController>getController();
        
        main.initStage(primaryStage);
        main.initClinic(ClinicDBAccess.getSingletonClinicDBAccess());
        
        Scene scene = new Scene(root);
        //Application.setUserAgentStylesheet(Application.CASPIAN);
        primaryStage.setTitle("Main Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
