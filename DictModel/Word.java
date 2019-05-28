package DictModel;

public class Word {
    private String name;
    private String pronounce;
    private String meaning;

    public Word(String name, String pronounce, String meaning) {
        this.name = name;
        this.pronounce = pronounce;
        this.meaning = meaning;
    }

    public Word(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getPronounce() {
        return this.pronounce;
    }

    public String getMeaning() {
        return this.meaning;
    }
}
