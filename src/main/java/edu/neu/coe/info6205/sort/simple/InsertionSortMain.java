package edu.neu.coe.info6205.sort.simple;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InsertionSortMain {

    /**
     * generate total random array
     * @param n the number of elements in array
     * @return a total random array
     */
    public static Integer[] randomArray(int n){
        Integer[] array=new Integer[n];
        Random random=new Random();
        for(int i=0;i<n;i++){
            array[i]=random.nextInt();
        }
        return array;
    }

    /**
     * generate a ordered array
     * @param n the number of elements in array
     * @return a ordered array
     */
    public static Integer[] orderedArray(int n){
        Integer[] array=new Integer[n];
        Random random=new Random();
        int init=random.nextInt();
        for(int i=0;i<n;i++){
            array[i]=init+i;
        }
        return array;
    }

    /**
     * generate a half ordered array
     * @param n the number of elements in array
     * @return
     */
    public static Integer[] pOrderedArray(int n) {
        Integer[] array=randomArray(n);
        for(int i=0;i<n/2;i++)
            array[i]=i;
        return array;
    }

    /**
     * generate a reversed array
     * @param n the number of elements in array
     * @return
     */
    public static Integer[] reversedArray(int n){
        Integer[] array=new Integer[n];
        Random random=new Random();
        int init=random.nextInt();
        for(int i=0;i<n;i++){
            array[i]=init-i;
        }
        return array;
    }
    public static void main(String[] args) throws IOException {
        for(int n=50;n<=800;n=n*2){
            BaseHelper<Integer> helper = new BaseHelper<>("InsertionSort", n, Config.load(InsertionSortMain.class));
            GenericSort<Integer> sorter = new InsertionSort<Integer>(helper);
            System.out.println("Array Size: "+n);
            System.out.println("Random array");
            Benchmark_Timer benchmark_timer=new Benchmark_Timer("InsertionSort",a->sorter.sort((Integer[]) a));
            int finalN = n;
            double time=benchmark_timer.runFromSupplier(()->randomArray(finalN),100);
            System.out.println("Mean Time for Random Array is: "+time);

            System.out.println("Ordered array");
            time=benchmark_timer.runFromSupplier(()->orderedArray(finalN),100);
            System.out.println("Mean Time for Ordered Array is: "+time);

            System.out.println("Partially Ordered array");
            time = benchmark_timer.runFromSupplier(() -> pOrderedArray(finalN), 100);
            System.out.println("Mean Time for Partially Ordered Array is: "+time);

            System.out.println("Reversed array");
            time=benchmark_timer.runFromSupplier(()->reversedArray(finalN),100);
            System.out.println("Mean Time for Reversed Array is: "+time);
            System.out.println();
        }
    }
}
