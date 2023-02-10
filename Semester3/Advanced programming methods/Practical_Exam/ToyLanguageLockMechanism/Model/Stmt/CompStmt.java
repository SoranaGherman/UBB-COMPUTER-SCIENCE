package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.Adt.MyIStack;
import Model.PrgState;
import Model.Type.Type;

public class CompStmt implements IStmt
{
    private IStmt first;
    private IStmt snd;


    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    public IStmt getFirst() {
        return first;
    }

    public void setFirst(IStmt first) {
        this.first = first;
    }

    public IStmt getSnd() {
        return snd;
    }

    public void setSnd(IStmt second) {
        this.snd = second;
    }

    public String toString()
    {
        return "("+first.toString()+";"+snd.toString()+")";
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return snd.typecheck(first.typecheck(typeEnv));
    }
}
