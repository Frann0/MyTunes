package gui;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSongViewController implements Initializable {
    private dbSongModel dbsongModel;
    @FXML
    private BorderPane editViewPane;
    @FXML
    private Button saveEditButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void handleCancelEdit(ActionEvent actionEvent) {
    }

    public void setDbsongModel(dbSongModel dbsongModel) {
        this.dbsongModel = dbsongModel;
    }

    @FXML
    public void handleEditSong(){

        // TODO
        // Få adgang til window, close();
        Stage window = (Stage) editViewPane.getScene().getWindow();
        window.close();

    }


}
