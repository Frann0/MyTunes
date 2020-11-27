package bll;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DragAndDropHandler {

    public void handleDragDropped(List<File> selectedFiles, Playlist currentPlaylist) {
        selectedFiles.sort((Comparator.comparing(File::getName)));
        for (File f : selectedFiles){
            Song s = new Song(f);
            currentPlaylist.addSong(s);
        }
    }
}
