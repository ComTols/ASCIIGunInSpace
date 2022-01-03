import java.util.Random;

public class ShipSpawner extends GameObject {

    private class Coroutine extends Thread {

        long sleep = 10000;

        @Override
        public void run() {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (Program.RUN) {
                Random r = new Random();
                if(r.nextBoolean()) {
                    new Ship(new Vector2(-5, r.nextInt(1,Console.rows)), 1);
                } else {
                    new Ship(new Vector2(Console.columns + 5, r.nextInt(1,Console.rows-3)), -1);
                }

                sleep -= 200;

                if(sleep < 500) sleep = 500;

                try {
                    Thread.sleep(r.nextLong(sleep));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    ShipSpawner() {
        Coroutine c = new Coroutine();
        c.start();
    }
}
