package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.MethodrefTag;
import com.baislsl.decompiler.structure.constantPool.NameAndTypeTag;
import com.baislsl.decompiler.utils.descriptor.Descriptor;
import com.baislsl.decompiler.utils.descriptor.MethodDescriptor;
import com.baislsl.decompiler.utils.descriptor.VoidDescriptor;

import java.util.ArrayList;
import java.util.List;

public class INVOKEVIRTUAL extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        MethodrefTag methodrefTag = (MethodrefTag) clazz.getConstantPool((int)get2u());
        NameAndTypeTag nameAndTypeIndexCp = (NameAndTypeTag) clazz.getConstantPool(methodrefTag.getNameAndTypeIndex());
        String descriptor = clazz.getUTF8Info(nameAndTypeIndexCp.getDescriptorIndex());
        MethodDescriptor methodDescriptor = new MethodDescriptor(descriptor);
        int cnt = methodDescriptor.getParamSize();
        Descriptor returnDescriptor = methodDescriptor.getReturnDescriptor();

        List<Value> parameters = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            parameters.add(opStack.pop());
        }
        String objectRef = opStack.pop().toString();
        StringBuilder ans = new StringBuilder();

        ans.append(objectRef)
              .append("(")
              .append(parameters.stream().reduce((p1, p2) -> new Value(p1 + ", " + p2)).get())
              .append(");\n");

        if(returnDescriptor instanceof VoidDescriptor){
            result.append(ans);
        } else {
            opStack.push(new Value(ans.toString(), false));
        }
    }
}
