package StatisticsService.Entities;

import vo.LogVO;

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

    public static TodayProducedLogE fromVO(LogVO logVO){
        TodayProducedLogE todayLog = new TodayProducedLogE();
        todayLog.setId(logVO.getId());
        todayLog.setStationId(logVO.getStationId());
        todayLog.setPanelId(logVO.getPanelId());

        String time = logVO.getDateTime().substring(11, 13) + ":00:00";
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
