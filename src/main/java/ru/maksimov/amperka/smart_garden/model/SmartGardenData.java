package ru.maksimov.amperka.smart_garden.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmartGardenData {
    private Double airTemperature;
    private Double soilMoisture;
}
