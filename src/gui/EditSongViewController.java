package gui;

import bll.Song;
import bll.dbSong;
import com.jfoenix.controls.JFXTextField;
import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditSongViewController implements Initializable {
    private dbSongModel dbsongModel;

    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField artistField;
    @FXML
    private Label timeLabel;
    @FXML
    private JFXTextField categoryField;
    @FXML
    private AnchorPane anchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setFields(){
        Song oldSong = dbsongModel.getOldSong();
        titleField.setText(oldSong.getSongName());
        artistField.setText(oldSong.getArtist());
        categoryField.setText(oldSong.getGenre());
        timeLabel.setText(oldSong.getDuration().getValue());
    }

    public void setDbsongModel(dbSongModel dbsongModel) {
        this.dbsongModel = dbsongModel;
    }


    public void handleSaveEdit() throws SQLException {
        Song oldSong = dbsongModel.getOldSong();

        String title = titleField.getText();
        String artist = artistField.getText();
        String genre = categoryField.getText();

        dbSong newSong = new dbSong(title,genre,oldSong.getDuration().getValue(), oldSong.getPath(), artist);
        dbsongModel.updateSong(newSong);

        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.close();
    }


    public void handleCancel(ActionEvent actionEvent) {
        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.close();
    }
}
