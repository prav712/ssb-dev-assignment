package com.ssb.dev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
{
  "last_updated": 1540219230,
  "data": {
    "stations": [
      {
        "is_installed": 1,
        "is_renting": 1,
        "num_bikes_available": 7,
        "num_docks_available": 5,
        "last_reported": 1540219230,
        "is_returning": 1,
        "station_id": "175"
      },
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationData {
    @Builder.Default
    private StationList data = StationList.builder().build();
}
