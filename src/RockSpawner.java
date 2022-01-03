import java.util.Random;

public class RockSpawner extends GameObject {

    private class Coroutine extends Thread {

        float maxSpeed = 5;
        float maxSize = 1;
        long sleep = 10000;

        @Override
        public void run() {
            while (Program.RUN) {
                Random r = new Random();
                if(r.nextBoolean()) {
                    new Rock(new Vector2(-3,r.nextInt(Console.rows - 2 )), 1, r.nextInt((int)maxSize) ,r.nextFloat(4,maxSpeed));
                } else {
                    new Rock(new Vector2(Console.columns,r.nextInt(Console.rows - 2 )), -1, r.nextInt((int)maxSize) ,r.nextFloat(4,maxSpeed));
                }

                maxSize += 0.1;
                maxSpeed += 0.3;
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

    RockSpawner() {
        Coroutine c = new Coroutine();
        c.start();
    }
}
