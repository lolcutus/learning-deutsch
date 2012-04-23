package ro.cuzma.tools.germana.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
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

    public static SubstantivPanel getPanel(Substantiv tr, RunMe application, String displayText) {
        if (onlyOne == null) {
            onlyOne = new SubstantivPanel(tr, application, displayText);
        } else {
            onlyOne.tr = tr;
        }
        return onlyOne;
    }

    private SubstantivPanel(Substantiv tr, RunMe application, String displayText) {
        super(tr, application, displayText);
    }

    protected void setMainPanel() {

        mainPanel.setLayout(new BorderLayout());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(2, 1));
        singularTF = new JTextField();
        pluralTF = new JTextField();

        pluralTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (setTestAction()) {
                        application.setAndNext(firstCorrectAnswer);
                    } else {
                        firstCorrectAnswer = false;
                    }
                }
            }
        });

        panelMain.add(singularTF);
        panelMain.add(pluralTF);

        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(2, 1));
        panelWest.add(new JLabel("Singular"));
        panelWest.add(new JLabel("Plural"));
        mainPanel.add(panelWest, BorderLayout.WEST);

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new GridLayout(1, 1));
        Substantiv substantiv = (Substantiv) tr;
        panelNorth.add(new JLabel(substantiv.getSourceSg() + " " + substantiv.getDescription()));
        mainPanel.add(panelNorth, BorderLayout.NORTH);

        mainPanel.add(panelMain, BorderLayout.CENTER);

    }

    protected void setSolutionAction() {
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
            singularTF.setBackground(new Color(0, 255, 0));
            contor++;
        } else {
            singularTF.setBackground(new Color(255, 0, 0));
        }
        if (pluralTF.getText().equals(substantiv.getTranslationPlWithArticle())) {
            pluralTF.setBackground(new Color(0, 255, 0));
            contor++;
        } else {
            pluralTF.setBackground(new Color(255, 0, 0));
        }
        if (contor == 2) {
            result = true;
        }
        return result;

    }

}
