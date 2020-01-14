import com.michelle.android.hiit.PresetStep;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int romanToInt(String s) {
        int i = 1;
        int v = 5;
        int x = 10;
        int l = 50;
        int c = 100;
        int d = 500;
        int m = 1000;


        HashMap<Character, Integer> mapped = getMappedCharsAndValues(s);
        char largestChar = getLargestValuedCharFromMap(mapped);

        // for any character, if the character after it is smaller, then add the current character
        // if the character after it is larger, then subtract the current character
        // if iiv then add them until it gets to v which is then bigger so subtract
        // two states for the string:
            // add the character
            // are not sure if i can add the character. so then keep track of current total and then later decide
                    // to either add or subtract

        // case for only I numbers -> total them and return
        if (largestChar == 'I') {
            return calculateSimpleTotal(s);
        } else if (largestChar== 'V') {

        }


    }

    private int calculateSimpleTotal(String s) {
        int simpleTotal = 0;
        for (int k = 0; k < s.length(); k++) {
            simpleTotal += convertSingleStringToSingleRomanNumber(s.charAt(k));
        }
        return simpleTotal;
    }

    private int calculateVTotal(String s) {

    }

    private HashMap<Character, Integer> getMappedCharsAndValues(String s) {

        HashMap<Character, Integer> map = new HashMap<>();

        // put all the characters and their values into a map
        for (int i = 0; i < s.length(); i++) {
            char c = Character.toUpperCase(s.charAt(i));
            int value = convertSingleStringToSingleRomanNumber((c));
            map.put(c, value);
        }
        return map;
    }

    private char getLargestValuedCharFromMap(HashMap<Character, Integer> map) {
        Map.Entry<Character, Integer> maxEntry = null;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        if (maxEntry != null) {
            return maxEntry.getKey();
        }
        return 0;
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
            return 10000;
        } else {
            return -1;
        }
    }
}