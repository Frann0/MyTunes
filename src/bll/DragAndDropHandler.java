package bll;

import be.dbSong;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DragAndDropHandler {
    //måske overflødig, men her bruger vi "responsibility-driven design"
    //altså det der med at hver klasse har en bestemt rolle de skal gøre.


    /**
     * håndtere vores droppede filer, laver alle de filer til sange, tilføjer dem til en liste
     * returnere derefter den liste.
     * @param selectedFiles de filer der er dropped.
     * @return listen af de nye sange.
     */
    public ArrayList<dbSong> handleDragDropped(List<File> selectedFiles) {
        ArrayList<dbSong> songs = new ArrayList<>();
        selectedFiles.sort((Comparator.comparing(File::getName)));
        for (File f : selectedFiles){
            dbSong s = new dbSong(f);

            MediaPlayer mp = new MediaPlayer(new Media(s.getFilePath()));
            mp.setOnReady(() -> {
                songs.add(s);
            });
        }
        return songs;
    }

}

