package bll;

public class dbSong {
    private String title;
    private String genre;
    private String filePath;
    private String artist;
    private String durationInSeconds;

    public dbSong(String title, String genre, String durationInSeconds, String filePath, String artist){
        this.title = title;
        this.artist = artist;
        this.durationInSeconds = durationInSeconds;
        this.genre = genre;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(String durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    @Override
    public String toString() {
        return title + " " + genre + " " + durationInSeconds + " " + filePath + " " + artist;
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
