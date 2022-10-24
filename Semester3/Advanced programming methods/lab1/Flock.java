package Model;
import  Model.Animals;

public class Flock implements Animals{
    private final int flockId;
    private final int flockWeight;
    public Flock()
    {
        flockId = 0;
        flockWeight = 0;
    }

    public Flock(int fId, int fWeight)
    {
        flockId = fId;
        flockWeight = fWeight;
    }

    public int getWeight()
    {
        return flockWeight;
    }

    public int getId()
    {
        return flockId;
    }

    public String toString()
    {
        return String.format("%d : the flock's id and %d : the flock's weight.", flockId, flockWeight);
    }

    public boolean equals(Animals a)
    {
        String flockIdS = String.format("%d", flockId);
        String aIdS = String.format("%d", a.getId());
        return flockIdS.equals(aIdS);
    }

}
