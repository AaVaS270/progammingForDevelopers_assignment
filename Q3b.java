package assignments;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

public class Q3b {
    public static boolean matchPattern(String str, String pattern) {
        String regex = pattern.replaceAll("@", str)
                              .replaceAll("#", ".");
        regex = Pattern.quote(regex);
        return str.matches(regex);
    }

    public static void main(String[] args) {
        String a = "ta";
        String pattern = "@";
        boolean result = matchPattern(a, pattern);
        System.out.println(result);//true
    }
}

