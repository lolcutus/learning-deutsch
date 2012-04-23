package ro.cuzma.tools.germana.ui.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ro.cuzma.tools.germana.translation.Translation;

public abstract class TranslationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    Translation tr;

    JButton btSolution = new JButton("Solution");
    JButton btNext = new JButton("Next");
    JButton btTest = new JButton("Test");
    JPanel panelSouth = new JPanel();
    JPanel mainPanel = new JPanel();

    protected final RunMe application;
    boolean firstCorrectAnswer = true;

    public TranslationPanel(Translation tr, RunMe application, String displayText)
            throws HeadlessException {
        this.tr = tr;
        this.application = application;
        initGUI(displayText);
    }

    private void initGUI(String displayText) {
        this.setLayout(new BorderLayout());
        panelSouth.setLayout(new GridLayout(1, 3));
        setMainPanel();
        createButtons();
        this.add(mainPanel, BorderLayout.CENTER);
        panelSouth.setLayout(new GridLayout(2, 1));
        panelSouth.add(createButtons());
        panelSouth.add(new JLabel(displayText));
        this.add(panelSouth, BorderLayout.SOUTH);
    }

    private JPanel createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btTest);
        buttonPanel.add(btSolution);
        buttonPanel.add(btNext);
        btNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                firstCorrectAnswer = false;
                if (!e.isConsumed()) {
                    e.consume();
                    application.setAndNext(firstCorrectAnswer);
                }

            }
        });
        btNext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    firstCorrectAnswer = false;
                    application.setAndNext(firstCorrectAnswer);
                }
            }
        });
        btTest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!setTestAction()) {
                        firstCorrectAnswer = false;
                    } else {
                        application.setAndNext(firstCorrectAnswer);
                    }
                }
            }
        });
        btTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!setTestAction()) {
                    firstCorrectAnswer = false;
                } else {
                    application.setAndNext(firstCorrectAnswer);
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

        btSolution.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setSolutionAction();
                firstCorrectAnswer = false;
            }
        });
        return buttonPanel;

    }

    protected abstract void setMainPanel();

    protected abstract void setSolutionAction();

    protected abstract boolean setTestAction();

    public boolean isFirstCorrectAnswer() {
        return firstCorrectAnswer;
    }

}
