package edu.neu.coe.info6205.sort.par;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {


        for(int arrayLength=10000000;arrayLength<=14000000;arrayLength+=500000){
            ForkJoinPool myPool = new ForkJoinPool(4);
//        System.out.println("Degree of parallelism: " + threadCount);
            System.out.println("Array length: " + arrayLength);
        Random random = new Random();
        int[] array = new int[arrayLength];
        double lessCutoff=0;
        long lessTime=999999999;
        int lessCutoffValue=0;
        ArrayList<Long> timeList = new ArrayList<>();
        for (double j = 0.1; j <= 0.5; j+=0.01) {
            ParSort.cutoff = (int) (arrayLength*j);
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length,myPool);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);
            if(lessTime>time){
                lessTime=time;
                lessCutoff=j;
                lessCutoffValue=ParSort.cutoff;
            }

            System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");

        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/new/result_"+arrayLength+".csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            double j = 0.1;
            for (long i : timeList) {
                String content = j+ "," + (double) i / 10 + "\n";
                j+=0.01;
                bw.write(content);

            }
            String content=lessCutoff+","+lessTime/10;
            bw.write(content);
            bw.flush();
            bw.close();

            File f=new File("./src/new/best.txt");
            FileWriter fw = new FileWriter(f, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(arrayLength+","+lessCutoffValue+","+lessCutoff+","+lessTime/10);
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();

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
