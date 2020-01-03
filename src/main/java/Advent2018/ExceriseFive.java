package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceriseFive extends ExceriseBase {

    Pattern smallLarge = Pattern.compile("aA|bB|cC|dD|eE|fF|gG|hH|iI|jJ|kK|lL|mM|nN|oO|pP|qQ|rR|sS|tT|uU|vV|wW|xX|yY|zZ");
    Pattern largeSmall = Pattern.compile("Aa|Bb|Cc|Dd|Ee|Ff|Gg|Hh|Ii|Jj|Kk|Ll|Mm|Nn|Oo|Pp|Qq|Rr|Ss|Tt|Uu|Vv|Ww|Xx|Yy|Zz");

    public void process() throws IOException {
        List<String> lines = readFile("/exceriseFive.txt");
        String workingString = lines.get(0);

        workingString = processString(workingString);
        System.out.println(workingString);
        System.out.println(workingString.length());
    }

    public void processTwo() throws IOException {
        List<String> lines = readFile("/exceriseFive.txt");
        String startingString = lines.get(0);

        Map<String, String> withoutLetter = new HashMap<>();

        for (char letter : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            String workingString = new String(startingString);
            String l = String.valueOf(letter);
            System.out.println("Processing " + l);
            workingString = workingString.replaceAll(l, "");
            workingString = workingString.replaceAll(l.toUpperCase(), "");
            workingString = processString(workingString);
            withoutLetter.put(l, workingString);
        }

        String shortestLength = withoutLetter.values().stream()
                .sorted(Comparator.comparingInt(String::length))
                .findFirst()
                .get();

        System.out.println(shortestLength);
        System.out.println(shortestLength.length());
    }

    private String processString(String workingString) {
        String previousString = "";
        while (!previousString.equals(workingString)) {
            previousString = workingString;
            workingString = replacePatterns(workingString);
        }
        return workingString;
    }

    private String replacePatterns(String workingString) {
        Matcher matcher = smallLarge.matcher(workingString);
        matcher.find();
        String resultingString = matcher.replaceAll("");

        Matcher matcherTwo = largeSmall.matcher(resultingString);
        matcherTwo.find();
        return matcherTwo.replaceAll("");
    }

}
