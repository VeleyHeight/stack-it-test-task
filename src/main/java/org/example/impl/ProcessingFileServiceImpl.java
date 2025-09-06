package org.example.impl;

import org.example.ProcessingFileService;
import org.example.model.Building;

import java.io.*;
import java.util.*;

public class ProcessingFileServiceImpl implements ProcessingFileService {
    private static final String inputFileName = "input.txt";
    private static final String outputFileName = "output.txt";
    @Override
    public void processingFile() {
        Map<String, Building> buildings = new HashMap<>();
        Set<String> chainAddresses = new HashSet<>();
        try(BufferedReader br = new BufferedReader(new FileReader(inputFileName))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(";");
                String id = words[0];
                String address = words[1];
                String nextId = words.length > 2 && !words[2].isEmpty() ? words[2] : null;
                buildings.put(id, new Building(address, nextId));
                if (nextId != null) {
                    chainAddresses.add(nextId);
                }
            }
            List<String> startedAddresses = findStartedAddresses(buildings, chainAddresses);
            Map<String, Integer> buildingLength = new HashMap<>();
            for (String address : startedAddresses) {
                findMaxLength(address, buildings, buildingLength);
            }

            int maxLength = 0;
            String maxAddress = null;
            for (String address : startedAddresses) {
                int length = buildingLength.get(address);
                if (length > maxLength) {
                    maxLength = length;
                    maxAddress = address;
                }
            }

            List<String> allAddresses = new ArrayList<>();
            for (String adress = maxAddress; adress != null;){
                Building building = buildings.get(adress);
                allAddresses.add(building.getAddress());
                adress = building.getNextId();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))){
                bw.write(String.join(" -> ", allAddresses));
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private List<String> findStartedAddresses(Map<String, Building> buildings, Set<String> chainAddresses) {
        List<String> startAddresses = new ArrayList<>();
        for (String address : buildings.keySet()) {
            if (!chainAddresses.contains(address)) {
                startAddresses.add(address);
            }
        }
        return startAddresses;
    }

    private int findMaxLength(String startAddress, Map<String, Building> buildings, Map<String, Integer> chainAddresses) {
        if(chainAddresses.containsKey(startAddress)) {
            return chainAddresses.get(startAddress);
        }
        Building building = buildings.get(startAddress);
        if(building.getNextId() == null){
            chainAddresses.put(startAddress, 1);
            return 1;
        }
        int length = 1+findMaxLength(building.getNextId(), buildings, chainAddresses);
        chainAddresses.put(startAddress, length);
        return length;
    }

}
