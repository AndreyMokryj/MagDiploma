package vo;

import java.util.Map;

public class StationVO {
    private String id;
    private String userId;
    private int maxOutputPower;
    private double energy;
    private int gridConnection;
    private int stationConnection;
    private String ukey;
    private String name;

    public static StationVO fromMap(Map map){
        StationVO station = new StationVO();
        station.setId((String) map.get("id"));
        station.setUserId((String) map.get("userId"));
        station.setMaxOutputPower((int) map.get("maxOutputPower"));
        station.setEnergy((double) map.get("energy"));
        station.setGridConnection((int) map.get("gridConnection"));
        station.setStationConnection((int) map.get("stationConnection"));
        station.setUkey((String) map.get("ukey"));
        station.setName((String) map.get("name"));
        return station;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getMaxOutputPower() {
        return maxOutputPower;
    }

    public void setMaxOutputPower(int maxOutputPower) {
        this.maxOutputPower = maxOutputPower;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public int getGridConnection() {
        return gridConnection;
    }

    public void setGridConnection(int gridConnection) {
        this.gridConnection = gridConnection;
    }

    public int getStationConnection() {
        return stationConnection;
    }

    public void setStationConnection(int stationConnection) {
        this.stationConnection = stationConnection;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
