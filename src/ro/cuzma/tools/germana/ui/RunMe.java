/*
 * Copyright (c) Salomon Automation GmbH
 */
package ro.cuzma.tools.germana.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private String separator = ",";
    private Language currentLanguage = Language.LANG_ALL;

    protected Rectangle currentBounds = new Rectangle(550, 450, 500, 215);

    // main frame
    private JFrame mainFrame;

    private JPanel mainPanel;
    private JPanel currentPanel = null;

    // buttons
    JButton btSolution = new JButton("Solution");
    JButton btNext = new JButton("Next");
    JButton btTest = new JButton("Test");

    JLabel showInfo = new JLabel();

    // menu
    private JMenuBar menu = new JMenuBar();
    private JMenu jMenuFile = new JMenu();
    private JMenuItem jMenuFileClose = new JMenuItem();
    private JMenuItem jMenuFileReset = new JMenuItem();
    private JMenuItem jMenuFileSave = new JMenuItem();
    private JMenuItem jMenuFileOpen = new JMenuItem();

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
            String tmp = props.getProperty(DB_FILE);
            if (tmp != null && !tmp.isEmpty()) {
                dbFile = tmp;
            }
            tmp = props.getProperty(CSV_SEPARATOR);
            if (tmp != null && !tmp.isEmpty()) {
                separator = tmp;
            }
            tmp = props.getProperty(CURRENT_LANG);
            if (tmp != null && !tmp.isEmpty()) {
                currentLanguage = Translation.gelLanguagefromString(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCfgFile() {
        props.put(DB_FILE, dbFile);
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

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                actionClose();
            }
        });

        // mainFrame.setLayout(new BorderLayout());
        // mainFrame.setBounds(0, 0, 800, 125);
        createMenu();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));

        bottomPanel.add(createButtons());
        bottomPanel.add(showInfo);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setContentPane(mainPanel);

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

        mainFrame.pack();
        mainFrame.setBounds(currentBounds);
        mainFrame.setVisible(true);
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
        jMenuFileOpen.setText("Open");
        jMenuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        menu.add(jMenuFile);
        jMenuFile.add(jMenuFileOpen);
        jMenuFile.add(jMenuFileSave);
        jMenuFile.add(jMenuFileReset);
        jMenuFile.add(jMenuFileClose);
        mainFrame.setJMenuBar(menu);

        JMenu menuEdit = new JMenu();
        menuEdit.setText("Edit");
        JMenuItem menuLangAll = new JMenuItem();
        menuLangAll.setText("All tests");
        menuLangAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeLanguage(Language.LANG_ALL);
            }
        });

        JMenuItem menuLangRO_DE = new JMenuItem();
        menuLangRO_DE.setText("Ro -> De tests");
        menuLangRO_DE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeLanguage(Language.LANG_RO_DE);
            }
        });
        JMenuItem menuLangDE_RO = new JMenuItem();
        menuLangDE_RO.setText("De -> Ro tests");
        menuLangDE_RO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeLanguage(Language.LANG_DE_RO);
            }
        });
        menuEdit.add(menuLangAll);
        menuEdit.add(menuLangRO_DE);
        menuEdit.add(menuLangDE_RO);

        menu.add(menuEdit);
    }

    private void actionClose() {
        learningList.save();
        saveCfgFile();
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
            showInfo.setText("No elements to display!");
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
            String displayText = currentLanguage.getDescription() + ": " + current + "/"
                    + remaining + "/" + all + " good: " + learningList.getGoodAnswers() + "("
                    + percent + "%) wrong: " + learningList.getBadAnswers();
            showInfo.setText(displayText);
            if (tr instanceof Substantiv) {
                newJPanel(SubstantivPanel.getPanel((Substantiv) tr, this));
            } else if (tr instanceof Cuvant) {
                newJPanel(CuvantPanel.getPanel((Cuvant) tr, this));
            } else if (tr instanceof Verb) {
                newJPanel(VerbPanel.getPanel((Verb) tr, this));
            }
        }
    }

    private void newJPanel(JPanel newPanel) {
        if (currentPanel != null) {
            mainPanel.remove(currentPanel);
        }
        currentPanel = newPanel;
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.validate();
        mainFrame.repaint();

    }

    public void setAndNext(boolean correctAnswer) {
        learningList.processresponse(correctAnswer);
        nextTranslation();
    }

    private JPanel createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btTest);
        buttonPanel.add(btSolution);
        buttonPanel.add(btNext);
        btNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setAndNext(false);
            }
        });
        btNext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setAndNext(false);
                }
            }
        });
        btTest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (currentPanel instanceof TranslationPanel) {
                        TranslationPanel tr = (TranslationPanel) currentPanel;
                        if (tr.isCorrectAnswer()) {
                            setAndNext(tr.isFirstCorrectAnswer());
                        }
                    }
                }

            }
        });
        btTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (currentPanel instanceof TranslationPanel) {
                    TranslationPanel tr = (TranslationPanel) currentPanel;
                    if (tr.isCorrectAnswer()) {
                        setAndNext(tr.isFirstCorrectAnswer());
                    }
                }
            }
        });

        btSolution.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (currentPanel instanceof TranslationPanel) {
                    TranslationPanel tr = (TranslationPanel) currentPanel;
                    tr.setSolution();
                }
            }
        });

        btSolution.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (currentPanel instanceof TranslationPanel) {
                    TranslationPanel tr = (TranslationPanel) currentPanel;
                    tr.setSolution();
                }
            }
        });
        return buttonPanel;

    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(iniFile);
        File thFile;
        int returnVal = chooser.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            thFile = chooser.getSelectedFile();
            dbFile = thFile.getAbsolutePath();
            learningList = new LearningList(dbFile, currentLanguage, separator);
        }
        nextTranslation();
    }

    private void changeLanguage(Language language) {
        currentLanguage = language;
        learningList.setLanguage(language);
        nextTranslation();
    }

}
