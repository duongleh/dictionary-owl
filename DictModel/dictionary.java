package DictModel;

import java.io.*;
import java.util.*;

import javax.swing.*;

public class dictionary {
    private String dictName;
    private String dictFIleName;
    private final List<Word> listWord = new ArrayList<Word>();
    private DefaultListModel<String> listName = new DefaultListModel<String>();
    private DefaultListModel<String> stage = new DefaultListModel<String>();

    public dictionary(String name, String filename) {

        this.dictName = name;
        this.dictFIleName = filename;
        File f = new File(dictFIleName);
        if (f.exists())
            readDictFromFile(dictFIleName);

        for (Word w : listWord) {
            stage.addElement(w.getName());
            listName.addElement(w.getName());
        }
    }

    public String getFileName() {
        return this.dictFIleName;
    }

    public List<Word> getListWord() {
        return this.listWord;
    }

    public DefaultListModel<String> getListName() {
        return this.listName;
    }

    public DefaultListModel<String> getStage() {
        return this.stage;
    }

    public Word findWord(String name) {
        for (Word x : listWord) {
            if (x.getName().equals(name))
                return x;
        }
        return null;
    }

    public void writeDictToFile() {
        PrintWriter out = null;

        try {
            out = new PrintWriter(getFileName());
            for (Word x : getListWord()) {
                out.println("@" + x.getName());
                out.println(x.getPronounce());
                out.println(x.getMeaning());
                out.println();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public void readDictFromFile(String filename) {
        BufferedReader fr = null;
        char text;
        int c;
        String name = null, pronounce = null, meaning = null;
        Word w = null;

        try {
            fr = new BufferedReader(new FileReader(filename));
            c = fr.read();
            text = (char) c;
            while (c != -1) {
                name = new String();
                pronounce = new String();
                meaning = new String();

                if (text == '@') {
                    do {
                        if (text != '@')
                            name += text;
                        c = fr.read();
                        text = (char) c;
                    } while (text != '\n' && c != -1);
                    c = fr.read();
                    text = (char) c;
                }

                if (text == '/') {
                    do {
                        pronounce += text;
                        c = fr.read();
                        text = (char) c;
                    } while (text != '\n' && c != -1);
                    c = fr.read();
                    text = (char) c;
                }

                if (text != '@') {
                    do {
                        meaning += text;
                        c = fr.read();
                        text = (char) c;
                    } while (text != '@' && c != -1);
                    while (meaning.length() > 0 && meaning.substring(meaning.length() - 1).equals("\n"))
                        meaning = meaning.substring(0, meaning.lastIndexOf("\n"));
                }

                if (name != null || pronounce != null || meaning != null) {
                    w = new Word(name, pronounce, meaning);
                    listWord.add(w);
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

}