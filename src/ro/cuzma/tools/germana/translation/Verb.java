package ro.cuzma.tools.germana.translation;

import java.util.ArrayList;
import java.util.List;

import ro.cuzma.tools.germana.tools.MyStringTokenizer;

public class Verb extends Translation {
    public static String ID = "verb";

    public Verb(int apparition, Language language, Tested tested) {
        super(apparition, language, tested);
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
            if (auxiliar != null && !auxiliar.equals("")) {
                return auxiliar + " " + pastParticiple;
            } else {
                return pastParticiple;
            }
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

    public static List<Translation> build(int apparition, MyStringTokenizer st) {
        Verb verb = new Verb(apparition, Language.LANG_RO_DE, Tested.NOTTESTED);
        verb.source = st.nextToken();
        verb.infinitiv = st.nextToken();
        verb.ich = st.nextToken();
        verb.do_ = st.nextToken();
        verb.er = st.nextToken();
        verb.wir = st.nextToken();
        verb.ihr = st.nextToken();
        verb.sie = st.nextToken();
        Language language = Language.LANG_ALL;
        Tested tested = Tested.NOTTESTED;
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
                            if (st.hasMoreTokens()) {
                                language = Translation.gelLanguagefromString(st.nextToken());
                                if (st.hasMoreTokens()) {
                                    tested = gelTestedfromString(st.nextToken());
                                }
                            }
                        }
                    }
                }
            }
        }
        verb.setLanguage(language);
        verb.setTested(tested);
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
        if (verb.getLanguage() == Language.LANG_ALL) {
            verb.setLanguage(Language.LANG_RO_DE);
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
            list.add(new Cuvant(verb.getApparition(), Language.LANG_DE_RO, toAddGer, rez + " "
                    + verb.getSource(), Tested.NOTTESTED));
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
                list.add(new Cuvant(verb.getApparition(), Language.LANG_DE_RO, toAddGer, rez + " "
                        + verb.getSource(), Tested.NOTTESTED));
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
                list.add(new Cuvant(verb.getApparition(), Language.LANG_DE_RO, toAddGer, rez + " "
                        + verb.getSource(), Tested.NOTTESTED));
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
                list.add(new Cuvant(verb.getApparition(), Language.LANG_DE_RO, toAddGer, rez + " "
                        + verb.getSource(), Tested.NOTTESTED));
            }
            if (addVoi) {
                rez = "";
                rez += "voi";
                toAddGer = verb.getIhr();
                if (toAddGer.equals(verb.getSie())) {
                    addEi = false;
                    rez += " ei";
                }
                list.add(new Cuvant(verb.getApparition(), Language.LANG_DE_RO, toAddGer, rez + " "
                        + verb.getSource(), Tested.NOTTESTED));
            }
            if (addEi) {
                list.add(new Cuvant(verb.getApparition(), Language.LANG_DE_RO, verb.getSie(), "ei "
                        + verb.getSource(), Tested.NOTTESTED));
            }
            if (!verb.getPastParticiple().equals("")) {
                list.add(new Cuvant(verb.getApparition(), Language.LANG_DE_RO, verb
                        .getPastParticiple(), verb.getRoTrecut(), Tested.NOTTESTED));
                list.add(new Cuvant(verb.getApparition(), Language.LANG_RO_DE, verb.getRoTrecut()
                        + desc, verb.getPastParticiple(), Tested.NOTTESTED));
            }
            if (!verb.getDoImperativ().equals("")) {

                list.add(new Cuvant(verb.getApparition(), Language.LANG_RO_DE, verb.getSource()
                        + desc + " tu Imperativ", verb.getDoImperativ(), Tested.NOTTESTED));
            }
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

    @Override
    public StringBuilder internal_getCsvString(StringBuilder sb, String separator) {
        sb.append(ID);
        sb.append(separator);
        sb.append(source);
        sb.append(separator);
        sb.append(infinitiv);
        sb.append(separator);
        sb.append(ich);
        sb.append(separator);
        sb.append(do_);
        sb.append(separator);
        sb.append(er);
        sb.append(separator);
        sb.append(wir);
        sb.append(separator);
        sb.append(ihr);
        sb.append(separator);
        sb.append(sie);
        sb.append(separator);
        sb.append(auxiliar);
        sb.append(separator);
        sb.append(pastParticiple);
        sb.append(separator);
        sb.append(roTrecut);
        sb.append(separator);
        sb.append(doImperativ);
        return sb;
    }
}
