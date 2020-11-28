package dal;

import bll.Playlist;
import bll.PlaylistHandler;

public class MyTunesDAO {

    private PlaylistHandler pl;

    public void setPl(PlaylistHandler pl) {
        this.pl = pl;
    }

    public void saveFile(){
        for (Playlist p : pl.getPlaylists()){
            //System.out.println(p.getName());
        }
    }
}
