package Model.Exp;

import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class NotExp implements Exp{
    Exp exp;

    public NotExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "NotExp{" +
                "exp=" + exp +
                '}';
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap heap) throws MyException {
        BoolValue val = (BoolValue) exp.eval(tbl, heap);
        if(! val.getVal()) return new BoolValue(true);
        else return new BoolValue(false);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
       return exp.typecheck(typeEnv);
    }
}
