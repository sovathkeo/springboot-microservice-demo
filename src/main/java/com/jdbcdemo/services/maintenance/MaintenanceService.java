package com.jdbcdemo.services.maintenance;

import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaintenanceService {
    private static final String MAINTENANCE_MODE_ON = "ON";
    private ApplicationConfiguration appSetting;

    public boolean isUnderMaintenance() {
        return isMaintenanceModeOn();
    }

    private boolean isMaintenanceModeOn() {
        if (appSetting.globalConfig == null) {
            return appSetting.maintenanceMode.equalsIgnoreCase(MAINTENANCE_MODE_ON);
        }
        return appSetting.globalConfig.maintenanceMode.equalsIgnoreCase(MAINTENANCE_MODE_ON)
                || appSetting.maintenanceMode.equalsIgnoreCase(MAINTENANCE_MODE_ON);
    }

    public String getMaintenanceMessage() {
        return appSetting.getMaintenanceMessage();
    }
}
