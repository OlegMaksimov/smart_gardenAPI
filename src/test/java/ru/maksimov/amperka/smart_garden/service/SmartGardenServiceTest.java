package ru.maksimov.amperka.smart_garden.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maksimov.amperka.smart_garden.model.SmartGardenData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartGardenServiceTest {

    SmartGardenService smartGarden = new SmartGardenService();

    @Test
    public void getDataFromMongoTest() {
        List<SmartGardenData> dataFromMongo = smartGarden.getDataFromMongo();
        Assertions.assertNotNull(dataFromMongo);
    }

    @Test
    public void getAvgDataTest() {
        String str = smartGarden.getAVGdata();
        Assertions.assertNotNull(str);
        System.out.println(str);
    }
}