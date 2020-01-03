package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExceriseSeven extends ExceriseBase {

    private static final Pattern STEP_PATTERN = Pattern.compile("Step (.) must be finished before step (.) can begin");

    private Map<String, Step> stepsByChar = new HashMap<>();

    private StringBuilder stepsString = new StringBuilder();

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        lines.stream()
                .map(this::preProcessLine)
                .forEach(this::preProcessStep);

        while (stepsByChar.size() > 0) {
            Step step = calculateNextStep(stepsByChar.values());
            executeStep(step);
        }
        System.out.println(stepsString);
    }

    private void executeStep(Step step) {
        stepsString.append(step.letter);
        stepsByChar.values().forEach(s -> s.removeStep(step.letter));
        stepsByChar.remove(step.letter);
    }

    private void preProcessStep(MatchResult matchResult) {
        String stepBeforeLetter = matchResult.group(1);
        String stepAfterLetter = matchResult.group(2);
        Step stepBefore = getOrCreateStep(stepBeforeLetter);
        Step stepAfter = getOrCreateStep(stepAfterLetter);

        stepBefore.addProceedingStep(stepAfter);
        stepAfter.addPreceedingStep(stepBefore);
    }

    private Step calculateNextStep(Collection<Step> steps) {
        List<Step> sortedSteps = steps.stream().sorted(this::compareSteps).collect(Collectors.toList());
        return sortedSteps.get(0);
    }

    private int compareSteps(Step a, Step b) {
        if (a.stepsPreceeding.size() < b.stepsPreceeding.size()) {
            return -1;
        } else if (a.stepsPreceeding.size() > b.stepsPreceeding.size()) {
            return 1;
        } else return a.letter.compareTo(b.letter);
    }

    private Step getOrCreateStep(String stepLetter) {
        Step step;
        if (stepsByChar.containsKey(stepLetter)) {
            step = stepsByChar.get(stepLetter);
        } else {
            step = new Step(stepLetter);
            stepsByChar.put(stepLetter, step);
        }
        return step;
    }

    private MatchResult preProcessLine(String line) {
        Matcher matcher = STEP_PATTERN.matcher(line);
        matcher.find();
        return matcher.toMatchResult();
    }

    public class Step {

        String letter;
        List<Step> stepsProceeding;
        List<Step> stepsPreceeding;

        public Step(String stepLetter) {
            this.letter = stepLetter;
            this.stepsProceeding = new ArrayList<>();
            this.stepsPreceeding = new ArrayList<>();
        }

        public void addProceedingStep(Step step) {
            stepsProceeding.add(step);
        }

        public void addPreceedingStep(Step step) {
            stepsPreceeding.add(step);
        }

        public void removeStep(String letter) {
            stepsProceeding.remove(stepsByChar.get(letter));
            stepsPreceeding.remove(stepsByChar.get(letter));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Step step1 = (Step) o;
            return Objects.equals(letter, step1.letter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(letter);
        }
    }



}
