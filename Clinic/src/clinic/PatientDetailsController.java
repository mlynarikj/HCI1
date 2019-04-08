/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Patient;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author olemf
 */
public class PatientDetailsController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private ListView<String> list;
    @FXML
    private ImageView image;
    @FXML
    private Button cancel;

    private Stage stage;
    private Scene scene;
    private String title;
    private Patient patient;
    private ObservableList<String>obsData = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> details = new ArrayList<String>();
        obsData = FXCollections.observableArrayList(details);
        list.setItems(obsData);
        //name.setText(patient.getName());
        //surname.setText(patient.getSurname());
        if(patient == null)
        {
            obsData.add("Please choose a patient to view the his/her details");
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }
        obsData.add(patient.getName());
        obsData.add(patient.getSurname());
        obsData.add(patient.getIdentifier());
        obsData.add(patient.getTelephon());
        image.setImage(patient.getPhoto());

    }

    public void initStage(Stage stage, Patient patient)
    {
        this.stage = stage;
        scene = this.stage.getScene();
        title = this.stage.getTitle();
        this.patient = patient;
    }

    public void cancel(MouseEvent event)
    {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

   /* public void cancel(javafx.scene.input.MouseEvent mouseEvent) {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }*/
}
