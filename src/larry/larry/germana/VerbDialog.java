package larry.germana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VerbDialog extends TranslationDialog {
    private Verb verb;
    private JTextField ichTF;
    private JTextField doTF;
    private JTextField erTF;
    private JTextField wirTF;
    private JTextField ihrTF;
    private JTextField sieTF;
    private JTextField pastParticipleTF;
    private JTextField doImperativTF;

    public VerbDialog(Translation tr) throws HeadlessException {
        super(tr);
        // System.out.println(substantiv.getPlural());
    }

    protected void setMainPanel() {
        this.setBounds(startPoint.x, startPoint.y, 600, 165);
        verb = (Verb) tr;
        jMainPanel.setLayout(new BorderLayout());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(4, 4));
        ichTF = new JTextField();
        doTF = new JTextField();
        erTF = new JTextField();
        wirTF = new JTextField();
        ihrTF = new JTextField();
        sieTF = new JTextField();
        pastParticipleTF = new JTextField();
        doImperativTF = new JTextField();
        doImperativTF.addKeyListener(new java.awt.event.KeyAdapter() {
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
        panelMain.setFocusTraversalPolicy(new MyOwnFocusTraversalPolicy());
        panelMain.add(new Label("Ich"));
        panelMain.add(ichTF);
        panelMain.add(new Label("Wir"));
        panelMain.add(wirTF);
        panelMain.add(new Label("Do"));
        panelMain.add(doTF);
        panelMain.add(new Label("Ihr"));
        panelMain.add(ihrTF);
        panelMain.add(new Label("Er/Sie/Es"));
        panelMain.add(erTF);
        panelMain.add(new Label("Sie"));
        panelMain.add(sieTF);
        panelMain.add(new Label("Past Partciple"));
        panelMain.add(pastParticipleTF);
        panelMain.add(new Label("Do Imperativ"));
        panelMain.add(doImperativTF);

        /*
         * JPanel panelWest = new JPanel(); panelWest.setLayout(new GridLayout(1, 1));
         * panelWest.add(new JLabel("Traducere")); jMainPanel.add(panelWest, BorderLayout.WEST);
         */

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new GridLayout(1, 1));
        panelNorth.add(new JLabel(verb.getSource() + " " + verb.getDescription()));
        jMainPanel.add(panelNorth, BorderLayout.NORTH);

        jMainPanel.add(panelSouth, BorderLayout.SOUTH);
        jMainPanel.add(panelMain, BorderLayout.CENTER);
        this.setFocusTraversalPolicy(new MyOwnFocusTraversalPolicy());

    }

    protected void setSolutionAction() {
        ichTF.setBackground(new Color(255, 255, 255));
        ichTF.setText(verb.getIch());
        doTF.setBackground(new Color(255, 255, 255));
        doTF.setText(verb.getDo_());
        erTF.setBackground(new Color(255, 255, 255));
        erTF.setText(verb.getEr());
        wirTF.setBackground(new Color(255, 255, 255));
        wirTF.setText(verb.getWir());
        ihrTF.setBackground(new Color(255, 255, 255));
        ihrTF.setText(verb.getIhr());
        sieTF.setBackground(new Color(255, 255, 255));
        sieTF.setText(verb.getSie());
        pastParticipleTF.setBackground(new Color(255, 255, 255));
        pastParticipleTF.setText(verb.getPastParticiple());
        doImperativTF.setBackground(new Color(255, 255, 255));
        doImperativTF.setText(verb.getDoImperativ());

    }

    protected boolean setTestAction() {
        boolean result = false;
        int contor = 0;
        if (ichTF.getText().equals(verb.getIch()) || verb.getIch().equals("-")) {
            ichTF.setBackground(new Color(0, 255, 0));

        } else {
            ichTF.setBackground(new Color(255, 0, 0));
            contor++;
        }
        if (doTF.getText().equals(verb.getDo_()) || verb.getDo_().equals("-")) {
            doTF.setBackground(new Color(0, 255, 0));

        } else {
            doTF.setBackground(new Color(255, 0, 0));
            contor++;
        }

        if (erTF.getText().equals(verb.getEr()) || verb.getEr().equals("-")) {
            erTF.setBackground(new Color(0, 255, 0));

        } else {
            erTF.setBackground(new Color(255, 0, 0));
            contor++;
        }

        if (wirTF.getText().equals(verb.getWir()) || verb.getWir().equals("-")) {
            wirTF.setBackground(new Color(0, 255, 0));

        } else {
            wirTF.setBackground(new Color(255, 0, 0));
            contor++;
        }
        if (ihrTF.getText().equals(verb.getIhr()) || verb.getIhr().equals("-")) {
            ihrTF.setBackground(new Color(0, 255, 0));

        } else {
            ihrTF.setBackground(new Color(255, 0, 0));
            contor++;
        }
        if (sieTF.getText().equals(verb.getSie()) || verb.getSie().equals("-")) {
            sieTF.setBackground(new Color(0, 255, 0));

        } else {
            sieTF.setBackground(new Color(255, 0, 0));
            contor++;
        }

        if (pastParticipleTF.getText().equals(verb.getPastParticiple())
                || verb.getPastParticiple().equals("-")) {
            pastParticipleTF.setBackground(new Color(0, 255, 0));

        } else {
            pastParticipleTF.setBackground(new Color(255, 0, 0));
            contor++;
        }
        if (doImperativTF.getText().equals(verb.getDoImperativ())
                || verb.getDoImperativ().equals("-")) {
            doImperativTF.setBackground(new Color(0, 255, 0));

        } else {
            doImperativTF.setBackground(new Color(255, 0, 0));
            contor++;
        }

        if (contor == 0) {
            result = true;
        }
        return result;

    }

    public class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy {

        public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
            if (aComponent.equals(ichTF)) {
                return doTF;
            } else if (aComponent.equals(doTF)) {
                return erTF;
            } else if (aComponent.equals(erTF)) {
                return wirTF;
            } else if (aComponent.equals(wirTF)) {
                return ihrTF;
            } else if (aComponent.equals(ihrTF)) {
                return sieTF;
            } else if (aComponent.equals(sieTF)) {
                return pastParticipleTF;
            } else if (aComponent.equals(pastParticipleTF)) {
                return doImperativTF;
            } else if (aComponent.equals(doImperativTF)) {
                return btTest;
            } else if (aComponent.equals(btTest)) {
                return btSolution;
            } else if (aComponent.equals(btSolution)) {
                return btClose;
            } else if (aComponent.equals(btClose)) {
                return ichTF;
            }
            return ichTF;
        }

        public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
            if (aComponent.equals(ichTF)) {
                return btClose;
            } else if (aComponent.equals(doTF)) {
                return ichTF;
            } else if (aComponent.equals(erTF)) {
                return doTF;
            } else if (aComponent.equals(wirTF)) {
                return erTF;
            } else if (aComponent.equals(ihrTF)) {
                return wirTF;
            } else if (aComponent.equals(sieTF)) {
                return ihrTF;
            } else if (aComponent.equals(btClose)) {
                return btSolution;
            } else if (aComponent.equals(btSolution)) {
                return btTest;
            } else if (aComponent.equals(btTest)) {
                return doImperativTF;
            } else if (aComponent.equals(doImperativTF)) {
                return pastParticipleTF;
            } else if (aComponent.equals(pastParticipleTF)) {
                return sieTF;
            }
            return ichTF;
        }

        public Component getDefaultComponent(Container focusCycleRoot) {
            return ichTF;
        }

        public Component getLastComponent(Container focusCycleRoot) {
            return btClose;
        }

        public Component getFirstComponent(Container focusCycleRoot) {
            return ichTF;
        }
    }

}
