package Model.Exp;

import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.Value;

public class MUL implements Exp{
    Exp exp1;
    Exp exp2;

    public MUL(Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap heap) throws MyException {
        Exp converted = new ArithExp("-", new ArithExp("*", exp1, exp2), new ArithExp("+", exp1, exp2));
        return converted.eval(tbl, heap);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
                return new IntType();
            else throw new MyException("second operand is not an int!");
        } else throw new MyException("first operand is not an int!");

    }

    public Exp getExp1() {
        return exp1;
    }

    public void setExp1(Exp exp1) {
        this.exp1 = exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public void setExp2(Exp exp2) {
        this.exp2 = exp2;
    }

    @Override
    public String toString() {
        return "MUL{" +
                "exp1=" + exp1 +
                ", exp2=" + exp2 +
                '}';
    }
}
