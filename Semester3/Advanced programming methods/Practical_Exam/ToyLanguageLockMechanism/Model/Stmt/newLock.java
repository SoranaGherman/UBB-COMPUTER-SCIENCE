package Model.Stmt;


import Model.Adt.ILockTable;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLock implements IStmt {

    private String str;
    private static Lock lock = new ReentrantLock();

    public newLock(String str) {
        this.str = str;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        ILockTable lockTable = state.getLockTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();

        int freeAddress = lockTable.getFreeValue();
        lockTable.put(freeAddress, -1);
        if(symTable.isDefined(str) && symTable.lookup(str).getType().equals(new IntType()))
            symTable.update(str, new IntValue(freeAddress));
        else
            throw new MyException("Variable not declared!");

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(str).equals(new IntType())) return typeEnv;
        else throw new MyException("Var must be of type int!");
    }

    @Override
    public String toString() {
        return "newLock{" +  str + '}';
    }
}
