package ParallelSolarPanelsPackage.Model;

import java.util.Map;

public class PanelDTO {
    private String id;
    private String name;
    private String model;
    private int nominalPower;
    private String stationId;
    private int azimuth;
    private int altitude;
    private int connected;

    public static PanelDTO fromMap(Map map){
        PanelDTO panelDTO = new PanelDTO();
        panelDTO.setId((String) map.get("id"));
        panelDTO.setName((String) map.get("name"));
        panelDTO.setModel((String) map.get("model"));
        panelDTO.setNominalPower((int) map.get("nominalPower"));
        panelDTO.setStationId((String) map.get("stationId"));
        panelDTO.setAzimuth((int) map.get("azimuth"));
        panelDTO.setAltitude((int) map.get("altitude"));
        panelDTO.setConnected((int) map.get("connected"));
        return panelDTO;
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
