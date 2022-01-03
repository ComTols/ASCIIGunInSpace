import java.util.ArrayList;

public class GameObject {

    //Der Name des Objekts
    String name;
    //Das Aussehen des Objekts
    char[][] ascii = null;
    //Der Collider des Objekts; null, wenn kein Collider benötigt wird
    Collider collider;
    //Position des Objekts
    Vector2 position = new Vector2();
    //Gibt an, ob das Objekt gezeichnet werden soll
    boolean isVisible = false;
    //Alle Kinder des Objekts
    ArrayList<GameObject> children;
    //Das Elternobjekt; null, wenn in der root Ebene
    GameObject parent;

    /**
     * Wird einmal pro Frame aufgerufen
     */
    public void Update() {
    }

    /**
     * bewegt das Objekt entlang des Vektors und prüft auf Kollisionen
     * @param direction Bewegungsrichtung
     */
    public boolean Move(Vector2 direction) {
        boolean canMove = true;
        Vector2 newPosition = new Vector2(position.x + direction.x, position.y + direction.y);
        if(collider != null) {
            //Prüfe auf Kollision
            if(collider.isCollisionForPosition(newPosition)) {
                canMove = false;
            }
        }

        if(canMove && children != null) {
            for(GameObject c: children) {
                if(!c.Move(direction)) {
                    canMove = false;
                }
            }
        }

        if(canMove) {
            position = newPosition;
        }
        return canMove;
    }

    /**
     * Wird aufgerufen, wenn das Objekt mit einem anderen Collider kollidiert
     * @param other Das Objekt, mit dem kollidiert wurde
     */
    public void Collision(GameObject other) {
    }

    /**
     * Wird nach dem Update aufgerufen, wenn eine Wand berührt wurde
     */
    public void HitWall() {
    }

    public void draw(Console c) {
        if(!isVisible || ascii == null) return;
        int row = (int) Math.round(position.y);
        int column = (int) Math.round(position.x);

        for(int i = 0; i < ascii.length; i++) {
            int columnPointer = column;
            for(int j = 0; j < ascii[i].length; j++) {
                try {
                    c.field[row][columnPointer] = ascii[i][j];
                } catch (ArrayIndexOutOfBoundsException e) {
                    //Nicht zeichnen
                }
                columnPointer++;
            }
            row++;
        }
    }

    public void Destroy() {
        //TODO: Zerstören
        if(collider != null) {
            Collider.colliders.remove(collider);
        }

        if(children != null) {
            for(int i = 0; i < children.size(); i++) {
                children.get(i).Destroy();
                i--;
            }
        }

        if(parent == null) {
            Console.gameObjects.remove(this);
        } else {
            parent.children.remove(this);
        }
    }

    public void Turn() {
        for (int j = 0; j < ascii.length; j++) {
            for (int i = 0; i < ascii[j].length / 2; i++) {
                char temp = turnLetter(ascii[j][i]);
                ascii[j][i] = turnLetter(ascii[j][ascii[j].length - 1 - i]);
                ascii[j][ascii[j].length - 1 - i] = temp;
            }
        }
    }

    private char turnLetter(char l) {
        switch (l) {
            case '<':
                return '>';
            case '>':
                return '<';
            case '/':
                return '\\';
            case '\\':
                return '/';
            case ')':
                return '(';
            case '(':
                return ')';
            default:
                return l;
        }
    }

}
