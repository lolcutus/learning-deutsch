package larry.germana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

public class CuvantDialog extends TranslationDialog {
    private Cuvant cuvant;
    private JTextField traducereTF;

    public CuvantDialog(Translation tr) throws HeadlessException {
        super(tr);
        // System.out.println(substantiv.getPlural());
    }

    protected void setMainPanel() {
        this.setBounds(startPoint.x, startPoint.y, 600, 125);
        cuvant = (Cuvant) tr;
        jMainPanel.setLayout(new BorderLayout());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(1, 1));
        traducereTF = new JTextField();

        traducereTF.addKeyListener(new java.awt.event.KeyAdapter() {
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

        panelMain.add(traducereTF);

        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(1, 1));
        panelWest.add(new JLabel("Traducere"));
        jMainPanel.add(panelWest, BorderLayout.WEST);

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new GridLayout(1, 1));
        panelNorth.add(new JLabel(cuvant.getRomana() + " " + cuvant.getDescription()));
        jMainPanel.add(panelNorth, BorderLayout.NORTH);

        jMainPanel.add(panelMain, BorderLayout.CENTER);

    }

    protected void setSolutionAction() {
        traducereTF.setBackground(new Color(255, 255, 255));
        traducereTF.setText(cuvant.getTraducere());

    }

    protected boolean setTestAction() {
        if (traducereTF.getText().equals(cuvant.getTraducere())) {
            traducereTF.setBackground(new Color(0, 255, 0));
            return true;
        } else {
            traducereTF.setBackground(new Color(255, 0, 0));
            return false;
        }
    }

}
