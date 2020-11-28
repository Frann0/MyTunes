package bll;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DragAndDropHandler {
    //måske overflødig, men her bruger vi "responsibility-driven design"
    //altså det der med at hver klasse har en bestemt rolle de skal gøre.

    /**
     * håndtere vores droppede filer, laver alle de filer til sange og tilføjer dem til vores playliste
     * @param selectedFiles de filer der er dropped.
     * @param currentPlaylist den nuværende playliste.
     */
    public void handleDragDropped(List<File> selectedFiles, Playlist currentPlaylist) {
        selectedFiles.sort((Comparator.comparing(File::getName)));
        for (File f : selectedFiles){
            Song s = new Song(f);
            currentPlaylist.addSong(s);
        }
    }
}
