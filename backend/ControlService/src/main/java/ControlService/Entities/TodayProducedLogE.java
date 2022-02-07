package ControlService.Entities;

import ControlService.vo.LogVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "today_produced_logs")
public class TodayProducedLogE {
    @Id
    private String id;

    private String time;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "panel_id")
    private String panelId;

    private double produced;

    public static TodayProducedLogE fromVO(LogVO logVO){
        TodayProducedLogE todayLog = new TodayProducedLogE();
        todayLog.setId(logVO.getId());
        todayLog.setUserId(logVO.getUserId());
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
