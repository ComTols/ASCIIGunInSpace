public class Score extends GameObject {

    private class Coroutine extends Thread {
        @Override
        public void run() {
            while (Program.RUN) {
                score++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    int score = 0;
    int length = 10;

    Score() {
        Coroutine c = new Coroutine();
        c.start();
    }

    public void addPoints(int points) {
        score += points;
    }

    @Override
    public String toString() {
        String s = "";
        for(int l = String.valueOf(score).length(); l < length; l++) {
            s += "0";
        }
        s += score;
        return s;
    }
}
