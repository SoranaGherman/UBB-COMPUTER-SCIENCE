package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Type.BoolType;
public class BoolValue implements Value {
    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    private boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public void setVal(boolean val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    public boolean equals(Object another){
        if(another instanceof BoolValue)
            return true;
        else return false;
    }
}
