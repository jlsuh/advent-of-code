package day11;

import utils.InputReader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    private static final Integer MONKEY_INPUT_SIZE = 7;
    private static final Integer ITEM_LEVELS_INDEX = 18;
    private static final Integer OPERATION_INDEX = 23;
    private static final Integer OPERATION_SCALAR_INDEX = 25;
    private static final Integer DIVISIBILITY_SCALAR_INDEX = 21;
    private static final Integer THROW_IF_TRUE_INDEX = 29;
    private static final Integer THROW_IF_FALSE_INDEX = 30;

    public static void main(String[] args) {
        BufferedReader br = InputReader.getBufferedReader("day11/input.txt");
        List<String> input = InputReader.getBufferedReaderLines(br);
        input.add("");
        System.out.println(part1Solution(input));
        System.out.println(part2Solution(input));
    }

    private static List<Monkey> parseMonkeys(List<String> lines) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += MONKEY_INPUT_SIZE) {
            List<String> monkeyInput = lines.subList(i + 1, i + MONKEY_INPUT_SIZE);
            Monkey monkey = new Monkey(
                    Arrays.stream(monkeyInput.get(0).substring(ITEM_LEVELS_INDEX).split(",\\s")).map(Long::parseLong).collect(Collectors.toList()),
                    monkeyInput.get(1).charAt(OPERATION_INDEX) == '+' ? Operation.ADD : Operation.MULT,
                    monkeyInput.get(1).substring(OPERATION_SCALAR_INDEX),
                    Long.parseLong(monkeyInput.get(2).substring(DIVISIBILITY_SCALAR_INDEX)),
                    Long.parseLong(monkeyInput.get(3).substring(THROW_IF_TRUE_INDEX)),
                    Long.parseLong(monkeyInput.get(4).substring(THROW_IF_FALSE_INDEX))
            );
            monkeys.add(monkey);
        }
        return monkeys;
    }

    private static Long getMonkeyBusiness(List<Monkey> monkeys, Integer rounds, Operation reliefOperation, Long reliefLevel) {
        for (int i = 0; i < rounds; i += 1) {
            for (Monkey srcMonkey : monkeys) {
                while (srcMonkey.isWorrying()) {
                    Long[] inspection = srcMonkey.inspectItem(reliefOperation, reliefLevel);
                    Monkey destMonkey = monkeys.get(Math.toIntExact(inspection[1]));
                    destMonkey.addItem(inspection[0]);
                }
            }
        }
        return monkeys.stream()
                .map(Monkey::getNumberOfInspectedItems)
                .sorted((i1, i2) -> Math.toIntExact(i2 - i1))
                .limit(2)
                .reduce(1L, (i1, i2) -> i1 * i2);
    }

    private static Long part1Solution(List<String> input) {
        List<Monkey> monkeys = parseMonkeys(input);
        return getMonkeyBusiness(monkeys, 20, Operation.DIV, 3L);
    }

    private static Long part2Solution(List<String> input) {
        List<Monkey> monkeys = parseMonkeys(input);
        Long cycleLength = monkeys.stream().map(Monkey::getDivisibilityScalar).reduce(1L, (cl1, cl2) -> cl1 * cl2);
        return getMonkeyBusiness(monkeys, 10000, Operation.MOD, cycleLength);
    }

}
