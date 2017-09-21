package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * implement of if_icmp<cond>
 *     if_icmpeq, if_icmpne, if_icmplt, if_icmpge, if_icmpgt, if_icmple
 */
public abstract class IfICmpInstruction extends ConditionalJumpInstruction {
    protected String operator;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        /// value1 operator value2
        Value value1 = opStack.pop();
        Value value2 = opStack.pop();

        int address = this.getJumpObject();
        String action = "\n";
        while(true){
            if(address == frame.getTo()){
                action = "break;";
                break;
            }
            else if(address == frame.getFrom()){
                action = "continue;";
                break;
            }
            if(frame.getExecutables()[address] instanceof UnconditionalJumpInstruction)
                address = ((UnconditionalJumpInstruction)frame.getExecutables()[address]).getJumpObject();
            else
                break;
        }

        result.append("if(")
              .append(value1).append(operator).append(value2)
              .append(") ")
              .append(action)
              .append("\n");
    }
}
