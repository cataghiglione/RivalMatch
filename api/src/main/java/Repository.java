import repository.UserRepository;

import javax.persistence.EntityManager;

public class Repository {

    private final UserRepository userRepository;

    public Repository(EntityManager entityManager) {
        this.userRepository = new UserRepository(entityManager);
    }

    public static Repository create(EntityManager entityManager) {
        return new Repository(entityManager);
    }

    public UserRepository users() {
        return userRepository;
    }

}
