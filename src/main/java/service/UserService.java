package service;

import model.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepositoryImpl();
    }


    public User create(String userName){
        return userRepository.create(userName);
    }


    public User update(Long id, String userName) {
        return userRepository.update(id, userName);
    }


    public User getById(Long id) {
        return userRepository.getById(id);
    }


    public void deleteById (Long id) {
        userRepository.deleteById(id);
    }


    public List<User> getAll () {
        return userRepository.getAll();
    }

}
