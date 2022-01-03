public class Rock extends GameObject {

    float speedPerSecond = 10;
    int direction;

    Rock(Vector2 pos, int dir, int size, float speed) {
        isVisible = true;
        ascii = new char[size + 2][];
        ascii[0] = new char[] {'/','|','\\'};
        for (int i = 1; i <= size; i++) {
            ascii[i] = new char[] {'|','|','|'};
        }
        ascii[ascii.length-1] = new char[] {'\\','|','/'};

        collider = new Collider(this);
        Console.gameObjects.add(this);
        position = pos;
        direction = dir;
        speedPerSecond = speed;
        collider.isTrigger = true;
    }

    @Override
    public void Update() {
        Move(new Vector2(direction * speedPerSecond * Time.DeltaTime, 0));
    }

    @Override
    public void Destroy() {
        super.Destroy();
        Console.score.addPoints(100);
        new DeathPointer(position, 100);
    }
}
