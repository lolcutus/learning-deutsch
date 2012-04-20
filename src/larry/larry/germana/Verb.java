package larry.germana;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Verb extends Translation {
    public Verb(int apparition, String language) {
		super(apparition, language);
	}

	private String source;
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

    public String getSource() {
        return source;
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
        this.source = romana;
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

    public static List<Translation> build(int apparition, StringTokenizer st){
    	Verb verb = new Verb(apparition,TestDialogs.LANG_RO_DE);
    	verb.source = st.nextToken();
    	verb.infinitiv = st.nextToken();
    	verb.ich = st.nextToken();
    	verb.do_ = st.nextToken();
    	verb.er = st.nextToken();
    	verb.wir = st.nextToken();
    	verb.ihr = st.nextToken();
    	verb.sie = st.nextToken();
        if (st.hasMoreTokens()) {
        	verb.setAuxiliar(st.nextToken());
            if (st.hasMoreTokens()) {
            	verb.setPastParticiple(st.nextToken());
                if (st.hasMoreTokens()) {
                	verb.setRoTrecut(st.nextToken());
                    if (st.hasMoreTokens()) {
                    	verb.setDoImperativ(st.nextToken());
                        if (st.hasMoreTokens()) {
                        	verb.setDescription(st.nextToken());

                        }
                    }
                }
            }
        }
        return getCuvinte(verb);

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

    private static List<Translation> getCuvinte(Verb verb) {
        List<Translation> list = new ArrayList<Translation>();
        list.add(verb);

        boolean addTu = true;
        boolean addEl = true;
        boolean addNoi = true;
        boolean addVoi = true;
        boolean addEi = true;

        String desc = "";
        if (verb.getDescription() != null && !verb.getDescription().isEmpty()) {
            desc = " (" + verb.getDescription() + ")";
        }

        String rez = "";
        rez += "eu";
        String toAddGer = verb.getIch();
        if (toAddGer.equals(verb.getDo_())) {
            addTu = false;
            rez += " tu";
        }
        if (toAddGer.equals(verb.getEr())) {
            addEl = false;
            rez += " el";
        }
        if (toAddGer.equals(verb.getWir())) {
            addNoi = false;
            rez += " noi";
        }
        if (toAddGer.equals(verb.getIhr())) {
            addVoi = false;
            rez += " voi";
        }
        if (toAddGer.equals(verb.getSie())) {
            addEi = false;
            rez += " ei";
        }
        list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, toAddGer, rez + " " + verb.getSource()));
        if (addTu) {
            rez = "";
            rez += "tu";
            toAddGer = verb.getDo_();
            if (toAddGer.equals(verb.getEr())) {
                addEl = false;
                rez += " el";
            }
            if (toAddGer.equals(verb.getWir())) {
                addNoi = false;
                rez += " noi";
            }
            if (toAddGer.equals(verb.getIhr())) {
                addVoi = false;
                rez += " voi";
            }
            if (toAddGer.equals(verb.getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, toAddGer, rez + " " + verb.getSource()));
        }
        if (addEl) {
            rez = "";
            rez += "el";
            toAddGer = verb.getEr();
            if (toAddGer.equals(verb.getWir())) {
                addNoi = false;
                rez += " noi";
            }
            if (toAddGer.equals(verb.getIhr())) {
                addVoi = false;
                rez += " voi";
            }
            if (toAddGer.equals(verb.getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, toAddGer, rez + " " + verb.getSource()));
        }
        if (addNoi) {
            rez = "";
            rez += "noi";
            toAddGer = verb.getWir();
            if (toAddGer.equals(verb.getIhr())) {
                addVoi = false;
                rez += " voi";
            }
            if (toAddGer.equals(verb.getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, toAddGer, rez + " " + verb.getSource()));
        }
        if (addVoi) {
            rez = "";
            rez += "voi";
            toAddGer = verb.getIhr();
            if (toAddGer.equals(verb.getSie())) {
                addEi = false;
                rez += " ei";
            }
            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, toAddGer, rez + " " + verb.getSource()));
        }
        if (addEi) {
            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, verb.getSie(), "ei " + verb.getSource()));
        }
        if (!verb.getPastParticiple().equals("")) {
            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, verb.getPastParticiple(), verb.getRoTrecut()));
            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, verb.getRoTrecut() + desc, verb.getPastParticiple()));
        }
        if (!verb.getDoImperativ().equals("")) {

            list.add(new Cuvant(verb.getApparition(),TestDialogs.LANG_DE_RO, verb.getSource() + desc + " tu Imperativ",
            		verb.getDoImperativ()));
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
