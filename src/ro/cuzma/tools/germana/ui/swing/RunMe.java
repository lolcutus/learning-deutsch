/*
 * Copyright (c) Salomon Automation GmbH
 */
package ro.cuzma.tools.germana.ui.swing;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ro.cuzma.tools.germana.translation.Cuvant;
import ro.cuzma.tools.germana.translation.LearningList;
import ro.cuzma.tools.germana.translation.Substantiv;
import ro.cuzma.tools.germana.translation.Translation;
import ro.cuzma.tools.germana.translation.Translation.Language;
import ro.cuzma.tools.germana.translation.Verb;

public class RunMe {

    // translations
    private LearningList learningList = null;

    // config
    private static String DEFAULT_INI_FILE = "init.cfg";
    private File iniFile;
    private static Properties props = new Properties();
    public static String DB_FILE = "dbFile";
    public static String CSV_SEPARATOR = "csvSeparator";
    public static String CURRENT_LANG = "currentLang";
    private String dbFile = "";
    private String separator = ";";
    private Language currentLanguage = Language.LANG_ALL;

    protected Rectangle currentBounds = new Rectangle(550, 450, 600, 185);

    // main frame
    private JFrame mainFrame;

    private JPanel currentPanel;

    // menu
    private JMenuBar jFile = new JMenuBar();
    private JMenu jMenuFile = new JMenu();
    private JMenuItem jMenuFileClose = new JMenuItem();
    private JMenuItem jMenuFileReset = new JMenuItem();
    private JMenuItem jMenuFileSave = new JMenuItem();

    public RunMe(String iniFileName) {
        String tmpIniFileName = DEFAULT_INI_FILE;
        if (iniFileName != null && !iniFileName.isEmpty()) {
            tmpIniFileName = iniFileName;
        }

        initApp(tmpIniFileName);
        initData();
        createGUI();
    }

    private void initData() {
        learningList = new LearningList(dbFile, currentLanguage, separator);

    }

    private void createGUI() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void initApp(String iniFileName) {
        iniFile = new File(iniFileName);
        if (!iniFile.exists()) {
            try {
                props.put(DB_FILE, dbFile);
                props.put(CSV_SEPARATOR, separator);
                props.put(CURRENT_LANG, currentLanguage.name());
                props.store(new FileOutputStream(iniFileName), "init app");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            props.load(new FileInputStream(iniFile));
            dbFile = props.getProperty(DB_FILE);
            separator = props.getProperty(CSV_SEPARATOR);
            currentLanguage = Translation.gelLanguagefromString(props.getProperty(CURRENT_LANG));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCfgFile(File dbFile) {
        props.put(DB_FILE, dbFile.getAbsolutePath());
        props.put(CSV_SEPARATOR, separator);
        props.put(CURRENT_LANG, currentLanguage.name());
        try {
            props.store(new FileOutputStream(iniFile), "init app");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void createAndShowGUI() {
        // Make sure we have nice window decorations.
        String lookAndFeel = null;
        lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
        }

        mainFrame = new JFrame("Learning Deutsch");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // mainFrame.setLayout(new BorderLayout());
        // mainFrame.setBounds(0, 0, 800, 125);
        createMenu();

        nextTranslation();

        mainFrame.addComponentListener(new ComponentListener() {
            public void componentMoved(ComponentEvent e) {
                currentBounds = mainFrame.getBounds();
            }

            @Override
            public void componentHidden(ComponentEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void componentResized(ComponentEvent arg0) {
                currentBounds = mainFrame.getBounds();

            }

            @Override
            public void componentShown(ComponentEvent arg0) {
                // TODO Auto-generated method stub

            }
        });

        // // Display the window.
        // mainFrame.pack();
        // mainFrame.setBounds(currentBounds);
        // mainFrame.setVisible(true);
    }

    public static void main(String[] args) {

        RunMe runMe = new RunMe(null);
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.

    }

    private void createMenu() {
        jMenuFile.setSelectedIcon(null);
        jMenuFile.setText("File");
        jMenuFileClose.setText("Close");
        jMenuFileClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionClose();
            }

        });
        jMenuFileReset.setText("Reset");
        jMenuFileReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionReset();
            }
        });
        jMenuFileSave.setText("Save");
        jMenuFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionSave();
            }
        });
        jFile.add(jMenuFile);
        jMenuFile.add(jMenuFileReset);
        jMenuFile.add(jMenuFileSave);
        jMenuFile.add(jMenuFileClose);
        mainFrame.setJMenuBar(jFile);
    }

    private void actionClose() {
        learningList.save();
        close();
    }

    private void actionReset() {
        learningList.reset();
        nextTranslation();
    }

    private void actionSave() {
        learningList.save();
    }

    protected void close() {
        mainFrame.dispose();
    }

    private void nextTranslation() {
        Translation tr = null;
        // check if there is no empty list
        if (!learningList.isEmpty()) {
            tr = learningList.getNext();
        }
        if (tr == null) {
            newJPanel(new EmptyPanel());
        } else {

            int remaining = learningList.getCurrentList().length
                    - learningList.getCurrentPosition() + 1;
            int all = learningList.getCurrentList().length;
            int current = learningList.getCurrentPosition() - 1;

            int percent = 0;
            if (learningList.getGoodAnswers() + learningList.getBadAnswers() != 0) {
                percent = 100 * learningList.getGoodAnswers()
                        / (learningList.getGoodAnswers() + learningList.getBadAnswers());
            }
            String displayText = "All correct " + current + "/" + remaining + "/" + all + " good: "
                    + learningList.getGoodAnswers() + "(" + percent + "%) wrong: "
                    + learningList.getBadAnswers();
            if (tr instanceof Substantiv) {
                newJPanel(SubstantivPanel.getPanel((Substantiv) tr, this, displayText));
            } else if (tr instanceof Cuvant) {
                newJPanel(CuvantPanel.getPanel((Cuvant) tr, this, displayText));
            } else if (tr instanceof Verb) {
                newJPanel(new EmptyPanel());
            }
        }
    }

    private void newJPanel(JPanel newPanel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(newPanel);
        mainFrame.pack();
        mainFrame.setBounds(currentBounds);
        mainFrame.setVisible(true);

    }

    public void setAndNext(boolean correctAnswer) {
        learningList.processresponse(correctAnswer);
        nextTranslation();
    }

}
