package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.ClassReader;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Method;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DecompileEngineTest2 {
    private final static Logger logger = LoggerFactory.getLogger(DecompileEngineTest2.class);

    private static void runClass(String path) throws DecompileException, IOException {
        Result clazz = ClassReader.decompile(path);
        DecompileEngine engine = new DecompileEngine(clazz);
        for (Method method : clazz.getMethods()) {
            logger.info(engine.decompile(method));
        }
    }

    @Test
    public void test1() throws Exception {
        runClass("target/test-classes/com/baislsl/decompiler/TestWrittenClass/NotLoopTest.class");
    }


}
