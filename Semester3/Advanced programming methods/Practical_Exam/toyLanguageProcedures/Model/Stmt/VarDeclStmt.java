package Model.Stmt;

import Model.*;
import Model.Adt.MyIDictionary;
import Model.Adt.MyIStack;
import Model.Type.Type;
import Model.Value.Value;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable().peek();
        MyIStack<IStmt> stk = state.getExeStack();
        if(tbl.isDefined(name)) throw new MyException("variable is already declared");
        else {
            tbl.add(name, type.defaultValue());
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "VarDeclStmt{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
