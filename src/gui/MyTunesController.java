package gui;

import bll.*;
import bll.Util.SongSearcher;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.io.File;
import java.net.URL;
import java.util.*;

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
    private Label lblAlbumTitle;
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
    private ListView<Playlist> lstPlaylist;
    @FXML
    private ListView<Song> lstCurrentPlayList;
    @FXML
    private ListView<Song> lstQueue;
    @FXML
    private MaterialDesignIconView icnRepeat;
    @FXML
    private TableView<Song> tblAllsongs;
    @FXML
    private TableColumn<Song, String> tblClmArtist;
    @FXML
    private TableColumn<Song, String> tblClmSongTitle;
    @FXML
    private TableColumn<Song, String> tblClmGenre;
    @FXML
    private TableColumn<Song, String> tblClmTime;
    @FXML
    private TableColumn tblClmSpacer;
    @FXML
    private MaterialDesignIconView tglPlay;
    @FXML
    private TextField txtSearchField;
    @FXML
    private VBox vboxQueue;

    private Playlist currentPlaylist;
    private final ObservableList<Song> allSongs = FXCollections.observableArrayList();

    private final PlaylistHandler playlistHandler = new PlaylistHandler();
    private final MyTunesModel myTunesModel = new MyTunesModel();
    private final MediaManager mediaManager = new MediaManager(currentPlaylist);
    private final DragAndDropHandler dragAndDropHandler = new DragAndDropHandler();

    private boolean isMuted;
    private boolean isPlaying;
    private double prevVolume;
    private boolean shuffleActive;
    private boolean repeatActive;
    private boolean queueShowing;

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
        queueShowing = false;

        playlistHandler.addPlaylist("test1", mediaManager);
        playlistHandler.addPlaylist("test2", mediaManager);
        playlistHandler.addPlaylist("test3", mediaManager);

        lstPlaylist.setItems(playlistHandler.getPlaylists());

        if (!lstPlaylist.getItems().isEmpty()) {
            lstPlaylist.getSelectionModel().select(0);
            Playlist firstPlaylist = lstPlaylist.getItems().get(0);
            lblCurrentPlaylist.setText(firstPlaylist.getName());
            tblAllsongs.setItems(firstPlaylist.getSongs());
            currentPlaylist = firstPlaylist;
        }

        tblClmArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        tblClmSongTitle.setCellValueFactory(new PropertyValueFactory<>("songName"));
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
    }

    //PLAYLIST FUNKTIONER

    /**
     * Håndtere hvad der skal ske når vi vælger en af vores playlister.
     */
    public void handlePlaylistSelect() {

        if (lstPlaylist.getSelectionModel().getSelectedItem() != null) {
            currentPlaylist = lstPlaylist.getSelectionModel().getSelectedItem();
            lblCurrentPlaylist.setText(currentPlaylist.getName());
            lstQueue.setItems(currentPlaylist.getSongs());

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
        result.ifPresent(navn -> playlistHandler.addPlaylist(navn, mediaManager));
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

    //SANG FUNKTIONER

    /**
     * Håndtere vores søge funktion. Og opdatere vores listview efter det.
     *
     */
    public void handleSearch() {
        if (!txtSearchField.getText().isEmpty() || txtSearchField.getText() != null && !tblAllsongs.getItems().isEmpty()){
            tblAllsongs.setItems(SongSearcher.search(allSongs,txtSearchField.getText()));
        }
        checkEmptySongList();
    }

    /**
     * Håndtere vores manuelle tilføj sang funktion.
     */
    public void handleAddSong() {
        currentPlaylist = playlistHandler.getPlaylists().get(lstPlaylist.getSelectionModel().getSelectedIndex());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a song you want to add to your playlist");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sound files (*.wav , *.mp3)", "*.wav" , "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (selectedFile != null) {
            Song s = new Song(selectedFile);
            MediaPlayer mp = new MediaPlayer(s.getMedia());
            mp.setOnReady(() -> {
                allSongs.add(s);
                checkEmptySongList();
            });
        }
    }

    /**
     * Håndtere hvad der skal ske når vi vil slette en sang fra vores playliste
     */
    public void handleRemoveSong() {
        allSongs.remove(tblAllsongs.getSelectionModel().getSelectedItem());
        currentPlaylist.removeSong(tblAllsongs.getSelectionModel().getSelectedItem());
        checkEmptySongList();
    }

    public void handleEditSong(ActionEvent actionEvent) {

    }

    /**
     * Håndtere hvad der skal ske når vi vælger en sang fra listen.
     */
    public void handleSongSelect() {
        if (lstQueue.getSelectionModel().getSelectedItem() != null) {
            Song cSong = lstQueue.getSelectionModel().getSelectedItem();

            mediaManager.setMedia(cSong.getMedia());

            lblTimeMin.setText(cSong.getCurrentTime());
            lblTimeMax.setText(cSong.getDuration().get());
            imgAlbumArt.setImage(cSong.getAlbumArt());
            lblArtist.setText(cSong.getArtist());
            lblSongTitle.setText(cSong.getSongName());
            lblTitlebar.setText("Codify - " + cSong.getSongName() + " by " + cSong.getArtist());

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

        if (!shuffleActive){
            icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 15");
            icnShuffle.setOnMouseExited(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 15"));
            shuffleActive = true;
        }else{
            icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 15");
            icnShuffle.setOnMouseExited(mouseEvent -> icnShuffle.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 15"));
            shuffleActive = false;
        }
        mediaManager.setCurrentPlaylist(currentPlaylist);
        mediaManager.shuffle(shuffleActive);
        lstQueue.setItems(mediaManager.getPlayOrder());
    }

    /**
     * Håndter om den nuværende sang skal køre på repeat.
     */
    public void handleRepeat() {
        icnRepeat.setOnMouseEntered(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: white; -fx-font-size: 20"));

        if (!repeatActive){
            icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #71BA51; -fx-font-size: 20");
            icnRepeat.setOnMouseExited(mouseEvent -> icnRepeat.setStyle("-fx-font-family: 'Material Design Icons'; -fx-fill: #71BA51; -fx-font-size: 20"));
            repeatActive = true;
        }else{
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
     * @param dragEvent dragEvent, hvor fra vi får de filer der bliver sluppet.
     */
    public void handleDragDropped(DragEvent dragEvent) {
        List<File> selectedFiles = dragEvent.getDragboard().getFiles();
        ArrayList<Song> songList = dragAndDropHandler.handleDragDropped(selectedFiles);

        MediaPlayer mp = new MediaPlayer(new Media(selectedFiles.get(0).toURI().toString()));
        mp.setOnReady(() -> {
            for (Song s : songList){
                allSongs.add(s);
            }
            tblAllsongs.refresh();
            checkEmptySongList();
        });
    }

    /**
     * Håndtere hvad der skal ske når vi trækker en fil henover vores program.
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

    /**
     * Checker hvorvidt vores nuværende playliste indeholder sang elementer.
     * Hvis ikke, så skal baggrunden være vores "Tutorial" altså vores
     * "Oh no!" billede, ellers skal den være blank.
     */
    private void checkEmptySongList() {
        if (tblAllsongs.getItems().size() <= 0) {
            tblAllsongs.styleProperty().set("-fx-background-image: url(\"/Resources/emptyList.png\")");
        } else {
            tblAllsongs.styleProperty().set("-fx-background-image: url(\"/Resources/empty.png\")");
        }
        tblAllsongs.refresh();
    }

    /**
     * Opdatere alle vores medier i vores nuværende playliste listview.
     * fikser null metadata problem, ved at reloade alle sangenes titler osv
     * da metadata fra fil, ikke er tilgængeligt med det samme.
     */
    private void updateMediaList(){
        for(Song s : currentPlaylist.getSongs()){
            s.updateMedia();
        }

        for (Song s : mediaManager.getPlayOrder()){
            s.updateMedia();
        }
    }

    public void handleAddQueue(ActionEvent actionEvent) {
        if (tblAllsongs.getSelectionModel().getSelectedItem() != null){
            mediaManager.getPlayOrder().add(tblAllsongs.getSelectionModel().getSelectedItem());
            mediaManager.getUnShuffledPlayOrder().add(tblAllsongs.getSelectionModel().getSelectedItem());
            currentPlaylist.addSong(tblAllsongs.getSelectionModel().getSelectedItem());
        }
    }

    public void handleRemoveQueue(ActionEvent actionEvent) {
        if (lstQueue.getSelectionModel().getSelectedItem() != null){
            mediaManager.getPlayOrder().remove(lstQueue.getSelectionModel().getSelectedItem());
            mediaManager.getUnShuffledPlayOrder().remove(lstQueue.getSelectionModel().getSelectedItem());
            currentPlaylist.removeSong(lstQueue.getSelectionModel().getSelectedItem());
        }
    }

    public void handleShowQueue() {
        icnQueue.setOnMouseEntered(mouseEvent -> icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: white; -fx-font-size: 20"));

        if (!queueShowing){
            icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 20");
            icnQueue.setOnMouseExited(mouseEvent -> icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #71BA51; -fx-font-size: 20"));

            queueShowing = true;
            vboxQueue.setVisible(true);
            vboxQueue.setMaxWidth(240);
            tblClmSpacer.setPrefWidth(172);
        } else{
            icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 20");
            icnQueue.setOnMouseExited(mouseEvent -> icnQueue.setStyle("-fx-font-family: FontAwesome; -fx-fill: #4f4f4f; -fx-font-size: 20"));
            vboxQueue.setMaxWidth(0);
            queueShowing = false;
            vboxQueue.setVisible(false);
            tblClmSpacer.setPrefWidth(406);

        }
        lstQueue.setItems(mediaManager.getPlayOrder());
        lstQueue.refresh();
    }

    public void handleMoveUpList(ActionEvent actionEvent) {
    }

    public void handleMoveDownList(ActionEvent actionEvent) {
    }
}
