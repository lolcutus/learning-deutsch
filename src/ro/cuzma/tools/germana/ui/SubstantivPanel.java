package ro.cuzma.tools.germana.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ro.cuzma.tools.germana.translation.Substantiv;

public class SubstantivPanel extends TranslationPanel {
    private static final long serialVersionUID = 8051640574039732653L;
    private JTextField singularTF;
    private JTextField pluralTF;
    private static SubstantivPanel onlyOne = null;

    public static SubstantivPanel getPanel(Substantiv tr, RunMe application) {
        if (onlyOne == null) {
            onlyOne = new SubstantivPanel(tr, application);
        } else {
            onlyOne.setTranslation(tr);
        }
        return onlyOne;
    }

    private SubstantivPanel(Substantiv tr, RunMe application) {
        super(tr, application);
    }

    protected JPanel setMainPanel() {

        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1));
        singularTF = new JTextField();
        pluralTF = new JTextField();

        pluralTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (isCorrectAnswer()) {
                        application.setAndNext(isFirstCorrectAnswer());
                    }
                }
            }
        });

        inputPanel.add(singularTF);
        inputPanel.add(pluralTF);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(2, 1));
        labelsPanel.add(new JLabel("Singular"));
        labelsPanel.add(new JLabel("Plural"));
        dataPanel.add(labelsPanel, BorderLayout.WEST);
        dataPanel.add(inputPanel, BorderLayout.CENTER);

        return dataPanel;

    }

    protected void internal_setSolution() {
        singularTF.setBackground(new Color(255, 255, 255));
        pluralTF.setBackground(new Color(255, 255, 255));
        Substantiv substantiv = (Substantiv) tr;
        singularTF.setText(substantiv.getTranslationSgWithArticle());
        pluralTF.setText(substantiv.getTranslationPlWithArticle());

    }

    protected boolean setTestAction() {
        boolean result = false;
        int contor = 0;
        Substantiv substantiv = (Substantiv) tr;
        if (singularTF.getText().equals(substantiv.getTranslationSgWithArticle())) {
            singularTF.setBackground(COLOR_GOOD);
            contor++;
        } else {
            singularTF.setBackground(COLOR_WRONG);
        }
        if (pluralTF.getText().equals(substantiv.getTranslationPlWithArticle())) {
            pluralTF.setBackground(COLOR_GOOD);
            contor++;
        } else {
            pluralTF.setBackground(COLOR_WRONG);
        }
        if (contor == 2) {
            result = true;
        }
        return result;

    }

    @Override
    public void reset() {
        singularTF.setBackground(TranslationPanel.COLOR_NEW);
        singularTF.setText("");
        pluralTF.setBackground(TranslationPanel.COLOR_NEW);
        pluralTF.setText("");

    }

    @Override
    protected String getTitle() {
        Substantiv substantiv = (Substantiv) tr;
        return substantiv.getSourceSg() + " " + substantiv.getDescription();
    }

    @Override
    public Component getNextFocus(Component component) {
        Component comp = null;
        if (component.equals(singularTF))
            comp = pluralTF;
        return comp;
    }

    @Override
    public Component getFirstFocusComponent() {
        return singularTF;
    }

    @Override
    public Component getBeforeFocus(Component component) {
        Component comp = null;
        if (component.equals(pluralTF))
            comp = singularTF;
        return comp;
    }

    @Override
    public Component getLastFocusComponent() {
        return pluralTF;
    }

}
