package ro.cuzma.tools.germana.translation;

import java.util.ArrayList;
import java.util.List;

import ro.cuzma.tools.germana.tools.MyStringTokenizer;

public class Cuvant extends Translation {
    private String input;
    private String traducere;
    public static String ID = "cuv";

    public String getTraducere() {
        return traducere;
    }

    public void setRomana(String romana) {
        this.input = romana;
    }

    public void setTraducere(String traducere) {
        this.traducere = traducere;
    }

    public String getRomana() {
        return input;
    }

    public Cuvant(int apparition, Language language, String input, String traducere, Tested tested) {
        super(apparition, language, tested);
        this.input = input;
        this.traducere = traducere;
    }

    public Cuvant(int apparition, Language language, String input, String traducere,
            String description, Tested tested) {
        super(apparition, language, tested);
        this.input = input;
        this.traducere = traducere;
        this.setDescription(description);
    }

    public static List<Translation> build(int apparition, MyStringTokenizer st) {
        List<Translation> list = new ArrayList<Translation>();
        String source = st.nextToken();
        String translation = st.nextToken();
        String desc = "";
        Language language = Language.LANG_ALL;
        Tested tested = Tested.NOTTESTED;
        if (st.hasMoreTokens()) {
            desc = st.nextToken();
            if (st.hasMoreTokens()) {
                language = Translation.gelLanguagefromString(st.nextToken());
                if (st.hasMoreTokens()) {
                    tested = gelTestedfromString(st.nextToken());
                }
            }

        }
        if (language == Language.LANG_ALL) {
            list.add(new Cuvant(apparition, Language.LANG_RO_DE, source, translation, desc, tested));
            list.add(new Cuvant(apparition, Language.LANG_DE_RO, translation, source,
                    Tested.NOTTESTED));
        } else {
            list.add(new Cuvant(apparition, language, source, translation, desc, tested));
        }
        return list;
    }

    @Override
    public StringBuilder internal_getCsvString(StringBuilder sb, String separator) {
        sb.append(ID);
        sb.append(separator);
        sb.append(input);
        sb.append(separator);
        sb.append(traducere);
        return sb;
    }

}
