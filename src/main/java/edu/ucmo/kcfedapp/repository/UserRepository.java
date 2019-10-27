package edu.ucmo.kcfedapp.repository;

import edu.ucmo.kcfedapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllBySkillsets(String skillset);

    Optional<User> findByVerifiedUser(String verifiedUser);
}
