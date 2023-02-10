package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Adt.MyStack;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

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
        return new PrgState(newStack, state.getSymTable().deepCopy(), state.getOut(), state.getFileTable(), state.getHeap(),
                state.getLockTable(), stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
