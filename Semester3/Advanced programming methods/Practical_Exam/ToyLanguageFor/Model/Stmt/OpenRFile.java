package Model.Stmt;

import Model.Adt.IFileTable;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Exp.Exp;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Model.Adt.IHeap;

public class OpenRFile implements IStmt{
    private Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IFileTable<String, BufferedReader> fileTbl = state.getFileTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        Value val = exp.eval(symTable, heap);
        if (val.getType().equals(new StringType()))
        {
            StringValue strValue = (StringValue) val;
            String file = strValue.getVal();
            if (fileTbl.isDefined(file)){
                throw  new MyException("File already exists!");
            }

            try{
                BufferedReader r = new BufferedReader(new FileReader(file));
                fileTbl.add(file, r);
            }
            catch (IOException e){
                throw new MyException(e.getMessage());
            }
        } else throw new MyException("Invalid type");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if (type.equals(new StringType()))
            return typeEnv;
        throw new MyException("Expression in statement '%s' is not a string!");
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "OpenRFile{" +
                "exp=" + exp +
                '}';
    }
}
