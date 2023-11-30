package day01;

import java.util.List;

public class Elf {

    private final List<Integer> calories;

    public Elf(List<Integer> calories) {
        this.calories = calories;
    }

    public Integer getCaloriesSum() {
        return this.calories.stream().reduce(0, Integer::sum);
    }

}
