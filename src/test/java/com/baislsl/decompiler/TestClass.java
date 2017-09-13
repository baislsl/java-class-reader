package com.baislsl.decompiler;

import org.junit.Test;

interface TestClassInterface {
    String func(int a, double d) throws DecompileException;
}

// compile to test only class file
public class TestClass implements TestClassInterface {
    private static int a;
    public static final double d = 1.03;
    private volatile int s;

    TestClass() {
        System.out.println("construct test class");
    }

    @Override
    public String func(int a, double d) throws DecompileException {
        return Double.toString(this.a * a + d * this.d);
    }

    private synchronized void func2(int i) {
        try {
            func(a, d);
        } catch (DecompileException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private synchronized void func2(int i, int j) {
        func2(i);
        func2(j);
    }

    public static void main(String[] args) {
        int cnt = 10, i;
        for (i = 0; i < cnt; i++) {
            new TestClass().func2(i);
        }
    }
}


class TestClassSub extends TestClass {
    TestClassSub() {
        super();
        TestClassSub.main(new String[]{"arg1", "arg2"});
    }

    @Test
    public void subTest(){
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