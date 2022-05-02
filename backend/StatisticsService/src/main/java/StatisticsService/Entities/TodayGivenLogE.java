package StatisticsService.Entities;

import ParallelSolarPanelsPackage.Model.LogDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "today_given_logs")
public class TodayGivenLogE {
    @Id
    private String id;

    private String time;

    @Column(name = "station_id")
    private String stationId;

    private double given;

    public static TodayGivenLogE fromDTO(LogDTO logDTO){
        TodayGivenLogE todayLog = new TodayGivenLogE();
        todayLog.setId(logDTO.getId());
        todayLog.setStationId(logDTO.getStationId());

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

    public double getGiven() {
        return given;
    }

    public void setGiven(double given) {
        this.given = given;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
