package kz.monetka.server.repository;

import kz.monetka.server.entities.docs.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByPayerId(String payerId);

    Payment findByIdAndPayerId(String id, String payerId);
}
