/**
 * <b>May not add any accessor/mutator for this class</b> to force them using
 * heap, key point is for the watchedcounts
 */
public class PlayableItem implements Comparable<PlayableItem> {
    private int lastTime;
    private int length;
    private String url;
    private String songName;
    private String artist;
    private int popularity;
    private int playedCounts; // How many time this video has been watched, initially to be 0

    public PlayableItem(String songName, String artist,
                        int popularity, int lastTime, int length, String url) {
        this.songName = songName;
        this.artist = artist;
        this.popularity = popularity;
        this.lastTime = lastTime;
        this.length = length;
        this.url = url;
        playedCounts = 0;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongName() {
        return songName;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int pop) {
        this.popularity = pop;
    }

    public boolean isComplete() {
        if (lastTime >= length) {
            return true;
        }
        return false;
    }

    public void atomicPlay() {
        if (isComplete()) {
            this.lastTime = 0;
            this.playedCounts++;
        } else {
            this.lastTime++;
        }
    }

    public boolean isSameSong(PlayableItem another) {
        if (another == null) {
            return false;
        }
        if (songName.equals(another.songName) && artist.equals(another.artist)
                && length == another.length && url.equals(another.url)) {
            return true;
        }
        return false;
    }

    public String toString() {
        String middle = ",";
        String result;
        result = songName + middle + url + middle + lastTime + middle
                + length + middle + artist + middle + popularity + middle + playedCounts;
        return result;
    }

    public int compareTo(PlayableItem another) {
        return another.playedCounts - playedCounts;
    }


}
