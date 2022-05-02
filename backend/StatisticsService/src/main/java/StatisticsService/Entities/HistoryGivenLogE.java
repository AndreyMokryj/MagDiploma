package StatisticsService.Entities;

import ParallelSolarPanelsPackage.Model.LogDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "history_given_logs")
public class HistoryGivenLogE {
    @Id
    private String id;

    private String date;

    @Column(name = "station_id")
    private String stationId;

    private double given;

    public static HistoryGivenLogE fromDTO(LogDTO logDTO){
        HistoryGivenLogE historyLog = new HistoryGivenLogE();
        historyLog.setId(logDTO.getId());
        historyLog.setStationId(logDTO.getStationId());

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

    public double getGiven() {
        return given;
    }

    public void setGiven(double given) {
        this.given = given;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
