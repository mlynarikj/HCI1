/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic.common;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author olemf
 */
public class PhotosController extends MainWindowController {

    @FXML
    private ToggleGroup gender;
    @FXML
    private ListView<ImageView> list;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private Button save;

    private Scene previous;

    private ImageView image;
    private Scene scene;
    private String title;
    private ObservableList<ImageView> photos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<ImageView> images = new ArrayList<>();

        photos = FXCollections.observableArrayList(images);
        list.setItems(photos);
        male.setUserData(true);
        female.setUserData(false);
        gender.selectedToggleProperty().addListener((observable, oldValue, newValue) -> loadPhotos(((boolean) newValue.getUserData())));
        female.setSelected(true);
        loadPhotos(false);

        save.disableProperty().bind(
                Bindings.equal(-1,
                        list.getSelectionModel().selectedIndexProperty()));

    }

    public void initStage(Stage stage) {
        super.initStage(stage);
        stage.setTitle("Add photo");
    }

    private void loadPhotos(boolean isMale) {
        photos.clear();
        String path = isMale ? "clinic/male" : "clinic/female";
        File dir = new File(PhotosController.class.getClassLoader().getResource(path).getPath());
        for (final File image : dir.listFiles()) {
            ImageView imgv = new ImageView(new Image(image.toURI().toString()));
            imgv.setFitHeight(80);
            imgv.setFitWidth(80);
            photos.add(imgv);
        }
    }

    public void save(MouseEvent mouseEvent) {

        image.setImage(list.getSelectionModel().getSelectedItem().getImage());
        stage.setScene(previous);
        stage.show();
    }

    public void initPrevious(Scene previous) {
        this.previous = previous;
    }

    public void initImageView(ImageView image) {
        this.image = image;
    }

    public void cancel(MouseEvent mouseEvent) {
        stage.setScene(previous);
        stage.show();
    }


}
