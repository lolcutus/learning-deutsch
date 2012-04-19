package larry.germana;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
    private String romanaSingular;
    private String romanaPlural;

    private String germanaSingular;
    private String germanaPlural;

    public String getGermanaPlural() {
        return germanaPlural;
    }

    public String getGermanaSingular() {
        return germanaSingular;
    }

    public void setRomanaSingular(String romana) {
        this.romanaSingular = romana;
    }

    public void setGermanaPlural(String plural) {
        this.germanaPlural = plural;
    }

    public void setGermanaSingular(String singular) {
        this.germanaSingular = singular;
    }

    public String getRomanaSingular() {
        return romanaSingular;
    }

    /**
     * Substantiv
     * 
     * @param romana
     *            String
     * @param singular
     *            String
     * @param plural
     *            String
     */
    public Substantiv(int apparition, StringTokenizer st) {
        super(apparition);
        this.romanaSingular = st.nextToken();
        String nextT = st.nextToken();
        if (!nextT.equals("#")) {
            this.germanaSingular = nextT + " " + st.nextToken();
        } else {
            st.nextToken();

        }
        this.germanaPlural = "";
        if (st.hasMoreTokens()) {
            this.germanaPlural = st.nextToken();
            if (this.germanaPlural != null && !this.germanaPlural.equals("")) {
                this.germanaPlural = "die " + this.germanaPlural;
            }
            if (st.hasMoreTokens()) {
                this.romanaPlural = st.nextToken();
                if (st.hasMoreTokens()) {
                    this.setDescription(st.nextToken());
                }
            }
        }

    }

    public String getRomanaPlural() {
        return romanaPlural;
    }

    public void setRomanaPlural(String romanaPlural) {
        this.romanaPlural = romanaPlural;
    }

    @Override
    public List<Translation> getTranslations() {
        List<Translation> list = new ArrayList<Translation>();
        list.add(this);
        if (getGermanaSingular() != null && !getGermanaSingular().equals("")) {
            list.add(new Cuvant(this.getApparition(), getGermanaSingular(), getRomanaSingular()));
        }
        if (getRomanaPlural() != null && !getRomanaPlural().equals("")) {
            list.add(new Cuvant(this.getApparition(), getGermanaPlural(), getRomanaPlural()));
        }
        return list;
    }

}
