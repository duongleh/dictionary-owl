package DictController;

import DictView.*;
import DictModel.*;

import javax.swing.*;

public class DictController {

    private static DictController instance = new DictController();

    private dictionary dict;
    private DefaultComboBoxModel<String> listDict = new DefaultComboBoxModel<>();

    private DefaultListModel<String> listName;
    private DefaultListModel<String> stage;

    public static DictController getInstance() {
        return instance;
    }

    public void startApplication() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DictGUI view = DictGUI.getInstance();
        view.setSize(1000, 675);
        view.setResizable(false);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    public DefaultComboBoxModel<String> initDictComboBox() {
        listDict.addElement("Anh - Việt");
        listDict.addElement("Việt - Anh");
        return listDict;
    }

    public DefaultListModel<String> initDict(String name) {
        String filename = null;
        if (name.equals("Anh - Việt"))
            filename = "AnhViet.txt";
        else if (name.equals("Việt - Anh"))
            filename = "VietAnh.txt";
        else
            filename = name + ".txt";

        dict = new dictionary(name, filename);
        listName = dict.getListName();
        stage = dict.getStage();

        return stage;
    }

    public DefaultListModel<String> searchWord(String word) {
        stage.clear();
        stage = new DefaultListModel<String>();

        if (word.equals("")) {
            return resetStage();
        } else {
            for (int indx = 0; indx < listName.getSize(); indx++) {
                if ((listName.getElementAt(indx)).startsWith(word)) {
                    stage.addElement(listName.getElementAt(indx));
                }
            }
            return stage;
        }
    }

    public String getContentOfWord(int index) {
        String name = stage.getElementAt(index);
        for (Word x : dict.getListWord()) {
            if (x.getName().equals(name)) {
                if (x.getPronounce().equals(""))
                    return x.getMeaning();
                else
                    return x.getPronounce() + "\n" + x.getMeaning();
            }
        }
        return null;

    }

    public String getMeaningOfWord(int index) {
        String name = stage.getElementAt(index);
        for (Word x : dict.getListWord()) {
            if (x.getName().equals(name)) {
                return x.getMeaning();
            }
        }
        return null;

    }

    public String getPronouceOfWord(int index) {
        String name = stage.getElementAt(index);
        for (Word x : dict.getListWord()) {
            if (x.getName().equals(name)) {
                return x.getPronounce();
            }
        }
        return null;

    }

    public DefaultListModel<String> resetStage() {
        for (int indx = 0; indx < listName.getSize(); indx++) {
            stage.addElement(listName.getElementAt(indx));
        }
        return stage;
    }

    public void addNewDict(String filename) {
        listDict.addElement(filename);
        listDict.setSelectedItem(filename);
    }

    public int addWord(String name, String pronounce, String meaning) {
        if (dict.findWord(name) == null) {
            Word w = new Word(name, pronounce, meaning);
            dict.getListWord().add(w);
            stage.addElement(name);
            listName.addElement(name);
        } else
            return -1;
        return 0;
    }

    public int updateWord(int index, String name, String pronounce, String meaning) {
        int i;
        Word w = dict.findWord(stage.getElementAt(index));

        if (dict.findWord(name) == null)
            ;
        else {
            if (w.getName().equals(name))
                ;
            else {
                return -1;
            }
        }

        Word x = new Word(name, pronounce, meaning);
        dict.getListWord().set(dict.getListWord().indexOf(w), x);

        for (i = 0; i < listName.size(); i++) {
            if (listName.getElementAt(i).equals(stage.getElementAt(index)))
                break;
        }

        stage.set(index, name);
        listName.set(i, name);

        return 0;
    }

    public void deleteWord(int index) {
        int i = 0;
        dict.getListWord().remove(dict.findWord(stage.getElementAt(index)));
        for (i = 0; i < listName.size(); i++) {
            if (listName.getElementAt(i).equals(stage.getElementAt(index)))
                break;
        }
        listName.remove(i);
        stage.remove(index);
    }

    public void writeToFile() {
        dict.writeDictToFile();
    }
}