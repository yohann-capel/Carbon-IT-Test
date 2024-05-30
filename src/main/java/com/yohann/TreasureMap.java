package com.yohann;

import com.yohann.models.Adventurer;
import com.yohann.models.Case;
import com.yohann.models.Treasure;
import com.yohann.enums.Type;
import com.yohann.exceptions.MultipleTreasureMapSizeException;
import com.yohann.models.TMap;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreasureMap {
    private final Logger logger = Logger.getLogger("TreasureMapLogic");
    private File file = null;

    public TreasureMap(Path filePath) {
        this.file = new File(filePath.toString());
    }

    public List<String> readFileContent() throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        List<String> fileContent = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.startsWith("#"))  continue;
            fileContent.add(line);
        }
        sc.close();
        logger.info("readFileContent end");
        return fileContent;
    }

    public TMap generateMap() throws FileNotFoundException, MultipleTreasureMapSizeException {
        return this.generateMap(this.readFileContent());
    }

    public TMap generateMap(List<String> fileContent) throws MultipleTreasureMapSizeException {
        if(fileContent.stream().filter(el -> el.startsWith("C")).count() != 1) throw new MultipleTreasureMapSizeException();

        List<String[]> fileData = fileContent
                .stream()
                .map(el -> el.replaceAll("\\s*-\\s*", "-").split("-"))
                .collect(Collectors.toList());

        String[] mapInfo = fileData.stream().filter(el -> el[0].equals("C")).findFirst().get();
        TMap result = new TMap(mapInfo[1], mapInfo[2]);

        fileData
                .stream()
                .map(this::dataMapping)
                .filter(Objects::nonNull)
                .forEach(result::setAtCoordinates);

        return result;
    }

    public Case dataMapping(String[] data) {
        if(data[0].equals("C"))
            return null;
        if(data[0].equals("A"))
            return new Adventurer(data[1], data[2], data[3], data[4], data[5]);
        if(data[0].equals("T"))
            return new Treasure(data[1], data[2], data[3]);

        return new Case(Type.retrieveByLetter(data[0]), data[1], data[2]);
    }

    public void generateResultFile(TMap map) throws IOException {
        FileWriter fileWriter = new FileWriter(String.format("src/main/results/result_%s", new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date())));
        PrintWriter printWriter = new PrintWriter(fileWriter);
        List<Case> mapContent = map
                .getMap()
                .stream()
                .flatMap(Collection::stream)
                .filter(caseEl -> !caseEl.getType().equals(Type.PLAINE))
                .collect(Collectors.toList());

        printWriter.printf("C - %s - %s", map.getLength(), map.getHeight());
        printWriter.println();

        for (Type type : Arrays.asList(Type.MONTAGNE, Type.TRESOR, Type.AVENTURIER)) {
            mapContent
                    .stream()
                    .filter(el -> el.getType().equals(type))
                    .forEach(el -> {
                        printWriter.printf(el.toString());
                        printWriter.println();
                    });
        }

        fileWriter.close();
        printWriter.close();
    }
}
