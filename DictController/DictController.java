package DictController;

import DictView.*;
import DictModel.*;

import java.awt.*;
import java.util.List;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DictController {
    private dictionary dict;
    private DefaultListModel<String> model;
    private DefaultListModel<String> stage;
    private DefaultComboBoxModel<String> listDict;

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


    public DefaultListModel<String> getAllNamesInDict(String name) {

        model = new DefaultListModel<String>();
        stage = new DefaultListModel<String>();
        String filename = null;
        if (name.equals("Anh - Việt"))
            filename = "AnhViet.txt";
        else if (name.equals("Việt - Anh"))
            filename = "VietAnh.txt";
        else
            filename = name + ".txt";

        dict = new dictionary(name, filename);

        List<Word> words = dict.getWords();
        for (Word w : words) {
            model.addElement(w.getName());
            stage.addElement(w.getName());
        }
        return stage;
    }

    public String getContentOfWord(int index) {
        return dict.getContent(stage.getElementAt(index));
    }

    public DefaultListModel<String> findWords(String word) {
        DefaultListModel<String> stage2 = new DefaultListModel<String>();
        int indx, change = 0;

        for (indx = 0; indx < model.getSize(); indx++) {
            if ((model.getElementAt(indx)).startsWith(word)) {
                stage2.addElement(model.getElementAt(indx));
                change = 1;
            }
        }

        if (change == 0)
            return null;
        return stage2;
    }

    public void resetStage() {
        int indx;
        for (indx = 0; indx < model.getSize(); indx++) {
            stage.addElement(model.getElementAt(indx));
        }
    }

    public DefaultComboBoxModel<String> initDictComboBox() {
        listDict = new DefaultComboBoxModel<>();
        listDict.addElement("Anh - Việt");
        listDict.addElement("Việt - Anh");
        return listDict;
    }

    public void addNewDict(String filename) {
        listDict.addElement(filename);
        listDict.setSelectedItem(filename);
    }

    public int addsWord(String name, String pronounce, String meaning) {
        if (dict.findWord(name) == null) {
            Word w = new Word(name, pronounce, meaning);
            dict.addWord(w);
            model.addElement(name);
            stage.addElement(name);
        } else
            return -1;
        return 0;
    }

    public int updateWord(int index, String name, String pronounce, String meaning) {
        int i = 0;
        if ((dict.findWord(name)) == null) {
            dict.editWord(dict.findWord(stage.getElementAt(index)), name, pronounce, meaning);
            for (i = 0; i < model.size(); i++) {
                if (model.getElementAt(i).equals(stage.getElementAt(index)))
                    break;
            }
            if (!name.equals("")) {
                model.set(i, name);
                stage.set(index, name);
            }
        } else
            return -1;
        return 0;
    }

    public void deleteWord(int index) {
        int i = 0;
        dict.removeWord(dict.findWord(stage.getElementAt(index)));
        for (i = 0; i < model.size(); i++) {
            if (model.getElementAt(i).equals(stage.getElementAt(index)))
                break;
        }
        stage.remove(index);
        model.remove(i);
    }

    public void writeToFile() {
        dict.writeDictToFile();
    }
}