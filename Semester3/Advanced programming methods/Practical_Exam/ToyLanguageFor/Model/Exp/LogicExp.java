package Model.Exp;

import Model.Adt.IHeap;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.MyException;
import Model.Adt.MyIDictionary;
import Model.Value.Value;
import Model.Type.BoolType;

public class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op;

    public LogicExp(String sign, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;

        switch (sign){
            case "&" -> op = 1;
            case "|" -> op = 2;
        }
    }


    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap heap) throws MyException {
        Value n1, n2;
        n1 = e1.eval(tbl, heap);
        if(n1.getType().equals(new BoolType()))
        {
            n2 = e2.eval(tbl, heap);
            if(n2.getType().equals(new BoolType()))
            {
                BoolValue b1 = (BoolValue) n1;
                BoolValue b2 = (BoolValue) n2;
                boolean nr1, nr2;
                nr1 = b1.getVal();
                nr2 = b2.getVal();
                if(op == 1)
                    return new BoolValue(nr1 && nr2);
                if(op == 2)
                    return new BoolValue(nr1 || nr2);
            }
            else throw new MyException("second operand is not boolean.");
        }
        else throw new MyException("first operand is not boolean.");
        return n1;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if(typ1.equals(new BoolType()))
        {
            if(typ2.equals(new BoolType()))
                return new BoolType();
            else throw new MyException("second operand is not a boolean!");
        } else throw new MyException("first operand is not a boolean!");
    }

    public Exp getE1() {
        return e1;
    }

    public void setE1(Exp e1) {
        this.e1 = e1;
    }

    public Exp getE2() {
        return e2;
    }

    public void setE2(Exp e2) {
        this.e2 = e2;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "LogicExp{" +
                "e1=" + e1 +
                ", e2=" + e2 +
                ", op=" + op +
                '}';
    }
}
