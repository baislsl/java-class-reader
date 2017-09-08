package com.baislsl.decompiler;

import java.io.DataInputStream;

public interface Reader {
    /**
     * 
     * @param file binary input stream of *.class format file
     * @return decompile result
     */
    Result decompile(DataInputStream file) throws DecompileException;
}
