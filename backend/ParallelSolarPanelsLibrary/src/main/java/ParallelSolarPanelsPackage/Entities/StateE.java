package ParallelSolarPanelsPackage.Entities;

import ParallelSolarPanelsPackage.Model.StateDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "panel_states")
public class StateE {
    @Id
    private String id;

    @Column(name = "panel_id")
    private String panelId;
    private int azimuth;
    private int altitude;

    @Column(name = "az_plus")
    private double azPlus;

    @Column(name = "az_minus")
    private double azMinus;

    @Column(name = "alt_plus")
    private double altPlus;

    @Column(name = "alt_minus")
    private double altMinus;

    public static StateE fromDTO(StateDTO stateDTO){
        StateE direction = new StateE();
        direction.setId(stateDTO.getId());
        direction.setPanelId(stateDTO.getPanelId());
        direction.setAzimuth(stateDTO.getAzimuth());
        direction.setAltitude(stateDTO.getAltitude());
        direction.setAzPlus(stateDTO.getAzPlus());
        direction.setAzMinus(stateDTO.getAzMinus());
        direction.setAltPlus(stateDTO.getAltPlus());
        direction.setAltMinus(stateDTO.getAltMinus());
        return direction;
    }

    public StateDTO toDTO(){
        StateDTO direction = new StateDTO();
        direction.setId(this.getId());
        direction.setPanelId(this.getPanelId());
        direction.setAzimuth(this.getAzimuth());
        direction.setAltitude(this.getAltitude());
        direction.setAzPlus(this.getAzPlus());
        direction.setAzMinus(this.getAzMinus());
        direction.setAltPlus(this.getAltPlus());
        direction.setAltMinus(this.getAltMinus());
        return direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
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

    public double getAzPlus() {
        return azPlus;
    }

    public void setAzPlus(double azPlus) {
        this.azPlus = azPlus;
    }

    public double getAzMinus() {
        return azMinus;
    }

    public void setAzMinus(double azMinus) {
        this.azMinus = azMinus;
    }

    public double getAltPlus() {
        return altPlus;
    }

    public void setAltPlus(double altPlus) {
        this.altPlus = altPlus;
    }

    public double getAltMinus() {
        return altMinus;
    }

    public void setAltMinus(double altMinus) {
        this.altMinus = altMinus;
    }
}
