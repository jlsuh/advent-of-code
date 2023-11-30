package day07;

import java.util.ArrayList;
import java.util.List;

public class Directory implements DEntry {

    private final List<DEntry> dEntries;
    private final Integer depth;
    private final String name;
    private final DEntry parent;

    public Directory(Integer depth, String name, DEntry parent) {
        this.dEntries = new ArrayList<>();
        this.depth = depth;
        this.name = name;
        this.parent = parent;
    }

    public void addEntry(DEntry entry) {
        dEntries.add(entry);
    }

    public Directory findDirectoryEntryByName(String name) {
        return (Directory) this.dEntries.stream().filter(dEntry -> dEntry.getName().equals(name)).findFirst().orElse(null);
    }

    public Integer getDepth() {
        return this.depth;
    }

    public Directory getParent() {
        return (Directory) this.parent;
    }

    @Override
    public Integer getSize() {
        return this.dEntries.stream().map(DEntry::getSize).reduce(0, Integer::sum);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void print() {
        System.out.println(new String(new char[this.depth]).replace("\0", "\t") + "- " + this.name + " (dir)");
        this.dEntries.forEach(DEntry::print);
    }

}
