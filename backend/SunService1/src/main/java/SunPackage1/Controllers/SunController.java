package SunPackage1.Controllers;

import SunPackage1.Coordinates.Coordinates;
import SunPackage1.Coordinates.SunCoordinatesList;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/sun")
@Component
public class SunController {
    @CrossOrigin(origins = "*")
    @GetMapping(path="/{index}")
    public @ResponseBody
    Coordinates getCoords(@PathVariable int index) {
        return SunCoordinatesList.getCoordinates(index);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/power-coef/{index}")
    public @ResponseBody
    double getPowerCoef(@RequestBody Coordinates coordinates, @PathVariable int index) {
        Coordinates sunCoordinates = SunCoordinatesList.getCoordinates(index);
        if(sunCoordinates == null){
            return -1;
        }
        double daz = Math.abs(coordinates.getAzimuth() - sunCoordinates.getAzimuth());
        double h1 = coordinates.getAltitude();
        double h2 = sunCoordinates.getAltitude();
        double coef = Math.cos(h1 * Math.PI / 180.0) * Math.cos(h2 * Math.PI / 180.0) * Math.cos(daz * Math.PI / 180.0) + Math.sin(h1 * Math.PI / 180.0) * Math.sin(h2 * Math.PI / 180.0);
        return coef;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/datetime/{index}")
    public @ResponseBody
    String getDateTime(@PathVariable int index) {
        return SunCoordinatesList.getKey(index);
    }

}
