package ro.cuzma.tools.germana.ui.old;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ro.cuzma.tools.germana.translation.Substantiv;
import ro.cuzma.tools.germana.translation.Translation;

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

public class SubstantivDialog extends TranslationDialog {
    /**
     * 
     */
    private static final long serialVersionUID = 8051640574039732653L;
    private Substantiv substantiv;
    private JTextField singularTF;
    private JTextField pluralTF;

    public SubstantivDialog(Translation tr) throws HeadlessException {
        super(tr);
        // System.out.println(substantiv.getPlural());
    }

    protected void setMainPanel() {
        this.setBounds(startPoint.x, startPoint.y, 600, 140);
        substantiv = (Substantiv) tr;
        jMainPanel.setLayout(new BorderLayout());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(2, 1));
        singularTF = new JTextField();
        pluralTF = new JTextField();

        pluralTF.addKeyListener(new java.awt.event.KeyAdapter() {
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

        panelMain.add(singularTF);
        panelMain.add(pluralTF);

        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(2, 1));
        panelWest.add(new JLabel("Singular"));
        panelWest.add(new JLabel("Plural"));
        jMainPanel.add(panelWest, BorderLayout.WEST);

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new GridLayout(1, 1));
        panelNorth.add(new JLabel(substantiv.getSourceSg() + " " + substantiv.getDescription()));
        jMainPanel.add(panelNorth, BorderLayout.NORTH);

        jMainPanel.add(panelMain, BorderLayout.CENTER);

    }

    protected void setSolutionAction() {
        singularTF.setBackground(new Color(255, 255, 255));
        pluralTF.setBackground(new Color(255, 255, 255));
        singularTF.setText(substantiv.getTranslationSg());
        pluralTF.setText(substantiv.getTranslationPl());

    }

    protected boolean setTestAction() {
        boolean result = false;
        int contor = 0;
        if (singularTF.getText().equals(substantiv.getTranslationSg())) {
            singularTF.setBackground(new Color(0, 255, 0));
            contor++;
        } else {
            singularTF.setBackground(new Color(255, 0, 0));
        }
        if (pluralTF.getText()
                .equals(substantiv.getArticol() + " " + substantiv.getTranslationPl())) {
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
