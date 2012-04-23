/*
 * Copyright (c) Salomon Automation GmbH
 */
package ro.cuzma.tools.germana.translation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ro.cuzma.tools.germana.tools.MyStringTokenizer;
import ro.cuzma.tools.germana.tools.UTFRandomFileLineReader;
import ro.cuzma.tools.germana.translation.Translation.Language;
import ro.cuzma.tools.germana.translation.Translation.Tested;

public class LearningList {
    private int currentPosition = 0;

    public int getCurrentPosition() {
        return currentPosition;
    }

    private int goodAnswers = 0;
    private int badAnswers = 0;
    private Translation[] initialList;
    private Translation[] currentList;

    public Translation[] getCurrentList() {
        return currentList;
    }

    private Translation currentTranslation;
    private final Language language;
    private final String separator;
    private final String fileName;

    public LearningList(String fileName, Language language, String separator) {
        this.separator = separator;
        this.fileName = fileName;
        this.initialList = load(fileName);
        this.language = language;
        setCurrentList();

    }

    private void setCurrentList() {
        Collection<Translation> tmpList = new ArrayList<Translation>();
        for (Translation trans : initialList) {
            if (language == Language.LANG_ALL || trans.getLanguage() == language) {
                if (trans.getTested() != Tested.OK)
                    tmpList.add(trans);
            }
        }

        currentList = tmpList.toArray(new Translation[tmpList.size()]);
        currentPosition = 0;
        goodAnswers = 0;
        badAnswers = 0;
    }

    public Translation getNext() {
        if (currentPosition >= currentList.length) {
            setCurrentList();
            if (this.currentList.length == 0)
                return null;
        }
        currentTranslation = currentList[currentPosition++];
        return currentTranslation;
    }

    public void processresponse(boolean answer) {
        if (answer) {
            goodAnswers++;
            currentTranslation.setTested(Tested.OK);
        } else {
            badAnswers++;
            currentTranslation.setTested(Tested.NOTOK);
        }
    }

    public void saveAs(String fileName) {
        File tmp = new File(fileName + ".old");
        tmp.delete();
        File curentFile = new File(fileName);
        curentFile.renameTo(tmp);
        try {
            curentFile.createNewFile();
            RandomAccessFile ra = new RandomAccessFile(curentFile, "rw");
            StringBuilder s = new StringBuilder();
            for (Translation trans : initialList) {
                s = trans.getCsvString(s, separator);
            }
            ra.write(s.toString().getBytes(Charset.forName("utf-8")));
            ra.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void save() {
        saveAs(fileName);

    }

    private Translation[] load(String fileName) {
        List<Translation> translations = new ArrayList<Translation>();
        try {
            RandomAccessFile cuvinte = new RandomAccessFile(fileName, "rw");

            UTFRandomFileLineReader reader = new UTFRandomFileLineReader(cuvinte);
            try {
                String line = reader.readLine();
                String tip = "";
                while (line != null) {
                    MyStringTokenizer mySt = new MyStringTokenizer(line, separator);
                    try {
                        // int cnt = (new Integer(st.nextToken())).intValue();
                        // if (cnt > 0) {
                        List<Translation> tran = null;
                        tip = mySt.nextToken();
                        if (tip.equals("subst")) {
                            tran = Substantiv.build(1, mySt);
                        }
                        if (tip.equals("cuv")) {
                            tran = Cuvant.build(1, mySt);
                        }
                        if (tip.equals("verb")) {
                            tran = Verb.build(1, mySt);

                        }
                        if (tran != null) {
                            for (Translation translation : tran) {
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
            System.out.println(fileName + " not found!!!");
        }
        Collections.shuffle(translations);
        return translations.toArray(new Translation[translations.size()]);
    }

    public int getGoodAnswers() {
        return goodAnswers;
    }

    public int getBadAnswers() {
        return badAnswers;
    }

    public void reset() {
        for (Translation trans : initialList) {
            trans.setTested(Tested.NOTTESTED);
        }
        setCurrentList();

    }

    public boolean isEmpty() {
        if (initialList.length > 0)
            return false;
        return true;
    }

}
