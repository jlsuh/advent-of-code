package day07;

public class File implements DEntry {

    private final Directory containerDir;
    private final String name;
    private final Integer size;

    public File(Directory containerDir, String name, Integer size) {
        this.containerDir = containerDir;
        this.name = name;
        this.size = size;
    }

    @Override
    public Integer getSize() {
        return this.size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void print() {
        System.out.println(new String(new char[this.containerDir.getDepth() + 1]).replace("\0", "\t") + "- " + this.name + " (file, size=" + this.size + ")");
    }

}
