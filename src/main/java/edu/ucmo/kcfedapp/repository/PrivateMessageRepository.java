package edu.ucmo.kcfedapp.repository;

import edu.ucmo.kcfedapp.model.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
}
