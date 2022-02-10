package PowerPlantPackage.Workflow;

import PowerPlantPackage.Entities.StateE;
import PowerPlantPackage.Model.*;
import PowerPlantPackage.Repositories.StateRepository;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class WorkProcess {
    private static WorkProcess workProcess;

    private WorkProcess(){
        panels = new ArrayList<PanelVO>();
        station = null;
        index = 50;
    }

    public static WorkProcess getInstance(){
        if(workProcess == null){
            workProcess = new WorkProcess();
        }
        return workProcess;
    }

    public final String baseUrl = "http://control-service/";
    public final String sunUrl = "http://sun-service/sun/power-coef/";
    public final String dateTimeUrl = "http://sun-service/sun/datetime/";

    public List<PanelVO> panels;
    private RestTemplate restTemplate;
    private StateRepository stateRepository;
    public StationVO station;

    private int index;

    public void execute(){
        Date startTime = new Date();
        if(!(station == null)) {
            if(station.getStationConnection() == 1) {
                for (PanelVO panel : panels) {
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
            } else {
                System.out.println("Station is disconnected");
            }
            updateGivenLogs();
            index += 2;

            Date endTime = new Date();
            System.out.println("Time to correct all panels: " + (endTime.getTime() - startTime.getTime()));


            System.out.println("Task executed on " + new Date());
        }
    }

    private void turnPanelEast(PanelVO panel) {
        if(panel.getAzimuth() != 90 && panel.getAltitude() != 10){
            panel.setAzimuth(90);
            panel.setAltitude(10);
            reduceForPanel(panel.getId());
            updatePanel(panel);
        }
    }

    public void findSun(PanelVO panel) {
        double azPlus = 0;
        double azMinus = 0;
        double altPlus = 0;
        double altMinus = 0;

        int iterator = 0;
        while (azPlus >= 0 || azMinus >= 0 || altPlus >= 0 || altMinus >= 0) {
            double prevPower = getPanelPower(panel);
            Random random = new Random();

            StateVO prevState = getState(panel);
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

            PreviousVO previousVO = new PreviousVO();

            switch (code) {
                case 0:
                    if (panel.getAltitude() < 90) {
                        panel.setAltitude(panel.getAltitude() + 1);
                        break;
                    } else {
                        previousVO.setAltPlus(-1);
                        code++;
                    }

                case 1:
                    if (panel.getAltitude() > 5) {
                        panel.setAltitude(panel.getAltitude() - 1);
                        break;
                    } else {
                        previousVO.setAltMinus(-1);
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

            StateVO newState = getState(panel);
            double newPower = getPanelPower(panel);

            double diff = newPower - prevPower;

            PreviousVO currentVO = new PreviousVO();
            previousVO.setId(prevState.getId());
            currentVO.setId(newState.getId());

            double k = 10;
            switch (code) {
                case 0:
                    previousVO.setAltPlus(diff);
                    currentVO.setAltPlus(diff / k);

                    previousVO.setAltMinus(-diff / k);
                    currentVO.setAltMinus(-diff / k / k);
                    break;
                case 1:
                    previousVO.setAltMinus(diff);
                    currentVO.setAltMinus(diff / k);

                    previousVO.setAltPlus(-diff / k);
                    currentVO.setAltPlus(-diff / k / k);
                    break;
                case 2:
                    previousVO.setAzPlus(diff);
                    currentVO.setAzPlus(diff / k);

                    previousVO.setAzMinus(-diff / k);
                    currentVO.setAzMinus(-diff / k / k);
                    break;
                case 3:
                    previousVO.setAzMinus(diff);
                    currentVO.setAzMinus(diff / k);

                    previousVO.setAzPlus(-diff / k);
                    currentVO.setAzPlus(-diff / k / k);
                    break;
            }

            sendUpdate(previousVO);
            sendUpdate(currentVO);
            updatePanel(panel);

            iterator++;
        }

//        System.out.println("Panel " + panel.getName() + ": final azimuth: " + panel.getAzimuth() + "; altitude: " + panel.getAltitude() + "; index: " + index + "; power: " + getPanelPower(panel));
//        System.out.println("Iterations: " + iterator);
    }

    public double getTotalPower(){
        if(station.getStationConnection() == 0) {
            return 0;
        }

        double power = 0;
        for (PanelVO panel : panels){
            power += getPanelPower(panel);
        }
        return power;
    }

    public double getPanelPower(PanelVO panel){
        if (panel.getConnected() == 0 || station.getStationConnection() == 0){
            return 0;
        }
        double coef = 0;
        try {
            coef = restTemplate.postForObject(sunUrl + index, new Coordinates(panel.getAzimuth(), 0,0,panel.getAltitude(),0,0), Double.class);
        }
        catch (Exception e){
            coef = restTemplate.postForObject(sunUrl + "0", new Coordinates(panel.getAzimuth(), 0,0,panel.getAltitude(),0,0), Double.class);
            index = 0;
        }
        return coef > 0 ? coef * panel.getNominalPower() : 0;
    }

    public StateVO getState(PanelVO panel){
        StateVO stateVOSent = new StateVO();
        stateVOSent.setPanelId(panel.getId());
        stateVOSent.setAzimuth(panel.getAzimuth());
        stateVOSent.setAltitude(panel.getAltitude());
//        StateVO state = restTemplate.postForObject(baseUrl + "states/get/", stateVOSent, StateVO.class);
//        return state;

        return (fetchState(stateVOSent)).toVO();
    }

    public void sendUpdate(PreviousVO previousVO){
//        Void response = restTemplate.postForObject(baseUrl + "states/update/", previousVO, void.class);
        updatePrevState(previousVO);
    }

    public void updatePanel(PanelVO panelVO){
        Void response = restTemplate.postForObject(baseUrl + "panels/" + panelVO.getId(), panelVO, void.class);
    }

    public void preparePanel(PanelVO panelVO){
        Void response = restTemplate.exchange(baseUrl + "panels/prepare/" + panelVO.getId(), HttpMethod.GET, null, void.class).getBody();
    }

    public void reduceForPanel(String panelId){
        Void response = restTemplate.exchange(baseUrl + "panels/reduce/" + panelId, HttpMethod.GET, null, void.class).getBody();
    }

    public void updateProducedLogs(PanelVO panelVO){
        LogVO logVO = new LogVO();
        logVO.setStationId(station.getId());
        logVO.setPanelId(panelVO.getId());
        String dateTime = restTemplate.exchange(dateTimeUrl + index, HttpMethod.GET, null, String.class).getBody();
        logVO.setDateTime(dateTime);
        logVO.setProduced(getPanelPower(panelVO) * 60 * 10);

        station.setEnergy(station.getEnergy() + logVO.getProduced());
        updateStation(station);
        restTemplate.postForObject(baseUrl + "logs/update/", logVO, void.class);
    }

    public void updateGivenLogs(){
        LogVO logVO = new LogVO();
        logVO.setStationId(station.getId());
        String dateTime = restTemplate.exchange(dateTimeUrl + index, HttpMethod.GET, null, String.class).getBody();
        logVO.setDateTime(dateTime);

        if (station.getGridConnection() == 1){
            double maxEnergyGiven = station.getMaxOutputPower() * 60 * 10;
            double additionalEnergy = Math.min(station.getEnergy(), maxEnergyGiven);
            logVO.setGiven(additionalEnergy);
            station.setEnergy(station.getEnergy() - additionalEnergy);
        }
        else {
            logVO.setGiven(0);
        }

        updateStation(station);
        restTemplate.postForObject(baseUrl + "logs/update/", logVO, void.class);
    }

    private void updateStation(StationVO stationVO) {
        Void response = restTemplate.postForObject(baseUrl + "stations/" + station.getId(), stationVO, void.class);
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

    public StateRepository getStateRepository() {
        return stateRepository;
    }

    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }






    private StateE fetchState(StateVO stateVO) {
        StateE state = StateE.fromVO(stateVO);
        try {
            Optional<StateE> state1 = stateRepository.findByParams(
                    state.getPanelId(),
                    state.getAzimuth(),
                    state.getAltitude()
            );
            return state1.get();
        }
        catch (Exception ex){
            state.setId(UUID.randomUUID().toString());
            StateE saved = stateRepository.save(state);
            return saved;
        }
    }

    public void updatePrevState(PreviousVO previousVO) {
        StateE state = getById(previousVO.getId());
        state.setAltPlus(state.getAltPlus() + previousVO.getAltPlus());
        state.setAltMinus(state.getAltMinus() + previousVO.getAltMinus());
        state.setAzPlus(state.getAzPlus() + previousVO.getAzPlus());
        state.setAzMinus(state.getAzMinus() + previousVO.getAzMinus());
        stateRepository.save(state);
    }

    StateE getById(String id) {
        Optional<StateE> direction = stateRepository.findById(id);
        return direction.get();
    }
}
