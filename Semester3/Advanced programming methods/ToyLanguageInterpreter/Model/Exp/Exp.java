package Model.Exp;

import Model.Adt.IHeap;
import Model.MyException;
import Model.Adt.MyIDictionary;
import Model.Value.Value;
import Model.Type.Type;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, IHeap heap) throws MyException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
