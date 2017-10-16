package com.baislsl.decompiler;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class DecompilerTest {
    private static final Logger logger = LoggerFactory.getLogger(DecompilerTest.class);
    @Test
    public void decompile() throws Exception {
        logger.info(Decompiler.decompile("target/test-classes/com/baislsl/decompiler/TestWrittenClass/NotLoopTest.class"));

    }

}