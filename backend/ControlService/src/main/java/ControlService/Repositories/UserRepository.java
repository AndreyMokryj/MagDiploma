package ControlService.Repositories;

import ControlService.Entities.UserE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserE, String> {
    @Query("SELECT u FROM users u where u.username = :un")
    @Transactional
    public Optional<UserE> findByUN(String un);

    @Query("SELECT u.id FROM users u where u.stationId = :sid")
    @Transactional
    public String findIdBySID(String sid);

    @Query("SELECT u.stationId FROM users u where u.id = :id")
    @Transactional
    public String findSID(String id);
}
