package repository;


import model.RegistrationUserForm;
import model.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class Users {

    private final EntityManager entityManager;

    public Users(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public boolean exists(String email) {
        return findByEmail(email).isPresent();
    }
    public User createUser(RegistrationUserForm signUpValues) {
        final User newUser = User.create(signUpValues.getEmail(), signUpValues.getPassword(),signUpValues.getFirstName(),signUpValues.getLastName());

        if (exists(newUser.getEmail())) throw new IllegalStateException("User already exists.");

        entityManager.persist(newUser);

        return newUser;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public Optional<User> findByEmail(String email) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.email LIKE :email", User.class)
                .setParameter("email", email).getResultList()
                .stream()
                .findFirst();
    }
    public List<User> list() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    public List<User> listAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User persist(User user) {
        entityManager.persist(user);
        return user;
    }
}
