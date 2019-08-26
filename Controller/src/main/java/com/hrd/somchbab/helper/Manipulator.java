package com.hrd.somchbab.helper;

public class Manipulator {
    public static String removeLastCharacter(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
            if (str.length() > 0 && str.charAt(str.length() - 1) == ',')
                removeLastCharacter(str);
        }
        return str;
    }
}
