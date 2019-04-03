/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import DBAccess.ClinicDBAccess;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    
    private Stage stage;
    private String title;
    private Scene scene;
    private ClinicDBAccess clinic;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClinicDBAccess clinic = ClinicDBAccess.getSingletonClinicDBAccess();
    }    
    
    public void initStage(Stage stage)
    {
        this.stage = stage;
        title = stage.getTitle();
        scene = stage.getScene();
        
    }

    @FXML
    private void addPhoto(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("photos.fxml"));
        Parent root = loader.load();
        
        PhotosController addPhoto = loader.<PhotosController>getController();
        
        addPhoto.initStage(stage, image);
        Scene scene = new Scene(root);
        
        stage.setTitle("Add Photo");
        stage.setScene(scene);
        stage.show();
        
    }
    
    @FXML
    private void save(MouseEvent event)
    {
        Patient patient = new Patient(dni.getText(), name.getText(), surname.getText(), telephone.getText(), image.getImage());

        clinic.getPatients().add(patient);
        clinic.saveDB();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void cancel(MouseEvent event)
    {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
