package Model.Exp;

import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExp implements Exp{
    Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap heap) throws MyException {
        Value val = exp.eval(tbl, heap);
        if(val instanceof RefValue){
            RefValue refV = (RefValue) val;
            int address = refV.getAddress();
            if(heap.containsKey(address))
                return heap.get(address);
            else throw new MyException("Location does not exist!");
        }else throw new MyException("expr must be RefValue!");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if(typ instanceof RefType){
            RefType reft = (RefType) typ;
            return reft.getInner();
        }
        else throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "ReadHeapExp{" +
                "exp=" + exp +
                '}';
    }
}
