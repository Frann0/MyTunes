package gui;

import bll.dbSong;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
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

    /**
     * Upon opening of the edit song window, the textfields are set with the song info to be edited.
     */
    public void setFields(){
        dbSong oldSong = dbsongModel.getOldSong();
        titleField.setText(oldSong.getTitle());
        artistField.setText(oldSong.getArtist());
        categoryField.setText(oldSong.getGenre());
        timeLabel.setText(oldSong.getDurationString());
    }

    /**
     * This method is called from the MyTunesController so they both reference the
     * same instance of dbSongModel.
     * @param dbsongModel
     */
    public void setDbsongModel(dbSongModel dbsongModel) {
        this.dbsongModel = dbsongModel;
    }

    /**
     * Creates a new dbSong object, which is passed to the updateSong().
     * oldSong is updated with the info in newSong.
     * @throws SQLException
     */
    public void handleSaveEdit() throws SQLException {
        dbSong oldSong = dbsongModel.getOldSong();

        String title = titleField.getText();
        String artist = artistField.getText();
        String genre = categoryField.getText();

        dbSong newSong = new dbSong(title,genre,oldSong.getDurationString(), oldSong.getFilePath(), artist);
        dbsongModel.updateSong(newSong);

        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.close();
    }

    /**
     * If cancel button is pressed, the edit song window close.
     * @param actionEvent
     */
    public void handleCancel(ActionEvent actionEvent) {
        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.close();
    }
}
