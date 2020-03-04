package DictView;

import DictController.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DictGUI extends JFrame {
    private static DictGUI instance = new DictGUI();

    private static final long serialVersionUID = 1L;
    private DictController controller = DictController.getInstance();
    private JPanel panel = new JPanel();
    private JTextField inputSearch = new JTextField();

    private DefaultListModel<String> stage;
    private DefaultComboBoxModel<String> listDict;

    private JTextArea meaningArea = new JTextArea();
    private JButton btnAddDict = new JButton("Thêm từ điển");
    private JButton btnAddWord = new JButton("Thêm từ");
    private JButton btnEdit = new JButton("Sửa từ");
    private JButton btnDel = new JButton("Xóa từ");
    private JScrollPane meaningScroll = new JScrollPane(meaningArea);

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Menu");
    private JMenuItem aboutItem = new JMenuItem("About");

    private JComboBox<String> selectBox;
    private JList<String> listWord;
    private JScrollPane scrollPanel;

    public static DictGUI getInstance() {
        return instance;
    }

    public DictGUI() {
        super("Từ Điển");
        initComponents();
    }

    public void initComponents() {

        listDict = controller.initDictComboBox();
        stage = controller.initDict(listDict.getElementAt(0));

        selectBox = new JComboBox<String>(listDict);
        listWord = new JList<String>(stage);
        scrollPanel = new JScrollPane(listWord);

        panel.setLayout(null);
        listWord.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        meaningArea.setEditable(false);
        listWord.setModel(stage);
        meaningArea.setLineWrap(true);

        inputSearch.setBounds(20, 15, 250, 30);
        selectBox.setBounds(300, 15, 130, 30);
        btnAddDict.setBounds(450, 15, 150, 30);
        btnAddWord.setBounds(620, 15, 100, 30);
        btnEdit.setBounds(730, 15, 100, 30);
        btnDel.setBounds(840, 15, 100, 30);
        scrollPanel.setBounds(20, 60, 250, 540);
        meaningScroll.setBounds(300, 60, 675, 540);

        menuBar.add(menu);
        menu.add(aboutItem);

        panel.add(inputSearch);
        panel.add(selectBox);
        panel.add(btnAddDict);
        panel.add(btnAddWord);
        panel.add(btnEdit);
        panel.add(btnDel);
        panel.add(scrollPanel);
        panel.add(meaningScroll);
        this.add(panel);
        this.setJMenuBar(menuBar);

        inputSearch.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                String input = inputSearch.getText();
                stage = controller.searchWord(input);
                listWord.setModel(stage);
                listWord.setSelectedIndex(0);
                if (stage.getSize() == 0)
                    meaningArea.setText("Word does not exist !");
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
                    meaningArea.setText("");
                else
                    meaningArea.setText(controller.getContentOfWord(listWord.getSelectedIndex()));
            }
        });

        selectBox.addActionListener(new ActionListener() {

            @Override
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent arg0) {
                JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
                String comboBox = (String) cb.getSelectedItem();
                stage = controller.initDict(comboBox);
                listWord.setModel(stage);
            }
        });

        btnAddDict.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Component component = (Component) arg0.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                (new addDictFrame(frame)).setVisible(true);
            }
        });

        btnAddWord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Component component = (Component) arg0.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                (new addWordFrame(frame)).setVisible(true);
            }
        });

        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!listWord.isSelectionEmpty()) {
                    Component component = (Component) arg0.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                    (new editWordFrame(frame, listWord.getSelectedValue(),
                            controller.getMeaningOfWord(listWord.getSelectedIndex()),
                            controller.getPronouceOfWord(listWord.getSelectedIndex()), listWord.getSelectedIndex()))
                                    .setVisible(true);
                    meaningArea.setText(controller.getContentOfWord(listWord.getSelectedIndex()));
                }
            }
        });

        btnDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!listWord.isSelectionEmpty()) {
                    controller.deleteWord(listWord.getSelectedIndex());
                }
            }
        });

        aboutItem.addActionListener(new ActionListener() {

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DictGUI().setVisible(true);
            }
        });
    }

}