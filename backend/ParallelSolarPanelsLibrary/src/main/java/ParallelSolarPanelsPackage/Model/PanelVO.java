package ParallelSolarPanelsPackage.Model;

import java.util.Map;

public class PanelVO {
    private String id;
    private String name;
    private String model;
    private int nominalPower;
    private String stationId;
    private int azimuth;
    private int altitude;
    private int connected;

    public static PanelVO fromMap(Map map){
        PanelVO panelVO = new PanelVO();
        panelVO.setId((String) map.get("id"));
        panelVO.setName((String) map.get("name"));
        panelVO.setModel((String) map.get("model"));
        panelVO.setNominalPower((int) map.get("nominalPower"));
        panelVO.setStationId((String) map.get("stationId"));
        panelVO.setAzimuth((int) map.get("azimuth"));
        panelVO.setAltitude((int) map.get("altitude"));
        panelVO.setConnected((int) map.get("connected"));
        return panelVO;
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

    public void setStationId(String stationId) {
        this.stationId = stationId;
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
