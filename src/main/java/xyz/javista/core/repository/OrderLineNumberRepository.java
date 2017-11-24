package xyz.javista.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.javista.core.domain.OrderLineNumber;

import java.util.UUID;

public interface OrderLineNumberRepository extends JpaRepository<OrderLineNumber, UUID> {
}
