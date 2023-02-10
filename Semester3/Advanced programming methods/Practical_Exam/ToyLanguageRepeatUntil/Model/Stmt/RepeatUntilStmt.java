package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Adt.MyStack;
import Model.Exp.Exp;
import Model.Exp.NotExp;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;

public class RepeatUntilStmt implements IStmt{
    IStmt stmt1;
    Exp exp2;


    public RepeatUntilStmt(IStmt stmt1, Exp exp2) {
        this.stmt1 = stmt1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        IStmt convertToReapUntil = new CompStmt(stmt1, new WhileStmt(new NotExp(exp2), stmt1));
        stk.push(convertToReapUntil);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = exp2.typecheck(typeEnv);
        if(t1.equals(new BoolType())){
            stmt1.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("Expression mast be bool type!");
    }

    @Override
    public String toString() {
        return "RepeatUntilStmt{" +
                "stmt1=" + stmt1 +
                ", exp2=" + exp2 +
                '}';
    }
}
