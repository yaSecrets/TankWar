import java.awt.*;
import java.util.List;

/**
 * Created by mding on 2017/12/11.
 */
public class Missile {
    public static final int XSPEED = 10;
    public static final int YSPEED = 10;
    public static final int WIDTH = 10;//子弹宽度
    public static final int HEIGHT = 10;//子弹高度
    //子弹的位置
    int x,y;
    Tank.Direction dir;
    //子弹也有好坏之分，自己人是不能打自己人的
    private boolean good;

    //子弹是否存在
    private boolean live = true;
    //持有client引用
    private TankClient tc;
    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public Missile(int x,int y,boolean good,Tank.Direction dir,TankClient tc){
        this(x,y,dir);
        this.good = good;
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

    public Tank.Direction getDir() {
        return dir;
    }

    public void setDir(Tank.Direction dir) {
        this.dir = dir;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
    public void draw(Graphics g){
        if(!live){
            tc.missiles.remove(this);
            return;
        }
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
        if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GMME_HEIGHT){
            tc.missiles.remove(this);
        }
    }
    // 判断子弹是否存在
    public boolean isLive(){
        return live;
    }
    //获取子弹自己
    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }
    //打中一辆坦克
    public boolean hitTank(Tank t){
        if(this.live && this.getRect().intersects(t.getRect()) && t.isLive()&&this.good != t.isGood() ){
            t.setLive(false);
            this.live = false;
            Explode  e = new Explode(x,y,tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }

    public  boolean hitTanks(List<Tank> tanks){
        for (int i = 0;i<tanks.size();i++){
            if(hitTank(tanks.get(i))){
                return  true;
            }
        }
        return  false;
    }
    //子弹不能穿墙
    public boolean hitWall(Wall w){
        if(this.live && this.getRect().intersects(w.getRect())){
            this.live = false;
            return true;
        }
        return false;
    }
}
