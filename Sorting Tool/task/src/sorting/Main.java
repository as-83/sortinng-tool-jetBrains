package sorting;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(final String[] args) {
        String dataType = "word";

        if (args.length > 0 && args[0].equals( "-dataType")) {
            dataType = args[1];
        }

        switch (dataType) {
            case "long": longParser(); break;
            case "word": wordParser(); break;
            case "line": lineParser(); break;
            default: break;
        }


    }

    private static void lineParser() {
    }

    private static void wordParser() {
    }

    private static void longParser() {
        List<Long> longList = new ArrayList<>();
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            longList.add(number);
        }

        Collections.sort(longList);
        int totalNumbers = longList.size();
        long greatestNumber = longList.get(totalNumbers - 1);
        long time = longList.stream().filter(l -> l == greatestNumber).count();


        System.out.printf("Total numbers: %d.\n" +
                "The greatest number: %d (%d time(s)).", totalNumbers, greatestNumber, time);
    }
}
