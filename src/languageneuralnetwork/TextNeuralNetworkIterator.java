package languageneuralnetwork;

import java.util.ArrayList;

import java.util.Map;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class is not allowed to change the text neural network that it uses.
 */
public class TextNeuralNetworkIterator extends Thread {
    private ArrayList<TextNeuralNetworkEntry> textnnetEntryList;
    private TextNeuralNetworkEntry currentTextnnetEntry;
    private int timeout = 5000;
    private boolean continueFlag = true;
    public static final int MAX_NEIGHBOUR_ITERATION_COUNTER = 1;
    private JTextArea textAreaConnectionText;
    private JTextField textFieldKeyText;
    private JLabel labelStatus;

    
    public TextNeuralNetworkIterator(ThreadGroup threadGroup, Runnable runnable, String string, long l) {
        super(threadGroup, runnable, string, l);
    }

    public TextNeuralNetworkIterator(ThreadGroup threadGroup, Runnable runnable, String string) {
        super(threadGroup, runnable, string);
    }

    public TextNeuralNetworkIterator(Runnable runnable, String string) {
        super(runnable, string);
    }

    public TextNeuralNetworkIterator(ThreadGroup threadGroup, String string) {
        super(threadGroup, string);
    }

    public TextNeuralNetworkIterator(String string) {
        super(string);
    }

    public TextNeuralNetworkIterator(ThreadGroup threadGroup, Runnable runnable) {
        super(threadGroup, runnable);
    }

    public TextNeuralNetworkIterator(Runnable runnable) {
        super(runnable);
    }

    public TextNeuralNetworkIterator() {
        super();
    }

    @Override
    public void run() {
        Random random = new Random(System.currentTimeMillis());
        int index;
        index = random.nextInt(textnnetEntryList.size());
        this.currentTextnnetEntry = this.textnnetEntryList.get(index);

        for (TextNeuralNetworkEntry textnnetEntry : this.textnnetEntryList) {
            textnnetEntry.resetCounter();
        }

        while (this.continueFlag) {
            try {
                this.currentTextnnetEntry.incrementCounter();
                this.textFieldKeyText.setText(this.currentTextnnetEntry.key());
                ArrayList<TextNeuralNetworkEntry> connectionList = this.currentTextnnetEntry.connectionList();
                TextNeuralNetworkEntry nextTextnnetEntry;
                if (connectionList.isEmpty()) {
                    nextTextnnetEntry = this.currentTextnnetEntry;
                } else {
                    StringBuilder sb = new StringBuilder();
                    this.currentTextnnetEntry.appendConnectionText(sb);
                    this.textAreaConnectionText.setText(sb.toString());
                    index = random.nextInt(connectionList.size());
                    nextTextnnetEntry = connectionList.get(index);
                }
                Thread.sleep(this.timeout);
                if (nextTextnnetEntry.getCounter() < MAX_NEIGHBOUR_ITERATION_COUNTER) {
                    setStatus("Neighbour entry selected.");
                    this.currentTextnnetEntry = nextTextnnetEntry;
                } else {
                    setStatus("Non-neighbour entry selected.");
                    index = random.nextInt(textnnetEntryList.size());
                    this.currentTextnnetEntry = this.textnnetEntryList.get(index);
                }
            } catch (InterruptedException ex) {
                setStatus(ex.getMessage());
            }
        }
    }

    private void setStatus(String text) {
        labelStatus.setText(text);
    }

    public void setContinueFlagFalse() {
        this.continueFlag = false;
    }


    public void setTextnnetEntryList(ArrayList<TextNeuralNetworkEntry> textnnetEntryList) {
        this.textnnetEntryList = textnnetEntryList;
    }

    public void setTextAreaConnectionText(JTextArea textAreaConnectionText) {
        this.textAreaConnectionText = textAreaConnectionText;
    }

    public void setTextFieldKeyText(JTextField textFieldKeyText) {
        this.textFieldKeyText = textFieldKeyText;
    }

    public void setLabelStatus(JLabel labelStatus) {
        this.labelStatus = labelStatus;
    }
}
