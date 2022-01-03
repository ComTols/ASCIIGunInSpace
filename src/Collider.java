import java.util.ArrayList;

public class Collider {

    public static ArrayList<Collider> colliders = new ArrayList<>();
    GameObject object;
    Vector2 size;
    boolean collisionBorder = false;
    boolean isTrigger = false;

    Collider(GameObject g) {
        object = g;

        if(g.ascii != null) {
            size = new Vector2(g.ascii[0].length, g.ascii.length);
        } else {
            size = new Vector2();
        }

        colliders.add(this);
    }

    public boolean isCollisionForPosition(Vector2 newPosition) {
        if(collisionBorder) {
            if(newPosition.x < 1 || newPosition.y < 1) {
                return true;
            }
            if (newPosition.x + size.x > Console.columns - 1 || newPosition.y + size.y > Console.rows - 1) {
                return true;
            }
        }
        if(isTrigger) return false;
        Collider[] col = colliders.toArray(new Collider[0]);
        for (Collider c : col) {
            if(c != this) {
                //X Match
                if((newPosition.x <= c.object.position.x && newPosition.x + size.x >= c.object.position.x) ||
                        (newPosition.x >= c.object.position.x && newPosition.x <= c.object.position.x + c.size.x)
                ) {
                    //Y Match
                    if((newPosition.y <= c.object.position.y && newPosition.y + size.y >= c.object.position.y) ||
                            (newPosition.y >= c.object.position.y && newPosition.y <= c.object.position.y + c.size.y)
                    ) {
                        //COLLISION!
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void Update() {
        Collider[] col = colliders.toArray(new Collider[0]);
        for (Collider c : col) {
            if(c != this) {
                //X Match
                if((object.position.x <= c.object.position.x && object.position.x + size.x >= c.object.position.x) ||
                        (object.position.x >= c.object.position.x && object.position.x <= c.object.position.x + c.size.x)
                ) {
                    //Y Match
                    if((object.position.y <= c.object.position.y && object.position.y + size.y >= c.object.position.y) ||
                            (object.position.y >= c.object.position.y && object.position.y <= c.object.position.y + c.size.y)
                    ) {
                        //COLLISION!
                        object.Collision(c.object);
                        c.object.Collision(object);
                    }
                }
            }
        }

        if(object.position.x < 1 || object.position.y < 1) {
            object.HitWall();
        }
        if (object.position.x + size.x > Console.columns - 1 || object.position.y + size.y > Console.rows - 1) {
            object.HitWall();
        }
    }

}
