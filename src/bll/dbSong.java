package bll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class dbSong {

    private Image albumArt = new Image("Resources/DefaultAlbumArt.png");
    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty artist = new SimpleStringProperty("");
    private final StringProperty genre = new SimpleStringProperty("");
    private final StringProperty durationString = new SimpleStringProperty("");
    private String filePath;
    private String durationInSeconds;

    public dbSong(String title, String genre, String durationInSeconds, String filePath, String artist){

        Media m = new Media(filePath);
        MediaPlayer mdp = new MediaPlayer(m);

        mdp.setOnReady(()-> {
            double millis = m.getDuration().toMillis();
            long minutes = TimeUnit.MILLISECONDS.toMinutes((long) millis);
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis) - (minutes * 60);
            if (seconds > 10) {
                durationString.set(minutes + ":" + seconds);
            } else {
                durationString.set(minutes + ":0" + seconds);
            }
            setAlbumArt(m.getMetadata().get("image") != null ? (Image) m.getMetadata().get("image") : albumArt);
            setTitle(title);
            setArtist(artist);
            setGenre(genre);
            setFilePath(filePath);
            setDurationInSeconds(durationInSeconds);
        });
        setTitle(title);
        setArtist(artist);
        setGenre(genre);
        setFilePath(filePath);
        setDurationInSeconds(durationInSeconds);

    }

    public dbSong(File f) {
        Media m = new Media(f.toURI().toString());

        MediaPlayer mdp = new MediaPlayer(m);

        mdp.setOnReady(()->{
            double millis = m.getDuration().toMillis();
            long minutes = TimeUnit.MILLISECONDS.toMinutes((long) millis);
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis) - (minutes * 60);
            if (seconds > 10) {
                durationString.set(minutes + ":" + seconds);
            }else{
                durationString.set(minutes + ":0" + seconds);
            }
            setTitle(m.getMetadata().get("title").toString());
            setArtist(m.getMetadata().get("artist").toString());
            setAlbumArt(m.getMetadata().get("image") != null ? (Image) m.getMetadata().get("image") : albumArt);
            setGenre(m.getMetadata().get("genre").toString());
        });

        setFilePath(f.toURI().toString());
    }

    /**
     * Get the title of dbSong.
     * @return title of dbSong.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Set the title of dbSong.
     * @param title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Get artist of song.
     * @return
     */
    public String getArtist() {
        return artist.get();
    }

    /**
     * Set artist of song.
     * @param artist
     */
    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    /**
     * Get filePath of dbSong.
     * @return
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Set filepath of dbSong.
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Get genre of dbSong.
     * @return
     */
    public String getGenre() {
        return genre.get();
    }

    /**
     * Set genre of dbSong.
     * @param genre
     */
    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    /**
     * Get the duration in seconds of dbSong.
     * @return
     */
    public String getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * Set the duration in seconds of dbSong.
     * @param durationInSeconds
     */
    public void setDurationInSeconds(String durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    /**
     * Get a string with the duration of dbSong.
     * @return
     */
    public String getDurationString() {
        return durationString.get();
    }

    /**
     * Get album art of dbSong.
     * @return
     */
    public Image getAlbumArt() {
        return albumArt;
    }

    /**
     * Set album art of dbSong.
     * @param albumArt
     */
    public void setAlbumArt(Image albumArt) {
        this.albumArt = albumArt;
    }

    /**
     * Sætter for duration string. Bruges til at sikre vi har længden af sangen efter vi editer den.
     * @param duration Længden af sangen formatted som "mm:ss:
     */
    public void setDurationString(String duration){
        this.durationString.set(duration);
    }

    @Override
    public String toString() {
        return artist.get() + " - " + title.get();
    }

}
