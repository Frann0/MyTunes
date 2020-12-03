package bll;

public class dbSong {
    private String title;
    private String artist;
    private int durationInSeconds;

    public dbSong(String title, String artist, int durationInSeconds){
        this.title = title;
        this.artist = artist;
        this.durationInSeconds = durationInSeconds;
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

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    @Override
    public String toString() {
        return "dbSong{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", durationInSeconds=" + durationInSeconds +
                '}';
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
