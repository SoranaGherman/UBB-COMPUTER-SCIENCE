package Model.Value;

import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{
    private String val;

    public StringValue(String val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

    public boolean equals(Object another){
        if(another instanceof StringValue)
            return true;
        else return false;
    }
}
