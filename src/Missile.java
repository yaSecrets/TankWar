import java.awt.*;

/**
 * Created by mding on 2017/12/11.
 */
public class Missile {
    public static final int XSPEED = 10;
    public static final int YSPEED = 10;
    public static final int WIDTH = 10;//子弹宽度
    public static final int HEIGHT = 10;//子弹高度
    int x,y;
    Tank.Direction dir;

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tank.Direction getDir() {
        return dir;
    }

    public void setDir(Tank.Direction dir) {
        this.dir = dir;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x,y,10,10);
        g.setColor(c);
        move();
    }

    private void move() {
        switch (dir){
            case L:
                x -= XSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }
    }
}
