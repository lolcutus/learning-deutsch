package ro.cuzma.tools.germana.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;

import ro.cuzma.tools.germana.translation.Verb;

public class VerbPanel extends TranslationPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 4414436308024591000L;
    private JTextField ichTF;
    private JTextField doTF;
    private JTextField erTF;
    private JTextField wirTF;
    private JTextField ihrTF;
    private JTextField sieTF;

    private static VerbPanel onlyOne = null;

    public static VerbPanel getPanel(Verb tr, RunMe application) {
        if (onlyOne == null) {
            onlyOne = new VerbPanel(tr, application);
        } else {
            onlyOne.setTranslation(tr);
        }
        return onlyOne;
    }

    @Override
    public void reset() {
        ichTF.setBackground(COLOR_NEW);
        ichTF.setText("");
        doTF.setBackground(COLOR_NEW);
        doTF.setText("");
        erTF.setBackground(COLOR_NEW);
        erTF.setText("");
        wirTF.setBackground(COLOR_NEW);
        wirTF.setText("");
        ihrTF.setBackground(COLOR_NEW);
        ihrTF.setText("");
        sieTF.setBackground(COLOR_NEW);
        sieTF.setText("");

    }

    public VerbPanel(Verb tr, RunMe application) {
        super(tr, application);
    }

    protected JPanel setMainPanel() {
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(3, 4));
        ichTF = new JTextField();
        doTF = new JTextField();
        erTF = new JTextField();
        wirTF = new JTextField();
        ihrTF = new JTextField();
        sieTF = new JTextField();
        sieTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (isCorrectAnswer()) {
                        application.setAndNext(isFirstCorrectAnswer());
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
        return panelMain;
    }

    protected void internal_setSolution() {
        Verb verb = (Verb) tr;
        ichTF.setBackground(COLOR_NEW);
        ichTF.setText(verb.getIch());
        doTF.setBackground(COLOR_NEW);
        doTF.setText(verb.getDo_());
        erTF.setBackground(COLOR_NEW);
        erTF.setText(verb.getEr());
        wirTF.setBackground(COLOR_NEW);
        wirTF.setText(verb.getWir());
        ihrTF.setBackground(COLOR_NEW);
        ihrTF.setText(verb.getIhr());
        sieTF.setBackground(COLOR_NEW);
        sieTF.setText(verb.getSie());
    }

    protected boolean setTestAction() {
        boolean result = false;
        Verb verb = (Verb) tr;
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
                return ichTF;
            }
            // } else if (aComponent.equals(btTest)) {
            // return btSolution;
            // } else if (aComponent.equals(btSolution)) {
            // return btNext;
            // } else if (aComponent.equals(btNext)) {
            // return ichTF;
            // }
            return ichTF;
        }

        public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
            if (aComponent.equals(ichTF)) {
                return sieTF;
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
            }
            // else if (aComponent.equals(btNext)) {
            // return btSolution;
            // } else if (aComponent.equals(btSolution)) {
            // return btTest;
            // } else if (aComponent.equals(btTest)) {
            // return sieTF;
            // }
            return ichTF;
        }

        public Component getDefaultComponent(Container focusCycleRoot) {
            return ichTF;
        }

        public Component getLastComponent(Container focusCycleRoot) {
            return sieTF;
        }

        public Component getFirstComponent(Container focusCycleRoot) {
            return ichTF;
        }
    }

    @Override
    protected String getTitle() {
        Verb verb = (Verb) tr;
        return verb.getSource() + " " + verb.getDescription();

    }

    @Override
    public Component getNextFocus(Component component) {
        if (component.equals(ichTF)) {
            return doTF;
        } else if (component.equals(doTF)) {
            return erTF;
        } else if (component.equals(erTF)) {
            return wirTF;
        } else if (component.equals(wirTF)) {
            return ihrTF;
        } else if (component.equals(ihrTF)) {
            return sieTF;
        }
        return null;
    }

    @Override
    public Component getFirstFocusComponent() {
        return ichTF;
    }

    @Override
    public Component getBeforeFocus(Component component) {
        if (component.equals(doTF)) {
            return ichTF;
        } else if (component.equals(erTF)) {
            return doTF;
        } else if (component.equals(wirTF)) {
            return erTF;
        } else if (component.equals(ihrTF)) {
            return wirTF;
        } else if (component.equals(sieTF)) {
            return ihrTF;
        }
        return null;
    }

    @Override
    public Component getLastFocusComponent() {
        return sieTF;
    }

}
