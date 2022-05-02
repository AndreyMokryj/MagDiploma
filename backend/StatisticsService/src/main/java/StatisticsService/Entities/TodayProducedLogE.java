package StatisticsService.Entities;

import ParallelSolarPanelsPackage.Model.LogDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "today_produced_logs")
public class TodayProducedLogE {
    @Id
    private String id;

    private String time;

    @Column(name = "station_id")
    private String stationId;

    @Column(name = "panel_id")
    private String panelId;

    private double produced;

    public static TodayProducedLogE fromDTO(LogDTO logDTO){
        TodayProducedLogE todayLog = new TodayProducedLogE();
        todayLog.setId(logDTO.getId());
        todayLog.setStationId(logDTO.getStationId());
        todayLog.setPanelId(logDTO.getPanelId());

        String time = logDTO.getDateTime().substring(11, 13) + ":00:00";
        todayLog.setTime(time);

        return todayLog;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
