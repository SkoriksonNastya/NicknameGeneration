import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger niceWordsWithLength3 = new AtomicInteger(0);
    static AtomicInteger niceWordsWithLength4 = new AtomicInteger(0);
    static AtomicInteger niceWordsWithLength5 = new AtomicInteger(0);
    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        for (int i = 0; i < texts.length; i++){
            System.out.println(texts[i]);
        }

        List<Thread> threads = new ArrayList<>(3);

        threads.add(new Thread(() -> {
            for (String text : texts){
                if (testPalindrome(text)){
                    addNiceWords(text.length());
                }
            }
        }));

        threads.add(new Thread(() -> {
            for (String text : texts){
                if (testIsSameLetter(text)){
                    addNiceWords(text.length());
                }
            }
        }));

        threads.add(new Thread(() -> {
            for (String text : texts){
                if (ascendingTest(text)){
                    addNiceWords(text.length());
                }
            }
        }));

        for (Thread thread : threads){
            thread.start();
        }

        System.out.println("Среди сгенерированных");
        System.out.println("- красивых слов с длиной 3: " + niceWordsWithLength3);
        System.out.println("- красивых слов с длиной 4: " + niceWordsWithLength4);
        System.out.println("- красивых слов с длиной 5: " + niceWordsWithLength5);

    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
    public static boolean testPalindrome(String someText) {
        StringBuffer sb = new StringBuffer(someText);
        return someText.equals(sb.reverse().toString());
    }

    public static boolean testIsSameLetter(String someText) {
        for (int i = 1; i < someText.length(); i++){
            if (someText.charAt(i) != someText.charAt(i-1)){
                return false;
            }
        }
        return true;
    }
    public static boolean ascendingTest(String someText) {
        for (int i = 1; i < someText.length(); i++){
            if (someText.charAt(i) < someText.charAt(i-1)){
                return false;
            }
        }
        return true;
    }

    public static void addNiceWords (int wordLength) {
        switch (wordLength) {
            case (3):
                niceWordsWithLength3.addAndGet(1);
                break;
            case (4):
                niceWordsWithLength4.addAndGet(1);
                break;
            case (5):
                niceWordsWithLength5.addAndGet(1);
                break;
            default:
                throw new IllegalArgumentException("Exception!");
        }
    }
}