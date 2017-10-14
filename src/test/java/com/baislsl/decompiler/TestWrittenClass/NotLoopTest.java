package com.baislsl.decompiler.TestWrittenClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class SubClass{}
public class NotLoopTest extends  SubClass{
    private static int i = 1;
    private double d = 2;
    private float f = 3;
    private static long l = 4;
    private char c = 5;
    private short s = 4;

    private int[] ia;
    private double[] da;
    private float[] fa;
    private long[] la;
    private char[] ca;
    private short[] sa;

    public NotLoopTest(){
        super();
    }

    public double func(int a, int g) {
        float f2 = f * a;
        long l2 = g & l % g;

        return f2 * l2;
    }

    private final double funca(int s2, double d2) {
        ia[3] = s2 + (int) la[0] - c;
        da[5] = d2;

        return fa[i] * (ia[(int)d] ^ s) / da[5];
    }

    public void newTest(){
        Integer I = new Integer(i);
        NotLoopTest test = new NotLoopTest();


        Long[] ls = new Long[20];
        double[] ds = new double[10];
        ls[0] = new Long(0);
    }

    public double invokeSpeicalVituralTest(){
        StringBuilder builder = new StringBuilder("gg");
        builder.append("a");
        super.getClass().getName();
        builder = new StringBuilder(builder);
        NotLoopTest test = new NotLoopTest();
        return test.funca(0, 0);
    }

    public void invokeVirutalTest(){
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(10);
    }

}
