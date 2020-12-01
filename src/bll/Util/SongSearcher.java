package bll.Util;

import bll.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {
    private static ObservableList<Song> OLreturn = FXCollections.observableArrayList();

    public static ObservableList<Song> search(List<Song> currentSongs, String query){
        OLreturn.clear();
        List<Song> results = new ArrayList<>(currentSongs);
        results.removeIf(song -> !(song.getSongName().toLowerCase().contains(query.toLowerCase())
                ||song.getArtist().toLowerCase().contains(query.toLowerCase())));

        OLreturn.addAll(results);
        return OLreturn;
    }
}
