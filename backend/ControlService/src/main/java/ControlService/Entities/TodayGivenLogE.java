package ControlService.Entities;

import ControlService.vo.LogVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "today_given_logs")
public class TodayGivenLogE {
    @Id
    private String id;

    private String time;

    @Column(name = "user_id")
    private String userId;

    private double given;

    public static TodayGivenLogE fromVO(LogVO logVO){
        TodayGivenLogE todayLog = new TodayGivenLogE();
        todayLog.setId(logVO.getId());
        todayLog.setUserId(logVO.getUserId());

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
