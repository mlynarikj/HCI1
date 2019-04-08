package clinic.appointments;

import clinic.Constants;
import clinic.common.MainWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Appointment;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ViewAppointment extends MainWindowController {

    @FXML
    private Label patient;
    @FXML
    private Label doctor;
    @FXML
    private Label slot;
    @FXML
    private Label room;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    public void initAppointment(Appointment appointment) {
        patient.setText(appointment.getPatient().getName() + " " + appointment.getPatient().getSurname());
        doctor.setText(appointment.getDoctor().getName() + " " + appointment.getDoctor().getSurname());
        slot.setText(appointment.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("HH:mm d. MMM. YYYY")));
        room.setText(appointment.getDoctor().getExaminationRoom().getIdentNumber()+"");
    }



    public void cancel(MouseEvent mouseEvent) {
        loadScene(Constants.APPOINTMENTS_LIST);
    }

}
