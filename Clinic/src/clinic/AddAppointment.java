package clinic;

import DBAccess.ClinicDBAccess;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FormatStringConverter;
import model.*;

import java.net.URL;
import java.text.Format;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddAppointment extends MainWindowController {

    @FXML
    private ChoiceBox<PersonWrapper> patient;
    @FXML
    private ChoiceBox<PersonWrapper> doctor;
    @FXML
    private ChoiceBox week;
    @FXML
    private ChoiceBox<Days> day;
    @FXML
    private ChoiceBox slot;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @Override
    public void initClinic(ClinicDBAccess clinicDBAccess) {
        super.initClinic(clinicDBAccess);
        //TODO initialize the time fields
        patient.setItems(FXCollections.observableList(PersonWrapper.convert(clinicDBAccess.getPatients())));
        patient.getSelectionModel().selectFirst();
        doctor.setItems(FXCollections.observableList(PersonWrapper.convert(clinicDBAccess.getDoctors())));
        doctor.getSelectionModel().selectFirst();
    }

    public void save(MouseEvent mouseEvent) {
        clinicDBAccess.getAppointments()
                .add(new Appointment(null, (Doctor) doctor.getValue().getPerson(), (Patient) patient.getValue().getPerson()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment created");
        alert.show();
        loadScene(Constants.APPOINTMENTS_LIST);
    }

    public void cancel(MouseEvent mouseEvent) {
        loadScene(Constants.APPOINTMENTS_LIST);
    }

}
