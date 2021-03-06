/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clinic;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import DBAccess.ClinicDBAccess;
import clinic.common.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * @author olemf
 */
public class Clinic extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        AtomicReference<Locale> local = new AtomicReference<>(Locale.getDefault());
        ButtonType english = new ButtonType("English");
        ButtonType spanish = new ButtonType("Español");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(english, spanish);
        alert.setTitle("Choose language");
        alert.getDialogPane().getStylesheets().add("styles/alerts.css");
        alert.setHeaderText("Confirmation/Confirmación");
        alert.setContentText("Choose language\nElegir idioma");
        alert.showAndWait().ifPresent(p -> {
            if (p == spanish) {
                local.set(new Locale("es"));
            }
        });

        ResourceBundle bundle = ResourceBundle.getBundle("languages", local.get());

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MAIN_WINDOW), bundle);

        Parent root = loader.load();

        MainWindowController main = loader.<MainWindowController>getController();

        main.initStage(primaryStage);
        main.initClinic(ClinicDBAccess.getSingletonClinicDBAccess());
        primaryStage.setOnCloseRequest(event -> {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.getDialogPane().getStylesheets().add("styles/alerts.css");
            alert1.setHeaderText(bundle.getString("alert.message"));
            alert1.setTitle(bundle.getString("savingDB"));
            alert1.setContentText(bundle.getString("savingDB"));
            alert1.show();
            ClinicDBAccess.getSingletonClinicDBAccess().saveDB();
            alert1.close();
        });
        Scene scene = new Scene(root);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.setTitle(bundle.getString("mainWindow.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
