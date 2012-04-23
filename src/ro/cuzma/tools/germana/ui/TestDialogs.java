package ro.cuzma.tools.germana.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import ro.cuzma.tools.germana.translation.Cuvant;
import ro.cuzma.tools.germana.translation.LearningList;
import ro.cuzma.tools.germana.translation.Substantiv;
import ro.cuzma.tools.germana.translation.Translation;
import ro.cuzma.tools.germana.translation.Translation.Language;
import ro.cuzma.tools.germana.translation.Verb;
import ro.cuzma.tools.germana.ui.TranslationDialog.Exit;

public class TestDialogs {

    private final static String TYPE_CONTOR = "contor";
    private final static String TYPE_ALL_CORRECT = "all_correct";

    private String fileName = "words.csv";
    private int sleepTime = 5 * 60;
    private String separator = ";";
    private String type = TYPE_ALL_CORRECT;
    private Language lang = Language.LANG_ALL;

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
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
                td.setLang(Translation.gelLanguagefromString(args[++i]));
            }

        }
        LearningList ls = new LearningList(td.getFileName(), td.getLang(), td.getSeparator());
        // td.init();
        td.runTest(ls);
    }

    public void runTest(LearningList ls) {
        if (getType().equals(TYPE_CONTOR)) {
            runContor(ls);
        } else if (getType().equals(TYPE_ALL_CORRECT)) {
            runAllCorrect(ls);
        }
    }

    private void runAllCorrect(LearningList ls) {
        boolean runThis = true;
        while (runThis) {
            Translation translation = ls.getNext();
            while (translation != null) {
                TranslationDialog sbD = null;
                if (translation instanceof Substantiv) {
                    sbD = new SubstantivDialog(translation);
                } else if (translation instanceof Cuvant) {
                    sbD = new CuvantDialog(translation);
                } else if (translation instanceof Verb) {
                    sbD = new VerbDialogNew(translation);
                }
                if (sbD != null) {
                    // sbD.setFocusTraversalPolicy();
                    int remaining = ls.getCurrentList().length - ls.getCurrentPosition() + 1;
                    int all = ls.getCurrentList().length;
                    int current = ls.getCurrentPosition() - 1;

                    int percent = 0;
                    if (ls.getGoodAnswers() + ls.getBadAnswers() != 0) {
                        percent = 100 * ls.getGoodAnswers()
                                / (ls.getGoodAnswers() + ls.getBadAnswers());
                    }
                    sbD.setTitle("All correct " + current + "/" + remaining + "/" + all + " good: "
                            + ls.getGoodAnswers() + "(" + percent + "%) wrong: "
                            + ls.getBadAnswers());
                    sbD.setVisible(true);
                    if (sbD.exit() == Exit.EXIT) {
                        ls.save();
                        runThis = false;
                        break;
                    } else if (sbD.exit() == Exit.RESET) {
                        ls.reset();
                        translation = ls.getNext();
                    } else if (sbD.exit() == Exit.SAVE) {
                        ls.save();
                    } else {
                        ls.processresponse(sbD.isFirstCorrectAnswer());
                        translation = ls.getNext();
                    }
                }
                try {
                    Thread.sleep(this.getSleepTime() * 1000);
                } catch (InterruptedException ex) {
                }

            }

            if (runThis) {
                if (JOptionPane.showConfirmDialog(null, "Would you like to restart?",
                        "An Inane Question", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    runThis = false;
                } else {
                    ls.reset();
                }
            }
        }

    }

    // private void runAllCorrect(LearningList ls) {
    // List<Translation> trans = translations;
    //
    // boolean runThis = true;
    // while (runThis) {
    // List<Translation> tmpTrans = new ArrayList<Translation>();
    // if (trans.size() > 0) {
    // Collections.shuffle(trans);
    // int total = trans.size();
    // int all = trans.size();
    // int wrong = 0;
    // int answers = 0;
    // for (Translation tr : trans) {
    // TranslationDialog sbD = null;
    // if (tr instanceof Substantiv) {
    // sbD = new SubstantivDialog(tr);
    // } else if (tr instanceof Cuvant) {
    // sbD = new CuvantDialog(tr);
    // } else if (tr instanceof Verb) {
    // sbD = new VerbDialogNew(tr);
    // }
    //
    // if (sbD != null) {
    // // sbD.setFocusTraversalPolicy();
    // int good = all - total - wrong;
    //
    // sbD.setTitle("All correct " + answers + "/" + total + "/" + all + " good: "
    // + good + " wrong: " + wrong);
    // total--;
    // sbD.setVisible(true);
    // answers++;
    // if (!sbD.isFirstCorrectAnswer()) {
    // wrong++;
    // tmpTrans.add(tr);
    // }
    // }
    // try {
    // Thread.sleep(this.getSleepTime() * 1000);
    // } catch (InterruptedException ex) {
    // }
    //
    // }
    // if (tmpTrans.size() > 0) {
    // trans = tmpTrans;
    // tmpTrans = new ArrayList<Translation>();
    // } else {
    // if (JOptionPane.showConfirmDialog(null, "Would you like to restart?",
    // "An Inane Question", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
    // runThis = false;
    // } else {
    // trans = translations;
    // }
    // }
    //
    // }
    // }
    //
    // }

    private void runContor(LearningList ls) {
        boolean runThis = true;
        List<Translation> translationContor = new ArrayList<Translation>();
        Translation tran = null;
        for (int j = 0; j < ls.getCurrentList().length; j++) {
            tran = ls.getCurrentList()[j];
            for (int i = 0; i < tran.getApparition(); i++) {
                translationContor.add(tran);
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
