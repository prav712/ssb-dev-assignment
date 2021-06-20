package com.ssb.dev.controller;

import com.ssb.dev.exception.DataDiscrepancyException;
import com.ssb.dev.exception.DataNotFoundException;
import com.ssb.dev.model.Station;
import com.ssb.dev.service.StationUpdateProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.ssb.dev.util.StationProviderUtil.CLIENT_IDENTIFIER;
import static com.ssb.dev.util.StationProviderUtil.CLIENT_SSB;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StationUpdateProviderController.class)
class StationUpdateProviderControllerTest {
    private static final String URL = "/bikeStationUpdate";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StationUpdateProvider stationUpdateProvider;

    @Test
    void shouldReturnOkWhenBikeStationUpdateCalled() throws Exception {
        when(stationUpdateProvider.getStationUpdate(CLIENT_SSB)).thenReturn(Arrays.asList(Station.builder().build()));
        this.mockMvc.perform(get(URL).header(CLIENT_IDENTIFIER, CLIENT_SSB))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnOkWhenShowStationsCalled() throws Exception {
        when(stationUpdateProvider.getStationUpdate(CLIENT_SSB)).thenReturn(Arrays.asList(Station.builder().build()));
        this.mockMvc.perform(get("/showStations").header(CLIENT_IDENTIFIER, CLIENT_SSB))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatusNotFoundForDataDiscrepancyException() throws Exception {
        when(stationUpdateProvider.getStationUpdate(CLIENT_SSB)).thenThrow(new DataDiscrepancyException("DataDiscrepancy found."));

        assertThrows(DataDiscrepancyException.class, () -> stationUpdateProvider.getStationUpdate(CLIENT_SSB));

        this.mockMvc.perform(get(URL).header(CLIENT_IDENTIFIER, CLIENT_SSB))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnStatusNotFoundForDataNotFoundException() throws Exception {
        when(stationUpdateProvider.getStationUpdate(CLIENT_SSB)).thenThrow(new DataNotFoundException("Data discreprancy found."));

        assertThrows(DataNotFoundException.class, () -> stationUpdateProvider.getStationUpdate(CLIENT_SSB));

        this.mockMvc.perform(get(URL).header(CLIENT_IDENTIFIER, CLIENT_SSB))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}