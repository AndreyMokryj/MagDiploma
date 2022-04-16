package ManagementService.Repositories;

import ManagementService.Entities.StationE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StationRepository extends CrudRepository<StationE, String> {
    @Query("SELECT s FROM stations s where s.userId = :uid")
    @Transactional
    Iterable<StationE> findByUserId(String uid);

    @Query("SELECT s FROM stations s where s.userId = :uid and s.ukey = :ukey")
    @Transactional
    Optional<StationE> findByUkey(String uid, String ukey);
}
