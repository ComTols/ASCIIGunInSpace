public class Projectile extends GameObject {

    int speedPerSecond = 25;
    int direction;
    boolean player = false;

    Projectile(Vector2 spawn, int direction) {
        ascii = new char[][] {{'-','-'}};
        position = spawn;
        isVisible = true;
        this.direction = direction;
        collider = new Collider(this);
        collider.isTrigger = true;
        Console.gameObjects.add(this);
    }

    @Override
    public void Update() {
        Move(new Vector2(direction * speedPerSecond * Time.DeltaTime, 0));
    }

    @Override
    public void Collision(GameObject other) {
        if(player) {
            other.Destroy();
            Destroy();
        }else if(other.getClass() == Player.class) {
            other.Destroy();
            Destroy();
        }

    }

    @Override
    public void HitWall() {
        Destroy();
    }
}
