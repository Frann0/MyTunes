package bll;

import dal.dbSongDAO;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbSongManager {
    dbSongDAO dbmySongDAO;

    public dbSongManager(){
        dbmySongDAO = new dbSongDAO();
    }

    public void addSong(dbSong song) throws SQLException {
        dbmySongDAO.addSong(song);
    }

    public List<Song> getAllSongs() throws SQLException {
        List<dbSong> allDbSongs = dbmySongDAO.getAllSongs();
        List<Song> allSongs = new ArrayList<>();
        for(dbSong song : allDbSongs){
            String path = song.getFilePath();
            File file = new File(path);
            allSongs.add(new Song(file));
        }

        return allSongs;


    }

    public void deleteSong(Song song) throws SQLException {
        dbmySongDAO.deleteSong(song);
    }

    public void updateSong(dbSong newSong) throws SQLException {
        dbmySongDAO.editSong(newSong);
    }

}
