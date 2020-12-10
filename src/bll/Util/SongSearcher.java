package bll.Util;

import bll.Song;
import bll.dbSong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {
    private static ObservableList<dbSong> OLreturn = FXCollections.observableArrayList();

    public static ObservableList<dbSong> search(List<dbSong> currentSongs, String query){
        OLreturn.clear();
        List<dbSong> results = new ArrayList<>(currentSongs);
        results.removeIf(song -> !(song.getTitle().toLowerCase().contains(query.toLowerCase())
                ||song.getArtist().toLowerCase().contains(query.toLowerCase())));

        OLreturn.addAll(results);
        return OLreturn;
    }
}
