package ro.cuzma.tools.germana.translation;

public abstract class Translation {

    public enum Language {
        LANG_ALL("all translations"), LANG_RO_DE("Ro -> De translations"), LANG_DE_RO(
                "De -> Ro translations");
        private String description;

        private Language(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }

    public enum Tested {
        OK, NOTOK, NOTTESTED
    }

    private String description = "";
    private int apparition = 0;
    private Tested tested = Tested.NOTTESTED;

    public Tested getTested() {
        return tested;
    }

    public void setTested(Tested tested) {
        this.tested = tested;
    }

    public Language getLanguage() {
        return language;
    }

    private Language language = Language.LANG_ALL;

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Translation(int apparition, Language language, Tested tested) {
        this.apparition = apparition;
        this.language = language;
        this.tested = tested;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getApparition() {
        return apparition;
    }

    public void setApparition(int apparition) {
        this.apparition = apparition;
    }

    public static Language gelLanguagefromString(String lang) {
        if (Language.LANG_DE_RO.name().equals(lang))
            return Language.LANG_DE_RO;
        if (Language.LANG_RO_DE.name().equals(lang))
            return Language.LANG_RO_DE;
        return Language.LANG_ALL;
    }

    public static Tested gelTestedfromString(String tested) {
        if (Tested.NOTOK.name().equals(tested))
            return Tested.NOTOK;
        if (Tested.OK.name().equals(tested))
            return Tested.OK;
        return Tested.NOTTESTED;
    }

    protected abstract StringBuilder internal_getCsvString(StringBuilder sb, String separator);

    public StringBuilder getCsvString(StringBuilder sb, String separator) {
        sb = internal_getCsvString(sb, separator);
        sb.append(separator);
        sb.append(getDescription());
        sb.append(separator);
        sb.append(getLanguage());
        sb.append(separator);
        sb.append(getTested());
        sb.append("\n");
        return sb;
    }
}
