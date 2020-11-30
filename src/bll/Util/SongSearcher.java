package bll.Util;

import bll.Song;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {

    public static List<Song> search(List<Song> currentSongs, String query){
        List<Song> results = new ArrayList<>(currentSongs);
        results.removeIf(song -> !(song.getSongName().toLowerCase().contains(query.toLowerCase())
                || song.getAlbumTitle().toLowerCase().contains(query.toLowerCase())
                ||song.getArtist().toLowerCase().contains(query.toLowerCase())));

        return results;
    }
}
