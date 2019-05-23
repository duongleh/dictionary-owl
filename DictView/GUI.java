package DictView;

import DictController.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    JComboBox<String> cbChoose;
    JPanel panel;
    JTextField tfWord;
    JButton butAddDict, butAddWord, butFix, butDel;
    JScrollPane scrollPanel;
    JList<String> listWord;
    JTextArea taMeaning;

    JMenuBar menu;
    JMenu menuFile, menuEdit, menuAboutus;
    JMenuItem itemOpen, itemSave;

    DictController controller = new DictController();
    String[] dictionary = { "Anh - Việt", "Việt - Anh" };

    DefaultListModel<String> stage;

    public GUI() {
        super("Từ Điển");
        initComponents();
    }

    public void initComponents() {

        stage = controller.getAllNamesInDict(dictionary[0]);

        panel = new JPanel();
        tfWord = new JTextField();
        cbChoose = new JComboBox<String>(dictionary);
        listWord = new JList<String>(stage);
        scrollPanel = new JScrollPane(listWord);
        taMeaning = new JTextArea();
        butAddDict = new JButton("Thêm từ điển");
        butAddWord = new JButton("Thêm từ");
        butFix = new JButton("Sửa từ");
        butDel = new JButton("Xóa từ");

        menu = new JMenuBar();
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuAboutus = new JMenu("About");
        itemOpen = new JMenuItem("Open");
        itemSave = new JMenuItem("Save");

        panel.setLayout(null);
        listWord.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taMeaning.setEditable(false);
        listWord.setModel(stage);

        tfWord.setBounds(20, 15, 250, 30);
        cbChoose.setBounds(300, 15, 130, 30);
        butAddDict.setBounds(450, 15, 150, 30);
        butAddWord.setBounds(620, 15, 100, 30);
        butFix.setBounds(730,15, 100, 30);
        butDel.setBounds(840, 15, 100, 30);
        scrollPanel.setBounds(20, 60, 250, 540);
        taMeaning.setBounds(300, 60, 675, 540);

        menu.add(menuFile);
        menu.add(menuEdit);
        menu.add(menuAboutus);
        menuFile.add(itemOpen);
        menuFile.add(itemSave);

        panel.add(tfWord);
        panel.add(cbChoose);
        panel.add(butAddDict);
        panel.add(butAddWord);
        panel.add(butFix);
        panel.add(butDel);
        panel.add(scrollPanel);
        panel.add(taMeaning);
        this.add(panel);
        this.setJMenuBar(menu);

        tfWord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DefaultListModel<String> stage2;
                int indx;
                String input = tfWord.getText();
                if (!input.equals("")) {
                    if ((stage2 = controller.findWords(input)) != null) {
                        stage.clear();
                        for (indx = 0; indx < stage2.getSize(); indx++) {
                            stage.addElement(stage2.getElementAt(indx));
                        }
                        listWord.setModel(stage);
                    }
                } else {
                    stage.clear();
                    controller.resetStage();
                    listWord.setModel(stage);
                }
            }
        });
        listWord.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                int idx = listWord.getSelectedIndex();
                taMeaning.setText(controller.getMeaningOfWord(idx));
            }
        });

        cbChoose.addActionListener(new ActionListener() {

            @Override
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent arg0) {
                JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
                String comboBox = (String) cb.getSelectedItem();
                stage.clear();
                stage = controller.getAllNamesInDict(comboBox);
                listWord.setModel(stage);
            }
        });

        butAddDict.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

            }
        });

        butAddWord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
              
            }
        });

butFix.addActionListener(new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
    }
});

        butDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            
            }
        });
     

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

}