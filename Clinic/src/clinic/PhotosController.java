/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author olemf
 */
public class PhotosController extends MainWindowController  {
   
    @FXML
    private ListView<Image> list;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;

    private Stage stage;

    private ObservableList<Image> photos = null;

    private ImageView image;
    private Scene scene;
    private String title;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Image> images = new ArrayList<Image>();
        
        photos = FXCollections.observableArrayList(images);
        list.setItems(photos);



        if(male.isSelected())
        {        
       // File dir = new File("C:\\Users\\olemf\\OneDrive\\Dokumenter\\Utveksling\\Informaticas\\New Folder\\HCI1\\Clinic");

            File dir = new File(PhotosController.class.getClassLoader().getResource("clinic/male").getPath());
            for(final File image : dir.listFiles())
        {
            photos.add(new Image(image.toURI().toString()));
            
        }
        }
        else
        {
            //File dir = new File(PhotosController.class.getClassLoader().getResource("clinic/female").getPath());
            File file = Paths.get(".", "resources", "female").normalize().toFile();
            for(final File image : file.listFiles())
            {
                    photos.add(new Image(image.toURI().toString()));
            }
        }
        
    }

    public void initStage(Stage stage) {
        super.initStage(stage);
        stage.setTitle("Add photo");
        }

    public void save(MouseEvent mouseEvent)
    {
        image.setImage(list.getSelectionModel().getSelectedItem());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


    public void cancel(MouseEvent mouseEvent) {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


}
