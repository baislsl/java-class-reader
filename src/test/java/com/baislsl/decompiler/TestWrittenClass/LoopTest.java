package com.baislsl.decompiler.TestWrittenClass;

public class LoopTest {
    private static int a;
    public static final double d = 1.03;
    private int loop(int a, double d){
        a = LoopTest.a;
        d = LoopTest.d;


        while(a < 10){
            d += 2;
            a += 2;
            for(int k = 0; k < 100;k++){
                k += d;
                if(k == 1) break;
                k /= 2;
                if(k == 0) continue;
                k *= 3;
            }
        }

        System.out.println("this is loop test, which handle while loop and ignore if else condition");

        for(int g = 1; g < 10; g++){
            a -= 10;
            if(a == 0) break;
        }
        double h = a * d;
        return a;
    }
}
