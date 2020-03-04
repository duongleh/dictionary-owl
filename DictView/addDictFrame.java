package DictView;

import DictController.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class addDictFrame extends JDialog {

    private static final long serialVersionUID = 1L;
    private DictController controller = DictController.getInstance();

    JLabel label;
    JTextField inputArea;
    JButton btnConfirm;

    public addDictFrame(Frame parent) {
        super(parent, "Add Dictionary");
        this.setSize(420, 140);
        this.setLayout(null);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);

        label = new JLabel("Dictionary:");
        inputArea = new JTextField();
        btnConfirm = new JButton("Confirm");

        label.setBounds(20, 20, 100, 20);
        inputArea.setBounds(120, 20, 280, 35);
        btnConfirm.setBounds(170, 60, 90, 30);

        this.add(label);
        this.add(inputArea);
        this.add(btnConfirm);

        btnConfirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                controller.addNewDict(inputArea.getText());
                dispose();
            }
        });
    }

}