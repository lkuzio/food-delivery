package xyz.javista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.javista.core.domain.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}