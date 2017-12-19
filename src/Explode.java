import java.awt.*;

/**爆炸类
 * Created by Administrator on 2017/12/19.
 */
public class Explode {
    int x,y;
    //是否存活
    private boolean live = true;
    private TankClient tc;
    //不同直径的圆模拟爆炸
    int[] diameter = {4,7,12,18,26,32,49,30,14,6};
    //爆炸步骤
    int step = 0;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g){
        if (!live){
            tc.explodes.remove(this);
            return;
        }

        if(step == diameter.length){
            live = false;
            step = 0;
            return;
        }

        Color c= g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x,y,diameter[step],diameter[step]);
        g.setColor(c);

        step++;
    }
}
