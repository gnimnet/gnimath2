package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *显示硬件信息Canvas测试            *
 ***********************************/
import javax.microedition.lcdui.*;
public class TestCanvas extends Canvas{
    public TestCanvas(){
        this.setTitle("屏幕信息");
    }
    protected void paint(Graphics g) {
        g.setColor(255,255,255);//设定背景色
        g.fillRect(0,0,this.getWidth(),this.getHeight());//初始化背景
        g.setColor(0,0,0);//设定字体色
        g.drawString("屏幕宽度:"+this.getWidth(),10,1*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        g.drawString("屏幕高度:"+this.getHeight(),10,2*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        if(this.hasPointerEvents())
            g.drawString("支持触摸:是",10,3*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("支持触摸:否",10,3*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        if(this.hasPointerMotionEvents())
            g.drawString("支持拖拽:是",10,4*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("支持拖拽:否",10,4*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
    }
}

