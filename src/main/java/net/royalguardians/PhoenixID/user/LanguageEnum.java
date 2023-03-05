package net.royalguardians.PhoenixID.user;

public enum LanguageEnum {

    GERMAN("DE", "German"),
    ENGLISH("ENG", "English");

    private String lang;
    private String name;

    private LanguageEnum(String lang, String name) {
        this.lang = lang;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
    }

    public static LanguageEnum getEnumByLang(String lang) {
        for(LanguageEnum languageEnum : values()) {
            if(lang.equals(languageEnum.getLang())) return languageEnum;
        }
        return ENGLISH;
    }
}
