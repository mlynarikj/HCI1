package clinic;

import DBAccess.ClinicDBAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentListPage extends MainWindowController {
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> doctor;
    @FXML
    private TableColumn<Appointment, String> patient;
    @FXML
    private Button add;
    @FXML
    private Button view;
    @FXML
    private Button delete;
    @FXML
    private Button patients;
    @FXML
    private Button doctors;
    @FXML
    private Button appointments;


    private ObservableList<Appointment> appointmentList;
    /*
     * Appointments:
     * - Doctor
     * - Patient
     * - Possible days, time(from, to, in 15 min intervals)
     *
     * */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        doctor.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDoctor().getSurname()));
        patient.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPatient().getSurname()));
    }

    @FXML
    private void add(MouseEvent mouseEvent) {
        loadScene(Constants.APPOINTMENTS_NEW);
    }

    @FXML
    private void view(MouseEvent mouseEvent) {
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            this.<ViewAppointment>loadScene(Constants.APPOINTMENTS_VIEW,
                    p -> p.initAppointment(appointmentTable.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void delete(MouseEvent mouseEvent) {
        Appointment delete = appointmentTable.getSelectionModel().getSelectedItem();
        if (delete == null) return;
        if (delete.getAppointmentDateTime().isBefore(LocalDateTime.now())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to delete");
            alert.setContentText("Cannot delete an appointment in the past");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting an appointment");
        alert.setContentText("You are about to delete an appointment for doctor " + delete.getDoctor().getSurname() + " and patient " + delete.getPatient().getSurname());
        alert.showAndWait().ifPresent(p -> {
            if (p == ButtonType.OK) {
                appointmentList.remove(delete);
            }
        });
    }

    @Override
    public void initClinic(ClinicDBAccess clinicDBAccess) {
        super.initClinic(clinicDBAccess);
        this.appointmentList = FXCollections.observableList(clinicDBAccess.getAppointments());
        appointmentTable.setItems(appointmentList);
    }

    @Override
    public void initStage(Stage primaryStage) {
        super.initStage(primaryStage);
        stage.setTitle("Appointments");
    }
}
