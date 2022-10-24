package Repository;

import Model.Animals;

public interface IRepository {
    void add(Animals a) throws RepositoryException;
    void remove(int index) throws RepositoryException;
    int size();
    Animals[] getEntities();
}
