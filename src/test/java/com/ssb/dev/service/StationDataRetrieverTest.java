package com.ssb.dev.service;

import com.ssb.dev.exception.DataNotFoundException;
import com.ssb.dev.model.Station;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static com.ssb.dev.util.StationProviderUtil.CLIENT_SSB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan
class StationDataRetrieverTest {
    @Autowired
    private StationDataRetriever stationDataRetriever;

    @Test
    void shouldReturnCorrectStationListWithStatus() throws DataNotFoundException {
        Map<String, Station> stationListWithStatus = stationDataRetriever.getStationListWithStatus(CLIENT_SSB);

        assertThat("Currently oslo has 250 bike stations", stationListWithStatus.size(), is(250));
    }

    @Test
    void shouldReturnCorrectStationListWithInfo() throws DataNotFoundException {

        List<Station> stationListWithInfo = stationDataRetriever.getStationListWithInfo("");

        assertThat("Currently oslo has 250 bike stations", stationListWithInfo.size(), is(250));
    }
}
