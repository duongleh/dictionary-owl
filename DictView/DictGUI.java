package DictView;

import DictController.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DictGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    JPanel panel;
    JComboBox<String> cbChoose;
    JTextField tfWord;
    JButton butAddDict, butAddWord, butEdit, butDel;
    JScrollPane scrollPanel;
    JList<String> listWord;
    JTextArea taMeaning;

    JMenuBar menu;
    JMenu menuFile, menuEdit, menuAboutus;
    JMenuItem itemOpen, itemSave;

    DictController controller = new DictController();

    DefaultListModel<String> stage;
    DefaultComboBoxModel<String> listDict;

    public DictGUI() {
        super("Từ Điển");
        initComponents();
    }

    public void initComponents() {

        listDict = controller.initDictComboBox();
        stage = controller.getAllNamesInDict(listDict.getElementAt(0));

        panel = new JPanel();
        tfWord = new JTextField();
        cbChoose = new JComboBox<String>(listDict);
        listWord = new JList<String>(stage);
        scrollPanel = new JScrollPane(listWord);
        taMeaning = new JTextArea();
        butAddDict = new JButton("Thêm từ điển");
        butAddWord = new JButton("Thêm từ");
        butEdit = new JButton("Sửa từ");
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
        taMeaning.setLineWrap(true);

        tfWord.setBounds(20, 15, 250, 30);
        cbChoose.setBounds(300, 15, 130, 30);
        butAddDict.setBounds(450, 15, 150, 30);
        butAddWord.setBounds(620, 15, 100, 30);
        butEdit.setBounds(730, 15, 100, 30);
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
        panel.add(butEdit);
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
                        // listWord.setSelectedIndex(0);
                    }
                } else {
                    listWord.setSelectedIndex(-1);
                    stage.clear();
                    controller.resetStage();
                    listWord.setModel(stage);
                }
            }
        });

        listWord.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (listWord.isSelectionEmpty())
                    taMeaning.setText("");
                else
                    taMeaning.setText(controller.getContentOfWord(listWord.getSelectedIndex()));
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
                Component component = (Component) arg0.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                (new addDictFrame(frame)).setVisible(true);
            }
        });

        butAddWord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Component component = (Component) arg0.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                (new addWordFrame(frame)).setVisible(true);
            }
        });

        butEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!listWord.isSelectionEmpty()) {
                    Component component = (Component) arg0.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                    (new editWordFrame(frame)).setVisible(true);
                }
            }
        });

        butDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!listWord.isSelectionEmpty()) {
                    controller.deleteWord(listWord.getSelectedIndex());
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.writeToFile();
            }
        });

    }

    public class addDictFrame extends JDialog {

        private static final long serialVersionUID = 1L;
        JLabel lDict;
        JTextField tfDict;
        JButton confirm;

        public addDictFrame(Frame parent) {
            super(parent, "Add Dictionary");
            this.setSize(400, 150);
            this.setLayout(null);
            this.setResizable(false);
            this.setModal(true);
            this.setLocationRelativeTo(null);

            lDict = new JLabel("Dictionary:");
            tfDict = new JTextField();
            confirm = new JButton("Confirm");

            lDict.setBounds(20, 20, 50, 20);
            tfDict.setBounds(90, 20, 280, 20);
            confirm.setBounds(150, 60, 90, 30);

            this.add(lDict);
            this.add(tfDict);
            this.add(confirm);

            confirm.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    controller.addNewDict(tfDict.getText());
                    dispose();
                }
            });
        }

    }

    public class addWordFrame extends JDialog {

        private static final long serialVersionUID = 1L;
        JLabel lWord, lPronounce, lMean;
        JTextField tfWord, tfPronouce, tfMean;
        JScrollPane spAdd;
        JButton confirm;
        JTextArea taMean;
        JLabel result;

        public addWordFrame(Frame parent) {
            super(parent, "Add Word");
            this.setSize(400, 330);
            this.setLayout(null);
            this.setResizable(false);
            this.setModal(true);
            this.setLocationRelativeTo(null);

            lWord = new JLabel("Word:");
            lPronounce = new JLabel("Pronounce:");
            lMean = new JLabel("Meaning:");
            tfWord = new JTextField();
            tfPronouce = new JTextField();
            taMean = new JTextArea();
            spAdd = new JScrollPane(taMean);
            confirm = new JButton("Confirm");
            result = new JLabel();
            taMean.setEditable(true);
            taMean.setLineWrap(true);

            lWord.setBounds(20, 20, 50, 20);
            lPronounce.setBounds(20, 60, 60, 20);
            lMean.setBounds(20, 100, 50, 20);
            tfWord.setBounds(90, 20, 280, 20);
            tfPronouce.setBounds(90, 60, 280, 20);
            spAdd.setBounds(90, 100, 280, 130);
            confirm.setBounds(150, 245, 90, 30);
            result.setBounds(250, 245, 90, 30);

            this.add(lWord);
            this.add(lPronounce);
            this.add(lMean);
            this.add(tfWord);
            this.add(tfPronouce);
            this.add(spAdd);
            this.add(confirm);
            this.add(result);

            confirm.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (tfWord.getText().equals("") && tfPronouce.getText().equals("") && taMean.getText().equals(""))
                        result.setText("Please Enter !");
                    else {
                        int re = -1;
                        re = controller.addsWord(tfWord.getText(), tfPronouce.getText(), taMean.getText());
                        if (re == -1)
                            result.setText("Word Exists !");
                        if (re == 0)
                            dispose();
                    }
                }
            });
        }

    }

    public class editWordFrame extends JDialog {

        private static final long serialVersionUID = 1L;
        JLabel lWord, lPronounce, lMean;
        JTextField tfWord, tfPronouce, tfMean;
        JScrollPane spAdd;
        JButton confirm;
        JTextArea taMean;
        JLabel result;

        public editWordFrame(Frame parent) {
            super(parent, "Edit Word");
            this.setSize(400, 330);
            this.setLayout(null);
            this.setResizable(false);
            this.setModal(true);
            this.setLocationRelativeTo(null);

            lWord = new JLabel("Word:");
            lPronounce = new JLabel("Pronounce:");
            lMean = new JLabel("Meaning:");
            tfWord = new JTextField();
            tfPronouce = new JTextField();
            taMean = new JTextArea();
            spAdd = new JScrollPane(taMean);
            confirm = new JButton("Confirm");
            result = new JLabel();
            taMean.setEditable(true);
            taMean.setLineWrap(true);

            lWord.setBounds(20, 20, 50, 20);
            lPronounce.setBounds(20, 60, 60, 20);
            lMean.setBounds(20, 100, 50, 20);
            tfWord.setBounds(90, 20, 280, 20);
            tfPronouce.setBounds(90, 60, 280, 20);
            spAdd.setBounds(90, 100, 280, 130);
            confirm.setBounds(150, 245, 90, 30);
            result.setBounds(250, 245, 90, 30);

            this.add(lWord);
            this.add(lPronounce);
            this.add(lMean);
            this.add(tfWord);
            this.add(tfPronouce);
            this.add(spAdd);
            this.add(confirm);
            this.add(result);

            confirm.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (tfWord.getText().equals("") && tfPronouce.getText().equals("") && taMean.getText().equals(""))
                        result.setText("Please Enter !");
                    else {
                        int re = -1;
                        re = controller.updateWord(listWord.getSelectedIndex(), tfWord.getText(), tfPronouce.getText(),
                                taMean.getText());
                        if (re == -1)
                            result.setText("Word Exists !");
                        if (re == 0)
                            dispose();
                    }
                }
            });
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DictGUI().setVisible(true);
            }
        });
    }

}