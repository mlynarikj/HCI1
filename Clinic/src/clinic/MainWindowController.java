/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author olemf
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button doctors;
    @FXML
    private Button patients;
    @FXML
    private Button appointments;

    private Stage stage;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    @FXML
    private void patients(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patients.fxml"));

        Parent root = loader.load();

        PatientsController patientsController = loader.<PatientsController>getController();

        patientsController.initStage(stage);
        Scene scene = new Scene(root);
        stage.setTitle("Add Patient");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void doctors(MouseEvent mouseEvent) {
    }

    @FXML
    public void appointments(MouseEvent mouseEvent) {
    }
}
