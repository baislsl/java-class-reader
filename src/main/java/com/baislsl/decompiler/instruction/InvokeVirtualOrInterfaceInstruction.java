package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.constantPool.FMIBasic;
import com.baislsl.decompiler.structure.constantPool.NameAndTypeTag;
import com.baislsl.decompiler.utils.descriptor.Descriptor;
import com.baislsl.decompiler.utils.descriptor.MethodDescriptor;
import com.baislsl.decompiler.utils.descriptor.VoidDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @see INVOKEVIRTUAL
 * @see INVOKEINTERFACE
 */
public class InvokeVirtualOrInterfaceInstruction extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        FMIBasic methodrefTag = (FMIBasic) clazz.getConstantPool(get2u());
        NameAndTypeTag nameAndTypeIndexCp = (NameAndTypeTag) clazz.getConstantPool(methodrefTag.getNameAndTypeIndex());
        String descriptor = clazz.getUTF8Info(nameAndTypeIndexCp.getDescriptorIndex());
        MethodDescriptor methodDescriptor = new MethodDescriptor(descriptor);
        int cnt = methodDescriptor.getParamSize();
        Descriptor returnDescriptor = methodDescriptor.getReturnDescriptor();

        List<Value> parameters = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            parameters.add(opStack.pop());
        }
        Value objectRef = opStack.pop();
        StringBuilder ans = new StringBuilder();
        StringBuilder param = new StringBuilder();
        if (parameters.size() != 0)
            param.append(parameters.get(0));
        for (int i = 1; i < parameters.size(); i++) {
            param.append(", ").append(parameters.get(i));
        }
        ans.append(objectRef)
                .append(".")
                .append(methodrefTag.getName(clazz))
                .append("(")
                .append(param)
                .append(")");
        if (returnDescriptor instanceof VoidDescriptor) {
            result.append(ans).append(";\n");
        } else {
            opStack.push(new Value(ans.toString(), false));
        }
    }
}
