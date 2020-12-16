package bll.Util;

import bll.dbSong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {
    private static ObservableList<dbSong> OLreturn = FXCollections.observableArrayList();

    /**
     * Returnere listen af sange som matcher string queryen.
     * @param currentSongs Original liste af sange.
     * @param query Queryen som vi søger efter.
     * @return Returnere liste af sange som matcher queryen.
     */
    public static ObservableList<dbSong> search(List<dbSong> currentSongs, String query){
        OLreturn.clear();
        List<dbSong> results = new ArrayList<>(currentSongs);
        results.removeIf(song -> !(song.getTitle().toLowerCase().contains(query.toLowerCase())
                ||song.getArtist().toLowerCase().contains(query.toLowerCase())));

        OLreturn.addAll(results);
        return OLreturn;
    }
}
