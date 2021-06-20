package com.ssb.dev.controller;

import com.ssb.dev.exception.DataNotFoundException;
import com.ssb.dev.exception.DataDiscrepancyException;
import com.ssb.dev.model.Station;
import com.ssb.dev.service.StationUpdateProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.ssb.dev.util.StationProviderUtil.CLIENT_IDENTIFIER;
import static com.ssb.dev.util.StationProviderUtil.CLIENT_SSB;

@Controller
public class StationUpdateProviderController {
    private final StationUpdateProvider stationUpdateProvider;

    public StationUpdateProviderController(StationUpdateProvider stationUpdateProvider) {
        this.stationUpdateProvider = stationUpdateProvider;
    }

    @GetMapping(value = "/getBikeStations", produces = {"application/json"})
    public ResponseEntity<List<Station>> getStationUpdate(@RequestHeader("Client-Identifier") String clientIdentifier) {
        try {
            if (clientIdentifier.split("-").length != 2) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Incorrect header value: " + clientIdentifier);
            }
            return new ResponseEntity<>(stationUpdateProvider.getStationUpdate(clientIdentifier), HttpStatus.OK);
        } catch (DataNotFoundException | DataDiscrepancyException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/showStations")
    public String viewBooks(@RequestHeader(value = CLIENT_IDENTIFIER, defaultValue = CLIENT_SSB) String clientIdentifier, Model model) throws DataDiscrepancyException, DataNotFoundException {
        model.addAttribute("stations", stationUpdateProvider.getStationUpdate(clientIdentifier));
        return "show_station";
    }

}
