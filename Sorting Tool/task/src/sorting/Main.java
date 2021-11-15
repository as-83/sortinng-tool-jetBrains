package sorting;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(final String[] args) {
        //Command-line arguments parsing
        String dataType = "word";


        if (args.length > 0) {
            List<String> arguments = List.of(args);
            if (arguments.contains("-sortIntegers")) {
                dataType = "sortIntegers";
            } else if (arguments.contains( "-dataType")) {
                int index = arguments.indexOf("-dataType");
                dataType = args[index + 1];
            }
        }



        switch (dataType) {
            case "long": longParser(); break;
            case "word": wordParser(); break;
            case "line": lineParser(); break;
            case "sortIntegers": sortIntegers(); break;
            default: break;
        }


    }

    private static void sortIntegers() {
        List<Long> longList = getLongsFromInput();

        Collections.sort(longList);
        int totalNumbers = longList.size();
        System.out.printf("Total numbers: %d.\n", totalNumbers);
        System.out.print("Sorted data:");
        longList.forEach(s -> System.out.print(" " + s));
    }

    private static void lineParser() {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
           /* if (line.length() == 0) {
                break;
            }*/
            lines.add(line);
        }

        sortStrings(lines);

        int totalLines = lines.size();
        String greatestLine = lines.get(totalLines - 1);
        long time = lines.stream().filter(l -> l.length() == greatestLine.length()).count();

        int percents = (int) Math.round(time*100.0/totalLines);


        System.out.printf("Total lines: %d.\n" +
                "The longest line:\n" +
                "%s\n (%d time(s), %d%%).", totalLines, greatestLine, time, percents);
    }

    private static void wordParser() {
        List<String> words = new ArrayList<>();
        while (scanner.hasNext("\\S+")) {
            String line = scanner.next("\\S+");
            if (line.length() == 0) {
                break;
            }
            words.add(line);
        }

        sortStrings(words);

        int totalLines = words.size();
        String greatestLine = words.get(totalLines - 1);
        long time = words.stream().filter(l -> l.length() == greatestLine.length()).count();

        int percents = (int) Math.round(time*100.0/totalLines);


        System.out.printf("Total words: %d.\n" +
                "The longest word: " +
                "%s (%d time(s), %d%%).", totalLines, greatestLine, time, percents);

    }

    private static void sortStrings(List<String> words) {
        Collections.sort(words, (l1, l2) -> {
            if (l1.length() != l2.length()) {
                return l1.length() - l2.length();
            } else {
                return l1.compareTo(l2);
            }
        });

    }

    private static void longParser() {
        List<Long> longList = getLongsFromInput();

        Collections.sort(longList);
        int totalNumbers = longList.size();
        long greatestNumber = longList.get(totalNumbers - 1);
        long time = longList.stream().filter(l -> l == greatestNumber).count();


        System.out.printf("Total numbers: %d.\n" +
                "The greatest number: %d (%d time(s)).", totalNumbers, greatestNumber, time);
    }

    @NotNull
    private static List<Long> getLongsFromInput() {
        List<Long> longList = new ArrayList<>();
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            longList.add(number);
        }
        return longList;
    }
}
