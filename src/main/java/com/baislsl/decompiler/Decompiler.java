package com.baislsl.decompiler;

import com.baislsl.decompiler.engine.DecompileEngine;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.utils.Javap;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

// TODO: simple class name
// TODO: remove 'synchronized' ... in class name
public class Decompiler {

    public static String decompile(String path) throws DecompileException, IOException {
        return decompile(new DataInputStream(new FileInputStream(path)));
    }

    public static String decompile(DataInputStream file) throws DecompileException, IOException {
        StringBuilder ans = new StringBuilder();
        Result result = ClassReader.decompile(file);

        addAccessFlag(ans, result);
        ans.append(" class ");
        addClassName(ans, result);
        addSuperClass(ans, result);
        addInterfaces(ans, result);
        addFieldAndMethod(ans, result);

        return ans.toString();
    }

    private static StringBuilder addFieldAndMethod(StringBuilder ans, Result result)
            throws DecompileException {
        ans.append("{\n");
        addField(ans, result);
        addMethod(ans, result);
        ans.append("}\n");
        return ans;
    }

    private static StringBuilder addField(StringBuilder ans, Result result)
            throws DecompileException {
        for(Field field : result.getFields()){
            ans.append(field.name()).append(";\n");
        }
        return ans;
    }

    private static StringBuilder addMethod(StringBuilder ans, Result result)
            throws DecompileException{
        DecompileEngine engine = new DecompileEngine(result);
        for(Method method : result.getMethods()){
            ans.append(engine.decompile(method)).append("\n");
        }
        return ans;
    }


    private static StringBuilder addClassName(StringBuilder ans, Result result)
            throws DecompileException {
        return ans.append(result.getClassName());
    }

    private static StringBuilder addAccessFlag(StringBuilder ans, Result result)
            throws DecompileException {
        return ans.append(Javap.accessFlagDescription(result.getAccessFlag()));
    }

    private static StringBuilder addSuperClass(StringBuilder ans, Result result)
            throws DecompileException {
        String superClassName = result.getSuperClassName();
        if (superClassName != null) {
            ans.append(" extends ").append(superClassName);
        }
        return ans;
    }

    private static StringBuilder addInterfaces(StringBuilder ans, Result result)
            throws DecompileException {
        String[] interfaces = result.getInterfacesName();
        if (interfaces.length != 0) {
            ans.append("implements ")
                    .append(interfaces[0]);
        }
        for (int i = 1; i < interfaces.length; i++) {
            ans.append(", ").append(interfaces[i]);
        }
        return ans;
    }
}
