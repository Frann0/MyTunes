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

    }

    public dbSong(File f) {
        Media m = new Media(f.toURI().toString());

        System.out.println(f.toURI().toString());

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

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getArtist() {
        return artist.get();
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(String durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getDurationString() {
        return durationString.get();
    }

    public Image getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Image albumArt) {
        this.albumArt = albumArt;
    }

    @Override
    public String toString() {
        return title.get() + " " + genre.get() + " " + durationString.get() + " " + filePath + " " + artist.get();
    }

/*
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        dbSong dbsong = (dbSong) o;
        return
    }*/

}
