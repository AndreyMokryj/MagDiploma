package ParallelSolarPanelsPackage.Repositories;

import ParallelSolarPanelsPackage.Entities.StateE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface IStateRepository extends CrudRepository<StateE, String> {
    @Query("SELECT ps FROM panel_states ps where ps.panelId = :panelId")
    @Transactional
    public Iterable<StateE> findByPanelId(String panelId);
}
