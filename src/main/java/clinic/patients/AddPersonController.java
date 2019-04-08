/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic.patients;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import clinic.Constants;
import clinic.common.MainWindowController;
import clinic.common.PhotosController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Patient;

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
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initStage(Stage primaryStage) {
        super.initStage(primaryStage);
        primaryStage.setTitle("Add Patient");
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

//        if(image == null)
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error box");
//            alert.setHeaderText("CHoose now");
//            alert.setContentText("PAtient could not be registrered due to lack of photo");
//            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.isPresent())
//                System.out.println("Cancel");        }


        StringBuilder errors = new StringBuilder();
        Patient patient = new Patient();

        patient.setIdentifier(dni.getText());
        if (patient.getIdentifier().isEmpty()) {
            errors.append("DNI cannot be empty\n");
        }
        patient.setName(name.getText());
        if (patient.getName().isEmpty()) {
            errors.append("Name cannot be empty\n");
        }
        patient.setSurname(surname.getText());
        if (patient.getSurname().isEmpty()) {
            errors.append("Surname cannot be empty\n");
        }
        patient.setTelephon(telephone.getText());
        if (patient.getTelephon().isEmpty()) {
            errors.append("Telephone cannot be empty\n");
        }
        patient.setPhoto(image.getImage());

        if (errors.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid patient");
            alert.setContentText(errors.toString());
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Patient created");
        alert.setContentText("Patient " + name.getText() + " " + surname.getText() + " created");
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
