/*
 * Copyright (c) Salomon Automation GmbH
 */
package ro.cuzma.tools.germana.tools;

import java.util.NoSuchElementException;

public class MyStringTokenizer {
    private final String[] result;
    private int position = 0;

    public MyStringTokenizer(String textToSplit, String delim) {
        result = textToSplit.split(delim);
    }

    public boolean hasMoreTokens() {
        if (position < result.length)
            return true;
        return false;
    }

    public String nextToken() {
        if (position >= result.length)
            throw new NoSuchElementException();
        return result[position++];
    }

}
