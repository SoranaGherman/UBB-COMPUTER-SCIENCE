package Model.Stmt;
import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStmt implements IStmt {
    private IStmt stmt;
    private Exp exp;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        Value val = exp.eval(symTable, heap);
        if(val.getType() instanceof BoolType){
            BoolValue v = (BoolValue) val;
            if(v.getVal()){
                stk.push(this);
                stk.push(stmt);
            }

        }else throw  new MyException("Invalid type!");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type exp_type = exp.typecheck(typeEnv);
        if (!exp_type.equals(new BoolType()))
            throw new MyException("The while condition from the statement '%s' must have boolean type!");
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "(while(" +
                 exp.toString() + ")" + stmt.toString() + ")";
    }

    public IStmt getStmt() {
        return stmt;
    }

    public void setStmt(IStmt stmt) {
        this.stmt = stmt;
    }
}
