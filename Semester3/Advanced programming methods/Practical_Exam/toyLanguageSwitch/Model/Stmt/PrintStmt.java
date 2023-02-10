package Model.Stmt;

import Model.*;
import Model.Adt.MyIDictionary;
import Model.Adt.MyIList;
import Model.Adt.MyIStack;
import Model.Exp.Exp;
import Model.Type.Type;
import Model.Value.Value;
import Model.Adt.IHeap;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public String toString()
    {
        return "print("+exp.toString()+")";
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk =  state.getExeStack();
        MyIList<Value> list = state.getOut();
        MyIDictionary<String, Value> tbl = state.getSymTable();
        IHeap heap = state.getHeap();
        list.add(exp.eval(tbl, heap));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
