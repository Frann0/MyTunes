package gui;

import bll.*;
import com.jfoenix.controls.JFXSlider;
import dal.MyTunesDAO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyTunesController implements Initializable {
    @FXML
    private FontAwesomeIconView icnShuffle;
    @FXML
    private MaterialDesignIconView icnRepeat;
    @FXML
    private HBox titlebar;
    @FXML
    private Label lblTimeMin;
    @FXML
    private Label lblTimeMax;
    @FXML
    private FontAwesomeIconView icnMute;
    @FXML
    private Label lblTitlebar;
    @FXML
    private ListView<Song> lstCurrentPlayList;
    @FXML
    private JFXSlider sldVolume;
    @FXML
    private ToggleButton tglPlay;
    @FXML
    private JFXSlider sldTime;
    @FXML
    private ImageView imgAlbumArt;
    @FXML
    private Label lblArtist;
    @FXML
    private Label lblAlbumTitle;
    @FXML
    private Label lblSongTitle;
    @FXML
    private BorderPane root;
    @FXML
    private ListView<Playlist> lstPlaylist;
    @FXML
    private Label lblCurrentPlaylist;

    private final PlaylistHandler playlistHandler = new PlaylistHandler();
    private final MyTunesDAO myTunesDAO = new MyTunesDAO();
    private final MediaManager mediaManager = new MediaManager();
    private final DragAndDropHandler dragAndDropHandler = new DragAndDropHandler();
    private boolean isMuted;
    private boolean isPlaying;
    private double prevVolume;
    private Playlist currentPlaylist;
    private boolean shuffleActive;
    private boolean repeatActive;

    /**
     * Initialize bruger vi til at sætte mange af vores FXML værdier efter de er blevet
     * initialiseret.
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

        handleShufflePlay();
        handleRepeat();
        handleMute();

        playlistHandler.addPlaylist("test1");
        playlistHandler.addPlaylist("test2");
        playlistHandler.addPlaylist("test3");

        lstPlaylist.setItems(playlistHandler.getPlaylists());

        if (!lstPlaylist.getItems().isEmpty()) {
            lstPlaylist.getSelectionModel().select(0);
            Playlist firstPlaylist = lstPlaylist.getItems().get(0);
            lblCurrentPlaylist.setText(firstPlaylist.getName());
            lstCurrentPlayList.setItems(firstPlaylist.getSongs());
            currentPlaylist = firstPlaylist;
        }

        sldTime.setMin(0);
        sldTime.setMax(0);
        sldTime.setValue(0);

        sldVolume.setMin(0);
        sldVolume.setMax(100);
        sldVolume.setValue(50);

        prevVolume = sldVolume.getValue();

    }

    /**
     * Håndtere når vi slipper en fil / filer på vores program.
     * @param dragEvent
     */
    public void handleDragDropped(DragEvent dragEvent) {
        List<File> selectedFiles = dragEvent.getDragboard().getFiles();

        currentPlaylist = playlistHandler.getPlaylists().get(lstPlaylist.getSelectionModel().getSelectedIndex());

        dragAndDropHandler.handleDragDropped(selectedFiles, currentPlaylist);
        checkEmptySongList();
        updateMediaList();
    }

    /**
     * Håndtere hvad der skal ske når vi trækker en fil henover vores program.
     * @param dragEvent
     */
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Håndtere vores pause/unpause knap.
     * TODO Skal opdateres.
     */
    public void handleToggleAction() {
        //TODO Lav getState i mediaManager, og opdater ikon derefter
        if (lstCurrentPlayList.getSelectionModel().getSelectedItem() != null) {
            mediaManager.setMedia(lstCurrentPlayList.getSelectionModel().getSelectedItem().getMedia());
            if (tglPlay.isSelected()) {
                isPlaying = true;
                tglPlay.setText("| |");

            } else {
                isPlaying = false;
                tglPlay.setText(">");
            }
            mediaManager.play(isPlaying);
        }
    }

    /**
     * Håndtere hvad der skal ske når vi vælger en af vores playlister.
     */
    public void handlePlaylistSelect() {

        if (lstPlaylist.getSelectionModel().getSelectedItem() != null) {
            currentPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();
            lblCurrentPlaylist.setText(currentPlaylist.getName());
            lstCurrentPlayList.setItems(currentPlaylist.getSongs());
            checkEmptySongList();
        }
    }

    /**
     * Håndtere hvad der skal ske når vi vil tilføje en ny playliste.
     */
    public void handleAddPlaylist() {
        TextInputDialog dialog = new TextInputDialog("Playlist");
        dialog.setTitle("Add playlist");
        dialog.setHeaderText("Add playlist");
        dialog.setContentText("Please enter your desired playlist name: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(playlistHandler::addPlaylist);
    }

    /**
     * Håndtere vores slet playliste funktion.
     */
    public void handleRemovePlaylist() {
        if (lstPlaylist.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Remove " + lstPlaylist.getSelectionModel().getSelectedItem().getName() + "?");
            alert.setHeaderText("Remove " + lstPlaylist.getSelectionModel().getSelectedItem().getName() + "?");
            alert.setContentText("Are you sure, you want to remove " + lstPlaylist.getSelectionModel().getSelectedItem().getName() + " playlist?\n" +
                    "All songs in the playlist will be lost, and will need to be readded");


            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    playlistHandler.removePlaylist(lstPlaylist.getSelectionModel().getSelectedIndex());
                }
            }
        }
    }

    /**
     * Håndtere hvad der skal ske når vi ville ændre i vores playliste.
     * Det vil sige, opdatere dens navn.
     */
    public void handleEditPlaylist() {
        if (lstPlaylist.getSelectionModel().getSelectedItem() != null) {
            TextInputDialog dialog = new TextInputDialog("New Playlist Name");
            dialog.setTitle("Edit playlist");
            dialog.setHeaderText("Edit playlist");
            dialog.setContentText("Please enter your new playlist name: ");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> playlistHandler.updatePlaylist(lstPlaylist.getSelectionModel().getSelectedIndex(), s));
            lblCurrentPlaylist.setText(lstPlaylist.getSelectionModel().getSelectedItem().getName());
            lstPlaylist.refresh();
        }
    }

    /**
     * Håndtere vores manuelle tilføj sang funktion.
     */
    public void handleAddSong() {
        currentPlaylist = playlistHandler.getPlaylists().get(lstPlaylist.getSelectionModel().getSelectedIndex());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a song you want to add to your playlist");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sound files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        currentPlaylist.addSong(new Song(selectedFile));
        checkEmptySongList();
        updateMediaList();
    }

    /**
     * Håndtere hvad der skal ske når vi vil slette en sang fra vores playliste
     */
    public void handleRemoveSong() {
        Playlist currentPlayList = lstPlaylist.getSelectionModel().getSelectedItem();
        currentPlayList.removeSong(lstCurrentPlayList.getSelectionModel().getSelectedItem());
        checkEmptySongList();
    }

    /**
     * Håndtere hvad der skal ske når vi vælger en sang fra listen.
     */
    public void handleSongSelect() {
        if (lstCurrentPlayList.getSelectionModel().getSelectedItem() != null) {
            Song cSong = lstCurrentPlayList.getSelectionModel().getSelectedItem();

            mediaManager.setMedia(cSong.getMedia());
            //Test for null metadata problem.
            updateMediaList();

            lstCurrentPlayList.refresh();

            lblTimeMin.setText(cSong.getCurrentTime());
            lblTimeMax.setText(mediaManager.getDuration().getValue());
            imgAlbumArt.setImage(cSong.getAlbumArt());
            lblArtist.setText(cSong.getArtist());
            lblAlbumTitle.setText(cSong.getAlbumTitle());
            lblSongTitle.setText(cSong.getSongName());
            lblTitlebar.setText("Codify - " + cSong.getSongName() + " by " + cSong.getArtist());

            sldTime.setMax(100);
        }
    }

    /**
     * Checker hvorvidt vores nuværende playliste indeholder sang elementer.
     * Hvis ikke, så skal baggrunden være vores "Tutorial" altså vores
     * "Oh no!" billede, ellers skal den være blank.
     */
    private void checkEmptySongList() {
        if (lstCurrentPlayList.getItems().size() <= 0) {
            lstCurrentPlayList.styleProperty().set("-fx-background-image: url(\"/Resources/emptyList.png\")");
        } else {
            lstCurrentPlayList.styleProperty().set("-fx-background-image: url(\"/Resources/empty.png\")");
        }
        lstCurrentPlayList.refresh();
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

    /**
     * Kommer til at håndtere shuffle play.
     * Ved at tage alle sangene i nuværende playlist.
     * Sætte dem ind i tilfældig rækkefølge i en ArrayList, og derefter afspille
     * den første sang i den liste.
     */
    public void handleShufflePlay() {
        icnShuffle.setOnMouseEntered(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: white; -fx-font-size: 15"));

        if (shuffleActive){
            icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 15");
            icnShuffle.setOnMouseExited(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 15"));
            shuffleActive = false;
        }else{
            icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 15");
            icnShuffle.setOnMouseExited(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 15"));
            shuffleActive = true;
        }
    }

    /**
     * Håndter om den nuværende sang skal køre på repeat.
     */
    public void handleRepeat() {
        icnRepeat.setOnMouseEntered(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: white; -fx-font-size: 20"));

        if (repeatActive){
            icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #71BA51; -fx-font-size: 20");
            icnRepeat.setOnMouseExited(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #71BA51; -fx-font-size: 20"));
            repeatActive = false;
        }else{
            icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #4f4f4f; -fx-font-size: 20");
            icnRepeat.setOnMouseExited(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #4f4f4f; -fx-font-size: 20"));
            repeatActive = true;
        }
    }

    /**
     * Håndtere vores mute funktion. Man muter ved at trykke på ikonet.
     * Så ikonet, skal opdateres efter det
     */
    public void handleMute() {
        if (isMuted) {
            prevVolume = sldVolume.getValue();
            sldVolume.setValue(0);
            icnMute.setIcon(FontAwesomeIcon.VOLUME_OFF);
            mediaManager.setMute(true);
            isMuted = false;
        } else {
            icnMute.setIcon(FontAwesomeIcon.VOLUME_UP);
            sldVolume.setValue(prevVolume);
            mediaManager.setMute(false);
            isMuted = true;
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

    /**
     * Håndtere vores søge funktion. Og opdatere vores listview efter det.
     *
     */
    public void handleSearch(KeyEvent event) {

    }

    private void updateMediaList(){
        for(Song s : currentPlaylist.getSongs()){
            s.updateMedia();
        }
    }

}
