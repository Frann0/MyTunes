package dal;

import bll.dbSong;

import java.sql.SQLException;
import java.util.List;

public class dbTester {


    public static void main(String[] args) throws SQLException {
        dbSongDAO s = new dbSongDAO();
        dbPlaylistDao p = new dbPlaylistDao();

        String pl1 = "Test1";
        /*
        dbSong song = new dbSong("title", "rock", "201", "c/:someadress" ,"Birger");
        dbSong song2 = new dbSong("title2", "rock", "202", "c/:someadress2" ,"Birger2");
        dbSong song3 = new dbSong("title3", "rock", "203", "c/:someadress3" ,"Birger3");
        dbSong song4 = new dbSong("title4", "rock", "204", "c/:someadress4" ,"Birger4");
        dbSong song5 = new dbSong("title5", "rock", "205", "c/:someadress5" ,"Birger5");
        dbSong song6 = new dbSong("title6", "rock", "206", "c/:someadress6" ,"Birger6"); */

        /*
        s.addSong(song);
        s.addSong(song2);
        s.addSong(song3);
        s.addSong(song4);
        s.addSong(song5);
        s.addSong(song6); */

        //p.addPlaylist("Test1");

        /*
        p.addSongToPlaylist(pl1, song);
        p.addSongToPlaylist(pl1, song2);
        p.addSongToPlaylist(pl1, song3);
        p.addSongToPlaylist(pl1, song4);
        p.addSongToPlaylist(pl1, song5);
        p.addSongToPlaylist("TestNew", song6);  */


        //p.removeSongFromPlaylist(pl1, song);
        //p.deletePlaylist(pl1);

        //p.editPlaylistName(pl1, "TestNew");

        List<dbSong> playlist = p.getPlaylist(pl1);
        for(dbSong song : playlist){
            System.out.println(song);
        }

    }



}
