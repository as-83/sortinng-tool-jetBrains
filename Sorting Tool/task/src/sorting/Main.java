package sorting;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static  String outputFile;
    private static boolean writeToFile;

    public static void main(final String[] args) {
        //Command-line arguments parsing
        String dataType = "word";
        String sortingType = "natural";
        try {
            if (args.length > 0) {
                List<String> arguments = Arrays.asList(args);
                if (arguments.contains("-sortingType")) {
                    int index = arguments.indexOf("-sortingType");
                    boolean sortingTypeArgNotValid = (arguments.size() ==index + 1) ||
                            ((!arguments.get(index + 1).equals("natural"))
                                    && (!arguments.get(index + 1).equals("byCount")));
                    if (sortingTypeArgNotValid) {
                        throw new IllegalArgumentException("No sorting type defined!");
                    }
                    sortingType = arguments.get(index + 1);
                }

                if (arguments.contains("-inputFile")) {
                    int index = arguments.indexOf("-inputFile");
                    String inputFile = arguments.get(index + 1);
                    scanner = new Scanner(inputFile);
                }

                if (arguments.contains("-outputFile")) {
                    int index = arguments.indexOf("-outputFile");
                    outputFile = arguments.get(index + 1);
                    writeToFile = true;
                }

                if (arguments.contains("-dataType")) {
                    int index = arguments.indexOf("-dataType");
                    boolean dataTypeArgNotValid = arguments.size() ==index + 1;
                    if (dataTypeArgNotValid) {
                        throw new IllegalArgumentException("No data type defined!");
                    }

                    dataType =  arguments.get(index + 1);
                }

                arguments.stream().filter(s -> !s.equals("-dataType") && !s.equals("-sortingType") && s.startsWith("-"))
                        .forEach(s -> System.out.println("\"" + s + "\" is not a valid parameter. It will be skipped."));
            }

            switch (dataType) {
                case "long":
                    longParser(sortingType);
                    break;
                case "word":
                    wordParser(sortingType);
                    break;
                case "line":
                    lineParser(sortingType);
                    break;
                default:
                    throw new IllegalArgumentException("No data type defined!");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        //intSortByCount(Arrays.asList(1L, 2L, 2L, 3L, 3L, 3L, 4L, 4L, 4L, 4L, 9L, 5L));// Method test


    }

    private static void lineParser(String sortingType) {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        int totalLines = lines.size();
        // System.out.printf("Total lines: %d.\n", totalLines);
        StringBuilder stringBuilder = new StringBuilder(String.format("Total lines: %d.\n", totalLines));

        if (sortingType.equals("natural")) {

            stringsNaturalSorting(lines);
            //System.out.println("Sorted data:");
            stringBuilder.append("Sorted data:\n");
            //lines.forEach(s -> System.out.println(s));
            lines.forEach(s -> stringBuilder.append(s + "\n"));

        } else {
            stringBuilder.append(wordsSortingByCount(lines));
        }
        printResult(stringBuilder.toString());

       // stringsNaturalSorting(lines);
    }

    private static void printResult(String output) {
        if (writeToFile) {
            try (PrintWriter printWriter = new PrintWriter(outputFile);){
                printWriter.println(output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println(output);
        }
    }

    private static void wordParser(String sortingType) {
        List<String> words = new ArrayList<>();
        while (scanner.hasNext("\\S+")) {
            String line = scanner.next("\\S+");
            if (line.length() == 0) {
                break;
            }
            words.add(line);
        }

        int totalLines = words.size();
        //System.out.printf("Total words: %d.\n", totalLines);
        StringBuilder stringBuilder = new StringBuilder(String.format("Total words: %d.\n", totalLines));


        if (sortingType.equals("natural")) {
            stringsNaturalSorting(words);
            //System.out.print("Sorted data:");
            stringBuilder.append("Sorted data:");
            //words.forEach(s -> System.out.print(" " + s));
            words.forEach(s -> stringBuilder.append(" " + s));

        } else {
            stringBuilder.append(wordsSortingByCount(words));
        }
        printResult(stringBuilder.toString());

    }

    private static String wordsSortingByCount(List<String> words) {
        StringBuilder stringBuilder = new StringBuilder();

         words.stream().collect(Collectors.groupingBy(String::valueOf, Collectors.counting()))
                .entrySet().stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                        /*.forEach((m) -> System.out.println(m.getKey() + ": " + m.getValue() +
                        " time(s), " + Math.round(m.getValue() * 100.0 / words.size()) + "%"));*/
                 .forEach((m) -> stringBuilder.append(m.getKey() + ": " + m.getValue() +
                         " time(s), " + Math.round(m.getValue() * 100.0 / words.size()) + "%\n"));
                 return stringBuilder.toString();
    }

    private static void stringsNaturalSorting(List<String> words) {
        Collections.sort(words, (l1, l2) -> {
            if (l1.length() != l2.length()) {
                return l1.length() - l2.length();
            } else {
                return l1.compareTo(l2);
            }
        });

    }

    private static void longParser(String sortingType) {
        List<Long> longList = getLongsFromInput();
        int totalNumbers = longList.size();
        //System.out.printf("Total numbers: %d.\n", totalNumbers);
        StringBuilder stringBuilder = new StringBuilder(String.format("Total numbers: %d.\n", totalNumbers));

        if (sortingType.equals("natural")) {
            //intNaturalSort(longList);
            Collections.sort(longList);
            //System.out.print("Sorted data:");
            stringBuilder.append("Sorted data:");
            longList.forEach(s -> stringBuilder.append(" " + s));
        } else {
            //intSortByCount(longList);
            longList.stream().collect(Collectors.groupingBy(Long::longValue ,Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.<Long, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEach((entry) -> stringBuilder.append(entry.getKey() + ": " + entry.getValue() +
                            " time(s), " + Math.round(entry.getValue() * 100.0 / longList.size()) + "%\n"));
        }
        printResult(stringBuilder.toString());


    }

    private static List<Long> getLongsFromInput() {
        List<Long> longList = new ArrayList<>();
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            longList.add(number);
        }
        return longList;
    }

    private static void intNaturalSort(List<Long> longList)  {
        Collections.sort(longList);
        System.out.print("Sorted data:");
        longList.forEach(s -> System.out.print(" " + s));
    }

    private static void intSortByCount(List<Long> longList) {
        longList.stream().collect(Collectors.groupingBy(Long::longValue ,Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
        .forEach((entry) -> System.out.println(entry.getKey() + ": " + entry.getValue() +
                " time(s), " + Math.round(entry.getValue() * 100.0 / longList.size()) + "%"));
    }
}
