package clinic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DoctorListPage {
    @FXML
    private TableView doctorTable;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn surname;
    @FXML
    private Button add;
    @FXML
    private Button view;
    @FXML
    private Button delete;
    @FXML
    private Button patients;
    @FXML
    private Button doctors;
    @FXML
    private Button appointments;

    private Stage stage;

    @FXML
    private void add(MouseEvent mouseEvent) {

    }

    @FXML
    private void view(MouseEvent mouseEvent) {

    }

    @FXML
    private void delete(MouseEvent mouseEvent) {

    }

    @FXML
    private void patients(MouseEvent mouseEvent) {

    }

    @FXML
    private void doctors(MouseEvent mouseEvent) {

    }

    @FXML
    private void appointments(MouseEvent mouseEvent) {

    }

    public void initStage(Stage stage){
        this.stage = stage;
    }
}
