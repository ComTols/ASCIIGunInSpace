import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Console extends JFrame{
    private JTextArea textArea1;
    private JPanel panel1;

    public static int columns;
    public static int rows;

    public char[][] field;
    public static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    public static Score score = new Score();

    private int colorIndex = 0;
    private Color[] colors = new Color[] {Color.ORANGE, Color.GREEN, Color.BLUE, Color.RED, Color.WHITE};


    public Console(int width, int height) {
        super();

        panel();
        txtArea();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel1);
        setPreferredSize(new Dimension(width, height));
        setResizable(true);

        columns = (width / 7) - 1;
        rows = (height / 15) - 2;
        field = new char[rows][columns];

        initialiseGameObjects();

        pack();
        setVisible(true);
    }

    private void panel() {
        panel1.setFocusable(true);
        panel1.requestFocusInWindow();
        panel1.setFocusTraversalKeysEnabled(false);
        panel1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                Input.KeyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Input.KeyReleased(e.getKeyCode());
            }
        });
    }

    private void txtArea() {
        textArea1.setBackground(Color.BLACK);
        textArea1.setForeground(Color.WHITE);
        textArea1.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea1.setEditable(false);
        textArea1.setHighlighter(null);
    }

    public void Update() {
        try {
            for(GameObject g : gameObjects) {
                updateChild(g);
            }
        } catch (ConcurrentModificationException e) {
        }

        if(Input.GetButtonDown(KeyEvent.VK_C)) {
            textArea1.setForeground(colors[colorIndex]);
            colorIndex++;
            colorIndex %= colors.length;
        }
    }

    public void LastUpdate() {
        try {
            for(GameObject g : gameObjects) {
                colliders(g);
            }
        } catch (ConcurrentModificationException e) {

        }

    }

    private void initialiseGameObjects() {
        gameObjects.add(new Player());
        gameObjects.add(new RockSpawner());
        gameObjects.add(new ShipSpawner());
        gameObjects.add(score);
    }

    public void draw() {
        //Bereinigen
        for (int row = 0; row < field.length; row++) {
            for (int column = 0; column < field[row].length; column++) {
                field[row][column] = ' ';
            }
        }

        //Objekte zeichnen
        try {
            for(GameObject g: gameObjects) {
                drawChild(g);
            }
        } catch (ConcurrentModificationException e) {

        }

        //Rahmen
        for (int row = 0; row < field.length; row++) {
            if (row == 0) {
                char[] score = Console.score.toString().toCharArray();
                for (int column = 0; column < score.length; column++) {
                    field[row][column] = score[column];
                }
                for (int column = 10; column < field[row].length; column++) {
                    field[row][column] = '#';
                }
            } else if (row == rows - 1) {
                for (int column = 0; column < field[row].length; column++) {
                    field[row][column] = '#';
                }
            } else {
                field[row][0] = '#';
                field[row][columns - 1] = '#';
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < field.length; row++) {
            for (int column = 0; column < field[row].length; column++) {
                stringBuilder.append(field[row][column]);
            }
            stringBuilder.append("\n");
        }
        textArea1.setText(stringBuilder.toString());
    }

    public void gameOver() {
        char[][] gameOverAscii = new char[17][40];
        gameOverAscii[0] = "|--------------------------------------|".toCharArray();
        gameOverAscii[1] = "| ######      ###    ##     ## ########|".toCharArray();
        gameOverAscii[2] = "|##    ##    ## ##   ###   ### ##      |".toCharArray();
        gameOverAscii[3] = "|##         ##   ##  #### #### ##      |".toCharArray();
        gameOverAscii[4] = "|##   #### ##     ## ## ### ## ######  |".toCharArray();
        gameOverAscii[5] = "|##    ##  ######### ##     ## ##      |".toCharArray();
        gameOverAscii[6] = "|##    ##  ##     ## ##     ## ##      |".toCharArray();
        gameOverAscii[7] = "| ######   ##     ## ##     ## ########|".toCharArray();
        gameOverAscii[8] = "|                                      |".toCharArray();
        gameOverAscii[9] = "| #######  ##     ## ######## ######## |".toCharArray();
        gameOverAscii[10]= "|##     ## ##     ## ##       ##     ##|".toCharArray();
        gameOverAscii[11]= "|##     ## ##     ## ##       ##     ##|".toCharArray();
        gameOverAscii[12]= "|##     ## ##     ## ######   ######## |".toCharArray();
        gameOverAscii[13]= "|##     ##  ##   ##  ##       ##   ##  |".toCharArray();
        gameOverAscii[14]= "|##     ##   ## ##   ##       ##    ## |".toCharArray();
        gameOverAscii[15]= "| #######     ###    ######## ##     ##|".toCharArray();
        gameOverAscii[16]= "|--------------------------------------|".toCharArray();

        int row = (rows - gameOverAscii.length) /2;
        int column = (columns - gameOverAscii[0].length) /2;

        for(int i = 0; i < gameOverAscii.length; i++) {
            int columnPointer = column;
            for(int j = 0; j < gameOverAscii[i].length; j++) {
                try {
                    field[row][columnPointer] = gameOverAscii[i][j];
                } catch (ArrayIndexOutOfBoundsException e) {
                    //Nicht zeichnen
                }
                columnPointer++;
            }
            row++;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (row = 0; row < field.length; row++) {
            for (column = 0; column < field[row].length; column++) {
                stringBuilder.append(field[row][column]);
            }
            stringBuilder.append("\n");
        }
        textArea1.setText(stringBuilder.toString());
        textArea1.setForeground(Color.RED);
    }

    private void drawChild(GameObject g) {
        if (g.children != null) {
            GameObject[] c = g.children.toArray(new GameObject[0]);
            for (GameObject child : c) {
                drawChild(child);
            }
        }
        g.draw(this);
    }

    private void updateChild(GameObject g) {
        if (g.children != null) {
            GameObject[] c = g.children.toArray(new GameObject[0]);
            for (GameObject child : c){
                updateChild(child);
            }
        }
        g.Update();
    }

    private void colliders(GameObject g) {
        if (g.children != null) {
            GameObject[] c = g.children.toArray(new GameObject[0]);
            for (GameObject child : c){
                colliders(child);
            }
        }
        if(g.collider != null) {
            g.collider.Update();
        }
    }
}
