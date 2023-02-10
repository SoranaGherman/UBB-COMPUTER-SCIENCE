package Model.Stmt;

import Model.Adt.IHeap;
import Model.Adt.ILatchTable;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Await implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public Await(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "await(" +  var +  '\'' +
                ')';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        MyIDictionary<String, Value> symTable = state.getSymTable();
        ILatchTable latchTable = state.getLatchTable();

        if(symTable.isDefined(var))
        {
            IntValue searchIndex = (IntValue) symTable.lookup(var);
            int foundIndex = searchIndex.getVal();

            if(latchTable.containsKey(foundIndex))
            {
                if(latchTable.get(foundIndex) != 0)
                    state.getExeStack().push(this);
            }
            else throw new MyException("Index not found in latch table!");

        }
        else throw  new MyException("Var is not defined");

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType())) return typeEnv;
        else throw  new MyException("Var must be int");
    }
}
