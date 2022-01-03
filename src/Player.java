import java.util.ArrayList;

public class Player extends GameObject{

    float speedPerSecond = 20;
    Gun gun;

    Player() {
        ascii = new char[][] {
                {'(','°','_','°',')'}
        };

        position = new Vector2(Console.columns /2,Console.rows/2);
        isVisible = true;
        collider = new Collider(this);
        collider.collisionBorder = true;
        collider.isTrigger = true;

        gun = new Gun();
        gun.parent = this;
        gun.position = new Vector2(position.x + 5, position.y - 1);
        children = new ArrayList<>();
        children.add(gun);
    }

    @Override
    public void Update() {
        Move(new Vector2(Input.GetAxis("Horizontal") * speedPerSecond * Time.DeltaTime, Input.GetAxis("Vertical") * speedPerSecond / 2 * Time.DeltaTime));
    }

    @Override
    public boolean Move(Vector2 direction) {
        if(super.Move(direction)) {
            if(direction.x < 0 && gun.direction != -1) {
                gun.Turn();
                gun.position = new Vector2(position.x - 6, position.y - 1);
            }else if(direction.x > 0 && gun.direction != 1) {
                gun.Turn();
                gun.position = new Vector2(position.x + 5, position.y-1);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void Collision(GameObject other) {
        Program.RUN = false;
    }
}
