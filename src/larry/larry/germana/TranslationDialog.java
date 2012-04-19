package larry.germana;

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

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public abstract class TranslationDialog extends JDialog {
    // protected String specialChars = "ü 0252 ä 0228 ö 0246 ß 0223 \u00C3 0195";
    JPanel jMainPanel = new JPanel();
    Translation tr;
    JMenuBar jFile = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileClose = new JMenuItem();

    JButton btSolution = new JButton("Solution");
    JButton btClose = new JButton("Close");
    JButton btTest = new JButton("Test");
    JPanel panelSouth = new JPanel();
    boolean first = true;
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
                System.exit(0);
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
                System.exit(0);
            }
        });

        jFile.add(jMenuFile);
        jMenuFile.add(jMenuFileClose);
        this.setJMenuBar(jFile);
    }

    private void createButtons() {
        panelSouth.setLayout(new GridLayout(1, 3));
        panelSouth.add(btTest);
        panelSouth.add(btSolution);
        panelSouth.add(btClose);
        btClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                first = false;
                close();
            }
        });
        btClose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    first = false;
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
                        first = false;
                    }

                }
            }
        });

        btSolution.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setSolutionAction();
                    first = false;
                }
            }
        });

        btTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (setTestAction()) {
                    close();
                } else {
                    first = false;
                }
            }
        });

        btSolution.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setSolutionAction();
                first = false;
            }
        });

        // setButtonsAction();
        jMainPanel.add(panelSouth, BorderLayout.SOUTH);

    }

    protected abstract void setMainPanel();

    protected abstract void setSolutionAction();

    protected abstract boolean setTestAction();

    public boolean isFirst() {
        return first;
    }

}
