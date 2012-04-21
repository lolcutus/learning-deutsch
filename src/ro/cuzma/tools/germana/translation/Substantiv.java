package ro.cuzma.tools.germana.translation;

import java.util.ArrayList;
import java.util.List;

import ro.cuzma.tools.germana.tools.MyStringTokenizer;
import ro.cuzma.tools.germana.ui.TestDialogs;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class Substantiv extends Translation {
    private String sourceSg;
    private String sourcePl;

    private String translationSg;
    private String translationPl;

    public Substantiv(int apparition, String language, String sourceSg, String sourcePl,
            String translationSg, String translationPl) {
        super(apparition, language);
        this.sourceSg = sourceSg;
        this.sourcePl = sourcePl;
        this.translationSg = translationSg;
        this.translationPl = translationPl;
    }

    public Substantiv(int apparition, String language, String sourceSg, String sourcePl,
            String translationSg, String translationPl, String description) {
        super(apparition, language);
        this.sourceSg = sourceSg;
        this.sourcePl = sourcePl;
        this.translationSg = translationSg;
        this.translationPl = translationPl;
        setDescription(description);
    }

    public static List<Translation> build(int apparition, MyStringTokenizer st) {

        String sourceSg = st.nextToken();
        String nextT = st.nextToken();
        String translationSg = null;
        if (!nextT.equals("#")) {
            translationSg = nextT + " " + st.nextToken();
        } else {
            st.nextToken();

        }
        String translationPl = "";
        String sourcePl = null;
        String description = "";
        if (st.hasMoreTokens()) {
            translationPl = st.nextToken();
            if (translationPl != null && !translationPl.equals("")) {
                translationPl = "die " + translationPl;
            }
            if (st.hasMoreTokens()) {
                sourcePl = st.nextToken();
                if (st.hasMoreTokens()) {
                    description = st.nextToken();
                }
            }
        }
        List<Translation> list = new ArrayList<Translation>();

        list.add(new Substantiv(apparition, TestDialogs.LANG_RO_DE, sourceSg, sourcePl,
                translationSg, translationPl, description));
        if (translationSg != null && !sourceSg.equals("")) {
            list.add(new Cuvant(apparition, TestDialogs.LANG_DE_RO, translationSg, sourceSg));
        }
        if (sourcePl != null && !translationPl.equals("")) {
            list.add(new Cuvant(apparition, TestDialogs.LANG_DE_RO, translationPl, sourcePl));
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

    public String getTranslationPl() {
        return translationPl;
    }

}
