package ControlService.Entities;

import ControlService.vo.LogVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "history_produced_logs")
public class HistoryProducedLogE {
    @Id
    private String id;

    private String date;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "panel_id")
    private String panelId;

    private double produced;

    public static HistoryProducedLogE fromVO(LogVO logVO){
        HistoryProducedLogE historyLog = new HistoryProducedLogE();
        historyLog.setId(logVO.getId());
        historyLog.setUserId(logVO.getUserId());
        historyLog.setPanelId(logVO.getPanelId());

        String dateTime = logVO.getDateTime().substring(0, 10);
        historyLog.setDate(dateTime);

        return historyLog;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
