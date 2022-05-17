package ParallelSolarPanelsPackage;

import ParallelSolarPanelsPackage.Model.*;
import ParallelSolarPanelsPackage.Utils.StateUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WorkProcess {
    private static WorkProcess workProcess;

    private WorkProcess(){
        panels = new ArrayList<PanelDTO>();
        station = null;
        index = 25;
    }

    public static WorkProcess getInstance(){
        if(workProcess == null){
            workProcess = new WorkProcess();
        }
        return workProcess;
    }

    //    public final String baseUrl = "http://control-service/";
    public final String managementUrl = "http://management-service/";
    public final String statisticsUrl = "http://statistics-service/";
    public final String sunUrl = "http://sun-service/sun/power-coef/";
    public final String dateTimeUrl = "http://sun-service/sun/datetime/";

    public List<PanelDTO> panels;
    private RestTemplate restTemplate;
    private StateUtils stateUtils;
    public StationDTO station;

    private int index;

    private double mediumIterations = 0;
    private double mediumTime = 0;

    public void execute(){
        Date startTime = new Date();
        if(!(station == null)) {
            if(station.getStationConnection() == 1) {
                List<Thread> threads = new ArrayList<>();
                for (PanelDTO panel : panels) {
                    Thread thread = new Thread(() -> doTaskForPanel(panel));
                    thread.start();
                    threads.add(thread);
                    if (threads.size() >= panels.size()) {
                        waitForThreads(threads);
                    }
                }
            } else {
                System.out.println("Station is disconnected");
            }
            updateGivenLogs();
            index += 1;

            Date endTime = new Date();
            processTime(endTime.getTime() - startTime.getTime());

            System.out.println("Task executed on " + new Date());
        }
    }

    private void processTime(long time) {
        System.out.println("Time to correct all panels: " + time);

        int _index = index % 144;
        if (_index == 123) {
            int _day = index / 144;
            System.out.println("-------Day " + _day + ":  medium time: " + (mediumTime / 84.0) + "------------");

            mediumTime = 0;
        }

        if(_index >= 36 && _index < 120) {
            mediumTime += time;
        }
    }

    public void doTaskForPanel(PanelDTO panel){
        if (panel.getConnected() == 1) {
            double power = getPanelPower(panel);
            if (power >= 10) {
                preparePanel(panel);
                findSun(panel);
                updateProducedLogs(panel);
            } else {
                turnPanelEast(panel);
                System.out.println("No sun found");
            }
        } else {
            System.out.println("Panel " + panel.getName() + " is disconnected");
        }
    }

    private void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
    }

    private void turnPanelEast(PanelDTO panel) {
        if(panel.getAzimuth() != 90 && panel.getAltitude() != 10){
            panel.setAzimuth(90);
            panel.setAltitude(10);
            reduceForPanel(panel.getId());
            updatePanel(panel);
        }
    }

    public void findSun(PanelDTO panel) {
        double azPlus = 0;
        double azMinus = 0;
        double altPlus = 0;
        double altMinus = 0;

        int iterator = 0;
        while (azPlus >= 0 || azMinus >= 0 || altPlus >= 0 || altMinus >= 0) {
            double prevPower = getPanelPower(panel);
            Random random = new Random();

            StateDTO prevState = getState(panel);
            azPlus = prevState.getAzPlus();
            azMinus = prevState.getAzMinus();
            altPlus = prevState.getAltPlus();
            altMinus = prevState.getAltMinus();

            int code = 0;

            int randomInt = random.nextInt(20);
            if (randomInt == 5) {
                code = random.nextInt(4);
            } else {
                if (azPlus >= azMinus && azPlus >= altPlus && azPlus >= altMinus) {
                    code = 2;
                }
                if (azMinus >= azPlus && azMinus >= altPlus && azMinus >= altMinus) {
                    code = 3;
                }
                if (altPlus >= azMinus && altPlus >= azPlus && altPlus >= altMinus) {
                    code = 0;
                }
                if (altMinus >= azMinus && altMinus >= azPlus && altMinus >= altPlus) {
                    code = 1;
                }
            }

            PreviousDTO previousDTO = new PreviousDTO();

            switch (code) {
                case 0:
                    if (panel.getAltitude() < 90) {
                        panel.setAltitude(panel.getAltitude() + 1);
                        break;
                    } else {
                        previousDTO.setAltPlus(-1);
                        code++;
                    }

                case 1:
                    if (panel.getAltitude() > 5) {
                        panel.setAltitude(panel.getAltitude() - 1);
                        break;
                    } else {
                        previousDTO.setAltMinus(-1);
                        code++;
                    }

                case 2:
                    if (panel.getAzimuth() < 359) {
                        panel.setAzimuth(panel.getAzimuth() + 1);
                    } else {
                        panel.setAzimuth(0);
                    }
                    break;
                case 3:
                    if (panel.getAzimuth() > 0) {
                        panel.setAzimuth(panel.getAzimuth() - 1);
                    } else {
                        panel.setAzimuth(359);
                    }
                    break;
            }

            StateDTO newState = getState(panel);
            double newPower = getPanelPower(panel);

            double diff = newPower - prevPower;

            PreviousDTO currentDTO = new PreviousDTO();
            previousDTO.setId(prevState.getId());
            currentDTO.setId(newState.getId());

            double k = 10;
            switch (code) {
                case 0:
                    previousDTO.setAltPlus(diff);
                    currentDTO.setAltPlus(diff / k);

                    previousDTO.setAltMinus(-diff / k);
                    currentDTO.setAltMinus(-diff / k / k);
                    break;
                case 1:
                    previousDTO.setAltMinus(diff);
                    currentDTO.setAltMinus(diff / k);

                    previousDTO.setAltPlus(-diff / k);
                    currentDTO.setAltPlus(-diff / k / k);
                    break;
                case 2:
                    previousDTO.setAzPlus(diff);
                    currentDTO.setAzPlus(diff / k);

                    previousDTO.setAzMinus(-diff / k);
                    currentDTO.setAzMinus(-diff / k / k);
                    break;
                case 3:
                    previousDTO.setAzMinus(diff);
                    currentDTO.setAzMinus(diff / k);

                    previousDTO.setAzPlus(-diff / k);
                    currentDTO.setAzPlus(-diff / k / k);
                    break;
            }

            sendUpdate(previousDTO);
            sendUpdate(currentDTO);
//            updatePanel(panel);

            iterator++;
        }

        updatePanel(panel);
        printPanelInfo(panel, iterator);
    }

    public double getTotalPower(){
        if(station.getStationConnection() == 0) {
            return 0;
        }

        double power = 0;
        for (PanelDTO panel : panels){
            power += getPanelPower(panel);
        }
        return power;
    }

    public double getPanelPower(PanelDTO panel){
        if (panel.getConnected() == 0 || station.getStationConnection() == 0){
            return 0;
        }
        double coef = 0;
        try {
            coef = restTemplate.postForObject(sunUrl + index, new CoordinatesDTO(panel.getAzimuth(), 0,0,panel.getAltitude(),0,0), Double.class);
        }
        catch (Exception e){
            coef = restTemplate.postForObject(sunUrl + "0", new CoordinatesDTO(panel.getAzimuth(), 0,0,panel.getAltitude(),0,0), Double.class);
            index = 0;
        }
        return coef > 0 ? coef * panel.getNominalPower() : 0;
    }

    public StateDTO getState(PanelDTO panel){
        StateDTO stateDTOSent = new StateDTO();
        stateDTOSent.setPanelId(panel.getId());
        stateDTOSent.setAzimuth(panel.getAzimuth());
        stateDTOSent.setAltitude(panel.getAltitude());

        return (stateUtils.fetchState(stateDTOSent)).toDTO();
    }

    public void sendUpdate(PreviousDTO previousDTO){
        stateUtils.updatePrevState(previousDTO);
    }

    public void updatePanel(PanelDTO panelDTO){
        try {
            Void response = restTemplate.postForObject(managementUrl + "panels/" + panelDTO.getId(), panelDTO, void.class);
        } catch (Exception e) {
            System.out.println("--------WARNING: Could not update panel information----------");
        }
    }

    public void preparePanel(PanelDTO panelDTO){
        stateUtils.preparePanel(panelDTO);
    }

    public void reduceForPanel(String panelId){
        stateUtils.reduceByPanelId(panelId);
    }

    public void updateProducedLogs(PanelDTO panelDTO){
        LogDTO logDTO = new LogDTO();
        logDTO.setStationId(station.getId());
        logDTO.setPanelId(panelDTO.getId());
        String dateTime = restTemplate.exchange(dateTimeUrl + index, HttpMethod.GET, null, String.class).getBody();
        logDTO.setDateTime(dateTime);
        logDTO.setProduced(getPanelPower(panelDTO) * 60 * 10);

        station.setEnergy(station.getEnergy() + logDTO.getProduced());
        updateStation(station);
        updateLog(logDTO);
    }

    public void updateGivenLogs(){
        LogDTO logDTO = new LogDTO();
        logDTO.setStationId(station.getId());
        String dateTime = restTemplate.exchange(dateTimeUrl + index, HttpMethod.GET, null, String.class).getBody();
        logDTO.setDateTime(dateTime);

        if (station.getGridConnection() == 1){
            double maxEnergyGiven = station.getMaxOutputPower() * 60 * 10;
            double additionalEnergy = Math.min(station.getEnergy(), maxEnergyGiven);
            logDTO.setGiven(additionalEnergy);
            station.setEnergy(station.getEnergy() - additionalEnergy);
        }
        else {
            logDTO.setGiven(0);
        }

        updateStation(station);
        updateLog(logDTO);
    }

    private void updateStation(StationDTO stationDTO) {
        try {
            Void response = restTemplate.postForObject(managementUrl + "stations/" + station.getId(), stationDTO, void.class);
        } catch (Exception e) {
            System.out.println("--------WARNING: Could not update station information----------");
        }
    }

    private void updateLog(LogDTO logDTO){
        try {
            restTemplate.postForObject(statisticsUrl + "logs/update/", logDTO, void.class);
        } catch (Exception e) {
            System.out.println("--------WARNING: Could not update statistics----------");
        }
    }

    private void printPanelInfo(PanelDTO panel, int iterator){
        System.out.println("Panel " + panel.getName() + ": final azimuth: " + panel.getAzimuth() + "; altitude: " + panel.getAltitude() + "; index: " + index + "; power: " + getPanelPower(panel));
        System.out.println("Iterations: " + iterator);

        int _index = index % 144;
        if (_index == 120) {
            int _day = index / 144;
            System.out.println("-------Day " + _day + ":  medium iterations: " + (mediumIterations / panels.size() / 84.0) + "------------");

            mediumIterations = 0;
        }

        if(_index >= 36 && _index < 120) {
            mediumIterations += iterator;
        }
    }

    public String getDateTime() {
        String dateTime = restTemplate.exchange(dateTimeUrl + index, HttpMethod.GET, null, String.class).getBody();
        return dateTime;
    }

    public RestTemplate getRestTemplate(){
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StateUtils getStateUtils() {
        return stateUtils;
    }

    public void setStateUtils(StateUtils stateUtils) {
        this.stateUtils = stateUtils;
    }
}
