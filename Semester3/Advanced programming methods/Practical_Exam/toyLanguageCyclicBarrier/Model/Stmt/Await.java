package Model.Stmt;

import Model.Adt.IBarrierTable;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Await implements IStmt{
    private String variable;
    Lock lock = new ReentrantLock();

    public Await(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "Await{" +
                "variable='" + variable + '\'' +
                '}';
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        MyIDictionary<String, Value> symTable = state.getSymTable();
        IBarrierTable barrierTable = state.getBarrierTable();
        if(symTable.isDefined(variable))
        {
            IntValue v = (IntValue) symTable.lookup(variable);
            int foundIndex = v.getVal();
            if(barrierTable.containsKey(foundIndex)){
                Pair<Integer, List<Integer>> foundBarrier = barrierTable.get(foundIndex);
                int NL = foundBarrier.getValue().size();
                int N1 = foundBarrier.getKey();
                ArrayList<Integer> myList = (ArrayList<Integer>) foundBarrier.getValue();
                if(N1 > NL)
                    if(myList.contains(state.getId()))
                    {
                        state.getExeStack().push(this);
                    }
                else{
                        myList.add(state.getId());
                        barrierTable.update(foundIndex, new Pair<>(N1, myList));
                    }
            }
            else throw new MyException("Index not in barrier table!");
        }
        else throw new MyException("Var is not defined!");

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(variable).equals(new IntType()))
            return typeEnv;
        else throw new MyException("Var is not int!");
    }
}
