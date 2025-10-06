package net.ausiasmarch.languages.model;

public class LanguageBean {

    private static String word;

    public static String getWord() {
        return word;
    }

    public static void setWord(String word) {
        LanguageBean.word = word;
    }
}
