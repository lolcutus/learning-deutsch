package ro.cuzma.tools.germana.translation;

import java.util.ArrayList;
import java.util.List;

import ro.cuzma.tools.germana.tools.MyStringTokenizer;

public class Substantiv extends Translation {

    public static String ID = "subst";

    private String sourceSg;
    private String articol;
    private String sourcePl;

    private String translationSg;
    private String translationPl;

    public Substantiv(int apparition, Language language, String sourceSg, String sourcePl,
            String translationSg, String translationPl, Tested tested, String articol) {
        super(apparition, language, tested);
        this.sourceSg = sourceSg;
        this.articol = articol;
        this.sourcePl = sourcePl;
        this.translationSg = translationSg;
        this.translationPl = translationPl;
    }

    public Substantiv(int apparition, Language language, String sourceSg, String sourcePl,
            String translationSg, String translationPl, String description, Tested tested,
            String articol) {
        super(apparition, language, tested);
        this.sourceSg = sourceSg;
        this.articol = articol;
        this.sourcePl = sourcePl;
        this.translationSg = translationSg;
        this.translationPl = translationPl;
        setDescription(description);
    }

    public static List<Translation> build(int apparition, MyStringTokenizer st) {

        String sourceSg = st.nextToken();
        String nextT = st.nextToken();
        String translationSg = null;
        String articol = null;
        if (!nextT.equals("#")) {
            articol = nextT;
            translationSg = st.nextToken();
        } else {
            st.nextToken();

        }
        String translationPl = "";
        String sourcePl = null;
        String description = "";
        Language language = Language.LANG_ALL;
        Tested tested = Tested.NOTTESTED;
        if (st.hasMoreTokens()) {
            translationPl = st.nextToken();
            // if (translationPl != null && !translationPl.equals("")) {
            // translationPl = translationPl;
            // }
            if (st.hasMoreTokens()) {
                sourcePl = st.nextToken();
                if (st.hasMoreTokens()) {
                    description = st.nextToken();
                    if (st.hasMoreTokens()) {
                        language = Translation.gelLanguagefromString(st.nextToken());
                        if (st.hasMoreTokens()) {
                            tested = gelTestedfromString(st.nextToken());
                        }
                    }
                }
            }
        }
        List<Translation> list = new ArrayList<Translation>();

        list.add(new Substantiv(apparition, Language.LANG_RO_DE, sourceSg, sourcePl, translationSg,
                translationPl, description, tested, articol));
        if (language == Language.LANG_ALL) {
            if (translationSg != null && !sourceSg.equals("")) {
                list.add(new Cuvant(apparition, Language.LANG_DE_RO, articol + " " + translationSg,
                        sourceSg, Tested.NOTTESTED));
            }
            if (sourcePl != null && !translationPl.equals("")) {
                list.add(new Cuvant(apparition, Language.LANG_DE_RO, "die " + translationPl,
                        sourcePl, Tested.NOTTESTED));
            }
        }
        return list;
    }

    public String getSourceSg() {
        return sourceSg;
    }

    public String getSourcePl() {
        return sourcePl;
    }

    public String getTranslationSg() {
        return translationSg;
    }

    public String getTranslationSgWithArticle() {

        if (translationSg != null && !translationSg.isEmpty())
            return articol + " " + translationSg;
        return translationSg;
    }

    public String getArticol() {
        return articol;
    }

    public String getTranslationPl() {
        return translationPl;
    }

    public String getTranslationPlWithArticle() {
        if (translationPl != null && !translationPl.isEmpty())
            return "die " + translationPl;
        return translationPl;
    }

    @Override
    public StringBuilder internal_getCsvString(StringBuilder sb, String separator) {
        sb.append(ID);
        sb.append(separator);
        sb.append(sourceSg);
        sb.append(separator);
        sb.append(articol);
        sb.append(separator);
        sb.append(translationSg);
        sb.append(separator);
        sb.append(translationPl);
        sb.append(separator);
        sb.append(sourcePl);
        return sb;
    }

}
