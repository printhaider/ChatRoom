package chatServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transcript {

    // stores all the messages sent by clients
    private List<String> transcript = Collections.synchronizedList(new ArrayList<String>());
    
    public Transcript() {
        
    }
    
    public void addText(String text) { transcript.add(text); }
    public int getSize() { return transcript.size(); }
    public String getComment(int n) { return transcript.get(n); }
}
