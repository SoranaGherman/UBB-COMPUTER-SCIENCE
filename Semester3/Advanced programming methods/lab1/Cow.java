package Model;
import  Model.Animals;

public class Cow implements Animals{
    private final int cowId;
    private final int cowWeight;
    public Cow()
    {
        cowId = 0;
        cowWeight = 0;
    }
    public Cow(int cId, int cWeight)
    {
        cowId = cId;
        cowWeight = cWeight;
    }

    public int getWeight()
    {
        return cowWeight;
    }

    public int getId()
    {
        return cowId;
    }

    public String toString()
    {
        return String.format("%d : the cow's id and %d : the cow's weight.", cowId, cowWeight);
    }

    public boolean equals(Animals a)
    {
        String cowIdS = String.format("%d", cowId);
        String aIdS = String.format("%d", a.getId());
        return cowIdS.equals(aIdS);
    }

}
