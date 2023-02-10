package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Adt.MyStack;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class forkStmt implements IStmt{
    private IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ')';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<IStmt>();
        MyIStack<MyIDictionary<String, Value>> newSymTable = state.getSymTable().clone();
        return new PrgState(newStack, newSymTable, state.getOut(), state.getFileTable(),
                state.getHeap(), state.getProcTable(), stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
