package com.yohann;

import com.yohann.exceptions.MultipleTreasureMapSizeException;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = Logger.getLogger("Main");
    public static void main(String[] args) {
        logger.info("Main start");

        TreasureMap treasureMapLogic = new TreasureMap(Paths.get("src", "test", "resources", "baseInputNoComments.txt"));
        try {
            treasureMapLogic.generateMap();
        } catch (FileNotFoundException | MultipleTreasureMapSizeException e) {
            throw new RuntimeException(e);
        }
    }
}