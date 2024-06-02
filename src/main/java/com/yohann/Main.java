package com.yohann;

import com.yohann.exceptions.MultipleTreasureMapSizeException;
import com.yohann.models.TreasureMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = Logger.getLogger("Main");
    public static void main(String[] args) {
        logger.info("Main start");

        Engine engine = new Engine(Paths.get("src", "main", "resources", "data.txt"));
        try {
            TreasureMap map = engine.generateMap();
            engine.run(map);
            engine.save(map, Paths.get("src", "main", "results"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input data file not found");
        } catch (MultipleTreasureMapSizeException e) {
            throw new RuntimeException("Error in file naming, containing 2 Map size");
        } catch (IOException e) {
            throw new RuntimeException("Can't save the data");
        }
    }
}