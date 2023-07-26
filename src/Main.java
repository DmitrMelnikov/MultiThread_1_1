import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future> futures = new ArrayList<>();
        String[] texts = new String[25];
        long startTs = System.currentTimeMillis(); // start time THREADS


        ExecutorService threadPool = Executors.newFixedThreadPool(texts.length);
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
            String text = texts[i];
            Callable task = () -> {
                int maxSize = 0;
                for (int t = 0; t < text.length(); t++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (t >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = t; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - t) {
                            maxSize = j - t;
                        }
                    }
                }

                return maxSize;
            };

            Future<Integer> future = threadPool.submit(task);
            futures.add(future);
        }
        threadPool.shutdown();
        int max = 0;
        int iMax = 0;
        for (int i = 0; i < futures.size(); i++) {
            Future<Integer> futureInt = futures.get(i);
            if (futureInt.get() > max) {
                max = futureInt.get();
                iMax = i;
            }
        }
        System.out.println(" Максимальное совпадение  в :: " + texts[iMax].substring(0, 100) + " -> " + max + " раз.");

        long endTs = System.currentTimeMillis(); // end time THREADS
        System.out.println("Time: THREADS " + (endTs - startTs) + "ms");

 

//        long startFor = System.currentTimeMillis(); // start time FOR
//        for (String text : texts) {
//            int maxSize = 0;
//            for (int i = 0; i < text.length(); i++) {
//                for (int j = 0; j < text.length(); j++) {
//                    if (i >= j) {
//                        continue;
//                    }
//                    boolean bFound = false;
//                    for (int k = i; k < j; k++) {
//                        if (text.charAt(k) == 'b') {
//                            bFound = true;
//                            break;
//                        }
//                    }
//                    if (!bFound && maxSize < j - i) {
//                        maxSize = j - i;
//                    }
//                }
//            }
//            System.out.println(text.substring(0, 100) + " -> " + maxSize);
//        }
//        long endFor = System.currentTimeMillis(); // end time FOR
//        System.out.println("Time: FOR " + (endFor - startFor) + "ms");

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


