package com.yohann;

import com.yohann.enums.Move;
import com.yohann.models.Adventurer;
import com.yohann.models.Case;
import com.yohann.models.Treasure;
import com.yohann.enums.Type;
import com.yohann.exceptions.MultipleTreasureMapSizeException;
import com.yohann.models.TreasureMap;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Engine {
    private final Logger logger = Logger.getLogger("TreasureMapLogic");
    private File file = null;

    public Engine(Path filePath) {
        this.file = new File(filePath.toString());
    }

    public void run(TreasureMap map) {
        List<Adventurer> adventurers = map.getAdventurers();
        boolean hasAMoveLeft;

        do {
            hasAMoveLeft = false;
            for(Adventurer adventurer : adventurers) {
                if(adventurer.getCurrentMove() == null) continue;

                hasAMoveLeft = true;
                if(adventurer.getCurrentMove() == Move.AVANCER) {
                    adventurer.doCurrentMove(map);
                    Case c = map.getUnderAdventurer(adventurer);
                    if (c.getType() == Type.TRESOR) {
                        adventurer.pickUp((Treasure) c);
                    }
                    continue;
                }

                adventurer.doCurrentMove(null);
            }
        } while(hasAMoveLeft);
    }

    public List<String> readFileContent() throws FileNotFoundException {
        logger.info("ReadFileContent");

        Scanner sc = new Scanner(file);
        List<String> fileContent = new ArrayList<>();

        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.startsWith("#"))  continue;
            fileContent.add(line);
        }
        sc.close();

        return fileContent;
    }

    public TreasureMap generateMap() throws FileNotFoundException, MultipleTreasureMapSizeException {
        return this.generateMap(this.readFileContent());
    }

    public TreasureMap generateMap(List<String> fileContent) throws MultipleTreasureMapSizeException {
        if(fileContent.stream().filter(el -> el.startsWith("C")).count() != 1) throw new MultipleTreasureMapSizeException("Found more than one C line");

        List<String[]> fileData = fileContent
                .stream()
                .map(el -> el.replaceAll("\\s*-\\s*", "-").split("-"))
                .collect(Collectors.toList());

        String[] mapInfo = fileData.stream().filter(el -> el[0].equals("C")).findFirst().get();
        TreasureMap map = new TreasureMap(mapInfo[1], mapInfo[2]);

        List<Case> finalData = fileData
                .stream()
                .map(this::dataMapping)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        finalData.forEach(el ->
                logger.info(
                        String.format("Case %s %s",
                                el.getType(),
                                map.addOnMap(el) ? "ajoutée" : "non ajoutée"
                        )
                )
        );

        return map;
    }

    public Case dataMapping(String[] data) {
        switch (data[0]) {
            case "C":
                return null;
            case "A":
                return new Adventurer(data[1], data[2], data[3], data[4], data[5]);
            case "T":
                return new Treasure(data[1], data[2], data[3]);
        }

        return new Case(Type.retrieveByLetter(data[0]), data[1], data[2]);
    }

    public void save(TreasureMap map, Path saveFile) throws IOException {
        File folders = (new File(saveFile.toString())).getParentFile();
        boolean created = folders.mkdirs();
        logger.info(created ? "Subfolders created" : "No new folder created");

        FileWriter fileWriter = new FileWriter(saveFile.toString());
        PrintWriter printWriter = new PrintWriter(fileWriter);
        List<Case> mapContent = map
                .getMap()
                .stream()
                .flatMap(Collection::stream)
                .filter(caseEl -> !caseEl.getType().equals(Type.PLAINE))
                .filter(caseEl -> {
                    if(caseEl.getType() != Type.TRESOR)
                        return true;

                    return ((Treasure) caseEl).hasTreasureLeft();
                })
                .collect(Collectors.toList());

        printWriter.printf("C - %s - %s", map.getLength(), map.getHeight());
        printWriter.println();

        for (Type type : Arrays.asList(Type.MONTAGNE, Type.TRESOR)) {
            mapContent
                    .stream()
                    .filter(el -> el.getType().equals(type))
                    .forEach(el -> {
                        printWriter.printf(el.toString());
                        printWriter.println();
                    });
        }

        for(Adventurer adventurer : map.getAdventurers()) {
            printWriter.printf(adventurer.toString());
            printWriter.println();
        }

        fileWriter.close();
        printWriter.close();
    }
}
