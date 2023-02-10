package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public class CondStmt implements IStmt{
        String var;
        Exp exp1, exp2, exp3;


    public CondStmt(String var, Exp exp1, Exp exp2, Exp exp3) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
