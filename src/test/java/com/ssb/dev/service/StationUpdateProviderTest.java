package com.ssb.dev.service;

import com.ssb.dev.exception.DataNotFoundException;
import com.ssb.dev.exception.DataDiscrepancyException;
import com.ssb.dev.model.Station;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ssb.dev.util.StationProviderUtil.CLIENT_SSB;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class StationUpdateProviderTest {
    private static final String ID_1 = "1";
    private static final String STATION_OSLO_1 = "oslo1";
    private static final String ID_2 = "2";
    private static final String STATION_OSLO_2 = "oslo2";

    @InjectMocks
    private StationUpdateProvider stationUpdateProvider;
    @Mock
    private StationDataRetriever stationDataRetriever;


    @Test
    void shouldThrowExceptionIfDataHasDiscrepancies() throws DataNotFoundException {
        List<Station> stationWithInfo = Arrays.asList(getStationWithInfo(ID_1, STATION_OSLO_1),getStationWithInfo(ID_2, STATION_OSLO_2));
        when(stationDataRetriever.getStationListWithInfo(CLIENT_SSB)).thenReturn(stationWithInfo);

        Map<String, Station> stationStatusById = Map.of(ID_1, getStationWithStatus(ID_1, 5, 10));
        when(stationDataRetriever.getStationListWithStatus(CLIENT_SSB)).thenReturn(stationStatusById);

        DataDiscrepancyException exception = assertThrows(DataDiscrepancyException.class, () -> stationUpdateProvider.getStationUpdate(CLIENT_SSB));

        String expectedMessage = "Discrepancy between StationInfo and StationStatus data.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldReturnUpdatedStationsCorrectly() throws DataNotFoundException, DataDiscrepancyException {
        List<Station> stationWithInfo = Collections.singletonList(getStationWithInfo(ID_1, STATION_OSLO_1));
        when(stationDataRetriever.getStationListWithInfo(CLIENT_SSB)).thenReturn(stationWithInfo);

        Map<String, Station> stationStatusById = Map.of(ID_1, getStationWithStatus(ID_1, 5, 10));
        when(stationDataRetriever.getStationListWithStatus(CLIENT_SSB)).thenReturn(stationStatusById);

        List<Station> stationList = stationUpdateProvider.getStationUpdate(CLIENT_SSB);

        verify(stationDataRetriever, times(1)).getStationListWithInfo(CLIENT_SSB);
        verify(stationDataRetriever, times(1)).getStationListWithStatus(CLIENT_SSB);

        assertThat(stationList, is(not(empty())));
        assertThat(stationList, hasSize(1));

        Station station = stationList.get(0);
        assertThat(station.getStation_id(), is(ID_1));
        assertThat(station.getNum_bikes_available(), is(10));
        assertThat(station.getNum_docks_available(), is(5));
    }

    private Station getStationWithInfo(String id, String name) {
        return Station.builder()
                .name(name)
                .station_id(id)
                .build();
    }

    private Station getStationWithStatus(String id, int noOfDocsAvailable, int noOfBikesAvailable) {
        return Station.builder()
                .station_id(id)
                .num_docks_available(noOfDocsAvailable)
                .num_bikes_available(noOfBikesAvailable)
                .build();
    }
}