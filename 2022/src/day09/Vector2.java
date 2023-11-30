package day09;

public class Vector2 {

    private Integer x;
    private Integer y;

    public Vector2(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Boolean isOverlapping(Vector2 node) {
        return this.x.equals(node.getX()) && this.y.equals(node.getY());
    }

    public Double differentialVectorNorm2(Vector2 dest) {
        return Math.sqrt(Math.pow(dest.getX() - this.x, 2) + Math.pow(dest.getY() - this.y, 2));
    }

    public Boolean isWithinRange(Vector2 dest, Double range) {
        return this.differentialVectorNorm2(dest) <= range;
    }

}
