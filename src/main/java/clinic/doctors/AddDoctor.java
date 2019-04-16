package clinic.doctors;

import DBAccess.ClinicDBAccess;
import clinic.Constants;
import clinic.common.MainWindowController;
import clinic.common.PhotosController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import jfxtras.scene.control.LocalTimePicker;
import model.Days;
import model.Doctor;
import model.ExaminationRoom;
import model.Person;

import java.awt.*;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddDoctor extends MainWindowController {

    @FXML
    private ImageView imageView;
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
    private LocalTimePicker from;
    @FXML
    private LocalTimePicker to;
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


    public void addPhoto(MouseEvent mouseEvent) {
        this.<PhotosController>loadScene(Constants.PHOTOS,
                photosController -> {
                    photosController.initPrevious(stage.getScene());
                    photosController.initImageView(imageView);
                });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @Override
    public void initClinic(ClinicDBAccess clinicDBAccess) {
        super.initClinic(clinicDBAccess);
        doctorList = FXCollections.observableList(clinicDBAccess.getDoctors());
        ArrayList<ExaminationRoom> available = clinicDBAccess.getExaminationRooms();
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
        from.setMinuteStep(15);
        from.setLocalTime(LocalTime.of(10,0));
        to.setLocalTime(LocalTime.of(12,0));
        to.setMinuteStep(15);
    }

    public void save(MouseEvent mouseEvent) {
        StringBuilder errors = new StringBuilder();
        Doctor doctor = new Doctor();
        doctor.setExaminationRoom(room.getValue());
        doctor.setIdentifier(dni.getText());

        List<String> identifiers = clinicDBAccess.getDoctors().stream().map(Person::getIdentifier).collect(Collectors.toList());
        identifiers.addAll(clinicDBAccess.getPatients().stream().map(Person::getIdentifier).collect(Collectors.toList()));
        if (doctor.getIdentifier().isEmpty()) {
            errors.append(bundle.getString("alerts.emptyID"));
        }
        if (identifiers.contains(doctor.getIdentifier())) {
            errors.append(bundle.getString("alerts.notUniqueId"));
        }
        doctor.setName(name.getText());
        if (doctor.getName().isEmpty()) {
            errors.append(bundle.getString("alerts.emptyName"));
        }
        doctor.setSurname(surname.getText());
        if (doctor.getSurname().isEmpty()) {
            errors.append(bundle.getString("alerts.emptySurname"));
        }
        doctor.setTelephon(telephone.getText());
        if (doctor.getTelephon().isEmpty()) {
            errors.append(bundle.getString("alerts.emptyTelephone"));
        }
        doctor.setVisitStartTime(from.getLocalTime());
        doctor.setVisitEndTime(to.getLocalTime());

        if (doctor.getVisitStartTime().isAfter(doctor.getVisitEndTime())
                || (from.getLocalTime().getHour() == to.getLocalTime().getHour() && from.getLocalTime().getMinute() == to.getLocalTime().getMinute())) {
            errors.append(bundle.getString("alerts.doctor.wrongTime"));
        }
        ArrayList<Days> days = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                days.add(Days.values()[i]);
            }
        }
        if (days.size() == 0) {
            errors.append(bundle.getString("alerts.doctor.wrongDays"));
        }
        doctor.setVisitDays(days);
        doctor.setPhoto(imageView.getImage());
        if (errors.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("alerts.doctor.invalid"));
            alert.setContentText(errors.toString());
            alert.show();
            return;
        }
        doctorList.add(doctor);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(bundle.getString("alert.message"));
        alert.setTitle(bundle.getString("alerts.doctor.created"));
        alert.setContentText(bundle.getString("doctor") + " " + name.getText() + " " + surname.getText() + " created");
        alert.show();
        loadScene(Constants.DOCTORS_LIST);
    }

    public void cancel(MouseEvent mouseEvent) {
        loadScene(Constants.DOCTORS_LIST);
    }

}
