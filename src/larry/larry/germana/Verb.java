package larry.germana;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Verb extends Translation {
    private String romana;
    private String infinitiv;
    private String ich;
    private String do_;
    private String er;
    private String wir;
    private String ihr;
    private String sie;
    private String auxiliar;
    private String doImperativ = "";
    private String roTrecut = "";

    public String getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(String auxiliar) {
        this.auxiliar = auxiliar;
    }

    public String getPastParticiple() {
        if (pastParticiple != null && !pastParticiple.equals("")) {
            return auxiliar + " " + pastParticiple;
        } else {
            return "";
        }
    }

    public void setPastParticiple(String pastParticiple) {
        this.pastParticiple = pastParticiple;
    }

    private String pastParticiple;

    public String getSie() {
        return sie;
    }

    public String getIhr() {
        return ihr;
    }

    public String getIch() {
        return ich;
    }

    public String getDo_() {
        return do_;
    }

    public String getRomana() {
        return romana;
    }

    public String getEr() {
        return er;
    }

    public void setWir(String wir) {
        this.wir = wir;
    }

    public void setSie(String sie) {
        this.sie = sie;
    }

    public void setIhr(String ihr) {
        this.ihr = ihr;
    }

    public void setIch(String ich) {
        this.ich = ich;
    }

    public void setDo_(String do_) {
        this.do_ = do_;
    }

    public void setRomana(String romana) {
        this.romana = romana;
    }

    public void setEr(String er) {
        this.er = er;
    }

    public String getWir() {
        return wir;
    }

    // public Verb(String romana, String infinitiv, String ich, String do_, String er, String wir,
    // String ihr, String sie) {
    // this.romana = romana;
    // this.infinitiv = infinitiv;
    // this.ich = ich;
    // this.do_ = do_;
    // this.er = er;
    // this.wir = wir;
    // this.ihr = ihr;
    // this.sie = sie;
    //
    // }

    public Verb(int apparition, StringTokenizer st) {
        super(apparition);
        this.romana = st.nextToken();
        this.infinitiv = st.nextToken();
        this.ich = st.nextToken();
        this.do_ = st.nextToken();
        this.er = st.nextToken();
        this.wir = st.nextToken();
        this.ihr = st.nextToken();
        this.sie = st.nextToken();
        if (st.hasMoreTokens()) {
            this.setAuxiliar(st.nextToken());
            if (st.hasMoreTokens()) {
                this.setPastParticiple(st.nextToken());
                if (st.hasMoreTokens()) {
                    this.setRoTrecut(st.nextToken());
                    if (st.hasMoreTokens()) {
                        this.setDoImperativ(st.nextToken());
                        if (st.hasMoreTokens()) {
                            this.setDescription(st.nextToken());

                        }
                    }
                }
            }
        }

    }

    public String getInfinitiv() {
        if (infinitiv != null && !infinitiv.equals("")) {
            return auxiliar + " " + infinitiv;
        } else {
            return "";
        }
    }

    public void setInfinitiv(String infinitiv) {
        this.infinitiv = infinitiv;
    }

    @Override
    public List<Translation> getTranslations() {
        List<Translation> list = new ArrayList<Translation>();
        list.add(this);

        boolean addTu = true;
        boolean addEl = true;
        boolean addNoi = true;
        boolean addVoi = true;
        boolean addEi = true;

        String desc = "";
        if (getDescription() != null && !getDescription().isEmpty()) {
            desc = " (" + getDescription() + ")";
        }

        String rez = "";
        rez += "eu";
        String toAddGer = getIch();
        if (toAddGer.equals(getDo_())) {
            addTu = false;
            rez += " tu";
        }
        if (toAddGer.equals(getEr())) {
            addEl = false;
            rez += " el";
        }
        if (toAddGer.equals(getWir())) {
            addNoi = false;
            rez += " noi";
        }
        if (toAddGer.equals(getIhr())) {
            addVoi = false;
            rez += " voi";
        }
        if (toAddGer.equals(getSie())) {
            addEi = false;
            rez += " ei";
        }
        list.add(new Cuvant(this.getApparition(), toAddGer, rez + " " + getRomana()));
        if (addTu) {
            rez = "";
            rez += "tu";
            toAddGer = getDo_();
            if (toAddGer.equals(getEr())) {
                addEl = false;
                rez += " el";
            }
            if (toAddGer.equals(getWir())) {
                addNoi = false;
                rez += " noi";
            }
            if (toAddGer.equals(getIhr())) {
                addVoi = false;
                rez += " voi";
            }
            if (toAddGer.equals(getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(this.getApparition(), toAddGer, rez + " " + getRomana()));
        }
        if (addEl) {
            rez = "";
            rez += "el";
            toAddGer = getEr();
            if (toAddGer.equals(getWir())) {
                addNoi = false;
                rez += " noi";
            }
            if (toAddGer.equals(getIhr())) {
                addVoi = false;
                rez += " voi";
            }
            if (toAddGer.equals(getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(this.getApparition(), toAddGer, rez + " " + getRomana()));
        }
        if (addNoi) {
            rez = "";
            rez += "noi";
            toAddGer = getWir();
            if (toAddGer.equals(getIhr())) {
                addVoi = false;
                rez += " voi";
            }
            if (toAddGer.equals(getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(this.getApparition(), toAddGer, rez + " " + getRomana()));
        }
        if (addVoi) {
            rez = "";
            rez += "voi";
            toAddGer = getIhr();
            if (toAddGer.equals(getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(this.getApparition(), toAddGer, rez + " " + getRomana()));
        }
        if (addEi) {
            list.add(new Cuvant(this.getApparition(), getSie(), "ei " + getRomana()));
        }
        if (!getPastParticiple().equals("")) {
            list.add(new Cuvant(this.getApparition(), getPastParticiple(), getRoTrecut()));
            list.add(new Cuvant(this.getApparition(), getRoTrecut() + desc, getPastParticiple()));
        }
        if (!getDoImperativ().equals("")) {

            list.add(new Cuvant(this.getApparition(), getRomana() + desc + " tu Imperativ",
                    getDoImperativ()));
        }
        return list;
    }

    public String getDoImperativ() {
        return doImperativ;
    }

    public void setDoImperativ(String doImperativ) {
        this.doImperativ = doImperativ;
    }

    public String getRoTrecut() {
        return roTrecut;
    }

    public void setRoTrecut(String roTrecut) {
        this.roTrecut = roTrecut;
    }
}
