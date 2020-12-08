package bll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Song implements Initializable {
    private Image albumArt = new Image("Resources/DefaultAlbumArt.png");
    private final StringProperty songName = new SimpleStringProperty("");
    private final StringProperty artist = new SimpleStringProperty("");
    private final StringProperty genre = new SimpleStringProperty("");
    private final StringProperty durationString = new SimpleStringProperty("");
    private String path;
    private final Media m;

    /**
     * Constructor for song klassen.
     *
     * @param f filen der skal laves til en sang.
     */
    public Song(File f) {
        this.m = new Media(f.toURI().toString());
        this.path = f.toURI().toString();

        MediaPlayer mdp = new MediaPlayer(m);

        mdp.setOnReady(()->{
            double millis = getMedia().getDuration().toMillis();
            long minutes = TimeUnit.MILLISECONDS.toMinutes((long) millis);
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis) - (minutes * 60);
            if (seconds > 10) {
                setDurationString(minutes + ":" + seconds);
            }else{
                setDurationString(minutes + ":0" + seconds);
            }

            setSongName(m.getMetadata().get("title").toString());
            setArtist(m.getMetadata().get("artist").toString());
            setAlbumArt(m.getMetadata().get("image") != null ? (Image) m.getMetadata().get("image") : albumArt);
            setGenre(m.getMetadata().get("genre").toString());
        });
    }

    /**
     * setter for sangens navn
     * @param songName navnet på sangen
     */
    public void setSongName(String songName) {
        this.songName.set(songName);
    }


    /**
     * setter for kunstneren
     * @param artist kunstneren af sangen
     */
    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    /**
     * setter for genren
     * @param genre genren af sangen
     */
    public void setGenre(String genre){
        this.genre.set(genre);
    }

    /**
     * setter for albumart
     * @param albumArt billede af albummet sangen er fra
     */
    public void setAlbumArt(Image albumArt) {
        this.albumArt = albumArt;
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

    public String getGenre() {
        return genre.get();
    }

    public String getDurationString() {
        return durationString.get();
    }

    /**
     * overrider toString metoden, for at vise de ting vi vil vise i listviewet.
     * TODO skal laves om. der skal mere info på, så som duration.
     *
     * @return
     */
    @Override
    public String toString() {
        return getArtist() + " - " + getSongName() + " - " + getGenre();
    }

    /**
     * getter for hvor vi er i sangen p.t
     * TODO undersøg om det er i media man finder det, eller om det er i mediaPlayeren.
     *
     * @return hvor langt vi er i sangen.
     */
    public String getCurrentTime() {
        return "0:00";
    }

    /**
     * getter for hvor lang sangen er i helhed.
     *
     * @return
     */
    public StringProperty getDuration() {
        return durationString;
    }

    public int getDurationInSeconds(){
        String duration = durationString.getValue();
        String[] secArr = duration.split(":");
        int minutesToSeconds = 60 * (Integer.parseInt(secArr[0]));
        int seconds = Integer.parseInt(secArr[1]);
        return minutesToSeconds + seconds;
    }

    /**
     * setter for durationString, vi sætter længden af filen til tekst.
     * OBS den er private, fordi vi ikke vil lade brugeren
     * definere hvor lang sangen er. Det kunne blive noget juks.
     * @param durationString længden på filen.
     */
    private void setDurationString(String durationString) {
        this.durationString.set(durationString);
    }
}
