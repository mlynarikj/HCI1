/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author olemf
 */
public class Clinic extends Application {
    
   @Override
    public void start(Stage primaryStage) throws IOException {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        
        Parent root = loader.load();
        
        MainWindowController main = loader.<MainWindowController>getController();
        
        main.initStage(primaryStage);
        
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
