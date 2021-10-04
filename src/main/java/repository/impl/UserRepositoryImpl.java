package repository.impl;

import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private Transaction transaction;


    @Override
    public User create(String userName) {

        User userResult = new User();
        Long userId;

        // нужно реализовать, если название по умолчанию то нужно добавить номер ID в конец имени чтобы было отличие
        if (userName.equals(defaultUser)) {

            try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                User user = new User();
                user.setUserName(userName);

                userId = (Long) session.save(user);

                //к названию по умолчанию добавляется ID и с этим название получается уникальным
                session.update(new User(userId, userName + userId));

                userResult = session.get(User.class, userId);

                transaction.commit();

            } catch (HibernateException e) {
                transaction.rollback();
                e.printStackTrace();
            }




        } else {
            try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                User user = new User();
                user.setUserName(userName);

                userId = (Long) session.save(user);

                userResult = session.get(User.class, userId);

                transaction.commit();

            } catch (HibernateException e) {
                transaction.rollback();
                e.printStackTrace();
            }

        }

        return userResult;
    }


    @Override
    public User update(Long id, String userName) {

        User userResult = new User();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            user.setUserName(userName);

            session.update(user);
            userResult = session.get(User.class, id);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return userResult;
    }

    @Override
    public User getById(Long id) {

        User user = new User();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            user = session.get(User.class, id);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void deleteById(Long id) {

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAll() {

        List<User> userList = new ArrayList<>();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            userList = session.createQuery("FROM User", User.class).list();

            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return userList;
    }
}
