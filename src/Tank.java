import java.awt.*;
import java.awt.event.KeyEvent;

/**建立Tank类
 * 为Tank类添加成员变量x，y
 * 添加draw方法
 * 添加处理按键的方法
 * 根据Tank类修改TankClient
 * Created by mding on 2017/12/10.
 */
public class Tank {
    public static final int XSPEED = 5;
    public static final int YSPEED = 5;
    private int x,y;
    private boolean bL = false,bU = false,bR = false,bD = false;
    enum Direction {L,R,U,D,LU,LD,RU,RD,STOP};

    private Direction dir = Direction.STOP;
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void draw(Graphics g){
        //窗口重画时会自动调用paint方法
        //获取前景色
        Color c = g.getColor();
        g.setColor(Color.RED);
        //设置圆的位置及大小，fillOVal使用当前颜色填充外接指定矩形框的椭圆。
        g.fillOval(x,y,30,30);
        //设回
        g.setColor(c);
        move();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        locateDirection();
    }
    //移动方向
    void move(){
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
    //定位方向
    void locateDirection(){
        if(bL && !bU && !bR && !bD) dir = Direction.L;
        else if(!bL && !bU && bR && !bD) dir = Direction.R;
        else if(!bL && bU && !bR && !bD) dir = Direction.U;
        else if(!bL && !bU && !bR && bD) dir = Direction.D;
        else if(bL && bU && !bR && !bD) dir = Direction.LU;
        else if(bL && !bU && !bR && bD) dir = Direction.LD;
        else if(!bL && bU && bR && !bD) dir = Direction.RU;
        else if(bL && !bU && bR && bD) dir = Direction.RD;
        else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
    }
}
