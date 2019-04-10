/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic.common;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import DBAccess.ClinicDBAccess;
import clinic.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    protected ResourceBundle bundle;

    protected Stage stage;

    protected ClinicDBAccess clinicDBAccess;

    protected DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
    }

    public void initStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void initClinic(ClinicDBAccess clinicDBAccess) {
        this.clinicDBAccess = clinicDBAccess;
    }

    @FXML
    protected void patients(MouseEvent mouseEvent) {
        loadScene(Constants.PATIENTS_LIST);
    }

    @FXML
    protected void doctors(MouseEvent mouseEvent) {
        loadScene(Constants.DOCTORS_LIST);
    }

    @FXML
    protected void appointments(MouseEvent mouseEvent) {
        loadScene(Constants.APPOINTMENTS_LIST);
    }


    protected <T extends MainWindowController> T loadScene(String fxml, Consumer<T> function) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), bundle);
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fxml");
            alert.setContentText(e.getMessage());
            alert.show();
            return null;
        }
        T controller = loader.getController();
        controller.initStage(stage);
        controller.initClinic(clinicDBAccess);
        function.accept(controller);

        Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
        return controller;
    }

    protected <T extends MainWindowController> T loadScene(String fxml) {
        return loadScene(fxml, t -> {
        });
    }
}



