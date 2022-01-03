import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Input {

    //Alle gedrückten Tasten
    private static ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
    //Alle Tasten, die in diesem Frame gedrückt wurden
    private static ArrayList<Integer> downKeys = new ArrayList<Integer>();

    /**
     * Bereinigt alle key downs für diesen Frame
     */
    public static void LastUpdate() {
        downKeys.clear();
    }

    /**
     * Speichert die gedrückte Taste für diesen Frame und als gedrückte Taste
     * @param keyCode Die gedrückte Taste
     */
    public static void KeyPressed(int keyCode) {
        if (!pressedKeys.contains(keyCode)) {
            pressedKeys.add(keyCode);
            downKeys.add(keyCode);
        }
    }

    /**
     * Löscht die losgelassene Taste aus den gedrückten Tasten
     * @param keyCode Die losgelassene Taste
     */
    public static void KeyReleased(int keyCode) {
        if (pressedKeys.contains(keyCode)) {
            pressedKeys.remove(pressedKeys.indexOf(keyCode));
        }
    }

    /**
     * Gibt true zurück, wenn die Taste gedrückt ist
     * @param keyCode Die Taste
     * @return true -> Taste gedrückt | false -> Taste nicht gedrückt
     */
    public static boolean GetButton(int keyCode) {
        for (int pressed : pressedKeys) {
            if(pressed == keyCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt true zurück, wenn die Taste in diesem Frame gedrückt wurde
     * @param keyCode Die Taste
     * @return true → Taste wurde gedrückt | false → Taste wurde nicht gedrückt
     */
    public static boolean GetButtonDown(int keyCode) {
        for (int down : downKeys) {
            if(down == keyCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt die Richtung der gedrückten Steuerungstasten zurück
     * @param axis 'Vertical' | 'Horizontal'
     * @return Die Richtung der Achse oder 0, wenn nichts gedrückt wurde
     */
    public static int GetAxis(String axis) {
        if(axis == "Vertical") {
            if(GetButton(KeyEvent.VK_W)) return -1;
            if(GetButton(KeyEvent.VK_UP)) return -1;
            if(GetButton(KeyEvent.VK_S)) return 1;
            if(GetButton(KeyEvent.VK_DOWN)) return 1;
        } else if (axis == "Horizontal") {
            if(GetButton(KeyEvent.VK_D)) return 1;
            if(GetButton(KeyEvent.VK_RIGHT)) return 1;
            if(GetButton(KeyEvent.VK_A)) return -1;
            if(GetButton(KeyEvent.VK_LEFT)) return -1;
        }
        return 0;
    }

}
