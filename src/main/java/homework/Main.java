package homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static final int AMOUNT = 100_000;
    public static AtomicInteger lengthThree = new AtomicInteger();
    public static AtomicInteger lengthFour = new AtomicInteger();
    public static AtomicInteger lengthFive = new AtomicInteger();


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[AMOUNT];
        List<Thread> threads = new ArrayList<>();
        System.out.println("1.Generating words...");
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable palindrome = () -> {
            for (String text : texts) {
                StringBuilder invertedText = new StringBuilder(text);
                String compared = invertedText.reverse().toString();
                increasingCounter(text, compared);
            }
        };
        Runnable singleLetter = () -> {
            for (String text : texts) {
                char firstChar = text.charAt(0);
                StringBuilder pattern = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    pattern.append(firstChar);
                }
                String compared = pattern.toString();
                increasingCounter(text, compared);
            }
        };
        Runnable sortedString = () -> {
            for (String text : texts) {
                String compared = Stream.of(text.split(""))
                        .sorted()
                        .collect(Collectors.joining());
                increasingCounter(text, compared);

            }
        };
        threads.add(new Thread(palindrome));
        threads.add(new Thread(singleLetter));
        threads.add(new Thread(sortedString));

        System.out.println("2.We count words in streams...");
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Result how many \"beautiful\" words are found among the generated");
        System.out.printf("Красивых слов с длиной 3: %d шт\n", lengthThree.get());
        System.out.printf("Красивых слов с длиной 4: %d шт\n", lengthFour.get());
        System.out.printf("Красивых слов с длиной 5: %d шт\n", lengthFive.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void increasingCounter(String text, String compared) {
        if (text.equals(compared)) {
            switch (text.length()) {
                case (3):
                    lengthThree.getAndIncrement();
                    break;
                case (4):
                    lengthFour.getAndIncrement();
                    break;
                case (5):
                    lengthFive.getAndIncrement();
                    break;
            }
        }
    }
}
