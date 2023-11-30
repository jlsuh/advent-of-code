package day15;

import java.util.AbstractMap.SimpleImmutableEntry;

public class Sensor {

    private static final Integer OFFSET = 1;
    private final SimpleImmutableEntry<Long, Long> position;
    private final SimpleImmutableEntry<Long, Long> closestBeacon;

    public Sensor(Long horizontalIndex, Long verticalIndex, Long closestBeaconHorizontalIndex, Long closestBeaconVerticalIndex) {
        this.position = new SimpleImmutableEntry<>(horizontalIndex, verticalIndex);
        this.closestBeacon = new SimpleImmutableEntry<>(closestBeaconHorizontalIndex, closestBeaconVerticalIndex);
    }

    public SimpleImmutableEntry<Long, Long> getClosestBeacon() {
        return closestBeacon;
    }

    public Long manhattanDistanceToClosestBeacon() {
        return Math.abs(position.getKey() - closestBeacon.getKey()) + Math.abs(position.getValue() - closestBeacon.getValue());
    }

    public Long rangeWidthInRow(Long targetRow) {
        return manhattanDistanceToClosestBeacon() - Math.abs(position.getValue() - targetRow);
    }

    public Long startIndex(Long row) {
        return position.getKey() - rangeWidthInRow(row);
    }

    public Long endIndex(Long row) {
        return position.getKey() + rangeWidthInRow(row);
    }

    public Long perimeterStartIndex(Long row) {
        return startIndex(row) - 1;
    }

    public Long perimeterEndIndex(Long row) {
        return endIndex(row) + 1;
    }

    public Boolean isWithinLimits(Long row, Long minSquaredDim, Long maxSquaredDim) {
        long perimeterStartIndex = perimeterStartIndex(row);
        long perimeterEndIndex = perimeterEndIndex(row);
        return perimeterStartIndex < minSquaredDim
                || perimeterStartIndex > maxSquaredDim
                || perimeterEndIndex < minSquaredDim
                || perimeterEndIndex > maxSquaredDim;
    }

    public Long verticalPerimeterStartIndex(Long minSquaredDim) {
        return Math.max(minSquaredDim, position.getValue() - manhattanDistanceToClosestBeacon() - OFFSET);
    }

    public Long verticalPerimeterEndIndex(Long maxSquaredDim) {
        return Math.min(maxSquaredDim, position.getValue() + manhattanDistanceToClosestBeacon() + OFFSET);
    }

}
