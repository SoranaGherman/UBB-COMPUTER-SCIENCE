package Model.Stmt;

import Model.Exp.Exp;
import Model.MyException;
import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Model.Type.BoolType;
import Model.Adt.IHeap;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public IStmt getThenS() {
        return thenS;
    }

    public void setThenS(IStmt thenS) {
        this.thenS = thenS;
    }

    public IStmt getElseS() {
        return elseS;
    }

    public void setElseS(IStmt elseS) {
        this.elseS = elseS;
    }
    public  String toString(){
        return "(IF("+exp.toString()+")THEN("+thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> tbl = state.getSymTable();
        IHeap heap = state.getHeap();
        Value condition = exp.eval(tbl, heap);
        if(!condition.getType().equals(new BoolType())) throw new MyException("Conditional expr is not a boolean");
        else
        {
            BoolValue v = (BoolValue) condition;
            boolean b = v.getVal();
            if(b)
                stk.push(thenS);
            else stk.push(elseS);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType()))
        {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());

            return typeEnv;
        }
        else throw new MyException("The condition of IF has not the type bool!");
    }
}
