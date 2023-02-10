package Model.Type;

import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements Type{
    private Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public boolean equals(Object another){
        if(another instanceof  RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    public Type getInner() {
        return inner;
    }

    public void setInner(Type inner) {
        this.inner = inner;
    }

    @Override
    public String toString() {
        return "RefType{" +
                "inner=" + inner +
                '}';
    }
}
