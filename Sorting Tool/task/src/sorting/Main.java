package sorting;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
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
