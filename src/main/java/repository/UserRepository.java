package repository;

import model.User;

public interface UserRepository extends GenericRepository<User, Long>{

    User create(String userName);
    User update(Long id, String userName);
}
