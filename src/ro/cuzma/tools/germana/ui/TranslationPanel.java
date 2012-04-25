package ro.cuzma.tools.germana.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ro.cuzma.tools.germana.translation.Translation;

public abstract class TranslationPanel extends JPanel {

    public static Color COLOR_NEW = new Color(255, 255, 255);
    public static Color COLOR_GOOD = new Color(0, 255, 0);
    public static Color COLOR_WRONG = new Color(255, 0, 0);

    private static final long serialVersionUID = 1L;
    Translation tr;

    JButton btSolution = new JButton("Solution");
    JButton btNext = new JButton("Next");
    JButton btTest = new JButton("Test");
    JPanel panelSouth = new JPanel();

    protected final RunMe application;
    boolean firstCorrectAnswer = true;
    protected JLabel title = new JLabel();

    public TranslationPanel(Translation tr, RunMe application) {
        this.tr = tr;
        this.application = application;
        initGUI();
        setTitle();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new GridLayout(1, 1));
        panelNorth.add(title);

        this.add(setMainPanel(), BorderLayout.CENTER);
        this.add(panelNorth, BorderLayout.NORTH);
    }

    protected abstract JPanel setMainPanel();

    protected abstract void internal_setSolution();

    protected abstract boolean setTestAction();

    public void setTranslation(Translation tr) {
        this.tr = tr;
        setTitle();
        reset();

    }

    protected abstract String getTitle();

    protected abstract void reset();

    public void setTitle() {
        this.title.setText(getTitle());
    }

    public boolean isFirstCorrectAnswer() {
        boolean tmp = setTestAction();
        if (firstCorrectAnswer)
            firstCorrectAnswer = tmp;
        return firstCorrectAnswer;
    }

    public boolean isCorrectAnswer() {
        boolean tmp = setTestAction();
        if (firstCorrectAnswer)
            firstCorrectAnswer = tmp;
        return tmp;
    }

    public void setSolution() {
        firstCorrectAnswer = false;
        internal_setSolution();
    }

}
