package bll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Song implements Initializable {
    private Image albumArt = new Image("Resources/DefaultAlbumArt.png");
    private StringProperty songName = new SimpleStringProperty();
    private StringProperty albumTitle = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private String path;
    private Media m;

    /**
     * Constructor for song klassen.
     *
     * @param f filen der skal laves til en sang.
     */
    public Song(File f) {
        this.m = new Media(f.toURI().toString());
        this.path = f.toURI().toString();
    }

    //TODO VIRKER IKKE D:
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * getter for artisten der har lavet sangen.
     * Fundet gennem mediaets metadata.
     *
     * @return artisten der har lavet sangen.
     */
    public String getArtist() {
        return this.m.getMetadata().get("artist").toString();
    }

    /**
     * getter for album title, sangen er fra.
     *
     * @return album titlen
     */
    public String getAlbumTitle() {
        return this.m.getMetadata().get("album").toString();
    }

    /**
     * getter for sang navnet.
     *
     * @return sang navnet
     */
    public String getSongName() {
        return this.m.getMetadata().get("title").toString();
    }

    /**
     * Getter for den lokale fil's sti.
     *
     * @return stien til den lokale fil.
     */
    public String getPath() {
        return m.getSource();
    }

    /**
     * getter for mediaet forbundet med denne sang.
     *
     * @return mediaet for denne sang.
     */
    public Media getMedia() {
        return m;
    }

    /**
     * getter for album covered.
     *
     * @return hvis metadataen er der, altså ikke er null, returnere den det billede
     * ellers returnere den vores defualtalbumart.png
     */
    public Image getAlbumArt() {
        return m.getMetadata().get("image") != null ? (Image) m.getMetadata().get("image") : albumArt;
    }

    /**
     * TODO Mangler fiks for at vi ikke skal bruge den her funktion.
     * har en ide om at det er en asyncron task at hente metadata. så man
     * kan ikke bare hente det med det samme sangen er oprettet. derfor
     * null metadata problem. FIND FIKS
     */
    public void updateMedia() {
        if (!m.getMetadata().isEmpty()) {
            songName.setValue(m.getMetadata().get("title").toString());
            artist.setValue(m.getMetadata().get("artist").toString());
            albumTitle.setValue(m.getMetadata().get("album").toString());
        } else {
            songName.setValue("Missing Title");
            artist.setValue("Missing Artist");
            albumTitle.setValue("Missing Album title");
        }
    }

    /**
     * overrider toString metoden, for at vise de ting vi vil vise i listviewet.
     * TODO skal laves om. der skal mere info på, så som duration.
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%-50s%-45s%-50s", artist.getValue(), albumTitle.getValue(), songName.getValue());
    }

    /**
     * getter for hvor vi er i sangen p.t
     * TODO undersøg om det er i media man finder det, eller om det er i mediaPlayeren.
     *
     * @return hvor langt vi er i sangen.
     */
    public String getCurrentTime() {
        //TODO Lav current time funktion
        return "0:00";
    }

    /**
     * getter for hvor lang sangen er i helhed.
     *
     * @return
     */
    public String getDuration() {
        return String.valueOf(m.getDuration().toSeconds());
    }
}
