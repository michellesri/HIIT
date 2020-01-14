package com.michelle.android.hiit;

public class roman {


    // for any character, if the character after it is smaller, then add the current character
    // if the character after it is larger, then subtract the current character
    // if iiv then add them until it gets to v which is then bigger so subtract
    // two states for the string:
    // add the character
    // are not sure if i can add the character. so then keep track of current total and then later decide
    // to either add or subtract


    public int romanToInt(String s) {
        int overAllTotal = 0;
        int currentTotal = 0;
        for (int k = 0; k < s.length(); k++) {
            int currentCharValue = convertSingleStringToSingleRomanNumber(s.charAt(k));
            currentTotal += currentCharValue;

            // if we're not at the end of the stringed number
            if (k != s.length() - 1) {
                int nextCharValue = convertSingleStringToSingleRomanNumber(s.charAt(k + 1));
                if (currentCharValue > nextCharValue) {
                    overAllTotal += currentTotal;
                    currentTotal = 0;
                } else if (currentCharValue < nextCharValue) {
                    overAllTotal -= currentTotal;
                    currentTotal = 0;
                } else {
                }
            } else {
                overAllTotal += currentTotal;
                currentTotal = 0;
            }
        }
        return overAllTotal;
    }

    private int convertSingleStringToSingleRomanNumber(char c) {
        if (c == 'I') {
            return 1;
        } else if (c == 'V') {
            return 5;
        } else if (c == 'X') {
            return 10;
        } else if (c == 'L') {
            return 50;
        } else if (c == 'C') {
            return 100;
        } else if (c == 'D') {
            return 500;
        } else if (c == 'M') {
            return 1000;
        } else {
            throw new IllegalArgumentException("not a valid char entered: " + c);
        }
    }
}
