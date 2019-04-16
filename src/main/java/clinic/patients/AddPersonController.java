/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic.patients;

import java.util.List;
import java.util.stream.Collectors;

import clinic.Constants;
import clinic.common.MainWindowController;
import clinic.common.PhotosController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Patient;
import model.Person;

/**
 * FXML Controller class
 *
 * @author olemf
 */
public class AddPersonController extends MainWindowController {
    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private TextField dni;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField telephone;

    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private ImageView image;
    



    /**
     * Initializes the controller class.
     */


    @Override
    public void initStage(Stage primaryStage) {
        super.initStage(primaryStage);
        primaryStage.setTitle(bundle.getString("addPatient.title"));
    }


    public void addPhoto(MouseEvent mouseEvent) {
        this.<PhotosController>loadScene(Constants.PHOTOS,
                photosController -> {
                    photosController.initPrevious(stage.getScene());
                    photosController.initImageView(image);
                });
    }



    @FXML
    private void save(MouseEvent event)
    {
        StringBuilder errors = new StringBuilder();
        Patient patient = new Patient();

        List<String> identifiers = clinicDBAccess.getDoctors().stream().map(Person::getIdentifier).collect(Collectors.toList());
        identifiers.addAll(clinicDBAccess.getPatients().stream().map(Person::getIdentifier).collect(Collectors.toList()));

        patient.setIdentifier(dni.getText());
        if (patient.getIdentifier().isEmpty()) {
            errors.append(bundle.getString("alerts.emptyID"));
        }
        if (identifiers.contains(patient.getIdentifier())){
            errors.append(bundle.getString("alerts.notUniqueId"));
        }
        patient.setName(name.getText());
        if (patient.getName().isEmpty()) {
            errors.append(bundle.getString("alerts.emptyName"));
        }
        patient.setSurname(surname.getText());
        if (patient.getSurname().isEmpty()) {
            errors.append(bundle.getString("alerts.emptySurname"));
        }
        patient.setTelephon(telephone.getText());
        if (patient.getTelephon().isEmpty()) {
            errors.append(bundle.getString("alerts.emptyTelephone"));
        }
        patient.setPhoto(image.getImage());

        if (errors.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("alerts.patient.invalid"));
            alert.setContentText(errors.toString());
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(bundle.getString("alert.message"));
        alert.setTitle(bundle.getString("alerts.patient.created"));
        alert.setContentText(bundle.getString("patient")+" " + name.getText() + " " + surname.getText() + bundle.getString("created"));
        alert.show();

        clinicDBAccess.getPatients().add(patient);
        loadScene(Constants.PATIENTS_LIST);
    }

    
    @FXML
    private void cancel(MouseEvent event)
    {
        loadScene(Constants.PATIENTS_LIST);
    }
}
