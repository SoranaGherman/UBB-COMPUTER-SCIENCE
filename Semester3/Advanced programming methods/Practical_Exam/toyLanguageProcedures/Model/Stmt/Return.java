package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public class Return implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getSymTable().pop();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "return";
    }
}
