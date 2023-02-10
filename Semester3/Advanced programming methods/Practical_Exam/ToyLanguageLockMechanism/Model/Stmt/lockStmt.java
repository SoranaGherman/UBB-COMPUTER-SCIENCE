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

public class lockStmt implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public lockStmt(String var) {
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
                IntValue searchIndex = (IntValue) symTable.lookup(var);
                int foundIndex = searchIndex.getVal();
                if(lockTable.containsKey(foundIndex))
                {
                    if(lockTable.get(foundIndex) == -1)
                    {
                        lockTable.update(foundIndex, state.getId());
                    }
                    else state.getExeStack().push(this);
                }
                else throw new MyException("Index is not in the lock table!");
            }
            else throw new MyException("Var must be of int type!");
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
        return "lockStmt{" +
                "var='" + var + '\'' +
                '}';
    }
}
