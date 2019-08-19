package DictController;

import DictView.*;
import DictModel.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DictController {
    private dictionary dict;
    private DefaultComboBoxModel<String> listDict;
    private DefaultListModel<String> listName;
    private DefaultListModel<String> stage;

    public void startApplication() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DictGUI view = new DictGUI();
        view.setSize(1000, 675);
        view.setResizable(false);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        try {
            BufferedImage bi = ImageIO.read(new File("resources/icon.png"));
            ImageIcon im = new ImageIcon(bi.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            view.setIconImage(im.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DefaultComboBoxModel<String> initDictComboBox() {
        listDict = new DefaultComboBoxModel<>();
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
        stage = new DefaultListModel();

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
        if (name.equals(""))
            return -2;
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
        int i = 0;
        Word w, x;
        if ((dict.findWord(name)) == null) {
            w = dict.findWord(stage.getElementAt(index));
            if (name.equals(""))
                name = w.getName();
            if (pronounce.equals(""))
                pronounce = w.getPronounce();
            if (meaning.equals(""))
                meaning = w.getMeaning();

            x = new Word(name, pronounce, meaning);
            dict.getListWord().set(dict.getListWord().indexOf(w), x);

            for (i = 0; i < listName.size(); i++) {
                if (listName.getElementAt(i).equals(stage.getElementAt(index)))
                    break;
            }
            if (!name.equals("")) {
                stage.set(index, name);
                listName.set(i, name);
            }
        } else
            return -1;
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