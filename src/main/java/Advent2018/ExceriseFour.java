package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExceriseFour extends ExceriseBase {

    Map<Integer, Guard> guardsById = new HashMap<>();
    private static final Pattern LINE_PATTERN = Pattern.compile("(\\d{4}-\\d\\d-\\d\\d \\d{2}:\\d{2})] (.+)");
    private static final Pattern GUARD_ID_PATTERN = Pattern.compile("Guard #(\\d+)");

    private Guard currentGuardOnShift = null;
    private LocalDateTime sleepTime = null;

    public void process() throws IOException {
        List<String> lines = readFile("/exceriseFour.txt");
        List<Command> commands = lines.stream()
                .map(this::processLine)
                .map(this::toCommand)
                .sorted()
                .map(Command::withGuardOnShift)
                .map(this::processCommand)
                .collect(Collectors.toList());

        Guard longestSleepingGuard = guardsById.values().stream()
                .max(Comparator.comparingLong(Guard::sumSleepingMinutes))
                .get();

        System.out.println(longestSleepingGuard.mostCommonMinute());
        System.out.println(longestSleepingGuard.id);
        System.out.println(longestSleepingGuard.id * longestSleepingGuard.mostCommonMinute());

        System.out.println("---");

        List<Guard> guardsByLongestMinute = guardsById.values().stream()
                .sorted(Comparator.comparingInt(Guard::mostCommonMinuteOccurance))
                .collect(Collectors.toList());

        Guard guardWithMostOccurance = guardsByLongestMinute.get(guardsByLongestMinute.size() - 1);
        System.out.println(guardWithMostOccurance.mostCommonMinute());
        System.out.println(guardWithMostOccurance.id);
        System.out.println(guardWithMostOccurance.id * guardWithMostOccurance.mostCommonMinute());
    }

    private Guard getOrCreateGuard(int id) {
        return guardsById.computeIfAbsent(id, Guard::new);
    }

    private Command processCommand(Command command) {
        if (command.commandType == CommandType.SLEEPING) {
            sleepTime = command.dateTime;
        } else if (command.commandType == CommandType.WAKING) {
            SleepTime sleepTimeObject = new SleepTime(this.sleepTime, Duration.between(sleepTime, command.dateTime));
            command.guard.sleepingTimes.add(sleepTimeObject);
            this.sleepTime = null;
        }
        return command;
    }

    private class SleepTime {

        LocalDateTime startSleepTime;
        Duration duration;

        public SleepTime(LocalDateTime startSleepTime, Duration duration) {
            this.startSleepTime = startSleepTime;
            this.duration = duration;
        }

        public LocalDateTime getStartSleepTime() {
            return startSleepTime;
        }

        public Duration getDuration() {
            return duration;
        }

        public List<Integer> asMinutes() {
            return IntStream.range(startSleepTime.getMinute(), startSleepTime.plus(duration).getMinute())
                    .boxed()
                    .collect(Collectors.toList());
        }
    }

    private class Command implements Comparable<Command> {

        LocalDateTime dateTime;
        String commandString;
        CommandType commandType;
        Guard guard;

        public Command(LocalDateTime dateTime, String command) {
            this.dateTime = dateTime;
            this.commandString = command;
            this.commandType = parseCommandType(command);
        }

        public Command withGuardOnShift() {
            if (commandType == CommandType.NEW_SHIFT) {
                this.guard = parseNewGuard(commandString);
                currentGuardOnShift = this.guard;
            } else {
                this.guard = currentGuardOnShift;
            }
            return this;
        }

        private CommandType parseCommandType(String fullCommand) {
            if (fullCommand.contains("wakes up")) {
                return CommandType.WAKING;
            } else if (fullCommand.contains("falls asleep")) {
                return CommandType.SLEEPING;
            } else return CommandType.NEW_SHIFT;
        }

        private Guard parseNewGuard(String fullCommand) {
            Matcher matcher = GUARD_ID_PATTERN.matcher(fullCommand);
            matcher.find();
            return getOrCreateGuard(Integer.parseInt(matcher.group(1)));
        }


        @Override
        public int compareTo(Command o) {
            if (o == null) {
                return 1;
            }
            return dateTime.compareTo(o.dateTime);
        }
    }

    private enum CommandType {
        WAKING,
        SLEEPING,
        NEW_SHIFT
    }

    private Command toCommand(MatchResult result) {
        LocalDateTime date = LocalDateTime.parse(result.group(1).replace(" ", "T"));
        String command = result.group(2);

        return new Command(date, command);
    }

    private MatchResult processLine(String line) {
        Matcher matcher = LINE_PATTERN.matcher(line);
        matcher.find();
        return matcher.toMatchResult();
    }

    private class Guard {

        int id;
        List<SleepTime> sleepingTimes = new ArrayList<>();

        public Guard(int id) {
            this.id = id;
        }

        public long sumSleepingMinutes() {
            return sleepingTimes.stream().map(SleepTime::getDuration).mapToLong(Duration::getSeconds).sum();
        }

        public Map<Integer, Integer> sleepingMinutes() {
            Map<Integer, Integer> minuteOccurances = new HashMap<>();

            sleepingTimes.stream()
                    .map(SleepTime::asMinutes)
                    .flatMap(List::stream)
                    .forEach((m) -> minuteOccurances.merge(m, 1, Integer::sum));

            return minuteOccurances;
        }

        public int mostCommonMinute() {
            Map<Integer, Integer> minuteOccurances = sleepingMinutes();

            return Collections.max(minuteOccurances.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        }

        public Integer mostCommonMinuteOccurance() {
            Map<Integer, Integer> minuteOccurances = sleepingMinutes();
            if (minuteOccurances.size() == 0) {
                return 0;
            }
            return Collections.max(minuteOccurances.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
        }
    }

}
