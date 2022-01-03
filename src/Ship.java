import java.util.Random;

public class Ship extends GameObject {

    private class Coroutine extends Thread {

        long sleep = 3000;

        @Override
        public void run() {
            while (fire && Program.RUN) {
                try {
                    Thread.sleep(new Random().nextLong(sleep));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(fire && Program.RUN) fire();
            }
        }
    }

    int direction;
    int speedPerSecond = 10;
    boolean fire = true;

    Ship(Vector2 pos, int dir) {
        isVisible = true;
        ascii = new char[][] {
                {' ','+','+','+',' '},
                {'(','=','=','=','>'},
                {' ','+','+','+',' '}
        };
        position = pos;
        collider = new Collider(this);
        direction = dir;
        if(direction < 0) {
            Turn();
        }
        Console.gameObjects.add(this);
        Coroutine c = new Coroutine();
        c.start();
    }

    @Override
    public void Update() {
        Move(new Vector2(direction * speedPerSecond * Time.DeltaTime, 0));
    }

    void fire() {
        if(direction < 0) {
            new Projectile(new Vector2(position.x-2, position.y+1 ), direction);
        } else {
            new Projectile(new Vector2(position.x+6, position.y+1 ), direction);
        }
    }

    @Override
    public void Destroy() {
        super.Destroy();
        fire = false;
        Console.score.addPoints(500);
        new DeathPointer(position, 500);
    }
}
