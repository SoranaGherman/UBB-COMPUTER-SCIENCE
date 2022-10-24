package Model;
import Model.Animals;

public class Pig implements Animals{
    private final int pigId;
    private final int pigWeight;
    public Pig()
    {
        pigId = 0;
        pigWeight = 0;
    }

    public Pig(int pId, int pWeight)
    {
        pigId = pId;
        pigWeight = pWeight;
    }

    public int getWeight()
    {
        return pigWeight;
    }

    public int getId()
    {
        return pigId;
    }

    public String toString()
    {
        return String.format("%d : the pig's id and %d : the pig's weight.", pigId, pigWeight);
    }

    public boolean equals(Animals a)
    {
        String pigIdS = String.format("%d", pigId);
        String aIdS = String.format("%d", a.getId());
        return pigIdS.equals(aIdS);
    }
}
