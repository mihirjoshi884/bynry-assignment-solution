package com.mihirjoshi.productinventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// The main response object, containing the list of alerts and a total count.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertsResponse {
    private List<AlertDto> alerts;
    private int totalAlerts;
}