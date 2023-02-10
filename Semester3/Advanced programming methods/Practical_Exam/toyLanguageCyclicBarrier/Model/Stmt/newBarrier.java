package Model.Stmt;

import Model.Adt.*;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newBarrier implements IStmt{
    private String var;
    private Exp exp1;

    private Lock lock = new ReentrantLock();

    public newBarrier(String var, Exp exp1) {
        this.var = var;
        this.exp1 = exp1;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public Exp getExp1() {
        return exp1;
    }

    public void setExp1(Exp exp1) {
        this.exp1 = exp1;
    }

    @Override
    public String toString() {
        return "newBarrier{" +
                "var='" + var + '\'' +
                ", exp1=" + exp1 +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IBarrierTable barrierTable = state.getBarrierTable();

        IntValue number = (IntValue) exp1.eval(symTable, heap);
        int no = number.getVal();

        int freeAddress = barrierTable.getFreeAddress();
        barrierTable.put(freeAddress, new Pair<>(no, new ArrayList<>()));

        if(symTable.isDefined(var))
        {
            symTable.update(var, new IntValue(freeAddress));
        }
        else throw new MyException("Var is not defined in the symTable!");

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        if(typeEnv.lookup(var).equals(new IntType()))
            if(exp1.typecheck(typeEnv).equals((new IntType())))
                return typeEnv;
            else throw new MyException("Expression must be int!");
        else
            throw new MyException("Var must be int!");
    }
}
