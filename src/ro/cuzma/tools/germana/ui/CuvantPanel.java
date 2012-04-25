package ro.cuzma.tools.germana.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;

import ro.cuzma.tools.germana.translation.Cuvant;

public class CuvantPanel extends TranslationPanel {

    private static final long serialVersionUID = -4757480905950978600L;
    private JTextField traducereTF;
    private static CuvantPanel onlyOne = null;

    public static CuvantPanel getPanel(Cuvant tr, RunMe application) {
        if (onlyOne == null) {
            onlyOne = new CuvantPanel(tr, application);
        } else {
            onlyOne.setTranslation(tr);
        }
        return onlyOne;
    }

    @Override
    public void reset() {
        firstCorrectAnswer = true;
        traducereTF.setBackground(TranslationPanel.COLOR_NEW);
        traducereTF.setText("");

    }

    private CuvantPanel(Cuvant tr, RunMe application) {
        super(tr, application);
    }

    protected JPanel setMainPanel() {
        JPanel panelMain = new JPanel();
        GridBagLayout layout = new GridBagLayout();

        panelMain.setLayout(layout);
        traducereTF = new JTextField();
        traducereTF.setPreferredSize(new Dimension(300, 20));
        traducereTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (isCorrectAnswer()) {
                        application.setAndNext(isFirstCorrectAnswer());
                    }
                }
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        layout.setConstraints(traducereTF, c);
        // panelMain.setBorder(new LineBorder(Color.BLACK, 1));
        panelMain.add(traducereTF);
        return panelMain;
    }

    protected void internal_setSolution() {
        traducereTF.setBackground(COLOR_NEW);
        Cuvant cuv = (Cuvant) tr;
        traducereTF.setText(cuv.getTraducere());

    }

    protected boolean setTestAction() {
        Cuvant cuv = (Cuvant) tr;
        if (traducereTF.getText().equals(cuv.getTraducere())) {
            traducereTF.setBackground(COLOR_GOOD);
            return true;
        } else {
            traducereTF.setBackground(COLOR_WRONG);
            return false;
        }
    }

    @Override
    protected String getTitle() {
        Cuvant cuv = (Cuvant) tr;
        return cuv.getRomana() + " " + cuv.getDescription();
    }

}
