package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Adt.MyStack;
import Model.Exp.Exp;
import Model.Exp.RelationalExp;
import Model.Exp.VarExp;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;

public class ForStmt implements IStmt{

    private String str;
    private Exp exp1, exp2, exp3;
    private IStmt stmt;

    public ForStmt(String str, Exp exp1, Exp exp2, Exp exp3, IStmt stmt) {
        this.str = str;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        IStmt convertToWhile = new CompStmt(new AssignStmt(str, exp1),
                               new WhileStmt(new RelationalExp("<" , new VarExp(str), exp2),
                                            new CompStmt(stmt, new AssignStmt(str, exp3))));
        stk.push(convertToWhile);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = exp1.typecheck(typeEnv);
        Type t2 = exp2.typecheck(typeEnv);
        Type t3 = exp2.typecheck(typeEnv);

        if(t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType())) return typeEnv;
        else
            throw new MyException("All types have to be int!");
    }

    @Override
    public String toString() {
        return "for(" + str + "=" + exp1 + ";" + str + "<" + exp2 + ";" + str + "=" + exp3 + ")" +
                "{" + stmt + "}";
    }
}
