package ControlService.Repositories;

import ControlService.Entities.PanelE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PanelRepository extends CrudRepository<PanelE, String> {
    @Query("SELECT p FROM panels p where p.stationId = :stationId")
    @Transactional
    public Iterable<PanelE> findByStationId(String stationId);
}
