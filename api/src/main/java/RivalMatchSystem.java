import model.RegistrationUserForm;
import model.User;
import repository.UserRepository;
import systems.MySystemRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RivalMatchSystem {

    private final EntityManagerFactory factory;

    private RivalMatchSystem(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public static RivalMatchSystem create(String persistenceUnitName) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        return new RivalMatchSystem(factory);
    }

    public Optional<User> registerUser(RegistrationUserForm form) {
        return runInTransaction(datasource -> {
            final UserRepository userRepository = datasource.users();
            return userRepository.exists(form.getEmail(),form.getUsername()) ? Optional.empty() : Optional.of(userRepository.createUser(form));
        });
    }


    public Optional<User> findUserByEmail(String email) {
        return runInTransaction(
                ds -> ds.users().findByEmail(email)
        );
    }

    public List<User> listUsers() {
        return runInTransaction(
                ds -> ds.users().list()
        );
    }


    private <E> E runInTransaction(Function<Repository, E> closure) {
        final EntityManager entityManager = factory.createEntityManager();
        final Repository ds = Repository.create(entityManager);

        try {
            entityManager.getTransaction().begin();
            final E result = closure.apply(ds);
            entityManager.getTransaction().commit();
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public boolean validPassword(String password, User foundUser) {
        // Super dummy implementation. Zero security
        return foundUser.getPassword().equals(password);
    }
}
