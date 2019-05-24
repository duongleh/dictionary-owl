package DictModel;

import java.io.*;
import java.util.*;

public class dictionary {
    private String dictName;
    private String dictFIleName;

    private final List<Word> words;

    public dictionary(String name, String filename) {
        this.dictName = name;
        this.dictFIleName = filename;
        File f = new File(filename);
        if (f.exists())
            this.words = readDictFromFile(filename);
        else
            this.words = new ArrayList<Word>();
    }

    public String getFileName() {
        return this.dictFIleName;
    }

    public String getContent(String name) {
        for (Word x : words) {
            if (x.getName().equals(name)) {
                if (x.getPronounce().equals(""))
                    return x.getMeaning();
                else
                    return x.getPronounce() + "\n" + x.getMeaning();
            }
        }
        return null;
    }

    public List<Word> getWords() {
        return this.words;
    }

    public Word findWord(String name) {
        for (Word x : words) {
            if (x.getName().equals(name))
                return x;
        }

        return null;
    }

    public void addWord(Word w) {
        words.add(w);
    }

    public void editWord(Word w, String name, String pronounce, String meaning) {
        if (name.equals(""))
            name = w.getName();
        if (pronounce.equals(""))
            pronounce = w.getPronounce();
        if (meaning.equals(""))
            meaning = w.getMeaning();
        Word x = new Word(name, pronounce, meaning);
        words.set(words.indexOf(w), x);
    }

    public void removeWord(Word w) {
        words.remove(w);
    }

    public void writeDictToFile() {
        PrintWriter out = null;

        try {
            out = new PrintWriter(getFileName());
            for (Word x : getWords()) {
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

    public List<Word> readDictFromFile(String filename) {
        BufferedReader fr = null;
        List<Word> listWord = new ArrayList<Word>();
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
        return listWord;
    }

}
