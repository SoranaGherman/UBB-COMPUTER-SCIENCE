package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Exp.ValueExp;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.IntValue;

public class Wait implements IStmt{
    private int number;

    public Wait(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Wait{" +
                "number=" + number +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIStack<IStmt> stk = state.getExeStack();

        if(number != 0)
            stk.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(number))), new Wait(number - 1)));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        return typeEnv;
    }
}
