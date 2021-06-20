package com.ssb.dev.service;

import com.ssb.dev.exception.DataNotFoundException;
import com.ssb.dev.model.Station;
import com.ssb.dev.model.StationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.ssb.dev.util.StationProviderUtil.CLIENT_IDENTIFIER;

@Service
public class StationDataRetriever {
    private static final Logger logger = LoggerFactory.getLogger(StationDataRetriever.class.getName());

    private final HttpClient httpClient;

    public StationDataRetriever() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    List<Station> getStationListWithInfo(String clientIdentifier) throws DataNotFoundException {
        var request = HttpRequest.newBuilder(
                URI.create("https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json"))
                .header(CLIENT_IDENTIFIER, clientIdentifier)
                .build();
        try {
            HttpResponse<Supplier<StationData>> response = httpClient.send(request, new JsonBodyHandler<>(StationData.class));
            if (response.statusCode() == 200) {
                return response.body().get().getData().getStations();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataNotFoundException("Unable to fetch information of the bike stations. :" + e.getMessage());
        }
    }

    Map<String, Station> getStationListWithStatus(String clientIdentifier) throws DataNotFoundException {
        var request = HttpRequest.newBuilder(
                URI.create("https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json"))
                .header(CLIENT_IDENTIFIER, clientIdentifier)
                .build();
        try {
            HttpResponse<Supplier<StationData>> response = httpClient.send(request, new JsonBodyHandler<>(StationData.class));
            if (response.statusCode() == 200) {
                return response.body().get().getData().getStations().stream()
                        .collect(Collectors.toMap(Station::getStation_id, Function.identity()));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataNotFoundException("Unable to fetch status of the bike stations. :" + e.getCause());
        }
    }
}
