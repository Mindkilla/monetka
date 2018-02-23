package kz.monetka.server.services;

import kz.monetka.server.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */
public interface LoginRepository extends JpaRepository<User, Long> {

    @Query("select user from User user where user.id = :#{#id}")
    User findByUUID(@Param("id") String id);

    @Query("select user from User user where user.login = :#{#login}")
    User findBylogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(user) > 0 THEN true ELSE false END FROM User user where user.login = :#{#login}")
    boolean exists(@Param("login") String login);

    @Override
    public <S extends User> S save(S s);

    @Override
    public User findOne(Long l);

    @Override
    public boolean exists(Long l);

    @Override
    public List<User> findAll();

    @Override
    public List<User> findAll(Sort sort);

    @Override
    public Page<User> findAll(Pageable pageable);

    @Override
    public List<User> findAll(Iterable<Long> iterable);

    @Override
    public long count() ;

    @Override
    public void delete(Long l);

    @Override
    public void delete(User user);

    @Override
    public void delete(Iterable<? extends User> iterable);

    @Override
    public void deleteAll();

    @Override
    public void flush();

    @Override
    public void deleteInBatch(Iterable<User> iterable) ;

    @Override
    public void deleteAllInBatch();

    @Override
    public User getOne(Long l);

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort);

    @Override
    public <S extends User> List<S> findAll(Example<S> example);

    @Override
    public <S extends User> S saveAndFlush(S s);

    @Override
    public <S extends User> List<S> save(Iterable<S> iterable);

    @Override
    public <S extends User> S findOne(Example<S> example);

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    public <S extends User> long count(Example<S> example);

    @Override
    public <S extends User> boolean exists(Example<S> example);
}
