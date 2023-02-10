package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public interface IStmt
{
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
