import java.io.File;
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;

public class MusicDatabase {

    private Hashtable<String, ArrayList<PlayableItem>> songNames;
    private TreeMap<String, ArrayList<PlayableItem>> artists;
    private int size;

    public MusicDatabase() {
        size = 0;
        songNames = new Hashtable<>();
        artists = new TreeMap<>();

    }

    public boolean addSongs(File inputFile) {
        try {
            List<String> lines = Files.readAllLines(inputFile.toPath());
            for (int i = 0; i < lines.size(); i++) {
                if (i == 0) {
                    continue;
                } else {
                    String line = lines.get(i);
                    String[] info = line.split(",");
                    int idxName = 0;
                    int idxArtist = 1;
                    int idxPop = 2;
                    int idxURL = 4;
                    int idxDur = 3;
                    addSong(info[idxName], info[idxArtist], Integer.parseInt(info[idxPop]),
                            Integer.parseInt(info[idxDur]), info[idxURL]);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void addSong(String name, String artist, int popularity, int duration, String url) {
        PlayableItem currSong = new PlayableItem(name, artist, popularity, 0,  duration, url);
        // checks if songNames has a linked list associated with the given songs name
        if (songNames.containsKey(name)) {
            ArrayList<PlayableItem> sameName = songNames.get(currSong.getSongName());
            PlayableItem similar = null;
            // checks if any of the items in the ArrayList are the same as the current
            for (PlayableItem item : sameName) {
                if (item.isSameSong(currSong)) {
                    similar = item;
                    break;
                }
            }
            // if current is already in it updates popularity and returns
            if (similar != null) {
                similar.setPopularity(currSong.getPopularity());
                return;
            }
            // if not there adds it
            sameName.add(currSong);
        // if the Song name is not in the HashTable creates a new Arraylist
        // with the current object and adds it to the hashtable;
        } else {
            ArrayList<PlayableItem> newSong = new ArrayList<>();
            newSong.add(currSong);
            songNames.put(name, newSong);
        }
        if (artists.containsKey(currSong.getArtist())) {
            ArrayList<PlayableItem> artistsArray = artists.get(currSong.getArtist());
            artistsArray.add(currSong);
        } else {
            ArrayList<PlayableItem> newArtist = new ArrayList<>();
            newArtist.add(currSong);
            artists.put(currSong.getArtist(), newArtist);
        }
        size++;
    }

    public ArrayList<PlayableItem> partialSearchBySongName(String songName) {
        // get the keys of the Hashtable
        Set<String> keys = songNames.keySet();
        ArrayList<PlayableItem> needed = new ArrayList<>();
        // loop through the set of keys
        for (String names : keys) {
            if (names.toLowerCase().contains(songName.toLowerCase())) {
                needed.addAll(songNames.get(names));
            }
        }
        if (needed.isEmpty()) {
            return needed;
        }
        return sorting(needed);
    }

    public ArrayList<PlayableItem> partialSearchByArtistName(String artistName) {
        Set<String> keys = artists.keySet();
        ArrayList<PlayableItem> needed = new ArrayList<>();
        for (String name : keys) {
            if (name.toLowerCase().contains(artistName.toLowerCase())) {
                needed.addAll(artists.get(name));
            }
        }
        if (needed.isEmpty()) {
            return new ArrayList<>();
        }
        return sorting(needed);
    }

    public ArrayList<PlayableItem> searchHighestPopularity(int threshold) {
        ArrayList<PlayableItem> needed = new ArrayList<>();
        Set<String> songSet = songNames.keySet();
        for (String song : songSet) {
            ArrayList<PlayableItem> current = songNames.get(song);
            for (int i = 0; i < current.size(); i++) {
                if (current.get(i).getPopularity() >= threshold) {
                    needed.add(current.get(i));
                }
            }
        }
        if (needed.isEmpty()) {
            return new ArrayList<>();
        }
        return sorting(needed);
    }

    public int size() {
        return size;
    }
    
    
    
    private static ArrayList<PlayableItem> sorting(ArrayList<PlayableItem> array) {
        array.sort(new Comparator<PlayableItem>() {
            public int compare(PlayableItem o1, PlayableItem o2) {
                int sComp = o2.getPopularity() - o1.getPopularity();
                if (sComp != 0) {
                    return sComp;
                }
                sComp = o1.getSongName().compareTo(o2.getSongName());
                if (sComp != 0) {
                    return sComp;
                }
                sComp = o1.getArtist().compareTo(o2.getArtist());

                return sComp;
            }
        });
        return array;
    }

}
