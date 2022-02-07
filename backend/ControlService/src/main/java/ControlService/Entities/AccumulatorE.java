package ControlService.Entities;

import ControlService.vo.AccumulatorVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "accumulators")
public class AccumulatorE {
    @Id
    private String id;

    @Column(name = "max_power")
    private int maxPower;

    private double energy;

    @Column(name = "grid_connection")
    private int gridConnection;

    @Column(name = "station_connection")
    private int stationConnection;

    public static AccumulatorE fromVO(AccumulatorVO accumulatorVO){
        AccumulatorE accumulator = new AccumulatorE();
        accumulator.setId(accumulatorVO.getId());
        accumulator.setMaxPower(accumulatorVO.getMaxPower());
        accumulator.setEnergy(accumulatorVO.getEnergy());
        accumulator.setGridConnection(accumulatorVO.getGridConnection());
        accumulator.setStationConnection(accumulatorVO.getStationConnection());
        return accumulator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
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
}
