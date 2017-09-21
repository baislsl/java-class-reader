package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.attribute.CodeAttr;


public class DecompileEngine {

    private Result clazz;

    public DecompileEngine(Result clazz) {
        this.clazz = clazz;
    }


    public String decompile(Field field) throws DecompileException {
        return field.name();
    }

    public String decompile(Method method) throws DecompileException {
        CodeAttr codeAttr = null;
        for (Attribute attribute : method.getAttributes()) {
            if (attribute instanceof CodeAttr) {
                codeAttr = (CodeAttr) attribute;
            }
        }

        return method.name()
                + "{\n"
                + decompileCode(method)
                + "\n}";

    }

    public String decompileAll() {
        return "";
    }

    private String decompileCode(Method method) throws DecompileException {
        return new Frame(clazz, method, null)
                .exec(0, method.getCodeLength())
                .get();
    }

}
