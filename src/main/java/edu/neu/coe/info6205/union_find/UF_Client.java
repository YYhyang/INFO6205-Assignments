package edu.neu.coe.info6205.union_find;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class UF_Client {
    public static void main(String[] args) throws IOException {
        /*
        The commented code is for data gathering
         */
//        File writeName=new File("C:\\Users\\63197\\Desktop\\data.txt");
//        BufferedWriter out=new BufferedWriter(new FileWriter(writeName));
        for(int n=100;n<=1000;n+=5){
            int count=count(n);
            System.out.println("For "+n+" Objects, we need "+count+" pairs to accomplish");
//            out.write(n+"\t"+count+"\r\n");
        }
//        out.flush();
//        out.close();
    }
    public static int count(int n){
        int count=0;
        UF_HWQUPC uf_hwqupc=new UF_HWQUPC(n);
        Random random=new Random();
        while (uf_hwqupc.components()!=1){
            int site1=random.nextInt(n);
            int site2=random.nextInt(n);
            uf_hwqupc.connect(site1,site2);
            count++;
        }
        return count;
    }

}
