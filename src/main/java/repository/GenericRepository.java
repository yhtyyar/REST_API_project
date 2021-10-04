package repository;

import java.util.List;

public interface GenericRepository <T,ID> {

    String defaultUser = "USER № ";

    T getById (ID id);
    void deleteById (ID id);
    List<T> getAll();
}
