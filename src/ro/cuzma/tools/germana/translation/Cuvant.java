package ro.cuzma.tools.germana.translation;

import java.util.ArrayList;
import java.util.List;

import ro.cuzma.tools.germana.tools.MyStringTokenizer;
import ro.cuzma.tools.germana.ui.TestDialogs;

public class Cuvant extends Translation {
    private String input;
    private String traducere;

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

    public Cuvant(int apparition, String language, String input, String traducere) {
        super(apparition, language);
        this.input = input;
        this.traducere = traducere;
    }

    public Cuvant(int apparition, String language, String input, String traducere,
            String description) {
        super(apparition, language);
        this.input = input;
        this.traducere = traducere;
        this.setDescription(description);
    }

    public static List<Translation> build(int apparition, MyStringTokenizer st) {
        List<Translation> list = new ArrayList<Translation>();
        String source = st.nextToken();
        String translation = st.nextToken();
        String desc = "";
        if (st.hasMoreTokens()) {
            desc = st.nextToken();
        }
        list.add(new Cuvant(apparition, TestDialogs.LANG_RO_DE, source, translation, desc));
        list.add(new Cuvant(apparition, TestDialogs.LANG_DE_RO, translation, source));
        return list;
    }

}
