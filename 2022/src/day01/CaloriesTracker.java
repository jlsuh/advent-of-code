package day01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CaloriesTracker {

    private final List<Elf> elves;

    public CaloriesTracker(List<String> calories) {
        this.elves = this.constructElves(calories);
    }

    private List<Elf> constructElves(List<String> calories) {
        List<Elf> elves = new ArrayList<>();
        Iterator<String> iterator = calories.iterator();
        List<Integer> elfCalories = new ArrayList<>();
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (!line.isEmpty()) elfCalories.add(Integer.parseInt(line));
            else {
                elves.add(new Elf(elfCalories));
                elfCalories = new ArrayList<>();
            }
        }
        elves.add(new Elf(elfCalories));
        return elves;
    }

    public Elf getElfWithMostCalories() {
        return this.elves.stream()
                .max(Comparator.comparing(Elf::getCaloriesSum))
                .orElse(null);
    }

    private List<Elf> getTop3ElvesWithMostCalories() {
        return this.elves.stream()
                .sorted(Comparator.comparing(Elf::getCaloriesSum).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public Integer getTotalCaloriesTop3Elves() {
        return this.getTop3ElvesWithMostCalories().stream()
                .reduce(0, (acc, elf) -> acc + elf.getCaloriesSum(), Integer::sum);
    }

}
