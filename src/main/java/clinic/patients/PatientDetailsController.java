/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic.patients;

import clinic.Constants;
import clinic.common.MainWindowController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Patient;


import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author olemf
 */
public class PatientDetailsController extends MainWindowController {
    @FXML
    private Label dni;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label telephone;
    @FXML
    private ListView<String> list;
    @FXML
    private ImageView image;




    private ObservableList<String>obsData = null;

    /**
     * Initializes the controller class.
     */


    @Override
    public void initStage(Stage primaryStage) {
        super.initStage(primaryStage);
        primaryStage.setTitle(bundle.getString("patientDetails.title"));
    }

    //    public void initStage(Stage stage, Patient patient)
//    {
//        this.stage = stage;
//        scene = this.stage.getScene();
//        title = this.stage.getTitle();
//        this.patient = patient;
//    }

    public void cancel(MouseEvent event)
    {
        loadScene(Constants.PATIENTS_LIST);
    }

    public void initPatient(Patient patient) {
        dni.setText(patient.getIdentifier());
        name.setText(patient.getName());
        surname.setText(patient.getSurname());
        telephone.setText(patient.getTelephon());
        image.setImage(patient.getPhoto());
    }

   /* public void cancel(javafx.scene.input.MouseEvent mouseEvent) {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }*/
}
