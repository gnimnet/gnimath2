package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *二维坐标系世界                    *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class World2D extends Canvas{
    //********************坐标系变量********************//
    protected double Xup;//x的上限
    protected double Xdown;//x的下限
    protected double Ymax;//函数最大值
    protected double Ymin;//函数最小值
    private double dfx;//fx值误差
    private double dfy;//fy值误差
    private int Window_W;//屏幕的宽度
    private int Window_H;//屏幕的高度 
    private int Axis_X;//y轴屏幕x值
    private int Axis_Y;//x轴屏幕y值
    protected boolean ChangeMode=true;//坐标变换模式
    //********************构造函数********************//
    public World2D() {
        this.setTitle("2D坐标系");
        Window_W=this.getWidth();//获得屏幕宽度
        Window_H=this.getHeight();//获得屏幕高度
    }
    //********************虚函数入口********************//
    protected void paint(Graphics g){//默认绘制函数
        SetRangeTitle();//设定标题为坐标范围
        InitScreen(g);//初始化背景
        DrawLineXY(g);//绘制坐标轴
    }
    public void pointerDragged(int x,int y){//显示触摸选中点的坐标值及误差
        SetPressTitle(x,y);
    }
    public void pointerPressed(int x,int y){//显示触摸选中点的坐标值及误差
        SetPressTitle(x,y);
    }
    public void pointerReleased(int x,int y){//触摸离开恢复显示坐标范围
        SetRangeTitle();
    }
    public void keyReleased(int key){//按按键改变坐标范围
        ChangeRange(key);
    }
    //********************坐标转换函数********************//
    protected double SxtoFx(double Sx){//从屏幕x坐标获得函数x值
        return Sx*(Xup-Xdown)/(Window_W)+Xdown;
    }
    protected double SytoFy(double Sy){//从屏幕y坐标获得函数y值
        if(Ymax!=Ymin)
            return (Window_H-Sy)*(Ymax-Ymin)/Window_H+Ymin;
        else
            return Ymax;
    }
    protected double FxtoSx(double Fx){//从函数x值获得屏幕x坐标
        return (Fx-Xdown)*Window_W/(Xup-Xdown);
    }
    protected double FytoSy(double Fy){//从函数y值获得屏幕y坐标
        if(Ymax!=Ymin)
            return Window_H-(Fy-Ymin)*Window_H/(Ymax-Ymin);
        else
            return ((double)Window_H)/2;
    }
    //********************自定义函数********************//
    protected void InitScreen(Graphics g){//初始化界面
        SetColor(g,0);//设定背景色
        g.fillRect(0,0,this.getWidth(),this.getHeight());//初始化背景
    }
    protected void DrawLineXY(Graphics g){//绘制坐标轴函数
        SetColor(g,1);//设定坐标轴线色
        //计算单位像素的函数间距
        dfx=(Xup-Xdown)/(Window_W);
        dfy=(Ymax-Ymin)/(Window_H);
        //绘制坐标轴
        if(Xup>0 && Xdown<0)
            Axis_X=(int)((java.lang.Math.abs(Xdown)*Window_W)/(Xup-Xdown));
        else
            Axis_X=5;
        if(Ymax>0 && Ymin<0)
            Axis_Y=(int)((java.lang.Math.abs(Ymax)*Window_H)/(Ymax-Ymin));
        else
            Axis_Y=Window_H-25;
        g.drawLine(Axis_X,0,Axis_X,Window_H);
        g.drawLine(Axis_X-2,3,Axis_X,0);
        g.drawLine(Axis_X+2,3,Axis_X,0);
        g.drawLine(0,Axis_Y,Window_W,Axis_Y);
        g.drawLine(Window_W-3,Axis_Y-2,Window_W,Axis_Y);
        g.drawLine(Window_W-3,Axis_Y+2,Window_W,Axis_Y);
        int tmp;
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_PLAIN,Font.SIZE_SMALL));
        //X轴参照线
        for(tmp=Axis_X;tmp<=Window_W-5;tmp+=5)
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-2);
        for(tmp=Axis_X;tmp>=0;tmp-=5)
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-2);
        for(tmp=Axis_X+50;tmp<=Window_W-5;tmp+=50){
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-5);
            if(ChangeMode)
                SetColor(g,-1);//变换模式1色
            else
                SetColor(g,-2);//变换模式2色
            g.drawString(MyFunction.CutNumStr(SxtoFx(tmp)),tmp,Axis_Y-5,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
        for(tmp=Axis_X-50;tmp>=0;tmp-=50){
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-5);
            if(ChangeMode)
                SetColor(g,-1);//变换模式1色
            else
                SetColor(g,-2);//变换模式2色
            g.drawString(MyFunction.CutNumStr(SxtoFx(tmp)),tmp,Axis_Y-5,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
        //Y轴参照线
        for(tmp=Axis_Y;tmp<=Window_H;tmp+=5)
            g.drawLine(Axis_X,tmp,Axis_X+2,tmp);
        for(tmp=Axis_Y;tmp>=5;tmp-=5)
            g.drawLine(Axis_X,tmp,Axis_X+2,tmp);
        for(tmp=Axis_Y+50;tmp<=Window_H;tmp+=50){
            g.drawLine(Axis_X,tmp,Axis_X+5,tmp);
            if(ChangeMode)
                SetColor(g,-1);//变换模式1色
            else
                SetColor(g,-2);//变换模式2色
            g.drawString(MyFunction.CutNumStr(SytoFy(tmp)),Axis_X+5,tmp,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
        for(tmp=Axis_Y-50;tmp>=5;tmp-=50){
            g.drawLine(Axis_X,tmp,Axis_X+5,tmp);
            if(ChangeMode)
                SetColor(g,-1);//变换模式1色
            else
                SetColor(g,-2);//变换模式2色
            g.drawString(MyFunction.CutNumStr(SytoFy(tmp)),Axis_X+5,tmp,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
    }
    protected void SetColor(Graphics g,int Type){//绘图颜色设定函数
        switch(Type){
            case 0:g.setColor(255,255,255);break;//白色(背景色)
            case 1:g.setColor(0,0,0);break;//黑色(默认线色)
            case 2:g.setColor(255,0,0);break;//红色
            case 3:g.setColor(0,255,0);break;//绿色
            case 4:g.setColor(0,0,255);break;//蓝色
            case -1:g.setColor(0,0,0);break;//黑色(变换模式1字色)
            case -2:g.setColor(0,255,0);break;//浅绿色(变换模式2字色)
            case -3:g.setColor(0,127,0);break;//深绿色(信息色)
            default:g.setColor(0,0,0);break;//默认色(黑色)
        }
    }
    protected void SetRangeTitle(){//显示坐标范围
        this.setTitle("x:["+MyFunction.CutNumStr(Xdown)+","+MyFunction.CutNumStr(Xup)+
                "],y:["+MyFunction.CutNumStr(Ymin)+","+MyFunction.CutNumStr(Ymax)+"]");
    }
    protected void SetPressTitle(int Sx,int Sy){//显示指定坐标及其误差
        //int Sx ---- 屏幕x坐标
        //int Sy ---- 屏幕y坐标
        this.setTitle("("+MyFunction.CutNumStr(SxtoFx(Sx))+"+"+MyFunction.CutNumStr(dfx)+","
            +MyFunction.CutNumStr(SytoFy(Sy))+"+"+MyFunction.CutNumStr(dfy)+")");
    }
    protected void ChangeRange(int Key){//改变坐标范围函数
        //int Key ---- 按下的按键
        int code=this.getGameAction(Key);
        double Xwidth=Xup-Xdown;//获取计算宽度
        double Yheight=Ymax-Ymin;//获取计算高度
        if(ChangeMode){//平移模式
            switch(code){
                case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                case Canvas.UP:Ymax-=Yheight/4;Ymin-=Yheight/4;repaint();break;
                case Canvas.DOWN:Ymax+=Yheight/4;Ymin+=Yheight/4;repaint();break;
                case Canvas.LEFT:Xup+=Xwidth/4;Xdown+=Xwidth/4;repaint();break;
                case Canvas.RIGHT:Xup-=Xwidth/4;Xdown-=Xwidth/4;repaint();break;
            }
        }
        else{//缩放模式
            switch(code){
                case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                case Canvas.UP:Xup-=Xwidth/4;Xdown+=Xwidth/4;repaint();break;
                case Canvas.DOWN:Xup+=Xwidth/2;Xdown-=Xwidth/2;repaint();break;
                case Canvas.LEFT:Ymax+=Yheight/2;Ymin-=Yheight/2;repaint();break;
                case Canvas.RIGHT:Ymax-=Yheight/4;Ymin+=Yheight/4;repaint();break;
            }
        }
    }
    protected void GetRange(String Range)throws MyError{
        int i=Range.indexOf('|');
        if(i==-1)
            throw new MyError(15);
        SetRange(Range.substring(0,i),true);
        SetRange(Range.substring(i+1),false);
    }
    protected void SetRange(String OneRange,boolean IsX)throws MyError{
        int i,j=0,k=0,l=0;
        for(i=0;i<OneRange.length();i++){
            if(OneRange.charAt(i)=='(')
                j++;
            if(OneRange.charAt(i)==')')
                k++;
            if(OneRange.charAt(i)==',')
                l++;
        }
        if(j!=1||k!=1||l!=1)//范围信息有误
            throw new MyError(15);
        if(OneRange.indexOf('(')!=0)
            throw new MyError(15);
        if(OneRange.indexOf(')')!=OneRange.length()-1)
            throw new MyError(15);
        if(IsX){
            Xup=MyFunction.IsNum(OneRange.substring(OneRange.indexOf(',')+1,OneRange.length()-1));//获得x的上限
            Xdown=MyFunction.IsNum(OneRange.substring(1,OneRange.indexOf(',')));//获得x的下限
            if(Xup<=Xdown)
                throw new MyError(16);
        }
        else{
            Ymax=MyFunction.IsNum(OneRange.substring(OneRange.indexOf(',')+1,OneRange.length()-1));//获得y的上限
            Ymin=MyFunction.IsNum(OneRange.substring(1,OneRange.indexOf(',')));//获得y的下限
            if(Ymax<=Ymin)
                throw new MyError(17);
        }
    }
}
