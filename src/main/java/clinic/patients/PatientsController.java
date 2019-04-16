/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic.patients;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DBAccess.ClinicDBAccess;
import clinic.Constants;
import clinic.common.MainWindowController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button addPatient;
    @FXML
    private Insets x1;
    @FXML
    private Button deletePatient;
    @FXML
    private Button viewDetails;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> name;
    @FXML
    private TableColumn<Patient, String> surname;

    private Scene scene;
    private String title;
    private ObservableList<Patient> obsList = null;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        super.initialize(url, rb);
        viewDetails.disableProperty().bind(
                Bindings.equal(-1,
                        patientTable.getSelectionModel().selectedIndexProperty()));
        deletePatient.disableProperty().bind(
                Bindings.equal(-1,
                        patientTable.getSelectionModel().selectedIndexProperty()));

        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        surname.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSurname()));

    }

    @Override
    public void initClinic(ClinicDBAccess clinicDBAccess) {
        super.initClinic(clinicDBAccess);
        obsList = FXCollections.observableList(clinicDBAccess.getPatients());
        patientTable.setItems(obsList);
    }

    @Override
    public void initStage(Stage primaryStage) {
        super.initStage(primaryStage);
        primaryStage.setTitle(bundle.getString("patient"));
    }


    @FXML
    private void addPatient(MouseEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPerson.fxml"));
//
//        Parent root = loader.load();
//
//        AddPersonController addPersonController = loader.getController();
//
//        addPersonController.initStage(stage);

        loadScene(Constants.PATIENTS_NEW);


    }

    @FXML
    private void deletePatient(MouseEvent event) {
        //clinicDBAccess.getPatients().remove(patientTable.getSelectionModel().getSelectedItem());

        Patient delete = patientTable.getSelectionModel().getSelectedItem();
        if (clinicDBAccess.hasAppointments(delete)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("alerts.FailedDelete"));
            alert.setContentText(bundle.getString("alerts.FailedDelete.patient"));
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(bundle.getString("alert.confirmation"));
        alert.setTitle(bundle.getString("delete") + " " + delete.getSurname());
        alert.setContentText(bundle.getString("alerts.aboutToDelete.patient") + delete.getSurname());
        alert.showAndWait().ifPresent(p -> {
            if (p == ButtonType.OK) {
                obsList.remove(delete);
                Alert ale =  new Alert(Alert.AlertType.INFORMATION);
                ale.setHeaderText(bundle.getString("alert.message"));
                ale.setTitle(bundle.getString("delete"));
                ale.setContentText(bundle.getString("alert.successfulDelete"));
                ale.show();
            }
        });
    }

    @FXML
    private void viewDetails(MouseEvent event) throws IOException {

        this.<PatientDetailsController>loadScene(Constants.PATIENTS_VIEW, e -> {
            e.initPatient(patientTable.getSelectionModel().getSelectedItem());
        });

    }

}
