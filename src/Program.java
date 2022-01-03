public class Program {

    //Läuft das Spiel?
    public static boolean RUN = true;

    /**
     * Startet das Spiel
     * @param args Unwichtige Argumente
     */
    public static void main(String[] args) {
        //Öffne Fenster
        Console c = new Console(1080, 1080/16*9);
        //Starte Zeiterfassung
        Time.Start();

        while (RUN) {
            //Aktualisiere Zeit für diesen Frame
            Time.BeforeUpdate();

            //Update für alle registrierten GameObjects
            c.Update();
            //Letztes Update für alle registrierten GameObjects
            c.LastUpdate();

            //Bereinigen der Inputs
            Input.LastUpdate();
            //Zeichne diesen Frame
            c.draw();

            try {
                //Warte 1 ms, damit Time.DeltaTime != 0
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Zeige Ende
        c.gameOver();
    }
}
