package clinic.doctors;

import clinic.Constants;
import clinic.common.MainWindowController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Appointment;
import model.Days;
import model.Doctor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewDoctor extends MainWindowController {

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> patient;
    @FXML
    private TableColumn<Appointment, LocalDateTime> dateTime;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private CheckBox monday;
    @FXML
    private CheckBox friday;
    @FXML
    private CheckBox tuesday;
    @FXML
    private CheckBox saturday;
    @FXML
    private CheckBox wednesday;
    @FXML
    private CheckBox sunday;
    @FXML
    private CheckBox thursday;
    @FXML
    private Label dni;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label phone;
    @FXML
    private Label room;
    @FXML
    private ImageView imgView;

    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    public void cancel(MouseEvent mouseEvent) {
        loadScene(Constants.DOCTORS_LIST);
    }

    public void initDoctor(Doctor doctor){
        from.setText(doctor.getVisitStartTime().format(timeFormatter));
        to.setText(doctor.getVisitEndTime().format(timeFormatter));
        dni.setText(doctor.getIdentifier());
        name.setText(doctor.getName());
        surname.setText(doctor.getSurname());
        phone.setText(doctor.getTelephon());
        room.setText(doctor.getExaminationRoom().getIdentNumber()+" "+doctor.getExaminationRoom().getEquipmentDescription());
        imgView.setImage(doctor.getPhoto());
        checkBoxes.addAll(Arrays.asList(monday, tuesday, wednesday, thursday, friday, saturday, sunday));
        for (Days day:  doctor.getVisitDays()){
            checkBoxes.get(day.ordinal()).setSelected(true);
        }

        appointmentTable.setItems(FXCollections.observableList(clinicDBAccess.getDoctorAppointments(doctor.getIdentifier())));
        dateTime.setCellValueFactory(param -> new SimpleObjectProperty<LocalDateTime>(param.getValue().getAppointmentDateTime()));

        dateTime.setCellFactory(col -> new TableCell<Appointment, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(DateTimeFormatter.ofPattern("HH:mm d. MMM. YYYY")));
            }
        });
        patient.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPatient().getSurname()));
    }
}
