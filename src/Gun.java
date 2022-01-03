import java.awt.event.KeyEvent;

public class Gun extends GameObject{

    int direction = 1;

    Gun() {
        ascii = new char[][] {
                {',','_', '_','_','_',','},
                {'/','/','\'',' ',' ',' '},
                {'~',' ', ' ',' ',' ',' '}
        };
        isVisible = true;
    }

    @Override
    public void Update() {
        if(Input.GetButtonDown(KeyEvent.VK_SPACE)) {
            Projectile p;
            if(direction < 0) {
                p = new Projectile(new Vector2(position.x - 1, position.y), direction);
            } else {
                p = new Projectile(new Vector2(position.x + 6, position.y), direction);
            }
            p.player = true;
        }
    }

    @Override
    public void Turn() {
        super.Turn();
        direction *= -1;
    }
}
