package Model.Stmt;
import Model.Adt.MyIDictionary;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.RefType;
import Model.Value.RefValue;
import Model.Value.Value;
import Model.Adt.IHeap;
import Model.Type.Type;

public class NewStmt implements IStmt{
    private String var_name;
    private Exp exp;

    public NewStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "new(" + var_name + "," +  exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable().peek();
        IHeap heap = state.getHeap();

        Value val = symtbl.lookup(var_name);
        if (val == null) throw new MyException("Variable was not defined!");
        if (!(val instanceof RefValue)) throw new MyException("Variable must be a ref type!");

        Value expr = exp.eval(symtbl, heap);
        Type locationType = ((RefType) val.getType()).getInner();
        if (!(expr.getType().equals(locationType))) throw new MyException("Ref loc type does not match expression");

        int heapAddress = heap.getFreeAddress();
        heap.put(heapAddress, expr);
        RefValue ref = (RefValue) val;
        RefValue newRef = new RefValue(heapAddress, ((RefType) ref.getType()).getInner());
        symtbl.update(var_name, newRef);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("New stmt: right hand side and left hand side have different types!");
    }
}
