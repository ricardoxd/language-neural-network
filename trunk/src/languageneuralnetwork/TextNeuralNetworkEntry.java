package languageneuralnetwork;

import java.util.ArrayList;

public class TextNeuralNetworkEntry {
    private String key;
    private ArrayList<TextNeuralNetworkEntry> connectionList;
    public static final int MAX_CONNECTION_LIST_SIZE = 1 << 3;
    private boolean connectionListOverflow;
    private int counter;
    
    public String key() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public boolean connectionListOverflow() {
        return connectionListOverflow;
    }
    
    public void setConnectionListOverflow(boolean connectionListOverflow) {
        this.connectionListOverflow = connectionListOverflow;
    }
    
    public ArrayList<TextNeuralNetworkEntry> connectionList() {
        return connectionList;
    }

    public TextNeuralNetworkEntry() {
        connectionList = new ArrayList<TextNeuralNetworkEntry>();
        connectionListOverflow = false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof TextNeuralNetworkEntry) {
            return ((TextNeuralNetworkEntry)obj).key.equals(this.key);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hashCode = 1;
        hashCode = hashCode * 31 + key.hashCode();
        return hashCode;
    }

    /**
     * @param connectionEntry
     */
    public void connect(TextNeuralNetworkEntry connectionEntry, boolean loopback) {
        if (connectionList.contains(connectionEntry)) {
            // 
        } else {
            if (connectionList.size() < MAX_CONNECTION_LIST_SIZE) {
                connectionList.add(connectionEntry);
            } else {
                // Connection list is enough long.
                // Do not add the connection text.
                connectionListOverflow = true;
            }
            if (loopback) {
                connectionEntry.connect(this, false);
            }
        }
    }

    public void completeConnectionData() {
        for (TextNeuralNetworkEntry connection : connectionList) {
            connection.connect(this, false);
        }
    }
    
    public void deleteConnection(TextNeuralNetworkEntry connectionEntry) {
        connectionList.remove(connectionEntry);
    }
    
    public void appendConnectionText(StringBuilder sb) {
        for (TextNeuralNetworkEntry connectionEntry : this.connectionList) {
            sb.append(connectionEntry.key()).append(TextNeuralNetwork.NEWLINE);
        }
        if (this.connectionListOverflow) {
            sb.append("...").append(TextNeuralNetwork.NEWLINE);
        }
        sb.append(TextNeuralNetwork.NEWLINE);

    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public void incrementCounter() {
        this.counter++;
    }

    public int getCounter() {
        return counter;
    }
}

