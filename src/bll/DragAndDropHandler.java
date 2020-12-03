package bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ArrayList<Song> handleDragDropped(List<File> selectedFiles) {
        ArrayList<Song> songs = new ArrayList<>();
        selectedFiles.sort((Comparator.comparing(File::getName)));
        for (File f : selectedFiles){
            Song s = new Song(f);

            MediaPlayer mp = new MediaPlayer(s.getMedia());
            mp.setOnReady(() -> {
                songs.add(s);
            });
        }
        return songs;
    }

}

