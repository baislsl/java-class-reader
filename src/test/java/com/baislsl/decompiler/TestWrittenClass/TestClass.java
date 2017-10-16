package com.baislsl.decompiler.TestWrittenClass;

import com.baislsl.decompiler.DecompileException;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

interface TestClassInterface {
    String func(int a, double d) throws DecompileException;
}

// compile to test only class file
public class TestClass implements TestClassInterface {
    private static int a;
    public static final double d = 1.03;
    protected String str = "Wonder";
    List<Integer> li = new ArrayList<>();
    @Deprecated private volatile long s = 4L;

    TestClass() {
        System.out.println("construct test class");
    }

    @Override
    public String func(int a, double d) throws DecompileException {
        return Double.toString(TestClass.a * a + d * TestClass.d);
    }



    private synchronized void func2(int i) {
        try {
            func(a, d);
        } catch (DecompileException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private synchronized void func2(final int i, int j) {
        func2(i);
        func2(j);

        if(i > j)
            func2(i - j);
        else
            func2(j - i);

        if(i > j)
            j++;

        while(i > j){
            ++j;
        }

    }

    public static void main(String[] args) {
        int cnt = 10, i;
        for (i = 0; i < cnt; i++) {
            new TestClass().func2(i, i + 1);
        }
        try {
            Class<?> cl = Class.forName(TestClassSub.class.getName());
            Constructor<?> constructor = cl.getConstructor(String.class, int.class);
            constructor.newInstance("arg", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class TestClassSub extends TestClass {
    TestClassSub() {
        super();
        TestClassSub.main(new String[]{"arg1", "arg2"});
    }

    TestClassSub(String args, int iarg) {
        super();
        TestClassSub.main(new String[]{args, Integer.toString(iarg)});
    }

    @Test
    public void subTest() {
        int i = 10;
        while (--i != 0) {
            try {
                new TestClass().func(i, 0.0);
            } catch (DecompileException e) {
                e.printStackTrace();
            }
        }
    }
}