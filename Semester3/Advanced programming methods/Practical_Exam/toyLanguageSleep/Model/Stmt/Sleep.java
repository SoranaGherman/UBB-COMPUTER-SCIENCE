package Model.Stmt;

import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Adt.MyStack;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public class Sleep implements IStmt{
    int number;

    public Sleep(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIStack<IStmt> stk = state.getExeStack();
        if (number != 0) {
            stk.push(new Sleep(number - 1));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        return typeEnv;
    }

    @Override
    public String toString() {
        return "sleep(" +  number + ')';
    }
}
