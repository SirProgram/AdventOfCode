package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceriseEight extends ExceriseBase {

    Pattern headerRegex = Pattern.compile("(\\d+) (\\d+)");
    Pattern metaDataRegex = Pattern.compile("(\\d+)");
    private String remainingLine;

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        remainingLine = lines.get(0);
        Header root = new Header(1, 0);

        processNextHeader(root);

        System.out.println(root.sumMetaData());
        System.out.println(root.children.get(0).getValue());
    }

    private void processNextHeader(Header parent) {
        Header header = toHeader(getNextHeader(remainingLine));
        parent.children.add(header);

        for (int child = 0; child < header.numChildren; child++) {
            processNextHeader(header);
        }

        for (int metaData = 0; metaData < header.numMetaData; metaData++) {
            processNextMetaData(header);
        }
    }

    private void processNextMetaData(Header owner) {
        int metaData = Integer.parseInt(getNextMetaData(remainingLine).group(1));
        owner.metaData.add(metaData);
    }

    public MatchResult getNextHeader(String line) {
        Matcher matcher = headerRegex.matcher(line);
        matcher.find();
        MatchResult matchResult = matcher.toMatchResult();
        remainingLine = line.substring(matchResult.end(2));
        return matchResult;
    }

    private Header toHeader(MatchResult result) {
        int numChildren = Integer.parseInt(result.group(1));
        int numMetaData = Integer.parseInt(result.group(2));

        return new Header(numChildren, numMetaData);
    }

    public MatchResult getNextMetaData(String line) {
        Matcher matcher = metaDataRegex.matcher(line);
        matcher.find();
        MatchResult matchResult = matcher.toMatchResult();
        remainingLine = line.substring(matchResult.end(1));
        return matchResult;
    }

    public class Header {

        int numChildren;
        int numMetaData;
        List<Header> children = new ArrayList<>();
        List<Integer> metaData = new ArrayList<>();

        public Header(int numChildren, int numMetaData) {
            this.numChildren = numChildren;
            this.numMetaData = numMetaData;
        }

        public int sumMetaData() {
            int sum = 0;
            for (int i : metaData) {
                sum += i;
            }

            for (Header child : children) {
                sum += child.sumMetaData();
            }

            return sum;
        }

        public int getValue() {
            int value = 0;
            if (numChildren == 0) {
                value = sumMetaData();
            } else {
                for (int meta : metaData) {
                    if (meta <= numChildren) {
                        value += children.get(meta - 1).getValue();
                    }
                }
            }

            return value;
        }

    }

}
