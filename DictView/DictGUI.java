package DictView;

import DictController.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DictGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private DictController controller = new DictController();
    private JPanel panel = new JPanel();
    private JTextField tfWord = new JTextField();

    private DefaultListModel<String> stage;
    private DefaultComboBoxModel<String> listDict;

    private JTextArea taMeaning = new JTextArea();
    private JButton butAddDict = new JButton("Thêm từ điển");
    private JButton butAddWord = new JButton("Thêm từ");
    private JButton butEdit = new JButton("Sửa từ");
    private JButton butDel = new JButton("Xóa từ");
    private JScrollPane spMeaning = new JScrollPane(taMeaning);

    private JMenuBar menu = new JMenuBar();
    private JMenu menuMenu = new JMenu("Menu");
    private JMenuItem itemAbout = new JMenuItem("About");

    private JComboBox<String> cbChoose;
    private JList<String> listWord;
    private JScrollPane scrollPanel;

    public DictGUI() {
        super("Từ Điển");
        initComponents();
    }

    public void initComponents() {

        listDict = controller.initDictComboBox();
        stage = controller.initDict(listDict.getElementAt(0));

        cbChoose = new JComboBox<String>(listDict);
        listWord = new JList<String>(stage);
        scrollPanel = new JScrollPane(listWord);

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
        spMeaning.setBounds(300, 60, 675, 540);

        menu.add(menuMenu);
        menuMenu.add(itemAbout);

        panel.add(tfWord);
        panel.add(cbChoose);
        panel.add(butAddDict);
        panel.add(butAddWord);
        panel.add(butEdit);
        panel.add(butDel);
        panel.add(scrollPanel);
        panel.add(spMeaning);
        this.add(panel);
        this.setJMenuBar(menu);

        tfWord.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                String input = tfWord.getText();
                stage = controller.searchWord(input);
                listWord.setModel(stage);
                listWord.setSelectedIndex(0);
                if (stage.getSize() == 0)
                    taMeaning.setText("Word does not exist !");
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
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
                stage = controller.initDict(comboBox);
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
                    (new editWordFrame(frame, listWord.getSelectedValue(),
                            controller.getMeaningOfWord(listWord.getSelectedIndex()),
                            controller.getPronouceOfWord(listWord.getSelectedIndex()))).setVisible(true);
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

        itemAbout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Component component = (Component) arg0.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                (new aboutFrame(frame)).setVisible(true);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // controller.writeToFile();
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
            this.setSize(420, 140);
            this.setLayout(null);
            this.setResizable(false);
            this.setModal(true);
            this.setLocationRelativeTo(null);

            lDict = new JLabel("Dictionary:");
            tfDict = new JTextField();
            confirm = new JButton("Confirm");

            lDict.setBounds(20, 20, 100, 20);
            tfDict.setBounds(120, 20, 280, 35);
            confirm.setBounds(170, 60, 90, 30);

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
            this.setSize(420, 320);
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

            lWord.setBounds(20, 20, 70, 20);
            lPronounce.setBounds(20, 60, 100, 20);
            lMean.setBounds(20, 100, 100, 20);
            tfWord.setBounds(120, 20, 280, 35);
            tfPronouce.setBounds(120, 60, 280, 35);
            spAdd.setBounds(120, 100, 280, 130);
            confirm.setBounds(160, 245, 90, 30);
            result.setBounds(260, 245, 130, 30);

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
                        result.setText("Invalid Value !");
                    else if (tfWord.getText().equals("")) {
                        result.setText("Invalid Word !");
                    } else {
                        int re = controller.addWord(tfWord.getText(), tfPronouce.getText(), taMean.getText());
                        if (re == -1)
                            result.setText("Word Exists !");
                        else if (re == 0)
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

        public editWordFrame(Frame parent, String word, String meaning, String pronounce) {
            super(parent, "Edit Word");
            this.setSize(420, 320);
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

            lWord.setBounds(20, 20, 70, 20);
            lPronounce.setBounds(20, 60, 100, 20);
            lMean.setBounds(20, 100, 100, 20);
            tfWord.setBounds(120, 20, 280, 35);
            tfPronouce.setBounds(120, 60, 280, 35);
            spAdd.setBounds(120, 100, 280, 130);
            confirm.setBounds(160, 245, 90, 30);
            result.setBounds(260, 245, 130, 30);

            tfWord.setText(word);
            tfPronouce.setText(pronounce);
            taMean.setText(meaning);

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
                    if (tfWord.getText().equals("") || tfPronouce.getText().equals("") || taMean.getText().equals(""))
                        result.setText("Invalid Value!");
                    else {
                        int re = controller.updateWord(listWord.getSelectedIndex(), tfWord.getText(),
                                tfPronouce.getText(), taMean.getText());
                        if (re == -1)
                            result.setText("Word Exists !");
                        if (re == 0) {
                            taMeaning.setText(controller.getContentOfWord(listWord.getSelectedIndex()));
                            dispose();
                        }
                    }
                }
            });
        }
    }

    public class aboutFrame extends JDialog {

        private static final long serialVersionUID = 1L;
        JLabel image;
        JTextArea taAbout;

        String about = "   Some people like to read on a screen. Other people need the variety and artistry, the sight, smell, \nand feel of actual knowledge. We love seeing them on their shelves; we love having shelves for them.\n\n"
                + "   We love taking them along when we leave the house and stacking them by their bedsides. We love \nfinding old letters and bookmarks in them. \n\n"
                + "   We want to read in a way that offers a rich experience, more than the words only: the full offering ofadictionary. They are particular about covers, we want to surround ourselves with the definition of \ngood design.\n\n"
                + "   We are constantly expanding our content scope with new, fresh material to help further educate ourusers as our community is growing bigger every day. Our goal is to help as many people as possible with reliable,high-quality, and easy-to-use reference material.\n\n"
                + "   The leading dictionary featuring over 25,000 definitions spanning across critical definition topics. \nEach definition provides a clear and concise description of the term to help our users gain a \ncomprehensive understanding of the concept. \n\n"
                + "   CONTACT US:\n" + "   Email:Vukhanhly30@gmail.com\n" + "   Address: HUD3 TayNam LinhDam Street";

        public aboutFrame(Frame parent) {
            super(parent, "About us");
            this.setSize(888, 613);
            this.setLayout(null);
            this.setResizable(false);
            this.setModal(true);
            this.setLocationRelativeTo(null);
            image = new JLabel(new ImageIcon("resources/Picture1.png"));
            taAbout = new JTextArea(about);
            taAbout.setLineWrap(true);
            taAbout.setEditable(false);

            image.setBounds(600, 385, 210, 210);
            taAbout.setBounds(10, 10, 868, 380);

            this.add(image);
            this.add(taAbout);

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