package ManagementService.Entities;

import ParallelSolarPanelsPackage.Model.PanelVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "panels")
public class PanelE {
    @Id
    private String id;

    @Column(name = "panel_name")
    private String name;

    @Column(name = "panel_model")
    private String model;

    @Column(name = "nominal_power")
    private int nominalPower;

    @Column(name = "station_id")
    private String stationId;

    private int azimuth;
    private int altitude;
    private int connected;


    public static PanelE fromVO(PanelVO panelVO){
        PanelE panel = new PanelE();
        panel.setId(panelVO.getId());
        panel.setName(panelVO.getName());
        panel.setModel(panelVO.getModel());
        panel.setNominalPower(panelVO.getNominalPower());
        panel.setStationId(panelVO.getStationId());
        panel.setAzimuth(panelVO.getAzimuth());
        panel.setAltitude(panelVO.getAltitude());
        panel.setConnected(panelVO.getConnected());
        return panel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNominalPower() {
        return nominalPower;
    }

    public void setNominalPower(int nominalPower) {
        this.nominalPower = nominalPower;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String userId) {
        this.stationId = userId;
    }

    public int getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(int azimuth) {
        this.azimuth = azimuth;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getConnected() {
        return connected;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }
}
