/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import java.beans.EventHandler;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * FXML Controller class
 *
 * @author olemf
 */
public class PhotosController implements Initializable  {
   
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
            File dir = new File("file:///C://Users//olemf//OneDrive//Dokumenter//Utveksling//Informaticas//New Folder//HCI1//Clinic//resources//male");
            for(final File image : dir.listFiles())
        {
            photos.add(new Image(image.getPath()));
            
        }
        }
        else
        {
            File dir = new File("file:///C://Users//olemf//OneDrive//Dokumenter//Utveksling//Informaticas//New Folder//HCI1//Clinic//resources//female");
            for(final File image : dir.listFiles())
            {
                    photos.add(new Image(image.getPath()));
            }
        }
        
    }

    public void initStage(Stage stage, ImageView image) {
        this.stage = stage;
        this.image = image;
        scene = stage.getScene();
        title = stage.getTitle();
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
