package PowerPlantPackage.Model;

public class Coordinates {
    private double azimuth;
    private double altitude;

    public Coordinates(double azdeg, double azmin, double azsec, double altdeg, double altmin, double altsec){
        this.azimuth = azdeg + (azmin * 60 + azsec) / 3600;
        this.altitude = altdeg + (altmin * 60 + altsec) / 3600;;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
