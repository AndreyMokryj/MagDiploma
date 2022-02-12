package ConnectionService.Repositories;

import ConnectionService.Entities.StationE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface StationRepository extends CrudRepository<StationE, String> {
    @Query("SELECT s FROM stations s where s.userId = :uid")
    @Transactional
    public Iterable<StationE> findByUserId(String uid);
}
