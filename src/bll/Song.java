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

    public Song(File f){
        this.m = new Media(f.toURI().toString());
        this.path = f.toURI().toString();
    }

    //TODO VIRKER IKKE D:
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public String getArtist() {
        return this.m.getMetadata().get("artist").toString();
    }

    public String getAlbumTitle() {
        return this.m.getMetadata().get("album").toString();
    }

    public String getSongName() {
        return this.m.getMetadata().get("title").toString();
    }

    public String getPath() {
        return m.getSource();
    }

    public Media getMedia() {
        return m;
    }

    public Image getAlbumArt() {
        return m.getMetadata().get("image") != null ? (Image) m.getMetadata().get("image") : albumArt;
    }

    public void setAlbumArt(Image albumArt) {
        this.albumArt = albumArt;
    }

    public void updateMedia(){
        songName.setValue(m.getMetadata().get("title").toString());
        artist.setValue(m.getMetadata().get("artist").toString());
        albumTitle.setValue(m.getMetadata().get("album").toString());
    }

    @Override
    public String toString() {
        return String.format("%s%45s%50s",artist.getValue(),albumTitle.getValue(),songName.getValue());
    }

    public String getCurrentTime() {
        //TODO Lav current time funktion
        return "0:00";
    }

    public String getDuration(){
        return String.valueOf(m.getDuration().toSeconds());
    }
}
