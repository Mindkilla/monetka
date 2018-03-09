package kz.monetka.server.repository;

import kz.monetka.server.entities.login.AuthToken;
import kz.monetka.server.entities.login.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    AuthToken findByToken(String token);

    AuthToken findByUser(User user);

    boolean existsByToken(String token);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from AuthToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
