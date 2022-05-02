package ManagementService.Entities;

import ParallelSolarPanelsPackage.Model.StationDTO;

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

    public static StationE fromDTO(StationDTO stationDTO){
        StationE stationE = new StationE();
        stationE.setId(stationDTO.getId());
        stationE.setUserId(stationDTO.getUserId());
        stationE.setMaxOutputPower(stationDTO.getMaxOutputPower());
        stationE.setEnergy(stationDTO.getEnergy());
        stationE.setGridConnection(stationDTO.getGridConnection());
        stationE.setStationConnection(stationDTO.getStationConnection());
        stationE.setUkey(stationDTO.getUkey());
        stationE.setName(stationDTO.getName());
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
