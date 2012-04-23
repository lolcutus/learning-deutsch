package ro.cuzma.tools.germana.ui.swing;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmptyPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public EmptyPanel() {
        initGUI();
    }

    private void initGUI() {
        this.setBounds(0, 0, 600, 125);
        this.setLayout(new BorderLayout());
        this.add(new JLabel("No translations!!!"));
    }
}
