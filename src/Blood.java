import java.awt.*;

/**
 * 血块类
 */
public class Blood {
    int x,y,w,h;
    TankClient tc;
    int step = 0;

    private boolean live = true;
    // 指明血块运动的轨迹，由pos中各个点构成
    private  int[][] pos= {
        {350,300},{360,300},{375,275},{400,200},{360,270},{365,290},{340,220}
    };
    public Blood(){
        x = pos[0][0];
        y = pos[0][1];
        w = h = 15;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void draw(Graphics g){
        if(!live) return;
        Color c = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillRect(x,y,w,h);
        g.setColor(c);

        move();
    }

    public void move(){
        step++;
        if(step == pos.length){
            step = 0;
        }
        x = pos[step][0];
        y = pos[step][1];
    }

    public Rectangle getRect(){
        return  new Rectangle(x,y,w,h);
    }
}
