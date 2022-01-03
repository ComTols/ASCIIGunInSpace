import java.util.Random;

public class DeathPointer extends GameObject {

    private class Coroutine extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Destroy();
        }
    }

    DeathPointer(Vector2 pos, int points) {
        Console.gameObjects.add(this);
        isVisible = true;
        position = pos;
        ascii = new char[1][];
        ascii[0] = String.valueOf(points).toCharArray();

        Coroutine c = new Coroutine();
        c.start();
    }
}
