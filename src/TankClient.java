import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类的作用是坦克游戏的主窗口
 */
public class TankClient extends Frame{
    //以后需要多处改变的量可以定义为常量
    public static final int GAME_WIDTH = 800;
    public static final int GMME_HEIGHT = 600;

    int x = 50,y = 50;
    //背后虚拟图片，用来解决双缓冲问题
    Image offScreenImage = null;
    //Tank实体类
    Tank myTank = new Tank(50,50,true,Tank.Direction.STOP,this);
    //定义两堵墙
    Wall w1 = new Wall(100,200,20,150,this);
    Wall w2 = new Wall(300,100,300,20,this);
    /*Tank enemyTank = new Tank(100,100,false,this);*/
    //爆照集合
    List<Explode> explodes = new ArrayList<>();
    //子弹实体类
    /* Missile m = null;*/
    List<Missile> missiles = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();

    Blood b = new Blood();
    @Override
    public void paint(Graphics g) {
        //指明子弹、爆炸、坦克数量、坦克生命值
        g.drawString("missile count:" + missiles.size(),10,50);
        g.drawString("explode count:" + explodes.size(),10,70);
        g.drawString("tanks count:" + tanks.size(),10,90);
        g.drawString("tanks life:" + myTank.getLife(),10,110);

        if(tanks.size() == 0){
            for (int i = 0;i < 5;i++){
                tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
            }
        }
        /*if(m != null) m.draw(g);*/
        //画子弹
        for (int i = 0;i < missiles.size();i++){
            Missile m = missiles.get(i);
            /*if(!m.isLive()) missiles.remove(m);*/
            /*m.hitTank(enemyTank);*/
            m.hitTanks(tanks);
            m.hitTank(myTank);
            m.hitWall(w1);
            m.hitWall(w2);
            m.draw(g);
        }
        // 画爆照效果
        for (int i = 0;i < explodes.size();i++){
            Explode e = explodes.get(i);
            e.draw(g);
        }
        //画坦克
        for (int i = 0;i < tanks.size();i++){
            Tank t = tanks.get(i);
            t.collidesWithWall(w1);
            t.collidesWithWall(w2);
            t.cpllidesWithTanks(tanks);
            t.draw(g);
        }
        myTank.draw(g);
        //吃血块满血
        myTank.eat(b);
        /*enemyTank.draw(g);*/
        w1.draw(g);
        w2.draw(g);
        b.draw(g);
    }

    /**
     * 本方法显示坦克主窗口
     */
    public void lauchFrame(){
        for (int i = 0;i < 10;i++){
            tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
        }
        //设置位置
        this.setLocation(200,100);
        //设置窗口大小
        this.setSize(GAME_WIDTH,GMME_HEIGHT);
        this.setTitle("TankWar");
        this.setBackground(Color.GREEN);
        //给窗口添加关闭的监听器
        this.addWindowListener(new WindowAdapter() {
            //使用匿名类条件：类要短小，不涉及将来的扩展、不涉及重要的业务逻辑
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //设置窗口大小不可变
        this.setResizable(false);
        this.addKeyListener(new keyMonitor());
        setVisible(true);
        //启动重画线程
        new Thread(new PaintThread()).start();
    }


    @Override
    public void update(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GMME_HEIGHT);
        }
        //获取背后图片的画笔
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GMME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    //内部类可以方便的访问包装类的方法，不方便公开的，只为包装类服务的类应当定义为内部类s
    private class PaintThread implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    repaint();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //实现键盘的监听
    private class keyMonitor extends KeyAdapter{
        //键盘按下事件
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }

    public static void main (String[] args){
        TankClient tc = new TankClient();
        tc.lauchFrame();
    }
}
