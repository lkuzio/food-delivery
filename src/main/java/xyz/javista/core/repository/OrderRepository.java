package xyz.javista.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.javista.core.domain.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor {

}
