//api implement

package com.mihir.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.AlertDto;
import com.example.inventory.dto.AlertsResponse;
import com.example.inventory.service.AlertService;

@RestController
@RequestMapping("/api/companies/{companyId}/alerts")
public class AlertController {

    
    private final AlertService alertService;

    // Dependency Injection
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/low-stock")
    public AlertsResponse getLowStockAlerts(@PathVariable Long companyId) {
        List<AlertDto> alerts = alertService.getLowStockAlerts(companyId);
        return new AlertsResponse(alerts, alerts.size());
    }
}
