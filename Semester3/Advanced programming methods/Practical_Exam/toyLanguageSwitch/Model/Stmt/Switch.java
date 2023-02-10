package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Exp.Exp;
import Model.Exp.RelationalExp;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public class Switch implements IStmt{
    private Exp exp;
    private Exp exp1;
    private Exp exp2;
    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    public Switch(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "exp=" + exp +
                ", exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", stmt1=" + stmt1 +
                ", stmt2=" + stmt2 +
                ", stmt3=" + stmt3 +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        IStmt converted = new IfStmt(new RelationalExp("==", exp, exp1), stmt1, new IfStmt(new RelationalExp(
                                     "==", exp, exp2), stmt2, stmt3));
        stk.push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);

        if(type.equals(type1) && type.equals(type2))
        {
            stmt1.typecheck(typeEnv);
            stmt2.typecheck(typeEnv);
            stmt3.typecheck(typeEnv);
            return typeEnv;
        }
        else throw new MyException("The exp types don't match the switch!");
    }
}
