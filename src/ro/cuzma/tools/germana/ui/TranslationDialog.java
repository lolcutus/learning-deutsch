package ro.cuzma.tools.germana.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ro.cuzma.tools.germana.translation.Translation;

public abstract class TranslationDialog extends JDialog {

    public enum Exit {
        EXIT, RESET, NOTHING
    }

    private static final long serialVersionUID = 1L;
    // protected String specialChars = "ü 0252 ä 0228 ö 0246 ß 0223 \u00C3 0195";
    JPanel jMainPanel = new JPanel();
    Translation tr;
    JMenuBar jFile = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileClose = new JMenuItem();
    JMenuItem jMenuFileReset = new JMenuItem();

    JButton btSolution = new JButton("Solution");
    JButton btNext = new JButton("Next");
    JButton btTest = new JButton("Test");
    JPanel panelSouth = new JPanel();
    boolean firstCorrectAnswer = true;
    private Exit exit = Exit.NOTHING;
    protected static Point startPoint = new Point(550, 450);

    public TranslationDialog(Translation tr) throws HeadlessException {
        this.setModal(true);
        this.tr = tr;
        initGUI();
    }

    private void initGUI() {
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                close();
                exit = Exit.EXIT;
            }
        });
        createMenu();
        setMainPanel();
        createButtons();
        getContentPane().add(jMainPanel, BorderLayout.CENTER);
    }

    protected void close() {
        startPoint = this.getLocation();
        this.dispose();
    }

    private void createMenu() {
        jMenuFile.setSelectedIcon(null);
        jMenuFile.setText("File");
        jMenuFileClose.setText("Close");
        jMenuFileClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
                exit = Exit.EXIT;
            }
        });
        jMenuFileReset.setText("Reset");
        jMenuFileReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
                exit = Exit.RESET;
            }
        });
        jFile.add(jMenuFile);
        jMenuFile.add(jMenuFileReset);
        jMenuFile.add(jMenuFileClose);
        this.setJMenuBar(jFile);
    }

    private void createButtons() {
        panelSouth.setLayout(new GridLayout(1, 3));
        panelSouth.add(btTest);
        panelSouth.add(btSolution);
        panelSouth.add(btNext);
        btNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                firstCorrectAnswer = false;
                close();
            }
        });
        btNext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    firstCorrectAnswer = false;
                    close();
                }
            }
        });
        btTest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (setTestAction()) {
                        close();
                    } else {
                        firstCorrectAnswer = false;
                    }

                }
            }
        });

        btSolution.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setSolutionAction();
                    firstCorrectAnswer = false;
                }
            }
        });

        btTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (setTestAction()) {
                    close();
                } else {
                    firstCorrectAnswer = false;
                }
            }
        });

        btSolution.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setSolutionAction();
                firstCorrectAnswer = false;
            }
        });

        // setButtonsAction();
        jMainPanel.add(panelSouth, BorderLayout.SOUTH);

    }

    protected abstract void setMainPanel();

    protected abstract void setSolutionAction();

    protected abstract boolean setTestAction();

    public boolean isFirstCorrectAnswer() {
        return firstCorrectAnswer;
    }

    public Exit exit() {
        return exit;
    }

}
