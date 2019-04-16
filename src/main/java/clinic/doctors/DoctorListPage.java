package clinic.doctors;

import DBAccess.ClinicDBAccess;
import clinic.Constants;
import clinic.common.MainWindowController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Doctor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorListPage extends MainWindowController {
    @FXML
    private TableView<Doctor> doctorTable;
    @FXML
    private TableColumn<Doctor, String> name;
    @FXML
    private TableColumn<Doctor, String> surname;
    @FXML
    private Button view;
    @FXML
    private Button delete;

    private ObservableList<Doctor> doctorList;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        doctorTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        surname.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSurname()));

        view.disableProperty().bind(
                Bindings.equal(-1,
                        doctorTable.getSelectionModel().selectedIndexProperty()));
        delete.disableProperty().bind(
                Bindings.equal(-1,
                        doctorTable.getSelectionModel().selectedIndexProperty()));
    }

    @Override
    public void initClinic(ClinicDBAccess clinicDBAccess) {
        super.initClinic(clinicDBAccess);
        doctorList = FXCollections.observableList(clinicDBAccess.getDoctors());
        doctorTable.setItems(doctorList);
    }

    @FXML
    private void add(MouseEvent mouseEvent) {
        loadScene(Constants.DOCTORS_NEW);
    }

    @FXML
    private void view(MouseEvent mouseEvent) {
        if (doctorTable.getSelectionModel().getSelectedItem() != null) {
            this.<ViewDoctor>loadScene(Constants.DOCTORS_VIEW, p -> p.initDoctor(doctorTable.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void delete(MouseEvent mouseEvent) {
        Doctor delete = doctorTable.getSelectionModel().getSelectedItem();
        if (delete == null) return;
        if (clinicDBAccess.hasAppointments(delete)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("alerts.FailedDelete"));
            alert.setContentText(bundle.getString("alerts.FailedDelete.doctor"));
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(bundle.getString("alert.confirmation"));
        alert.setTitle(bundle.getString("delete")+" " + delete.getSurname());
        alert.setContentText(bundle.getString("alerts.aboutToDelete.doctor") + delete.getSurname());
        alert.showAndWait().ifPresent(p -> {
            if (p == ButtonType.OK) {
                doctorList.remove(delete);
                Alert ale =  new Alert(Alert.AlertType.INFORMATION);
                ale.setHeaderText(bundle.getString("alert.message"));
                ale.setTitle(bundle.getString("delete"));
                ale.setContentText(bundle.getString("alert.successfulDelete"));
                ale.show();
            }
        });

    }


    public void initStage(Stage stage) {
        this.stage = stage;
        stage.setTitle(bundle.getString("doctors"));
    }
}
