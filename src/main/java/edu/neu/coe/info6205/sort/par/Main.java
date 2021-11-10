package edu.neu.coe.info6205.sort.par;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {


        for(int threadCount=8;threadCount>=2;threadCount/=2){
        System.out.println("Degree of parallelism: " + threadCount);
//            System.out.println("Array length: " + arrayLength);
        Random random = new Random();
        int[] array = new int[2000000];
        ArrayList<Long> timeList = new ArrayList<>();
            for (int j = 50; j < 100; j++) {
                ForkJoinPool mypool=  new ForkJoinPool(threadCount);
                ParSort.cutoff = 10000 * (j + 1);
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time=0;
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                long startTime = System.currentTimeMillis();
                ParSort.sort(array, 0, array.length,mypool);
                long endTime = System.currentTimeMillis();
                time += (endTime - startTime);
            }
            timeList.add(time);
            System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");
            mypool.shutdown();
        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result_"+threadCount+".csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            double j = 50;
            for (long i : timeList) {
                String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i / 10 + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();
//            String content=lessCutoff+","+lessTime/10;
//            bw.write(content);

//
//            File f=new File("./src/new/best.txt");
//            FileWriter fw = new FileWriter(f, true);
//            PrintWriter pw = new PrintWriter(fw);
//            pw.println(arrayLength+","+lessCutoffValue+","+lessCutoff+","+lessTime/10);
//            pw.flush();
//            fw.flush();
//            pw.close();
//            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
