/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import DBAccess.ClinicDBAccess;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author olemf
 */
public class AddPersonController implements Initializable {
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
    private TextField photography;
    
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

    @FXML
    private void addPhoto(MouseEvent event) {
        
    }
    
    @FXML
    private void save(MouseEvent event)
    {
        Patient patient = new Patient(dni.getText(), name.getText(), surname.getText(), telephone.getText(), image.getImage());
        ClinicDBAccess clinic = new ClinicDBAccess();
        clinic.getPatients().add(patient);
    }
    
    @FXML
    private void cancel(MouseEvent event)
    {
        
    }
}
