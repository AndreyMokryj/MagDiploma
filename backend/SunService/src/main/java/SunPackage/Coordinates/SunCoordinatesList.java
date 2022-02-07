package SunPackage.Coordinates;

public class SunCoordinatesList {
    public static double sunDegRad = (31 * 60 + 59) / 7200;

    public static Coordinates getCoordinates(int index) {
        return DataList.coordinates[index];
    }

    public static String getKey(int index) {
        return DataList.dates[index];
    }
}
