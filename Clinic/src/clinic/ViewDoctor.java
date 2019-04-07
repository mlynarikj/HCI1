package clinic;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Days;
import model.Doctor;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewDoctor extends MainWindowController {

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
//        TODO add appointments
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
    }
}
