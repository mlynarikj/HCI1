/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DBAccess.ClinicDBAccess;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author olemf
 */
public class PatientsController extends MainWindowController {
    @FXML
    private Button patients;
    @FXML
    private Button doctors;
    @FXML
    private Button appointments;
    @FXML
    private ListView<Patient> list;
    @FXML
    private Button addPatient;
    @FXML
    private Insets x1;
    @FXML
    private Button deletePatient;
    @FXML
    private Button viewDetails;

    private Scene scene;
    private String title;
    private ObservableList<Patient> obsList = null;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        viewDetails.disableProperty().bind(
                Bindings.equal(-1,
                        list.getSelectionModel().selectedIndexProperty()));

        ArrayList<Patient> arrayList = new ArrayList<Patient>();
        obsList = FXCollections.observableArrayList(arrayList);
        list.setItems(obsList);
    }    



    @FXML
    private void addPatient(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPerson.fxml"));

        Parent root = loader.load();

        AddPersonController addPersonController = loader.getController();

        addPersonController.initStage(stage);
        Scene scene = new Scene(root);
        stage.setTitle("Add Patient");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void deletePatient(MouseEvent event) {
        obsList.remove(list.getSelectionModel().getSelectedItems());

    }

    @FXML
    private void viewDetails(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientDetails.fxml"));

        Parent root = loader.load();

        PatientDetailsController patientDetails = loader.<PatientDetailsController>getController();

        patientDetails.initStage(stage, list.getSelectionModel().getSelectedItem());


        Scene scene = new Scene(root);

        stage.setTitle("Patient Details - " + list.getSelectionModel().getSelectedItem().getName() + " " + list.getSelectionModel().getSelectedItem().getSurname());
        stage.setScene(scene);
        stage.show();

    }
    
}
