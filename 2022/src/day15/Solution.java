package day15;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.*;
import java.util.function.Function;

public class Solution {

    private static final String NUMBER = "-?[1-9]\\d*|0";
    private static final Long TARGET_ROW = 2000000L;
    private static final Long MIN_SQUARED_DIM = 0L;
    private static final Long MAX_SQUARED_DIM = 4000000L;

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day15/input.txt");
        List<String> input = InputReader.getBufferedReaderLines(br);
        System.out.println(part1Solution(input));
        System.out.println(part2Solution(input));
    }

    private static Long[] scannerBeaconPositions(String line) {
        Scanner s = new Scanner(line);
        Long[] positions = s.findAll(NUMBER).map(matchResult -> Long.parseLong(matchResult.group())).toArray(Long[]::new);
        s.close();
        return positions;
    }

    private static List<Sensor> extractSensors(List<String> input) {
        List<Sensor> sensors = new ArrayList<>();
        input.forEach(line -> {
            Long[] limits = scannerBeaconPositions(line);
            sensors.add(new Sensor(limits[0], limits[1], limits[2], limits[3]));
        });
        return sensors;
    }

    private static Boolean isBeaconPosition(List<SimpleImmutableEntry<Long, Long>> beaconPositions, Long horizontalIndex) {
        return beaconPositions.stream().anyMatch(beaconPosition ->
                beaconPosition.getKey().equals(horizontalIndex) && beaconPosition.getValue().equals(TARGET_ROW)
        );
    }

    private static final Function<List<Sensor>, List<SimpleImmutableEntry<Long, Long>>> beacons =
            sensors -> sensors.stream().map(Sensor::getClosestBeacon).toList();

    private static Integer positionsWithNoBeacons(List<Sensor> sensors) {
        List<SimpleImmutableEntry<Long, Long>> positionsWithABeacon = new ArrayList<>(beacons.apply(sensors));
        Map<Long, Long> positionsWithNoBeacon = new HashMap<>();
        for (Sensor sensor : sensors) {
            for (Long i = sensor.startIndex(TARGET_ROW); i <= sensor.endIndex(TARGET_ROW); i += 1)
                if (!isBeaconPosition(positionsWithABeacon, i)) positionsWithNoBeacon.put(i, TARGET_ROW);
        }
        return positionsWithNoBeacon.size();
    }

    private static Integer part1Solution(List<String> input) {
        return positionsWithNoBeacons(extractSensors(input));
    }

    private static Boolean isContainedByAnotherSensor(List<Sensor> sensors, Long horizontalIndex, Long row) {
        for (Sensor sensor : sensors)
            if (horizontalIndex >= sensor.startIndex(row) && horizontalIndex <= sensor.endIndex(row)) return true;
        return false;
    }

    private static SimpleImmutableEntry<Long, Long> distressBeacon(List<Sensor> sensors) {
        for (Sensor sensor : sensors) {
            for (Long row = sensor.verticalPerimeterStartIndex(MIN_SQUARED_DIM); row <= sensor.verticalPerimeterEndIndex(MAX_SQUARED_DIM); row += 1) {
                if (sensor.isWithinLimits(row, MIN_SQUARED_DIM, MAX_SQUARED_DIM))
                    continue;
                Long perimeterStart = sensor.perimeterStartIndex(row);
                if (!isContainedByAnotherSensor(sensors, perimeterStart, row))
                    return new SimpleImmutableEntry<>(perimeterStart, row);
                Long perimeterEnd = sensor.perimeterEndIndex(row);
                if (!isContainedByAnotherSensor(sensors, perimeterEnd, row))
                    return new SimpleImmutableEntry<>(perimeterEnd, row);
            }
        }
        return null;
    }

    private static final Function<SimpleImmutableEntry<Long, Long>, Long> tuningFrequency =
            coordinates -> coordinates.getKey() * MAX_SQUARED_DIM + coordinates.getValue();

    private static Long part2Solution(List<String> input) {
        return tuningFrequency.apply(distressBeacon(extractSensors(input)));
    }

}
