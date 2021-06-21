package com.ssb.dev.service;

import com.ssb.dev.exception.DataNotFoundException;
import com.ssb.dev.exception.DataDiscrepancyException;
import com.ssb.dev.model.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StationUpdateProvider {
    private static final Logger logger = LoggerFactory.getLogger(StationUpdateProvider.class.getName());

    private final StationDataRetriever stationDataRetriever;
    private List<Station> stationInfoList = new ArrayList<>();

    public StationUpdateProvider(StationDataRetriever stationDataRetriever) {
        this.stationDataRetriever = stationDataRetriever;
    }

    public List<Station> getStationUpdate(String clientIdentifier) throws DataNotFoundException, DataDiscrepancyException {
        Map<String, Station> stationStatusById = stationDataRetriever.getStationListWithStatus(clientIdentifier);

        if ( stationInfoList.size() != stationStatusById.size()) {
            // should re fetch stationInfoList if a station has been added/removed
            stationInfoList = stationDataRetriever.getStationListWithInfo(clientIdentifier);
        }
        if(stationInfoList.size() != stationStatusById.size()){
            logger.error("Discrepancy between StationInfo and StationStatus data.");
            throw new DataDiscrepancyException("Discrepancy between StationInfo and StationStatus data.");
        }

        stationInfoList.forEach(stationWithInfo -> {
                Station matchingStationWithStatus = stationStatusById.get(stationWithInfo.getStation_id());
                if(matchingStationWithStatus == null){
                    logger.error(String.format("No matching status found for station id: %s", stationWithInfo.getStation_id()));
                    stationWithInfo.setNum_bikes_available(0);
                    stationWithInfo.setNum_docks_available(0);
                }else {
                    stationWithInfo.setNum_bikes_available(matchingStationWithStatus.getNum_bikes_available());
                    stationWithInfo.setNum_docks_available(matchingStationWithStatus.getNum_docks_available());
                }
        });
        return stationInfoList;
    }
}
