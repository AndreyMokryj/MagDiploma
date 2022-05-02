package StatisticsService.Entities;

import ParallelSolarPanelsPackage.Model.LogDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "history_produced_logs")
public class HistoryProducedLogE {
    @Id
    private String id;

    private String date;

    @Column(name = "station_id")
    private String stationId;

    @Column(name = "panel_id")
    private String panelId;

    private double produced;

    public static HistoryProducedLogE fromDTO(LogDTO logDTO){
        HistoryProducedLogE historyLog = new HistoryProducedLogE();
        historyLog.setId(logDTO.getId());
        historyLog.setStationId(logDTO.getStationId());
        historyLog.setPanelId(logDTO.getPanelId());

        String dateTime = logDTO.getDateTime().substring(0, 10);
        historyLog.setDate(dateTime);

        return historyLog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String userId) {
        this.stationId = userId;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public double getProduced() {
        return produced;
    }

    public void setProduced(double produced) {
        this.produced = produced;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
