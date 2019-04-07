package clinic;

import DBAccess.ClinicDBAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import model.Days;
import model.Doctor;
import model.ExaminationRoom;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddDoctor extends MainWindowController {

    @FXML
    private TextField dni;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField telephone;
    @FXML
    private ChoiceBox<ExaminationRoom> room;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private CheckBox monday;
    @FXML
    private CheckBox tuesday;
    @FXML
    private CheckBox wednesday;
    @FXML
    private CheckBox thursday;
    @FXML
    private CheckBox friday;
    @FXML
    private CheckBox saturday;
    @FXML
    private CheckBox sunday;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    private ObservableList<Doctor> doctorList;

    private Image photograph;


    public void addPhoto(MouseEvent mouseEvent) {
//        TODO fix photo dialog
        loadScene(Constants.PHOTOS);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @Override
    public void initClinic(ClinicDBAccess clinicDBAccess) {
        super.initClinic(clinicDBAccess);
        //TODO fix timeFormatter for textfields
        doctorList = FXCollections.observableList(clinicDBAccess.getDoctors());
        ArrayList<ExaminationRoom> available = clinicDBAccess.getExaminationRooms();
//        List<ExaminationRoom> taken = doctorList.stream().map(Doctor::getExaminationRoom).collect(Collectors.toList());
//        available.removeAll(taken);
        available = available.stream().filter(p ->
                doctorList.stream().noneMatch(q -> q.getExaminationRoom().getIdentNumber() == p.getIdentNumber())).collect(Collectors.toCollection(ArrayList::new));
        room.setItems(FXCollections.observableArrayList(available));

        room.setConverter(new StringConverter<ExaminationRoom>() {
            @Override
            public String toString(ExaminationRoom object) {
                return object.getIdentNumber() + " " + object.getEquipmentDescription();
            }

            @Override
            public ExaminationRoom fromString(String string) {
                int ident = Integer.valueOf(string.split(" ")[0]);
                return clinicDBAccess.getExaminationRooms().stream().filter(p -> p.getIdentNumber() == ident).findFirst().get();
            }
        });
        room.getSelectionModel().selectFirst();
        checkBoxes.addAll(Arrays.asList(monday, tuesday, wednesday, thursday, friday, saturday, sunday));

    }

    public void save(MouseEvent mouseEvent) {
//        TODO check error values
        StringBuilder errors = new StringBuilder();
        Doctor doctor = new Doctor();
        doctor.setExaminationRoom(room.getValue());
        doctor.setIdentifier(dni.getText());
        doctor.setName(name.getText());
        doctor.setSurname(surname.getText());
        doctor.setTelephon(telephone.getText());
        doctor.setVisitStartTime(LocalTime.parse(from.getText(), timeFormatter));
        doctor.setVisitEndTime(LocalTime.parse(to.getText(), timeFormatter));
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
        if (errors.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid doctor");
            alert.setContentText(errors.toString());
            alert.show();

            return;
        }
        doctorList.add(doctor);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Doctor created");
        alert.setContentText("Doctor" + name.getText() + " " + surname.getText() + " created");
        alert.show();
        loadScene(Constants.DOCTORS_LIST);
    }

    public void cancel(MouseEvent mouseEvent) {
        loadScene(Constants.DOCTORS_LIST);
    }

}
