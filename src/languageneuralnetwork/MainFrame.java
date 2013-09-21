package languageneuralnetwork;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;


public class MainFrame extends JFrame {
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JToolBar toolBar = new JToolBar();
    private JButton buttonOpen = new JButton();
    private JButton buttonSave = new JButton();
    private JButton buttonHelp = new JButton();
    private ImageIcon imageOpen = new ImageIcon(MainFrame.class.getResource("openfile.gif"));
    private ImageIcon imageClose = new ImageIcon(MainFrame.class.getResource("closefile.gif"));
    private ImageIcon imageHelp = new ImageIcon(MainFrame.class.getResource("help.gif"));
    private JButton buttonSaveEntry = new JButton();
    private JTextArea textAreaConnectionText = new JTextArea();
    private JTextField textFieldKeyText = new JTextField();
    private JButton buttonConnectEntry = new JButton();
    private JLabel labelStatus = new JLabel();
    private JButton buttonDeleteEntry = new JButton();
    private JMenu menuData = new JMenu();
    private JButton buttonClearEntryText = new JButton();
    private JProgressBar progressBar = new JProgressBar();
    private JButton buttonStartStopIteration = new JButton();

    public MainFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        textnnet = null;
        connectionTextSync = true;
    }
    
    private transient TextNeuralNetwork textnnet;
    private boolean connectionTextSync;
    private transient TextNeuralNetworkIterator textnnetIterator;
    public static final String RUNTIME_DATA = "runtime_data.txt";
    
    public void setTextNeuralNetwork(TextNeuralNetwork textnnet) {
        this.textnnet = textnnet;
    }

    private void jbInit() throws Exception {
        this.setJMenuBar( menuBar );
        this.getContentPane().setLayout( layoutMain );
        panelCenter.setLayout( null );
        this.setSize(new Dimension(600, 400));
        this.setTitle( "Language Neural Network" );
        menuFile.setText( "File" );
        menuFileExit.setText( "Exit" );
        menuFileExit.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { fileExit_ActionPerformed( ae ); } } );
        statusBar.setText( "" );
        buttonOpen.setToolTipText( "Open File" );
        buttonOpen.setIcon( imageOpen );
        buttonOpen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonOpen_actionPerformed(e);
                }
            });
        buttonSave.setToolTipText( "Close File" );
        buttonSave.setIcon( imageClose );
        buttonSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonSave_actionPerformed(e);
                }
            });
        buttonHelp.setToolTipText( "About" );
        buttonHelp.setIcon( imageHelp );
        buttonSaveEntry.setText("Save");
        buttonSaveEntry.setBounds(new Rectangle(380, 5, 105, 25));
        buttonSaveEntry.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonSaveEntry_actionPerformed(e);
                }
            });
        textAreaConnectionText.setBounds(new Rectangle(5, 35, 370, 200));
        textAreaConnectionText.setFont(new Font("Tahoma", 0, 16));
        textAreaConnectionText.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        textAreaConnectionText.setAutoscrolls(false);
        textAreaConnectionText.setEnabled(false);
        textAreaConnectionText.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    textAreaConnectionText_keyReleased(e);
                }
            });
        textFieldKeyText.setBounds(new Rectangle(5, 5, 370, 25));
        textFieldKeyText.setFont(new Font("Tahoma", 0, 16));
        textFieldKeyText.setEnabled(false);
        textFieldKeyText.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    textFieldKeyText_keyReleased(e);
                }
            });
        buttonConnectEntry.setText("Connect");
        buttonConnectEntry.setBounds(new Rectangle(380, 35, 105, 25));
        buttonConnectEntry.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonConnectEntry_actionPerformed(e);
                }
            });
        labelStatus.setBounds(new Rectangle(10, 245, 590, 45));
        labelStatus.setVerticalAlignment(SwingConstants.TOP);
        labelStatus.setVerticalTextPosition(SwingConstants.TOP);
        buttonDeleteEntry.setText("Delete");
        buttonDeleteEntry.setBounds(new Rectangle(490, 5, 70, 25));
        buttonDeleteEntry.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonDeleteEntry_actionPerformed(e);
                }
            });
        menuData.setText("Data");
        buttonClearEntryText.setText("Clear");
        buttonClearEntryText.setBounds(new Rectangle(490, 35, 70, 25));
        buttonClearEntryText.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClearEntryText_actionPerformed(e);
                }
            });
        progressBar.setBounds(new Rectangle(5, 300, 585, 15));
        buttonStartStopIteration.setText("Start iteration");
        buttonStartStopIteration.setBounds(new Rectangle(380, 65, 180, 25));
        buttonStartStopIteration.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonStartStopIteration_actionPerformed(e);
                }
            });
        menuFile.add( menuFileExit );
        menuBar.add( menuFile );
        menuBar.add(menuData);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        toolBar.add(buttonOpen);
        toolBar.add(buttonSave);
        toolBar.add(buttonHelp);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        panelCenter.add(buttonStartStopIteration, null);
        panelCenter.add(progressBar, null);
        panelCenter.add(buttonClearEntryText, null);
        panelCenter.add(buttonDeleteEntry, null);
        panelCenter.add(buttonConnectEntry, null);
        panelCenter.add(textFieldKeyText, null);
        panelCenter.add(textAreaConnectionText, null);
        panelCenter.add(buttonSaveEntry, null);
        panelCenter.add(labelStatus, null);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void buttonOpen_actionPerformed(ActionEvent e) {
        if (textnnet != null) {
            if (textnnet.fileSync()) {
                // textnnet content is saved -- textnnet object can be reused
                this.open();
            } else {
                Object[] options = { "Yes", "No", "Cancel" };
                int n =
                    JOptionPane.showOptionDialog(this, "Program file sync is false. Save program to file?", "Program save",
                                                 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                                 options, options[2]);
                switch (n) {
                case 0:
                    try {
                        this.textnnet.save();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(),
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                    this.open();
                case 1:
                    this.open();
                    break;
                case 2:
                    break;
                default:
                }
            }
        } else {
            this.open();
        }
        
        System.out.println(this.textnnet);

        updateTitle();
        updateTextNNetInfo();
    }
    
    private void open() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter;
        filter = new FileNameExtensionFilter("Text files", "txt");
        File dir;
        dir = new File(this.getRuntimeData());
        chooser.setCurrentDirectory(dir);
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getAbsolutePath();
            this.textnnet = new TextNeuralNetwork();
            try {
                this.textnnet.open(filename);
                this.textFieldKeyText.setEnabled(true);
                this.textAreaConnectionText.setEnabled(true);
                this.saveRuntimeData(filename);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(),
                                              JOptionPane.ERROR_MESSAGE);
            } catch (InvalidTextNeuralNetworkEntryException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveRuntimeData(String textnnetFilename) {
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(MainFrame.RUNTIME_DATA));
            outputStream.write(textnnetFilename);
            outputStream.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getRuntimeData() {
        String result = null;
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(MainFrame.RUNTIME_DATA));
            String line = inputStream.readLine();
            result = line;
            inputStream.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(), JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    private void updateTitle() {
        if (textnnet != null) {
            String s = textnnet.filename();
            if (!textnnet.fileSync()) {
                s += '*';
            }
            setTitle(s);
        } else {
            setTitle("Language Neural Network");
        }
    }

    private void buttonSave_actionPerformed(ActionEvent e) {
        if (this.textnnet != null) {
            try {
                this.textnnet.save();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid textnnet", "Text neural network save failure",
                                          JOptionPane.ERROR_MESSAGE);
        }

        updateTitle();
    }

    private void textAreaCurrentEntry_keyTyped(KeyEvent e) {
        //
    }
    
    private ArrayList<String> getConnectionText() {
        ArrayList<String> connectionText = new ArrayList<String>();
        for (int i = 0; i < textAreaConnectionText.getLineCount(); i++) {
            try {
                int lineStartOffset = textAreaConnectionText.getLineStartOffset(i);
                int lineEndOffset = textAreaConnectionText.getLineEndOffset(i);
                String line = textAreaConnectionText.getText(lineStartOffset, lineEndOffset - lineStartOffset);
                line = line.trim(); // otherwise would contain char 10
                if (!line.isEmpty()) {
                    connectionText.add(line);
                }
            } catch (BadLocationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
        return connectionText;
    }

    private void buttonConnectEntry_actionPerformed(ActionEvent e) {
        String keyText = textFieldKeyText.getText();
        ArrayList<String> connectionText = getConnectionText();
        try {
            textnnet.connect(keyText, connectionText);
            connectionTextSync = true;
        } catch (InvalidTextNeuralNetworkEntryException f) {
            JOptionPane.showMessageDialog(this, "Invalid textnnet", "Text neural network save failure",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        updateTitle();
        updateTextNNetInfo();
    }

    private void buttonSaveEntry_actionPerformed(ActionEvent e) {
        String keyText = textFieldKeyText.getText();
        if (textnnet != null) {
            textnnet.addEntry(keyText);
            updateTextNNetInfo();
        }
    }
    
    private void updateTextNNetInfo() {
        labelStatus.setText("Entry set size: " + textnnet.entrySetSize());
    }

    private void buttonShowConnectionList_actionPerformed(ActionEvent e) {
        
        String key = textFieldKeyText.getText();
        TextNeuralNetworkEntry entry = textnnet.getEntry(key);
        if (entry != null) {
            textAreaConnectionText.setText(textAreaConnectionText(entry));
            labelStatus.setText("Connection list size: " + entry.connectionList().size());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid textnnet entry", "Connection list show failure",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String textAreaConnectionText(TextNeuralNetworkEntry entry) {
        StringBuilder sb = new StringBuilder();
        for (TextNeuralNetworkEntry conn : entry.connectionList()) {
            String s = conn.key();
            sb.append(s).append('\n');
        }
        return sb.toString();
    }

    private void buttonDeleteEntry_actionPerformed(ActionEvent e) {
        String key = textFieldKeyText.getText();
        if (!key.trim().isEmpty()) {
            if (textnnet != null) {
                Object[] options = { "Yes", "No" };
                int n =
                    JOptionPane.showOptionDialog(this, "Are you sure you want to delete entry key \"" + key + "\"?",
                                                 "Delete entry key", JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                switch (n) {
                case 0:
                    textnnet.deleteEntry(key);
                    updateTitle();
                    updateTextNNetInfo();
                    break;
                case 1:
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid textnnet", "Entry key delete failure",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void textFieldKeyText_keyReleased(KeyEvent e) {
        updateConnectionText();
    }

    private void updateConnectionText() {
        if (connectionTextSync) {
            if (textnnet != null) {
                String key = textFieldKeyText.getText().trim();
                TextNeuralNetworkEntry entry = textnnet.getEntry(key);
                if (entry != null) {
                    textAreaConnectionText.setText(textAreaConnectionText(entry));
                    setStatus("Key found.");
                } else {
                    textAreaConnectionText.setText("");
                    setStatus("Key not found.");
                }
            }
        }
    }

    private void textAreaConnectionText_keyReleased(KeyEvent e) {
        connectionTextSync = false;
    }

    private void buttonClearEntryText_actionPerformed(ActionEvent e) {
        textAreaConnectionText.setText("");
        connectionTextSync = true;
    }
    
    private void setStatus(String text) {
        labelStatus.setText(text);
    }

    private void buttonStartStopIteration_actionPerformed(ActionEvent e) {
        if (this.textnnetIterator == null) {
            this.textnnetIterator = new TextNeuralNetworkIterator();
            this.textnnetIterator.setTextnnetEntryList(this.textnnet.getEntryList());
            this.textnnetIterator.setTextFieldKeyText(this.textFieldKeyText);
            this.textnnetIterator.setTextAreaConnectionText(this.textAreaConnectionText);
            this.textnnetIterator.setLabelStatus(this.labelStatus);
        }
        
        switch (this.textnnetIterator.getState()) {
        case NEW:
            this.textnnetIterator.start();
            this.buttonStartStopIteration.setText("Stop iteration");
            this.progressBar.setValue(50);
            break;
        default:
            this.textnnetIterator.setContinueFlagFalse();
            this.textnnetIterator = null;
            this.buttonStartStopIteration.setText("Start iteration");
            break;
        }
    }
}
