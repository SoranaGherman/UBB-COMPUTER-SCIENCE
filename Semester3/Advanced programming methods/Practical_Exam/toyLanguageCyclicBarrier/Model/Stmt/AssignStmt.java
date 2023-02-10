package Model.Stmt;

import Model.*;
import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Exp.Exp;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String id, Exp ex) {
        this.id = id;
        this.exp = ex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public String toString(){
        return id+"="+exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IHeap heap = state.getHeap();

        if(symTbl.isDefined(id))
        {
            Value val = exp.eval(symTbl, heap);
            Type typId  = (symTbl.lookup(id).getType());
            if(val.getType().equals(typId))
                symTbl.update(id, val);
            else throw new MyException("declared type of variable"+id+"and type of the assigned expression do not match");
        }
        else throw  new MyException("the used variable"+id+"v=was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if(typevar.equals(typexp)) return typeEnv;
        else throw new MyException("Assignment: right hand side and left hand side have different types!");
    }
}
