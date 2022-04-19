package ManagementService.Entities;

import ParallelSolarPanelsPackage.Model.StationVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "stations")
public class StationE {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "max_output_power")
    private int maxOutputPower;

    private double energy;

    @Column(name = "grid_connection")
    private int gridConnection;

    @Column(name = "station_connection")
    private int stationConnection;

    private String ukey;
    private String name;

    public static StationE fromVO(StationVO stationVO){
        StationE stationE = new StationE();
        stationE.setId(stationVO.getId());
        stationE.setUserId(stationVO.getUserId());
        stationE.setMaxOutputPower(stationVO.getMaxOutputPower());
        stationE.setEnergy(stationVO.getEnergy());
        stationE.setGridConnection(stationVO.getGridConnection());
        stationE.setStationConnection(stationVO.getStationConnection());
        stationE.setUkey(stationVO.getUkey());
        stationE.setName(stationVO.getName());
        return stationE;
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

    public void setMaxOutputPower(int maxPower) {
        this.maxOutputPower = maxPower;
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

    public void setGridConnection(int gridStatus) {
        this.gridConnection = gridStatus;
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
