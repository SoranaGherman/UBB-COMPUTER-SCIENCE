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

public class unlockStmt implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public unlockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        ILockTable lockTable = state.getLockTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();

        if(symTable.isDefined(var))
        {
            if(symTable.lookup(var).getType().equals(new IntType()))
            {
                IntValue index = (IntValue) symTable.lookup(var);
                int foundIndex = index.getVal();
                if(lockTable.containsKey(foundIndex))
                {
                    if(lockTable.get(foundIndex) == state.getId())
                        lockTable.update(foundIndex, -1);
                }
                else
                    throw new MyException("Index not in lock table!");
            }
            else throw new MyException("Var is not int type!");
        }
        else throw new MyException("Var is not defined!");

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType())) return typeEnv;
        else throw new MyException("Var type must be int!");
    }

    @Override
    public String toString() {
        return "unlockStmt{" +
                "var='" + var + '\'' +
                '}';
    }
}
