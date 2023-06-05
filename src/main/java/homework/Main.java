package homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int AMOUNT = 100_000;
    public static int lengthThree;
    public static int lengthFour;
    public static int lengthFive;


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[AMOUNT];
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        for (String text : texts) {
            Runnable palindrome = () -> {
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == text.charAt(texts.length-1)) {
                        if (text.charAt(i++) == text.charAt(texts.length - 2)) {
                            switch (text.length()) {
                                case (3):
                                    lengthThree++;
                                    break;
                                case (4):
                                    lengthFour++;
                                    break;
                                case (5):
                                    lengthFive++;
                                    break;
                            }
                        }
                    }
                }
            };
            threads.add(new Thread(palindrome));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.printf("Красивых слов с длиной 3: %d шт\n", lengthThree);
        System.out.printf("Красивых слов с длиной 4: %d шт\n", lengthFour);
        System.out.printf("Красивых слов с длиной 5: %d шт\n", lengthFive);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
