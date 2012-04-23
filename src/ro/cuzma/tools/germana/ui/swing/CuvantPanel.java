package ro.cuzma.tools.germana.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ro.cuzma.tools.germana.translation.Cuvant;

public class CuvantPanel extends TranslationPanel {

    private static final long serialVersionUID = -4757480905950978600L;
    private JTextField traducereTF;
    private static CuvantPanel onlyOne = null;

    public static CuvantPanel getPanel(Cuvant tr, RunMe application, String displayText) {
        if (onlyOne == null) {
            onlyOne = new CuvantPanel(tr, application, displayText);
        } else {
            onlyOne.tr = tr;
        }
        return onlyOne;
    }

    private CuvantPanel(Cuvant tr, RunMe application, String displayText) {
        super(tr, application, displayText);
    }

    protected void setMainPanel() {
        mainPanel.setLayout(new BorderLayout());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(1, 1));
        traducereTF = new JTextField();

        traducereTF.addKeyListener(new java.awt.event.KeyAdapter() {
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

        panelMain.add(traducereTF);

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new GridLayout(1, 1));
        Cuvant cuv = (Cuvant) tr;
        panelNorth.add(new JLabel(cuv.getRomana() + " " + cuv.getDescription()));

        mainPanel.add(panelMain, BorderLayout.CENTER);
        mainPanel.add(panelNorth, BorderLayout.NORTH);
    }

    protected void setSolutionAction() {
        traducereTF.setBackground(new Color(255, 255, 255));
        Cuvant cuv = (Cuvant) tr;
        traducereTF.setText(cuv.getTraducere());

    }

    protected boolean setTestAction() {
        Cuvant cuv = (Cuvant) tr;
        if (traducereTF.getText().equals(cuv.getTraducere())) {
            traducereTF.setBackground(new Color(0, 255, 0));
            return true;
        } else {
            traducereTF.setBackground(new Color(255, 0, 0));
            return false;
        }
    }

}
