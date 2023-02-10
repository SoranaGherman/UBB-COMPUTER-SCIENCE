package Model.Value;

import Model.Type.Type;
import Model.Value.Value;
import Model.Type.IntType;

public class IntValue implements Value {
    int val;

    public IntValue(int val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
    public boolean equals(Object another){
        if(another instanceof IntValue)
            return true;
        else return false;
    }
}
