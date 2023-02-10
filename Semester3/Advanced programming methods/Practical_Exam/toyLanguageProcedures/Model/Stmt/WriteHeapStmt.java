package Model.Stmt;

import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStmt implements IStmt{
    private String var_name;
    private Exp exp;

    public WriteHeapStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable().peek();
        IHeap heap = state.getHeap();

        Value varValue = null;
        varValue = symTable.lookup(var_name);
        if (varValue == null) throw new MyException("variable has not been declared!");
        if (!(varValue instanceof RefValue)) throw new MyException("var should be of ref type!");

        RefValue varRefValue = (RefValue) varValue;
        int heapAddress = varRefValue.getAddress();
        if(heap.get(heapAddress) == null) throw new MyException("heap address is not valid!");
        Value expValue = exp.eval(symTable, heap);

        if(! expValue.getType().equals(((RefType) varRefValue.getType()).getInner())) throw  new MyException("" +
                "location pointed by ref address does not match the type of variable");

        heap.update(heapAddress, expValue);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type exp_type = exp.typecheck(typeEnv);
        Type var_type = typeEnv.lookup(var_name);

        if (!var_type.equals(new RefType(exp_type)))
            throw new MyException("right hand side an left hand side have different types!");

        return typeEnv;
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
        return "wH(" + var_name + ", " + exp + ")";
    }
}
