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
    public static final int WIDTH = 30;//tank宽度
    public static final int HEIGHT = 30;//tank高度
    private int x,y;
    private boolean bL = false,bU = false,bR = false,bD = false;

    TankClient tc;
    enum Direction {L,R,U,D,LU,LD,RU,RD,STOP};
    //坦克方向
    private Direction dir = Direction.STOP;
    //炮筒方向
    private Direction ptDir = Direction.D;
    //区分tank好坏
    private boolean good;
    //定义坦克的生存状态
    private boolean live = true;
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Tank(int x, int y,boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
    }
    public Tank(int x,int y,boolean good,TankClient tc){
        this(x,y,good);
        this.tc = tc;
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
        if(!live) return;
        //窗口重画时会自动调用paint方法
        //获取前景色
        Color c = g.getColor();
        if(good) g.setColor(Color.RED);
        else g.setColor(Color.BLUE);
        //设置圆的位置及大小，fillOVal使用当前颜色填充外接指定矩形框的椭圆。
        g.fillOval(x,y,WIDTH,HEIGHT);
        //设回
        g.setColor(c);
        //画炮弹
        switch (ptDir) {
            case L:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
                break;
            case R:
                g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGHT/2,x+Tank.WIDTH,y+Tank.HEIGHT/2);
                break;
            case U:
                g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGHT/2,x+Tank.WIDTH/2,y);
                break;
            case D:
                g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGHT/2,x+Tank.WIDTH/2,y+Tank.HEIGHT);
                break;
            case LU:
                g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGHT/2,x,y);
                break;
            case LD:
                g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGHT/2,x,y+Tank.HEIGHT);
                break;
            case RU:
                g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGHT/2,x+Tank.WIDTH,y);
                break;
            case RD:
                g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGHT/2,x+Tank.WIDTH,y+Tank.HEIGHT);
                break;
        }
        move();
    }
    //键盘按下事件
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
    //键盘抬起事件
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_CONTROL:
                /*tc.m = fire();*/
                /*tc.missiles.add(fire());*/
                fire();
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
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
        //如果坦克不是停止的，则吧炮筒方向设为和坦克方向一致
        if(this.dir != Direction.STOP){
            this.ptDir = this.dir;
        }
        if(x < 0) x = 0;
        if(y < 30) y = 30;
        if(x+Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
        if(y+Tank.HEIGHT > TankClient.GMME_HEIGHT) y = TankClient.GMME_HEIGHT - Tank.HEIGHT;
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
        else if(!bL && !bU && bR && bD) dir = Direction.RD;
        else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
    }

    public Missile fire(){
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile m = new Missile(x,y,ptDir,this.tc);
        tc.missiles.add(m);
        return m;
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }
}
