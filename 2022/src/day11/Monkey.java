package day11;

import java.util.List;

public class Monkey {

    private final List<Long> items;
    private final Operation operation;
    private final String operationScalar;
    private final Long divisibilityScalar;
    private final Long throwIndexIfTrue;
    private final Long throwIndexIfFalse;
    private Long numberOfInspectedItems;

    public Monkey(List<Long> items, Operation operation, String operationScalar, Long divisibilityScalar,
                  Long throwIndexIfTrue, Long throwIndexIfFalse) {
        this.items = items;
        this.operation = operation;
        this.operationScalar = operationScalar;
        this.divisibilityScalar = divisibilityScalar;
        this.throwIndexIfTrue = throwIndexIfTrue;
        this.throwIndexIfFalse = throwIndexIfFalse;
        this.numberOfInspectedItems = 0L;
    }

    public Long getNumberOfInspectedItems() {
        return numberOfInspectedItems;
    }

    public Long getDivisibilityScalar() {
        return divisibilityScalar;
    }

    public String toString() {
        return "Monkey{" +
                "items=" + items +
                ", operation=" + operation +
                ", operationScalar=" + operationScalar +
                ", divisibilityScalar=" + divisibilityScalar +
                ", throwIndexIfTrue=" + throwIndexIfTrue +
                ", throwIndexIfFalse=" + throwIndexIfFalse +
                ", numberOfInspectedItems=" + numberOfInspectedItems +
                '}';
    }

    public void addItem(Long newWorryLevel) {
        items.add(newWorryLevel);
    }

    public Boolean isWorrying() {
        return items.size() > 0;
    }

    public Long[] inspectItem(Operation reliefOperation, Long reliefScalar) {
        Long oldWorryLevel = items.remove(0);
        Long operationScalar = this.operationScalar.equals("old") ? oldWorryLevel : Long.parseLong(this.operationScalar);
        Long newWorryLevel = reliefOperation.apply(operation.apply(oldWorryLevel, operationScalar), reliefScalar);
        Long throwIndex = Operation.MOD.apply(newWorryLevel, divisibilityScalar) == 0 ? throwIndexIfTrue : throwIndexIfFalse;
        numberOfInspectedItems += 1;
        return new Long[]{newWorryLevel, throwIndex};
    }

}
