package Model.Stmt;

import Model.Adt.IHeap;
import Model.Adt.ILatchTable;
import Model.Adt.MyDictionary;
import Model.Adt.MyIDictionary;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatch implements IStmt{
    private String var;
    private Exp exp;

    private static Lock lock = new ReentrantLock();

    public NewLatch(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "NewLatch{" +
                "var='" + var + '\'' +
                ", exp=" + exp +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();

        IntValue no = (IntValue) exp.eval(symTable, heap);

        int number = no.getVal();

        int freeAddress = latchTable.getFreeValue();

        latchTable.put(freeAddress, number);

        if(symTable.isDefined(var))
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new MyException("Var is not defined!");

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType())) {
            if (exp.typecheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else throw new MyException("Exp must be int");
        }
        else
            throw new MyException("Var must be int");
    }
}
