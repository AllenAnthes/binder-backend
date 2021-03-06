package edu.ucmo.kcfedapp.repository;

import edu.ucmo.kcfedapp.model.Business;
import edu.ucmo.kcfedapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {

    Optional<Business> findByUser(User user);

}
