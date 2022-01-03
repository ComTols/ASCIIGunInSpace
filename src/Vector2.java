public class Vector2 {

    public double x = 0;
    public double y = 0;

    Vector2() {}

    Vector2(double _x, double _y) {
        x = _x;
        y = _y;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
