package DictView;

import DictController.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class addWordFrame extends JDialog {

    private static final long serialVersionUID = 1L;
    private DictController controller = DictController.getInstance();

    JLabel wordLabel, pronounceLabel, meaningLabel, resultLabel;
    JTextField inputWord, inputPronouce;
    JTextArea inputMeaning;
    JScrollPane meaningScroll;
    JButton btnConfirm;

    public addWordFrame(Frame parent) {
        super(parent, "Add Word");
        this.setSize(420, 320);
        this.setLayout(null);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);

        wordLabel = new JLabel("Word:");
        pronounceLabel = new JLabel("Pronounce:");
        meaningLabel = new JLabel("Meaning:");
        inputWord = new JTextField();
        inputPronouce = new JTextField();
        inputMeaning = new JTextArea();
        meaningScroll = new JScrollPane(inputMeaning);
        btnConfirm = new JButton("Confirm");
        resultLabel = new JLabel();
        inputMeaning.setEditable(true);
        inputMeaning.setLineWrap(true);

        wordLabel.setBounds(20, 20, 70, 20);
        pronounceLabel.setBounds(20, 60, 100, 20);
        meaningLabel.setBounds(20, 100, 100, 20);
        inputWord.setBounds(120, 20, 280, 35);
        inputPronouce.setBounds(120, 60, 280, 35);
        meaningScroll.setBounds(120, 100, 280, 130);
        btnConfirm.setBounds(160, 245, 90, 30);
        resultLabel.setBounds(260, 245, 130, 30);

        this.add(wordLabel);
        this.add(pronounceLabel);
        this.add(meaningLabel);
        this.add(inputWord);
        this.add(inputPronouce);
        this.add(meaningScroll);
        this.add(btnConfirm);
        this.add(resultLabel);

        btnConfirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (inputWord.getText().equals("") && inputPronouce.getText().equals("")
                        && inputMeaning.getText().equals(""))
                    resultLabel.setText("Invalid Value !");
                else if (inputWord.getText().equals("")) {
                    resultLabel.setText("Invalid Word !");
                } else {
                    int re = controller.addWord(inputWord.getText(), inputPronouce.getText(), inputMeaning.getText());
                    if (re == -1)
                        resultLabel.setText("Word Exists !");
                    else if (re == 0)
                        dispose();
                }
            }
        });
    }

}