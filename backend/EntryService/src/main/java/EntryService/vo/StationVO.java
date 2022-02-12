package EntryService.vo;

public class StationVO {
    private String id;
    private String userId;
    private int maxOutputPower;
    private double energy;
    private int gridConnection;
    private int stationConnection;

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
}
