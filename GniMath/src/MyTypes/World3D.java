package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *三维坐标系世界                    *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class World3D extends Canvas{
    protected boolean ChangeMode=true;//模式选择
    protected boolean Pressed=false;//触摸按下
    protected double Proportion;//缩放比例
    protected int Xplus,Yplus;//坐标平移量
    protected double Angle1,Angle2;//角度参数
    protected int OldPressX,OldPressY;//最早按下的屏幕坐标
    protected double AngleBox1,AngleBox2;//临时角度参数(For Test Box)
    //********************构造函数********************//
    public World3D() {
        this.setTitle("3D坐标系");
        Proportion=50;
        Xplus=this.getWidth()/2;
        Yplus=this.getHeight()/2;
        AngleBox1=Angle1=Math.toRadians(-125);
        AngleBox2=Angle2=Math.toRadians(-65);
    }
    //********************重构虚函数********************//
    protected void paint(Graphics g) {
        InitScreen(g);//初始化屏幕
        if(Pressed){
            g.setColor(255,0,0);
            DrawBox(g);
        }
        else{
            g.setColor(0,0,0);
            DrawBox(g);
        }
    }
    public void pointerDragged(int x,int y){
        this.setTitle("拖动旋转中...");
        AngleBox1=Angle1+Math.PI*((double)OldPressX-(double)x)/(double)this.getWidth();
        AngleBox2=Angle2+Math.PI*((double)OldPressY-(double)y)/(double)this.getWidth();
        repaint();
    }
    public void pointerPressed(int x,int y){
        this.setTitle("请拖动旋转");
        Pressed=true;
        OldPressX=x;
        OldPressY=y;
        repaint();
    }
    public void pointerReleased(int x,int y){
        this.setTitle("3D坐标系");
        Pressed=false;
        Angle1=AngleBox1;
        Angle2=AngleBox2;
        repaint();
    }
    public void keyReleased(int key){//按按键改变坐标范围
        ChangeRange(key);
    }
    //********************自定义函数********************//
    protected double GetSx(double fx,double fy,double fz){//由3维坐标转化至2维坐标的x
        return (-fx*Math.sin(Angle1)+fy*Math.cos(Angle1))*Proportion+Xplus;
    }
    protected double GetSy(double fx,double fy,double fz){//由3维坐标转化至2维坐标的y
        return (-fx*Math.cos(Angle2)*Math.cos(Angle1)-fy*Math.cos(Angle2)*Math.sin(Angle1)+fz*Math.sin(Angle2))*Proportion+Yplus;
    }
    protected void Line3D(Graphics g,double x1,double y1,double z1,double x2,double y2,double z2){//绘制三维直线
        g.drawLine((int)GetSx(x1,y1,z1),(int)GetSy(x1,y1,z1),(int)GetSx(x2,y2,z2),(int)GetSy(x2,y2,z2));
    }
    protected void InitScreen(Graphics g){//初始化界面
        SetColor(g,0);//设定背景色
        g.fillRect(0,0,this.getWidth(),this.getHeight());//初始化背景
    }
    protected void SetColor(Graphics g,int Type){//绘图颜色设定函数
        switch(Type){
            case 0:g.setColor(255,255,255);break;//白色(背景色)
            case 1:g.setColor(0,0,0);break;//黑色(默认线色)
            case 2:g.setColor(255,0,0);break;//红色
            case 3:g.setColor(0,255,0);break;//绿色
            case 4:g.setColor(0,0,255);break;//蓝色
            case -1:g.setColor(0,0,0);break;//黑色(图线色)
            case -2:g.setColor(0,255,0);break;//浅绿色(范围线)
            case -3:g.setColor(0,127,0);break;//深绿色(信息色)
            case -4:g.setColor(255,0,0);break;//红色(范围信息色)
            default:g.setColor(0,0,0);break;//默认色(黑色)
        }
    }
    protected void ChangeRange(int Key){//改变坐标范围函数
        //int Key ---- 按下的按键
        int code=this.getGameAction(Key);
        if(ChangeMode){//平移模式
            switch(code){
                case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                case Canvas.UP:Yplus-=this.getHeight()/16;repaint();break;
                case Canvas.DOWN:Yplus+=this.getHeight()/16;repaint();break;
                case Canvas.LEFT:Xplus-=this.getWidth()/16;repaint();break;
                case Canvas.RIGHT:Xplus+=this.getWidth()/16;repaint();break;
            }
        }
        else{//旋转
            switch(code){
                case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                case Canvas.UP:Angle2-=Math.PI/16;repaint();break;
                case Canvas.DOWN:Angle2+=Math.PI/16;repaint();break;
                case Canvas.LEFT:Angle1-=Math.PI/16;repaint();break;
                case Canvas.RIGHT:Angle1+=Math.PI/16;repaint();break;
            }
        }
    }
    //********************3D测试盒函数********************//
    private double GetSxForBox(double fx,double fy,double fz){
        return (-fx*Math.sin(AngleBox1)+fy*Math.cos(AngleBox1))*64+this.getWidth()/2;
    }
    private double GetSyForBox(double fx,double fy,double fz){
        return (-fx*Math.cos(AngleBox2)*Math.cos(AngleBox1)-fy*Math.cos(AngleBox2)*Math.sin(AngleBox1)+fz*Math.sin(AngleBox2))*64+this.getHeight()/2;
    }
    private void Line3DForBox(Graphics g,int x1,int y1,int z1,int x2,int y2,int z2){
        g.drawLine((int)GetSxForBox(x1,y1,z1),(int)GetSyForBox(x1,y1,z1),(int)GetSxForBox(x2,y2,z2),(int)GetSyForBox(x2,y2,z2));
    }
    protected void DrawBox(Graphics g){
        Line3DForBox(g,0,0,0,0,0,1);
        Line3DForBox(g,0,1,0,0,1,1);
        Line3DForBox(g,1,1,0,1,1,1);
        Line3DForBox(g,1,0,0,1,0,1);
        Line3DForBox(g,0,0,1,0,1,1);
        Line3DForBox(g,0,1,1,1,1,1);
        Line3DForBox(g,1,1,1,1,0,1);
        Line3DForBox(g,1,0,1,0,0,1);
        Line3DForBox(g,0,0,0,0,1,0);
        Line3DForBox(g,0,1,0,1,1,0);
        Line3DForBox(g,1,1,0,1,0,0);
        Line3DForBox(g,1,0,0,0,0,0);
        Line3DForBox(g,1,1,0,0,0,0);
        Line3DForBox(g,1,0,0,0,1,0);
        g.drawString("x",(int)GetSxForBox(1,0,0),(int)GetSyForBox(1,0,0),Graphics.TOP | Graphics.LEFT);
        g.drawString("y",(int)GetSxForBox(0,1,0),(int)GetSyForBox(0,1,0),Graphics.TOP | Graphics.LEFT);
        g.drawString("z",(int)GetSxForBox(0,0,1),(int)GetSyForBox(0,0,1),Graphics.TOP | Graphics.LEFT);
    }
}
