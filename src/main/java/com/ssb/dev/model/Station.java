package com.ssb.dev.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    private String station_id;
    private String name;
    private float lat;
    private float lon;
    @Builder.Default
    private int num_bikes_available = 0;
    @Builder.Default
    private int num_docks_available = 0;
}

