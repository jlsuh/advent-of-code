package day12;

public class Node {

    private final Integer row;
    private final Integer col;
    private Character height;
    private Integer distanceFromStart;
    private Node parentNode;

    public Node(Character height, Integer distanceFromStart, Integer row, Integer col) {
        this.height = height;
        this.distanceFromStart = distanceFromStart;
        this.row = row;
        this.col = col;
    }

    public Character getHeight() {
        return height;
    }

    public Integer getDistanceFromStart() {
        return distanceFromStart;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    public void setDistanceFromStart(Integer distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public void setHeight(Character height) {
        this.height = height;
    }

}
