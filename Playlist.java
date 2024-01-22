import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Random;

public class Playlist {

    private String name;
    private int playingMode = 0;
    private int playingIndex = 0;
    private PlayableItem cur;
    private Stack<PlayableItem> history;
    private PriorityQueue<PlayableItem> freqListened;
    private ArrayList<PlayableItem> playlist;

    public Playlist() {
        name = "Default";
        history = new Stack<>();
        freqListened = new PriorityQueue<>();
        playlist = new ArrayList<>();
        cur = null;
    }

    public Playlist(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int size() {
        return playlist.size();
    }

    public void addPlayableItem(PlayableItem newItem) {
        playlist.add(newItem);
        if (playingMode == 2) {
            freqListened = new PriorityQueue<>();
            freqListened.addAll(playlist);

        }
    }

    public void addPlayableItem(ArrayList<PlayableItem> newItem) {
        playlist.addAll(newItem);
        //for (PlayableItem item : newItem) {
        //freqListened.add(item);
        //}
        if (playingMode == 2) {
            freqListened = new PriorityQueue<>();
            freqListened.addAll(playlist);
        }
    }

    public boolean removePlayableItem(int number) {
        if (number >= playlist.size() || number < 0) {
            return false;
        } else {
            //new
            if (playingMode == 2) {
                freqListened.remove(playlist.get(number));
            }
            playlist.remove(number);
            return true;
        }
    }

    public void switchPlayingMode(int newMode) {
        if (newMode == 0) {
            if (playingMode != 0) {
                playingIndex = -1;
                // added
                history.clear();
            }
            playingMode = newMode;
            //history.clear();
        } else if (newMode == 1) {
            history.clear();
            playingMode = newMode;

        } else {
            // added now
            if (playingMode != 2) {
                freqListened = new PriorityQueue<>();
                freqListened.addAll(playlist);
                history.clear();
            }
           // history.clear();
            playingMode = 2;
            playingIndex = 0;
        }
    }

    public void goBack() {
        if (history.isEmpty()) {
            System.out.println("No more step to go back");
        } else {
            cur = history.pop();
            playingIndex--;
        }

    }

    public void play(int seconds) {
        if (cur == null) {
            //cur = playlist.get(0);
            cur = this.goForward();
        }

        int curSec = 0;
        while (curSec < seconds) {
            curSec = helpPlay(curSec, seconds, cur);
            if (curSec == seconds) {
                return;
            }
            cur = this.goForward();
            if (cur == null) {
                System.out.println("No more music to play.");
                return;
            }

        }
    }

    private int helpPlay(int curSec, int seconds, PlayableItem current) {
        String needsPrinting = "Seconds " + curSec + " : " + cur.getSongName() + " start.";
        System.out.println(needsPrinting);
        while (!current.isComplete()) {
            if (curSec >= seconds) {
                return seconds;
            } else {
                current.atomicPlay();
                curSec++;
            }
        }
        String endPrint = "Seconds " + curSec + " : " + cur.getSongName() + " complete.";
        System.out.println(endPrint);
        cur.atomicPlay();
        curSec++;
        return curSec;
    }

    public String showPlaylistStatus() {
        String curPlay = " - Currently play";
        String result = "";
        for (int i = 0; i < playlist.size(); i++) {
            if (playlist.get(i) == cur) {
                result += i + ". " + playlist.get(i).toString() + curPlay + "\n";
            } else {
                result += i + ". " + playlist.get(i).toString() + "\n";
            }
        }
        return result;
    }
    
    public PlayableItem goForward() {
        if (playingMode == 0) {
            if (cur == null && playlist.size() > 0) {
                cur = playlist.get(0);
                playingIndex = 0;
                return cur;
            }
            if (playingIndex == -1 && playlist.size() > 0) {
                history.add(cur);
                cur = playlist.get(0);
                return cur;
            }
            if (playingIndex + 1 < playlist.size() && cur != null) {
                history.add(cur);
                playingIndex++;
                cur = playlist.get(playingIndex);
                return cur;
            }
            return null;
        } else if (playingMode == 1) {
            if (cur != null) {
                history.add(cur);
            }
            Random random = new Random();
            int randomIndex = random.nextInt(playlist.size());
            cur = playlist.get(randomIndex);
            playingIndex = randomIndex;
            return cur;
        } else {
            if (cur != null) {
                history.add(cur);
            }
            cur = freqListened.poll();
            return cur;
        }

        /*
        if (playingMode == 0) {
            if (cur == null & playlist.size() > 0) {
                cur = playlist.get(0);
                playingIndex = 0;
                history.add(cur);
                return cur;
            }
            if (history.isEmpty()) {
                history.add(cur);
            }
            if (playingIndex + 1 < playlist.size()) {
                playingIndex += 1;
                cur = playlist.get(playingIndex);
                history.add(cur);
                return cur;
            }
            return null;
        } else if (playingMode == 1) {
            Random random = new Random();
            int randomIndex = random.nextInt(playlist.size());
            cur = playlist.get(randomIndex);
            playingIndex = randomIndex;
            history.add(cur);
            return cur;
        } else {
            cur = freqListened.poll();
            history.add(cur);
            return cur;
        }

         */
    }

    /*
    public static void main(String[] args) {
        Playlist Milan = new Playlist("Milan");
        Milan.switchPlayingMode(1);
        PlayableItem ez = new PlayableItem("video2", "Milan", 9, 0, 80, "ab");
        PlayableItem az = new PlayableItem("video3", "the", 8, 12, 114, "bc");
        PlayableItem emez = new PlayableItem("video1", "dogs", 10, 20, 100, "ac");
        PlayableItem who = new PlayableItem("who", "let", 10,32, 104, "abc");
        Milan.addPlayableItem(emez);
        Milan.addPlayableItem(ez);
        Milan.addPlayableItem(az);
        Milan.addPlayableItem(who);
        Milan.play(11100);
        Milan.switchPlayingMode(2);
        Milan.play(400);
    }

     */

}
