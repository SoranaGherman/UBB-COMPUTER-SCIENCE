package Model.Exp;

import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Model.Value.BoolValue;

public class RelationalExp implements Exp{
    private Exp exp1;
    private Exp exp2;
    private int op;

    public RelationalExp(String opS, Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        switch (opS) {
            case "<" -> op = 1;
            case "<=" -> op = 2;
            case "==" -> op = 3;
            case "!=" -> op = 4;
            case ">" -> op = 5;
            case ">=" -> op = 6;
        }

    }


    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap heap) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = exp2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new BoolValue(n1 < n2);
                if (op == 2) return new BoolValue(n1 <= n2);
                if (op == 3) return new BoolValue(n1 == n2);
                if (op == 4) return new BoolValue(n1 != n2);
                if (op == 5) return new BoolValue(n1 > n2);
                if (op == 6) return new BoolValue(n1 >= n2);
            }
            else throw new MyException("second operand is not an integer");
        }
        else throw new MyException("first operand is not an integer");
        return v1; // missing return statement
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
                return new BoolType();
            else throw new MyException("second operand is not a boolean!");
        } else throw new MyException("first operand is not a boolean!");
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

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "RelationalExp{" +
                "exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", op=" + op +
                '}';
    }
}
