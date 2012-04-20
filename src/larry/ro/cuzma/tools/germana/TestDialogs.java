package ro.cuzma.tools.germana;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class TestDialogs {

    private final static String TYPE_CONTOR = "contor";
    private final static String TYPE_ALL_CORRECT = "all_correct";
    private final static String LANG_ALL = "all";
    public final static String LANG_RO_DE = "ro-de";
    public final static String LANG_DE_RO = "de-ro";
    private List<Translation> translations = new ArrayList<Translation>();
    Random rd = new Random();
    private String fileName = "words.csv";
    private int sleepTime = 5 * 60;
    private String separator = ";";
    private String type = TYPE_ALL_CORRECT;
    private String lang = LANG_ALL;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = (new Integer(sleepTime)).intValue();
    }

    public String getFileName() {
        return fileName;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public static void main(String[] args) {
        TestDialogs td = new TestDialogs();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-t")) {
                td.setSleepTime(args[++i]);
            }
            if (args[i].equals("-f")) {
                td.setFileName(args[++i]);
            }
            if (args[i].equals("-s")) {
                td.setSeparator(args[++i]);
            }
            if (args[i].equals("-type")) {
                i++;
                if (args[i].equals(TYPE_ALL_CORRECT) || args[i].equals(TYPE_CONTOR)) {
                    td.setType(args[i]);
                }
            }
            if (args[i].equals("-lang")) {
                td.setLang(args[++i]);
            }

        }
        /*
         * if(args.length == 2){ td.setFileName(args[0]); } else if(args.length == 0){
         * td.setFileName("words.csv"); } else{
         * System.out.println("java larry.germana.TestDialogs file "); System.exit(1); }
         */
        td.init();
        td.runTest();
    }

    private void init() {
        try {
            RandomAccessFile cuvinte = new RandomAccessFile(fileName, "rw");

            UTFRandomFileLineReader reader = new UTFRandomFileLineReader(cuvinte);
            try {
                String line = reader.readLine();
                String tip = "";
                while (line != null) {
                    StringTokenizer st = new StringTokenizer(line, getSeparator());
                    try {
                        // int cnt = (new Integer(st.nextToken())).intValue();
                        // if (cnt > 0) {
                        List<Translation> tran = null;
                        tip = st.nextToken();
                        if (tip.equals("subst")) {
                            tran = Substantiv.build(1, st);
                        }
                        if (tip.equals("cuv")) {
                            tran = Cuvant.build(1, st);
                        }
                        if (tip.equals("verb")) {
                            tran = Verb.build(1, st);

                        }
                        if (tran != null) {
                            for (Translation translation : tran) {
                                if (getLang().equals(LANG_ALL)
                                        || getLang().equals(translation.getLanguage()))
                                    translations.add(translation);
                            }
                        }
                        // }
                    } catch (Exception ex2) {
                        System.out.println("=============" + line + "-" + ex2.getMessage());
                    }
                    line = reader.readLine();
                }
                cuvinte.close();
            } catch (IOException ex1) {
            }
        } catch (FileNotFoundException ex) {
            /*
             * File f = new File(fileName); try { f.createNewFile(); } catch (IOException e) { //
             * TODO Auto-generated catch block e.printStackTrace(); }
             */
            System.out.println(fileName + " not found!!!");
            System.exit(1);
        }
    }

    public void runTest() {
        if (getType().equals(TYPE_CONTOR)) {
            runContor();
        } else if (getType().equals(TYPE_ALL_CORRECT)) {
            runAllCorrect();
        }
    }

    private void runAllCorrect() {
        List<Translation> trans = translations;

        boolean runThis = true;
        while (runThis) {
            List<Translation> tmpTrans = new ArrayList<Translation>();
            if (trans.size() > 0) {
                Collections.shuffle(trans);
                int total = trans.size();
                int all = trans.size();
                int wrong = 0;
                int answers = 0;
                for (Translation tr : trans) {
                    TranslationDialog sbD = null;
                    if (tr instanceof Substantiv) {
                        sbD = new SubstantivDialog(tr);
                    } else if (tr instanceof Cuvant) {
                        sbD = new CuvantDialog(tr);
                    } else if (tr instanceof Verb) {
                        sbD = new VerbDialogNew(tr);
                    }

                    if (sbD != null) {
                        // sbD.setFocusTraversalPolicy();
                        int good = all - total - wrong;

                        sbD.setTitle("All correct " + answers + "/" + total + "/" + all + " good: "
                                + good + " wrong: " + wrong);
                        total--;
                        sbD.setVisible(true);
                        answers++;
                        if (!sbD.isFirst()) {
                            wrong++;
                            tmpTrans.add(tr);
                        }
                    }
                    try {
                        Thread.sleep(this.getSleepTime() * 1000);
                    } catch (InterruptedException ex) {
                    }

                }
                if (tmpTrans.size() > 0) {
                    trans = tmpTrans;
                    tmpTrans = new ArrayList<Translation>();
                } else {
                    if (JOptionPane.showConfirmDialog(null, "Would you like to restart?",
                            "An Inane Question", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                        runThis = false;
                    } else {
                        trans = translations;
                    }
                }

            }
        }

    }

    private void runContor() {
        boolean runThis = true;
        List<Translation> translationContor = new ArrayList<Translation>();
        if (translations.size() > 0) {
            for (Translation tr : translations) {
                for (int i = 0; i < tr.getApparition(); i++) {
                    translationContor.add(tr);
                }
            }
        }
        while (runThis) {
            runThis = false;

            if (translationContor.size() > 0) {
                Collections.shuffle(translationContor);
                int total = translationContor.size();
                int all = translationContor.size();
                for (Translation tr : translationContor) {
                    TranslationDialog sbD = null;
                    if (tr instanceof Substantiv) {
                        sbD = new SubstantivDialog(tr);
                    } else if (tr instanceof Cuvant) {
                        sbD = new CuvantDialog(tr);
                    } else if (tr instanceof Verb) {
                        sbD = new VerbDialogNew(tr);
                    }

                    if (sbD != null) {
                        // sbD.setFocusTraversalPolicy();
                        sbD.setTitle("Contor " + total + "/" + all);
                        total--;
                        sbD.setVisible(true);
                    }
                    try {
                        Thread.sleep(this.getSleepTime() * 1000);
                    } catch (InterruptedException ex) {
                    }

                }
            }

        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
