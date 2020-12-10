package gui;

import bll.*;
import bll.Util.SongSearcher;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MyTunesController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private FontAwesomeIconView icnMute;
    @FXML
    private FontAwesomeIconView icnShuffle;
    @FXML
    private FontAwesomeIconView icnQueue;
    @FXML
    private ImageView imgAlbumArt;
    @FXML
    private JFXSlider sldTime;
    @FXML
    private JFXSlider sldVolume;
    @FXML
    private Label lblArtist;
    @FXML
    private Label lblCurrentPlaylist;
    @FXML
    private Label lblSongTitle;
    @FXML
    private Label lblTimeMax;
    @FXML
    private Label lblTimeMin;
    @FXML
    private Label lblTitlebar;
    @FXML
    private ListView<String> lstPlaylist;
    @FXML
    private ListView<dbSong> lstQueue;
    @FXML
    private MaterialDesignIconView icnRepeat;
    @FXML
    private TableView<dbSong> tblAllsongs;
    @FXML
    private TableColumn<dbSong, String> tblClmArtist;
    @FXML
    private TableColumn<dbSong, String> tblClmSongTitle;
    @FXML
    private TableColumn<dbSong, String> tblClmGenre;
    @FXML
    private TableColumn<dbSong, String> tblClmTime;
    @FXML
    private TableColumn tblClmSpacer;
    @FXML
    private MaterialDesignIconView tglPlay;
    @FXML
    private TextField txtSearchField;
    @FXML
    private VBox vboxQueue;

    private String currentPlaylist;
    private final ObservableList<dbSong> allSongs = FXCollections.observableArrayList();
    private final ObservableList<String> allPlaylists = FXCollections.observableArrayList();
    private final ObservableList<dbSong> playlistSongs = FXCollections.observableArrayList();

    private final PlaylistHandler playlistHandler = new PlaylistHandler();
    private final MediaManager mediaManager = new MediaManager();
    private final DragAndDropHandler dragAndDropHandler = new DragAndDropHandler();
    private dbSongModel dbsongModel = new dbSongModel();
    private dbPlaylistModel dbPlaylistModel = new dbPlaylistModel();

    private boolean isMuted;
    private boolean isPlaying;
    private double prevVolume;
    private boolean shuffleActive;
    private boolean repeatActive;
    private boolean queueShowing;

    public MyTunesController() throws SQLException {
    }

    /**
     * Initialize bruger vi til at sætte mange af vores FXML værdier efter de er blevet
     * initialiseret.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblTitlebar.setText("Codify - No song playing");

        isMuted = false;
        isPlaying = false;
        shuffleActive = false;
        repeatActive = false;
        queueShowing = false;

        try {
            allPlaylists.addAll(dbPlaylistModel.getPlaylistNames());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src/Resources/123.mp3").toURI().toString()));
        mediaPlayer.setOnReady(() -> {
            allSongs.addAll(dbsongModel.getSongs());
        });

        if (!allPlaylists.isEmpty()){
            lstPlaylist.getSelectionModel().select(allPlaylists.get(0));
            try {
                handlePlaylistSelect();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        lstPlaylist.setItems(allPlaylists);

        tblClmArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        tblClmSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblClmGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tblClmTime.setCellValueFactory(new PropertyValueFactory<>("durationString"));


        tblAllsongs.setItems(allSongs);

        sldTime.setMin(0);
        sldTime.setMax(0);
        sldTime.setValue(0);

        sldVolume.setMin(0);
        sldVolume.setMax(100);
        sldVolume.setValue(50);

        prevVolume = sldVolume.getValue();
        vboxQueue.setVisible(false);
        vboxQueue.setMaxWidth(0);

        tblClmSpacer.setPrefWidth(405);

        //Hvis all songs er tom, display dette billede istedet for "This table is empty" string.
        tblAllsongs.setPlaceholder(new ImageView(new Image("Resources/emptyListNew.png")));
    }

    //PLAYLIST FUNKTIONER

    /**
     * Håndtere hvad der skal ske når vi vælger en af vores playlister.
     */
    public void handlePlaylistSelect() throws SQLException {

        if (lstPlaylist.getSelectionModel().getSelectedItem() != null) {
            currentPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();
            lblCurrentPlaylist.setText(currentPlaylist);
            playlistSongs.clear();

            MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src/Resources/123.mp3").toURI().toString()));
            mediaPlayer.setOnReady(() -> {
                try {
                    playlistSongs.addAll(dbPlaylistModel.getPlaylist(currentPlaylist));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            });
            lstQueue.setItems(playlistSongs);
            lstQueue.refresh();


        }
    }

    /**
     * Håndtere hvad der skal ske når vi vil tilføje en ny playliste.
     */
    public void handleAddPlaylist() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("Playlist");
        dialog.setTitle("Add playlist");
        dialog.setHeaderText("Add playlist");
        dialog.setContentText("Please enter your desired playlist name: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(navn -> {
            try {
                dbPlaylistModel.addPlaylist(navn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        allPlaylists.clear();
        allPlaylists.addAll(dbPlaylistModel.getPlaylistNames());

        lstPlaylist.setItems(allPlaylists);
        //dbPlaylistModel.addPlaylist(navn);
    }

    /**
     * Håndtere vores slet playliste funktion.
     */
    public void handleRemovePlaylist() throws SQLException {
        if (lstPlaylist.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Remove " + lstPlaylist.getSelectionModel().getSelectedItem() + "?");
            alert.setHeaderText("Remove " + lstPlaylist.getSelectionModel().getSelectedItem() + "?");
            alert.setContentText("Are you sure, you want to remove " + lstPlaylist.getSelectionModel().getSelectedItem() + " playlist?\n" +
                    "All songs in the playlist will be lost, and will need to be re-added");


            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    dbPlaylistModel.deletePlaylist(currentPlaylist);
                }
            }
            allPlaylists.clear();
            allPlaylists.addAll(dbPlaylistModel.getPlaylistNames());

            lstPlaylist.setItems(allPlaylists);
        }
    }

    /**
     * Håndtere hvad der skal ske når vi ville ændre i vores playliste.
     * Det vil sige, opdatere dens navn.
     */
    public void handleEditPlaylist() throws SQLException {
        if (lstPlaylist.getSelectionModel().getSelectedItem() != null) {
            TextInputDialog dialog = new TextInputDialog("New Playlist Name");
            dialog.setTitle("Edit playlist");
            dialog.setHeaderText("Edit playlist");
            dialog.setContentText("Please enter your new playlist name: ");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> {
                try {
                    dbPlaylistModel.editPlaylistName(currentPlaylist, s);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            lblCurrentPlaylist.setText(currentPlaylist);

            allPlaylists.clear();
            allPlaylists.addAll(dbPlaylistModel.getPlaylistNames());

            lstPlaylist.setItems(allPlaylists);
        }
    }

    //SANG FUNKTIONER

    /**
     * Håndtere vores søge funktion. Og opdatere vores listview efter det.
     */
    public void handleSearch() {
        if (!txtSearchField.getText().isEmpty() || txtSearchField.getText() != null && !tblAllsongs.getItems().isEmpty()) {
            tblAllsongs.setItems(SongSearcher.search(allSongs, txtSearchField.getText()));
        }
    }

    /**
     * Håndtere vores manuelle tilføj sang funktion.
     */
    public void handleAddSong() throws SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a song you want to add to your playlist");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sound files (*.wav , *.mp3)", "*.wav", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        MediaPlayer mp = new MediaPlayer(new Media(selectedFile.toURI().toString()));

        dbSong s = new dbSong(selectedFile);
        mp.setOnReady(() -> {
            allSongs.add(s);
            try {
                dbsongModel.addSong(s);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            tblAllsongs.refresh();
        });

    }

    /**
     * Håndtere hvad der skal ske når vi vil slette en sang fra vores playliste
     */
    public void handleRemoveSong() throws SQLException {
        if (playlistSongs.contains(tblAllsongs.getSelectionModel().getSelectedItem())){
            dbPlaylistModel.removeSongFromPlaylist(currentPlaylist,tblAllsongs.getSelectionModel().getSelectedItem());
            playlistSongs.remove(tblAllsongs.getSelectionModel().getSelectedItem());
        }
        dbsongModel.deleteSong(tblAllsongs.getSelectionModel().getSelectedItem());
        allSongs.remove(tblAllsongs.getSelectionModel().getSelectedItem());
        // currentPlaylist.removeSong(tblAllsongs.getSelectionModel().getSelectedItem());
    }

    public void handleEditSong(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("EditSongView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        EditSongViewController controller = fxmlLoader.getController();
        dbsongModel.setOldSong(tblAllsongs.getSelectionModel().getSelectedItem());
        controller.setDbsongModel(dbsongModel);
        controller.setFields();
    }

    /**
     * Håndtere hvad der skal ske når vi vælger en sang fra listen.
     */
    public void handleSongSelect() {
        if (lstQueue.getSelectionModel().getSelectedItem() != null) {
            dbSong cSong = lstQueue.getSelectionModel().getSelectedItem();

            mediaManager.setMedia(cSong.getFilePath());

            lblTimeMin.setText("0:00");
            lblTimeMax.setText(cSong.getDurationString());
            imgAlbumArt.setImage(cSong.getAlbumArt());
            lblArtist.setText(cSong.getArtist());
            lblSongTitle.setText(cSong.getTitle());
            lblTitlebar.setText("Codify - " + cSong.getTitle() + " by " + cSong.getArtist());

            sldTime.setMax(100);
        }
    }

    //UI KNAPPER FUNKTIONER

    /**
     * Håndtere vores pause/unpause knap.
     * TODO Skal opdateres.
     */
    public void handleToggleAction() {
        //TODO Lav getState i mediaManager, og opdater ikon derefter
        tglPlay.setOnMouseEntered(mouseEvent -> tglPlay.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: white; -fx-font-size: 40"));
        tglPlay.setOnMouseExited(mouseEvent -> tglPlay.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #4f4f4f; -fx-font-size: 40"));
        if (tblAllsongs.getSelectionModel().getSelectedItem() != null) {

            if (!isPlaying) {
                tglPlay.setIcon(MaterialDesignIcon.PAUSE_CIRCLE_OUTLINE);
                isPlaying = true;
            } else {
                tglPlay.setIcon(MaterialDesignIcon.PLAY_CIRCLE_OUTLINE);
                isPlaying = false;
            }
        }
    }

    /**
     * Kommer til at håndtere shuffle play.
     * Ved at tage alle sangene i nuværende playlist.
     * Sætte dem ind i tilfældig rækkefølge i en ArrayList, og derefter afspille
     * den første sang i den liste.
     */
    public void handleShufflePlay() {
        icnShuffle.setOnMouseEntered(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: white; -fx-font-size: 15"));

        if (!shuffleActive) {
            icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 15");
            icnShuffle.setOnMouseExited(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 15"));
            shuffleActive = true;
        } else {
            icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 15");
            icnShuffle.setOnMouseExited(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 15"));
            shuffleActive = false;
        }
        //mediaManager.setCurrentPlaylist(currentPlaylist);
        mediaManager.shuffle(shuffleActive);
        //lstQueue.setItems(mediaManager.getPlayOrder());
    }

    /**
     * Håndter om den nuværende sang skal køre på repeat.
     */
    public void handleRepeat() {
        icnRepeat.setOnMouseEntered(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: white; -fx-font-size: 20"));

        if (!repeatActive) {
            icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #71BA51; -fx-font-size: 20");
            icnRepeat.setOnMouseExited(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #71BA51; -fx-font-size: 20"));
            repeatActive = true;
        } else {
            icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #4f4f4f; -fx-font-size: 20");
            icnRepeat.setOnMouseExited(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #4f4f4f; -fx-font-size: 20"));
            repeatActive = false;
        }
    }

    /**
     * Håndtere vores mute funktion. Man muter ved at trykke på ikonet.
     * Så ikonet, skal opdateres efter det
     */
    public void handleMute() {
        if (!isMuted) {
            prevVolume = sldVolume.getValue();
            sldVolume.setValue(0);
            icnMute.setIcon(FontAwesomeIcon.VOLUME_OFF);
            mediaManager.setMute(true);
            isMuted = true;
        } else {
            icnMute.setIcon(FontAwesomeIcon.VOLUME_UP);
            sldVolume.setValue(prevVolume);
            mediaManager.setMute(false);
            isMuted = false;
        }
    }

    /**
     * Håndtere vores manuelle mute funktion. Manuelt i det at man kan "Mute" ved
     * at slide slideren i bund. Så der skal den også håndtere hvilket ikon der skal
     * vises. TODO Sikkert en bedre måde at gøre de to funktioner på.
     */
    public void handleManuelMute() {
        if (sldVolume.getValue() <= 0) {
            icnMute.setIcon(FontAwesomeIcon.VOLUME_OFF);
            mediaManager.setVolume(sldVolume.getValue());
            isMuted = true;
        } else {
            icnMute.setIcon(FontAwesomeIcon.VOLUME_UP);
            prevVolume = sldVolume.getValue();
            mediaManager.setVolume(sldVolume.getValue());
            isMuted = false;
        }
    }

    //ØVRIGE FUNKTIONER.

    /**
     * Håndtere når vi slipper en fil / filer på vores program.
     *
     * @param dragEvent dragEvent, hvor fra vi får de filer der bliver sluppet.
     */
    public void handleDragDropped(DragEvent dragEvent) {
        List<File> selectedFiles = dragEvent.getDragboard().getFiles();
        ArrayList<dbSong> songList = dragAndDropHandler.handleDragDropped(selectedFiles);

        MediaPlayer mp = new MediaPlayer(new Media(selectedFiles.get(0).toURI().toString()));
        mp.setOnReady(() -> {
            for (dbSong s : songList) {
                allSongs.add(s);
                try {
                    dbsongModel.addSong(s);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            tblAllsongs.refresh();
        });
    }

    /**
     * Håndtere hvad der skal ske når vi trækker en fil henover vores program.
     *
     * @param dragEvent dragEvent
     */
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Håndtere vores luk knap.
     */
    public void handleExit() {
        System.exit(0);
    }

    /**
     * Håndtere vores minimer knap i vores custom title bar.
     */
    public void handleMinimize() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleAddToPlaylist(ActionEvent actionEvent) throws SQLException {
        if (tblAllsongs.getSelectionModel().getSelectedItem() != null) {
            dbSong cSong = tblAllsongs.getSelectionModel().getSelectedItem();
            MediaPlayer mp = new MediaPlayer(new Media(cSong.getFilePath()));
            mp.setOnReady(() -> {
                playlistSongs.add(cSong);
                try {
                    dbPlaylistModel.addSongToPlaylist(currentPlaylist, cSong);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                lstQueue.refresh();
            });
        }
    }

    public void handleRemoveFromPlaylist(ActionEvent actionEvent){
        if (lstQueue.getSelectionModel().getSelectedItem() != null) {
            dbSong cSong = lstQueue.getSelectionModel().getSelectedItem();
            MediaPlayer mp = new MediaPlayer(new Media(cSong.getFilePath()));
            mp.setOnReady(() -> {
                playlistSongs.remove(cSong);
                try {
                    dbPlaylistModel.removeSongFromPlaylist(currentPlaylist, cSong);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                lstQueue.refresh();
            });
        }
    }

    public void handleShowPlaylist() {
        icnQueue.setOnMouseEntered(mouseEvent -> icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: white; -fx-font-size: 20"));
        FadeTransition fadeT = new FadeTransition(Duration.millis(250), vboxQueue);

        if (!queueShowing) {
            icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 20");
            icnQueue.setOnMouseExited(mouseEvent -> icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 20"));

            queueShowing = true;
            vboxQueue.setVisible(true);
            vboxQueue.setMaxWidth(240);
            tblClmSpacer.setPrefWidth(172);
            fadeT.setFromValue(0);
            fadeT.setToValue(1);
            fadeT.play();
        } else {
            icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 20");
            icnQueue.setOnMouseExited(mouseEvent -> icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 20"));
            queueShowing = false;

            fadeT.setFromValue(1);
            fadeT.setToValue(0);
            fadeT.play();

            fadeT.onFinishedProperty().set((event) -> {
                vboxQueue.setVisible(false);
                vboxQueue.setMaxWidth(0);
            });

            tblClmSpacer.setPrefWidth(406);

        }
        //lstQueue.setItems(mediaManager.getPlayOrder());
        lstQueue.refresh();
    }

    /**
     * Flytter en sang i vores nuværende playliste op, hvis indexet valgt ikke et toppen.
     */
    public void handleMoveUpList() {
        if (lstQueue.getSelectionModel().getSelectedIndex() != 0 && lstQueue.getSelectionModel().getSelectedItem() != null) {
            Collections.swap(lstQueue.getItems(), lstQueue.getSelectionModel().getSelectedIndex(), lstQueue.getSelectionModel().getSelectedIndex() - 1);
            lstQueue.getSelectionModel().select(lstQueue.getSelectionModel().getSelectedIndex() - 1);
        }
        lstQueue.refresh();
    }

    /**
     * Flytter en sang i vores nuværende playliste ned, hvis ikke indexet er bunden af listen
     */
    public void handleMoveDownList() {
        if (lstQueue.getSelectionModel().getSelectedIndex() != lstQueue.getItems().size() - 1 && lstQueue.getSelectionModel().getSelectedItem() != null) {
            Collections.swap(lstQueue.getItems(), lstQueue.getSelectionModel().getSelectedIndex(), lstQueue.getSelectionModel().getSelectedIndex() + 1);
            lstQueue.getSelectionModel().select(lstQueue.getSelectionModel().getSelectedIndex() + 1);
        }
        lstQueue.refresh();
    }

}
