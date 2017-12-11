import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by midng on 2017/12/10.
 */
public class TankClient extends Frame{
    //以后需要多处改变的量可以定义为常量
    public static final int GAME_WIDTH = 800;
    public static final int GMME_HEIGHT = 600;

    int x = 50,y = 50;
    //背后虚拟图片，用来解决双缓冲问题
    Image offScreenImage = null;
    Tank myTank = new Tank(50,50);

    public void lauchFrame(){
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

    public static void main (String[] args){
        TankClient tc = new TankClient();
        tc.lauchFrame();
    }

    @Override
    public void paint(Graphics g) {
        myTank.draw(g);
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

}
