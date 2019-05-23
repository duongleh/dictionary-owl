package DictController;

import DictView.*;
import DictModel.*;

import java.io.File;
import java.util.*;

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

        GUI view = new GUI();
        view.setSize(1000, 675);
        view.setResizable(false);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    public DefaultListModel<String> getAllNamesInDict(String name) {

        model = new DefaultListModel<String>();
        stage = new DefaultListModel<String>();
        String filename = null;
        if (name.equals("Anh - Việt"))
            filename = "AnhViet.txt";
        else if (name.equals("Việt - Anh"))
            filename = "VietAnh.txt";
            else filename = name + ".txt";

        dict = new dictionary(name,filename);

        List<Word> words = dict.getWords();
        for (Word w : words) {
            model.addElement(w.getName());
            stage.addElement(w.getName());
        }
        return stage;
    }

    public String getMeaningOfWord(int index) {
        return dict.getMeaning(stage.getElementAt(index));
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

    public Word getWord(String string) {
        return dict.findWord(string);
    }

    public void addsWord() {
        String name = null;
        String pronounce = null;
        String meaning = null;
        Scanner scanner;
        Word w = null;
        boolean hasExc;
        do {
            try {
                scanner = new Scanner(System.in);
                System.out.println("Nhập từ mà bạn muốn thêm ");
                System.out.print("Từ: ");
                name = scanner.nextLine();
                System.out.print("Phát âm: ");
                pronounce = scanner.nextLine();
                System.out.print("Nghĩa: ");
                meaning = scanner.nextLine();
                hasExc = false;
                if (dict.findWord(name) != null)
                    throw new Exception();
            } catch (Exception exc) {
                System.out.println("Word exists !");
                System.out.println();
                hasExc = true;
            }
        } while (hasExc == true);
        w = new Word(name, pronounce, meaning);
        dict.addWord(w);
    }

    public void deleteWord(Word w) {
        dict.removeWord(w);
    }

    public void updateWord(Word w) {
        String name = null;
        String pronounce = null;
        String meaning = null;
        Scanner scanner;
        boolean hasExc;
        do {
            try {
                scanner = new Scanner(System.in);
                System.out.println("Cập nhập từ mới (ấn enter để bỏ qua): ");
                System.out.print("Từ: ");
                name = scanner.nextLine();
                System.out.print("Phát âm: ");
                pronounce = scanner.nextLine();
                System.out.print("Nghĩa: ");
                meaning = scanner.nextLine();
                hasExc = false;
            } catch (Exception exc) {
                System.out.println("Invalid Syntax");
                hasExc = true;
            }
        } while (hasExc == true);

        if (name.equals(""))
            name = w.getName();
        if (pronounce.equals(""))
            pronounce = w.getPronounce();
        if (meaning.equals(""))
            meaning = w.getMeaning();
        dict.editWord(w, name, pronounce, meaning);
    }

}