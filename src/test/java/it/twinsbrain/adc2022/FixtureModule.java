package it.twinsbrain.adc2022;

import java.net.URISyntaxException;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class FixtureModule {
    public static List<String> readSample(String day) {
        try {
            return read(resource("/" + day + "/sample.txt"));
        } catch (URISyntaxException e) {
            throw new RuntimeException("input file not found!");
        }
    }
}
