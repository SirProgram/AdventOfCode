package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExceriseSevenB extends ExceriseBase {

    private static final Pattern STEP_PATTERN = Pattern.compile("Step (.) must be finished before step (.) can begin");

    private Map<String, Step> stepsByChar = new HashMap<>();

    private StringBuilder stepsString = new StringBuilder();

    private List<Worker> workers = new ArrayList<>();

    int timeTaken = 0;

    public void process(String path, int numWorkers, int extraTimePerStep) throws IOException {
        List<String> lines = readFile(path);

        lines.stream()
                .map(this::preProcessLine)
                .forEach(this::preProcessStep);

        for (int i = 0; i < numWorkers; i++) {
            Worker worker = new Worker(extraTimePerStep);
            workers.add(worker);
        }

        while (stepsByChar.size() > 0) {
            timeTaken++;
            for (Worker worker : workers) {
                if (worker.needsNewTask()) {
                    Optional<Step> step = calculateNextUnworkedStep(stepsByChar.values());
                    step.ifPresent(worker::assignStep);
                }
            }
            for (Worker worker : workers) {
                worker.decrementSteps();
                if (worker.hasFinishedTask()) {
                    finishStep(worker.currentStep);
                    worker.unassignStep();
                }
            }
        }
        System.out.println(stepsString);
        System.out.println(timeTaken);
    }

    private void finishStep(Step step) {
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

    private Optional<Step> calculateNextUnworkedStep(Collection<Step> steps) {
        List<Step> sortedSteps = steps.stream().sorted(this::compareSteps).collect(Collectors.toList());

        for (Step nextStep : sortedSteps) {
            if (nextStep.stepsPreceeding.size() != 0) {
                break;
            }

            if (nextStep.worker == null) {
                return Optional.of(nextStep);
            }
        }
        return Optional.empty();
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
        Worker worker;

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


    public class Worker {

        int stepsRemaining = 0;
        Step currentStep;
        private int extraTimePerStep;

        public Worker(int extraTimePerStep) {
            this.extraTimePerStep = extraTimePerStep;
        }

        public boolean needsNewTask() {
            return stepsRemaining == 0;
        }

        public boolean hasFinishedTask() {
            return currentStep != null && stepsRemaining == 0;
        }

        public void decrementSteps() {
            if (stepsRemaining > 0) {
                stepsRemaining -= 1;
            }
        }

        public void assignStep(Step step) {
            this.currentStep = step;
            step.worker = this;

            stepsRemaining = (int) step.letter.charAt(0) - "A".charAt(0) + 1 + extraTimePerStep;
        }

        public void unassignStep() {
            this.currentStep.worker = null;
            currentStep = null;
        }
    }


}
