package com.yohann;

import com.yohann.models.Adventurer;
import com.yohann.models.Case;
import com.yohann.models.Treasure;
import com.yohann.enums.Type;
import com.yohann.exceptions.MultipleTreasureMapSizeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TreasureMap {
    private File file = null;

    public TreasureMap(Path filePath) {
        this.file = new File(filePath.toString());
    }

    protected List<String> readFileContent() throws FileNotFoundException {
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

    public List<List<Case>> generateMap() throws FileNotFoundException, MultipleTreasureMapSizeException {
        return this.generateMap(this.readFileContent());
    }

    public List<List<Case>> generateMap(List<String> fileContent) throws MultipleTreasureMapSizeException {
        if(fileContent.stream().filter(el -> el.startsWith("C")).count() != 1) throw new MultipleTreasureMapSizeException();

        List<String[]> fileData = fileContent
                .stream()
                .map(el -> el.replaceAll("\\s*-\\s*", "-").split("-"))
                .collect(Collectors.toList());

        String[] mapInfo = fileData.stream().filter(el -> el[0].equals("C")).findFirst().get();
        List<List<Case>> result = createBaseTreasureMap(mapInfo);

        fileData
                .stream()
                .map(this::mapData)
                .filter(Objects::nonNull)
                .forEach(caseEl -> result.get(caseEl.getX()).set(caseEl.getY(), caseEl));


        return result;
    }

    private List<List<Case>> createBaseTreasureMap(String[] mapInfo) {
        int length = Integer.parseInt(mapInfo[1]);
        int height = Integer.parseInt(mapInfo[2]);
        List<List<Case>> result = new ArrayList<>();

        for(int x = 0; x < length; x++) {
            result.add(new ArrayList<>());
            for(int y = 0; y < height; y++) {
                result.get(x).add(new Case(Type.PLAINE, x, y));
            }
        }

        return result;
    }

    protected Case mapData(String[] data) {
        if(data[0].equals("C"))
            return null;
        if(data[0].equals("A"))
            return new Adventurer(data[1], data[2], data[3], data[4], data[5]);
        if(data[0].equals("T"))
            return new Treasure(data[1], data[2], data[3]);

        return new Case(Type.retrieveByLetter(data[0]), data[1], data[2]);
    }
}
