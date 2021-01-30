package ru.maksimov.amperka.smart_garden.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.maksimov.amperka.smart_garden.model.SmartGardenData;
import ru.maksimov.amperka.smart_garden.model.SmartGardenDataResponseDto;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SmartGardenService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "http://localhost:1880/getAll";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public List<SmartGardenData> getDataFromMongo() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        SmartGardenDataResponseDto[] smartGardenDataResponseDtos = objectMapper.readValue(response.getBody(),
                SmartGardenDataResponseDto[].class);
        List<SmartGardenData> gardenDataList = Arrays.stream(smartGardenDataResponseDtos)
                .map(e -> e.getPayload().replaceAll("\"", ""))
                .map(e -> {
                    String[] arr = e.split(",");
                    SmartGardenData data = SmartGardenData.builder()
                            .airTemperature(Double.valueOf(arr[0].substring(arr[0].indexOf(":") + 1)))
                            .soilMoisture(Double.valueOf(arr[1].substring(arr[1].indexOf(":") + 1)))
                            .build();
                    return data;
                })
                .collect(Collectors.toList());
        return gardenDataList;
    }

    public String getAVGdata() {
        DecimalFormat decimalFormat = new DecimalFormat("00.000");
        List<SmartGardenData> datalist = getDataFromMongo();

        Double airTemperatureAvg = datalist.stream().mapToDouble(e -> e.getAirTemperature())
                .reduce((s1, s2) -> s1 + s2).orElse(0);
        airTemperatureAvg = airTemperatureAvg / datalist.size();

        Double soilMoistureAvg = datalist.stream().mapToDouble(e -> e.getSoilMoisture())
                .reduce((s1, s2) -> s1 + s2).orElse(0);
        soilMoistureAvg = soilMoistureAvg / datalist.size();
        return String.format("airTemperatureAvg: %s, soilMoistureAvg: %s", decimalFormat.format(airTemperatureAvg),
                decimalFormat.format(soilMoistureAvg));
    }
}
