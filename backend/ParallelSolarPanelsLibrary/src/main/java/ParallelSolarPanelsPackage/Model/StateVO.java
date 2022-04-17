package ParallelSolarPanelsPackage.Model;

public class StateVO {
    private String id;
    private String panelId;
//    private double power;
    private int azimuth;
    private int altitude;
    private double azPlus;
    private double azMinus;
    private double altPlus;
    private double altMinus;

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

//    public double getPower() {
//        return power;
//    }
//
//    public void setPower(double power) {
//        this.power = power;
//    }

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
