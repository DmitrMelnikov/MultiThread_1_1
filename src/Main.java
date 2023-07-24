import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        String[] texts = new String[25];
        long startTs = System.currentTimeMillis(); // start time THREADS
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
            threads.add(new Thread(new MyRunnable(texts[i])));
            threads.get(i).start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long endTs = System.currentTimeMillis(); // end time THREADS
        System.out.println("Time: THREADS " + (endTs - startTs) + "ms");

        long startFor = System.currentTimeMillis(); // start time FOR
        for (String text : texts) {
            int maxSize = 0;
            for (int i = 0; i < text.length(); i++) {
                for (int j = 0; j < text.length(); j++) {
                    if (i >= j) {
                        continue;
                    }
                    boolean bFound = false;
                    for (int k = i; k < j; k++) {
                        if (text.charAt(k) == 'b') {
                            bFound = true;
                            break;
                        }
                    }
                    if (!bFound && maxSize < j - i) {
                        maxSize = j - i;
                    }
                }
            }
            System.out.println(text.substring(0, 100) + " -> " + maxSize);
        }
        long endFor = System.currentTimeMillis(); // end time FOR
        System.out.println("Time: FOR " + (endFor - startFor) + "ms");

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