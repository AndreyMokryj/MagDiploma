package vo;

public class PreviousVO {
    private String id;

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
