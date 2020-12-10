package dal;

import bll.Playlist;
import bll.dbSong;

import java.sql.SQLException;
import java.util.List;

public class dbTester {


    public static void main(String[] args) throws SQLException {
        dbSongDAO s = new dbSongDAO();
        dbPlaylistDAO p = new dbPlaylistDAO();

        String pl1 = "Test1";
        String pl2 = "Test2";
        String pl3 = "Test3";


        /*
        dbSong song1 = new dbSong("title1", "rock", "201", "c/:someadress721" ,"Birger1");
        dbSong song2 = new dbSong("title2", "rock", "202", "c/:someadress722" ,"Birger2");
        dbSong song3 = new dbSong("title3", "rock", "203", "c/:someadress423" ,"Birger3");
        dbSong song4 = new dbSong("title4", "rock", "204", "c/:someadress344" ,"Birger4");
        dbSong song5 = new dbSong("title5", "rock", "205", "c/:someadress655" ,"Birger5");
        dbSong song6 = new dbSong("title6", "rock", "206", "c/:someadress216" ,"Birger6");
        dbSong song7 = new dbSong("title7", "rock", "201", "c/:someadress7" ,"Birger7");
        dbSong song8 = new dbSong("title8", "rock", "202", "c/:someadress72" ,"Birger8");
        dbSong song9 = new dbSong("title9", "rock", "203", "c/:someadress43" ,"Birger9");
        dbSong song10 = new dbSong("title10", "rock", "204", "c/:someadress34" ,"Birger10");
        dbSong song11 = new dbSong("title11", "rock", "205", "c/:someadress65" ,"Birger11");
        dbSong song12 = new dbSong("title12", "rock", "206", "c/:someadress26" ,"Birger12");

        s.addSong(song1);
        s.addSong(song2);
        s.addSong(song3);
        s.addSong(song4);
        s.addSong(song5);
        s.addSong(song6);
        s.addSong(song7);
        s.addSong(song8);
        s.addSong(song9);
        s.addSong(song10);
        s.addSong(song11);
        s.addSong(song12);



        p.addSongToPlaylist(pl1, song1);
        p.addSongToPlaylist(pl1, song2);
        p.addSongToPlaylist(pl1, song3);
        p.addSongToPlaylist(pl2, song4);
        p.addSongToPlaylist(pl2, song5);
        p.addSongToPlaylist(pl2, song6);
        p.addSongToPlaylist(pl3, song7);
        p.addSongToPlaylist(pl3, song8);
        p.addSongToPlaylist(pl3, song9);
        p.addSongToPlaylist("TestNew", song10);
        p.addSongToPlaylist("TestNew", song11);
        p.addSongToPlaylist("TestNew", song12);*/


        //p.removeSongFromPlaylist(pl1, song);
        //p.deletePlaylist(pl1);

        //p.editPlaylistName(pl1, "TestNew");

        /*
        List<dbSong> playlist = p.getPlaylist(pl3);
        for(dbSong song : playlist){
            System.out.println(song);
        }*/

        List<Playlist> allPlaylists = p.getAllPlaylists();
        for(int i = 0; i < allPlaylists.size(); i++){
            List<dbSong> songs =  allPlaylists.get(i).getSongs();
            for (dbSong song : songs){
                System.out.print(allPlaylists.get(i).getName() + " ,");
                System.out.print(song.getTitle() + " ,");
                System.out.println(song.getArtist());

            }
        }



    }



}
