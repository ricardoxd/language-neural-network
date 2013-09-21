package languageneuralnetwork;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TextNeuralNetwork {
    private HashMap<String, TextNeuralNetworkEntry> data;
    public static final String NEWLINE = System.getProperty("line.separator");
    private String filename;
    private boolean fileSync;

    public TextNeuralNetwork() {
        data = new HashMap<String, TextNeuralNetworkEntry>();
        fileSync = false;
    }
    
    public void open(String filename) throws FileNotFoundException, IOException, InvalidTextNeuralNetworkEntryException {
        BufferedReader inputStream = new BufferedReader(new FileReader(filename));
        TextNeuralNetworkEntry entry = null;
        while (true) {
            String line = inputStream.readLine();
            if (line != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    // end of entry definition
                    TextNeuralNetworkEntry previousEntry = data.put(entry.key(), entry);
                    if (previousEntry != null) {
                        throw new InvalidTextNeuralNetworkEntryException();
                    }
                    entry = null;
                } else {
                    if (entry == null) {
                        // line string is key
                        entry = new TextNeuralNetworkEntry();
                        entry.setKey(line);
                    } else {
                        // line string is connection text
                        if (line.equals("...")) {
                            entry.setConnectionListOverflow(true);
                        } else {
                            TextNeuralNetworkEntry connectionEntry = data.get(line);
                            if (connectionEntry != null) {
                                entry.connectionList().add(connectionEntry);
                            }
                        }
                    }
                }
            } else {
                // end of file
                if (entry != null) {
                    TextNeuralNetworkEntry previousEntry = data.put(entry.key(), entry);
                    if (previousEntry != null) {
                        throw new InvalidTextNeuralNetworkEntryException();
                    }
                    entry = null;
                }
                break;
            }
        }
        
        completeConnectionData();
        
        this.filename = filename;
        fileSync = true;

        inputStream.close();
    }
    
    /**
     * 
     */
    public void completeConnectionData() {
        for (Map.Entry<String, TextNeuralNetworkEntry> mapEntry : data.entrySet()) {
            ((TextNeuralNetworkEntry)mapEntry.getValue()).completeConnectionData();
        }
        fileSync = false;
    }
    
    public boolean fileSync() {
        return fileSync;
    }

    public void save() throws IOException {
        BufferedWriter outputStream = new BufferedWriter(new FileWriter(filename));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, TextNeuralNetworkEntry> mapEntry : data.entrySet()) {
            String key = mapEntry.getKey();
            sb.append(key).append(NEWLINE);
            TextNeuralNetworkEntry textnnetEntry = mapEntry.getValue();
            textnnetEntry.appendConnectionText(sb);
        }
        outputStream.write(sb.toString());
        outputStream.close();
        fileSync = true;
    }


    public String filename() {
        return filename;
    }

    public TextNeuralNetworkEntry addEntry(String key) {
        if (!key.isEmpty()) {
            TextNeuralNetworkEntry entry = new TextNeuralNetworkEntry();
            entry.setKey(key);
            data.put(key, entry);
            fileSync = false;
            return entry;
        } else {
            return null;
        }
    }
    
    public void connect(String keyText, ArrayList<String> connectionText) throws InvalidTextNeuralNetworkEntryException {
        TextNeuralNetworkEntry keyEntry = data.get(keyText);
        if (keyEntry != null) {
            for (String s : connectionText) {
                TextNeuralNetworkEntry connectionEntry = data.get(s);
                if (connectionEntry == null) {
                    connectionEntry = addEntry(s);
                }
                keyEntry.connect(connectionEntry, true);
            }
        } else {
            throw new InvalidTextNeuralNetworkEntryException();
        }
        fileSync = false;
    }
    
    public int entrySetSize() {
        return data.entrySet().size();
    }
    
    public TextNeuralNetworkEntry getEntry(String key) {
        return data.get(key);
    }
    
    public ArrayList<TextNeuralNetworkEntry> getEntryList() {
        return new ArrayList<TextNeuralNetworkEntry>(this.data.values());
    }
    
    public void deleteEntry(String key) {
        TextNeuralNetworkEntry delEntry = data.get(key);
        if (delEntry != null) {
            for (Map.Entry<String, TextNeuralNetworkEntry> mapEntry : data.entrySet()) {
                TextNeuralNetworkEntry entry = mapEntry.getValue();
                entry.deleteConnection(delEntry);
            }
            data.remove(key);
            fileSync = false;
        }
    }
}
