package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Exp.Exp;
import Model.Exp.VarExp;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;

public class ConditionalAssignment implements IStmt{
    private String var;
    private Exp exp1, exp2, exp3;


    public ConditionalAssignment(String var, Exp exp1, Exp exp2, Exp exp3) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public String toString() {
        return "ConditionalAssignment{" +
                "var='" + var + '\'' +
                ", exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", exp3=" + exp3 +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt converted = new IfStmt(exp1, new AssignStmt(var, exp2), new AssignStmt(var, exp3));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = new VarExp(var).typecheck(typeEnv);
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        Type type3 = exp3.typecheck(typeEnv);
        if (type1.equals(new BoolType()) && type2.equals(varType) && type3.equals(varType))
            return typeEnv;
        else
            throw new MyException("The conditional assignment is invalid!");
    }
}
