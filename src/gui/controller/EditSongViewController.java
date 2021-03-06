package gui.controller;

import be.dbSong;
import com.jfoenix.controls.JFXTextField;
import gui.model.dbPlaylistModel;
import gui.model.dbSongModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditSongViewController implements Initializable {
    private dbSongModel dbsongModel;
    private dbPlaylistModel dbplaylistModel;

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
    @FXML
    private ListView<dbSong> lstQueue;

    private ObservableList<dbSong> allSongs;
    private ObservableList<dbSong> playlistSongs;
    private String currentPlaylist;
    private String durationString;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * set allSongs
     * @param allSongs
     */
    public void setAllSongs(ObservableList<dbSong> allSongs) {
        this.allSongs = allSongs;
    }

    /**
     * set dbPlaylistModel
     * @param dbplaylistModel
     */
    public void setDbplaylistModel(dbPlaylistModel dbplaylistModel) {
        this.dbplaylistModel = dbplaylistModel;
    }

    /**
     * set lstQueue
     * @param lstQueue
     */
    public void setLstQueue(ListView<dbSong> lstQueue) {
        this.lstQueue = lstQueue;
    }

    /**
     *
     * @param playlistSongs
     */
    public void setPlaylistSongs(ObservableList<dbSong> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    /**
     * set currentPlaylist
     * @param currentPlaylist
     */
    public void setCurrentPlaylist(String currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
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
        durationString = oldSong.getDurationString();
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
        newSong.setDurationString(durationString);
        allSongs.remove(oldSong);
        allSongs.add(newSong);

        dbsongModel.updateSong(newSong);
        playlistSongs.clear();
        playlistSongs.addAll(dbplaylistModel.getPlaylist(currentPlaylist));
        lstQueue.setItems(playlistSongs);
        lstQueue.refresh();

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
