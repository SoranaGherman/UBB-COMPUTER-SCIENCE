package Model.Exp;

import Model.Adt.IHeap;
import Model.MyException;
import Model.Adt.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    public Value getE() {
        return e;
    }

    public void setE(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap heap) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return "ValueExp{" +
                "e=" + e +
                '}';
    }
}
