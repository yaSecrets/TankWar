import java.awt.*;

/**
 * Created by Administrator on 2017/12/24.
 */
public class Wall {
    //坐上角位置以及宽和高
    int x,y,w,h;
    TankClient tc;
    public Wall(int x, int y, int w, int h,TankClient tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }

    public void draw(Graphics g){
        g.fillRect(x,y,w,h);
    }

    public Rectangle getRect(){
        return  new Rectangle(x,y,w,h);
    }
}
