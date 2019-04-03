package clinic;

import DBAccess.ClinicDBAccess;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import model.Days;
import model.Doctor;
import model.ExaminationRoom;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class AddDoctor {


    public TextField dni;
    public TextField name;
    public TextField surname;
    public TextField telephone;
    public ChoiceBox<ExaminationRoom> room;
    public TextField from;
    public TextField to;
    public CheckBox monday;
    public CheckBox tuesday;
    public CheckBox wednesday;
    public CheckBox thursday;
    public CheckBox friday;
    public CheckBox saturday;
    public CheckBox sunday;
    private ArrayList<CheckBox> checkBoxes =
            new ArrayList<>(Arrays.asList(monday, tuesday, wednesday, thursday, friday, saturday, sunday));
    private StringBuilder errors = new StringBuilder();

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    private Image photograph;
    public void addPhoto(MouseEvent mouseEvent) {
//        TODO open photo dialog
    }

    public void save(MouseEvent mouseEvent) {

//        TODO check more values
        Doctor doctor = new Doctor();
        doctor.setExaminationRoom(room.getValue());
        doctor.setIdentifier(dni.getText());
        doctor.setName(name.getText());
        doctor.setSurname(surname.getText());
        doctor.setTelephon(telephone.getText());
        doctor.setVisitStartTime(LocalTime.parse(from.getText(), formatter));
        doctor.setVisitEndTime(LocalTime.parse(to.getText(), formatter));
        ArrayList<Days> days = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                days.add(Days.values()[i]);
            }
        }
        if (days.size() == 0) {
            errors.append("Doctor has to visit at least one day of the week!\n");
        }
        doctor.setVisitDays(days);
        doctor.setPhoto(photograph);
        if (errors.length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid doctor");
            alert.setContentText(errors.toString());
            return;
        }
        ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
        clinicDBAccess.getDoctors().add(doctor);
        clinicDBAccess.saveDB();

    }

    public void cancel(MouseEvent mouseEvent) {
//        TODO switch back to the original view and possibly erase values
    }

}
