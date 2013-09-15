package languageneuralnetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
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
    private JLabel labelTextNNetInfo = new JLabel();
    private JButton buttonDeleteEntry = new JButton();
    private JMenu menuData = new JMenu();
    private JButton buttonClearEntryText = new JButton();

    public MainFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        textnnet = null;
        connectionTextSync = true;
    }
    
    private TextNeuralNetwork textnnet;
    private boolean connectionTextSync;
    
    public void setTextNeuralNetwork(TextNeuralNetwork textnnet) {
        this.textnnet = textnnet;
    }

    private void jbInit() throws Exception {
        this.setJMenuBar( menuBar );
        this.getContentPane().setLayout( layoutMain );
        panelCenter.setLayout( null );
        this.setSize(new Dimension(650, 350));
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
        textAreaConnectionText.setFont(new Font("Courier New", 0, 16));
        textAreaConnectionText.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        textAreaConnectionText.setAutoscrolls(false);
        textAreaConnectionText.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    textAreaConnectionText_keyReleased(e);
                }
            });
        textFieldKeyText.setBounds(new Rectangle(5, 5, 370, 25));
        textFieldKeyText.setFont(new Font("Courier New", 0, 16));
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
        labelTextNNetInfo.setText("Open text neural network file");
        labelTextNNetInfo.setBounds(new Rectangle(10, 245, 590, 15));
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
        menuFile.add( menuFileExit );
        menuBar.add( menuFile );
        menuBar.add(menuData);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        toolBar.add(buttonOpen);
        toolBar.add(buttonSave);
        toolBar.add(buttonHelp);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        panelCenter.add(buttonClearEntryText, null);
        panelCenter.add(buttonDeleteEntry, null);
        panelCenter.add(buttonConnectEntry, null);
        panelCenter.add(textFieldKeyText, null);
        panelCenter.add(textAreaConnectionText, null);
        panelCenter.add(buttonSaveEntry, null);
        panelCenter.add(labelTextNNetInfo, null);
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
        
        System.out.println(textnnet);

        updateTitle();
        updateTextNNetInfo();
    }
    
    private void open() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getAbsolutePath();
            this.textnnet = new TextNeuralNetwork();
            try {
                this.textnnet.open(filename);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(),
                                              JOptionPane.ERROR_MESSAGE);
            } catch (InvalidTextNeuralNetworkEntryException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getClass().toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
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
        labelTextNNetInfo.setText("Entry set size: " + textnnet.entrySetSize());
    }

    private void buttonShowConnectionList_actionPerformed(ActionEvent e) {
        
        String key = textFieldKeyText.getText();
        TextNeuralNetworkEntry entry = textnnet.getEntry(key);
        if (entry != null) {
            textAreaConnectionText.setText(textAreaConnectionText(entry));
            labelTextNNetInfo.setText("Connection list size: " + entry.connectionList().size());
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
                } else {
                    textAreaConnectionText.setText("");
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
}
