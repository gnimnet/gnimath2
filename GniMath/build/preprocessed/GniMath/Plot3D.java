package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *Plot3D绘制Canvas类                *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class Plot3D extends World3D{
    private String ComStr;//表达式
    private StrComTree ComTree;//计算树
    private double Xup,Xdown;//计算的x范围
    private double Ymax,Ymin;//计算的y范围
    private double[][]ArrayZ;//计算结果矩阵
    private double Zmax,Zmin;//z的变化范围
    private double StepX;//X步长
    private double StepY;//Y步长
    private boolean Pressed;//按下触摸屏
    private int PointX,PointY;//X,Y方向上的点数
    private boolean HaveErr;
    //********************构造函数********************//
    public Plot3D(String FuncStr,String Range,String MovXY) throws MyError{
        GetComTree(FuncStr);//生成计算树
        GetRange(Range);//获取坐标系范围
        InitInfo(MovXY);//获取偏移信息及缩放比
        PointX=10;
        PointY=10;
        GetArrayZ();//生成结果矩阵
        this.setTitle(this.ComStr);
    }
    //********************重构虚函数********************//
    protected void paint(Graphics g) {
        InitScreen(g);//初始化屏幕
        if(Pressed){
            g.setColor(255,0,0);
            DrawBox(g);
        }
        else{
            DrawFunc(g);
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
        this.setTitle(this.ComStr);
        Pressed=false;
        Angle1=AngleBox1;
        Angle2=AngleBox2;
        repaint();
    }
    /********************自定义内部函数********************/
    private void DrawFunc(Graphics g){
        DrawFuncGrid(g);//网格线绘制
        DrawRange(g);//范围绘制
        DrawInfo(g);//输出信息
    }
    private void DrawFuncGrid(Graphics g){
        SetColor(g,-1);
        for(int i=0;i<PointX;i++)
            for(int j=0;j<PointY;j++){
                this.Line3D(g,Xdown+i*StepX,Ymin+j*StepY,ArrayZ[i][j],
                        Xdown+(i+1)*StepX,Ymin+j*StepY,ArrayZ[i+1][j]);
                this.Line3D(g,Xdown+i*StepX,Ymin+j*StepY,ArrayZ[i][j],
                        Xdown+i*StepX,Ymin+(j+1)*StepY,ArrayZ[i][j+1]);
            }
        for(int m=0;m<PointX;m++)
            this.Line3D(g,Xdown+m*StepX,Ymax,ArrayZ[m][PointY],
                    Xdown+(m+1)*StepX,Ymax,ArrayZ[m+1][PointY]);
        for(int n=0;n<PointY;n++)
            this.Line3D(g,Xup,Ymin+n*StepY,ArrayZ[PointX][n],
                    Xup,Ymin+(n+1)*StepY,ArrayZ[PointX][n+1]);
        
    }
    private void DrawRange(Graphics g){
        SetColor(g,-2);
        Line3D(g,Xdown,Ymin,Zmin,Xdown,Ymin,Zmax);
        Line3D(g,Xdown,Ymax,Zmin,Xdown,Ymax,Zmax);
        Line3D(g,Xup,Ymax,Zmin,Xup,Ymax,Zmax);
        Line3D(g,Xup,Ymin,Zmin,Xup,Ymin,Zmax);
        Line3D(g,Xdown,Ymin,Zmax,Xdown,Ymax,Zmax);
        Line3D(g,Xdown,Ymax,Zmax,Xup,Ymax,Zmax);
        Line3D(g,Xup,Ymax,Zmax,Xup,Ymin,Zmax);
        Line3D(g,Xup,Ymin,Zmax,Xdown,Ymin,Zmax);
        Line3D(g,Xdown,Ymin,Zmin,Xdown,Ymax,Zmin);
        Line3D(g,Xdown,Ymax,Zmin,Xup,Ymax,Zmin);
        Line3D(g,Xup,Ymax,Zmin,Xup,Ymin,Zmin);
        Line3D(g,Xup,Ymin,Zmin,Xdown,Ymin,Zmin);
    }
    private void DrawInfo(Graphics g){
        SetColor(g,-3);
        if(!this.ChangeMode){
            if(HaveErr)
                g.drawString("旋转模式,计算中有错误",10,10,g.LEFT|g.TOP);
            else
                g.drawString("旋转模式",10,10,g.LEFT|g.TOP);
            SetColor(g,-4);
            g.drawString("x范围:"+MyFunction.CutNumStr(Xdown,5)+","+
                    MyFunction.CutNumStr(Xup,5),10,10+g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("y范围:"+MyFunction.CutNumStr(Ymin,5)+","+
                    MyFunction.CutNumStr(Ymax,5),10,12+2*g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("z范围:"+MyFunction.CutNumStr(Zmin,5)+","+
                    MyFunction.CutNumStr(Zmax,5),10,14+3*g.getFont().getHeight(),g.LEFT|g.TOP);
        }
        else{
            if(HaveErr)
                g.drawString("平移模式,计算中有错误",10,10,g.LEFT|g.TOP);
            else
                g.drawString("平移模式",10,10,g.LEFT|g.TOP);
            g.drawString("x",(int)GetSx(Xup,Ymin,Zmin),(int)GetSy(Xup,Ymin,Zmin),g.LEFT|g.TOP);
            g.drawString("y",(int)GetSx(Xdown,Ymax,Zmin),(int)GetSy(Xdown,Ymax,Zmin),g.LEFT|g.TOP);
            g.drawString("z",(int)GetSx(Xdown,Ymin,Zmax),(int)GetSy(Xdown,Ymin,Zmax),g.LEFT|g.TOP);
        }
    }
    private void GetComTree(String FuncStr) throws MyError{
        ComStr=MyFunction.DelSpace(MyFunction.MyTranStr(FuncStr));
        ComTree=new StrComTree(ComStr);
        ComTree.AddVar("x");
        ComTree.AddVar("y");
        ComTree.AddVar("pi",java.lang.Math.PI);//注册常量PI
        ComTree.AddVar("e",java.lang.Math.E);//注册常量E
    }
    private void InitInfo(String Info)throws MyError{
        int i,j=0,k=0,m=0,n=0;
        for(i=0;i<Info.length();i++){
            if(Info.charAt(i)=='(')
                j++;
            if(Info.charAt(i)==')')
                k++;
            if(Info.charAt(i)==',')
                m++;
            if(Info.charAt(i)=='|')
                n++;
        }
        if(j!=1||k!=1||m!=1||n!=1)//信息有误
            throw new MyError(55);
        i=Info.indexOf('(');
        j=Info.indexOf(',');
        m=Info.indexOf(')');
        n=Info.indexOf('|');
        if(i>j||j>m||m>n||(m!=n-1))
            throw new MyError(55);
        Xplus=(int)MyFunction.IsNum(Info.substring(i+1,j))+this.getWidth()/2;
        Yplus=(int)MyFunction.IsNum(Info.substring(j+1,m))+this.getHeight()/2;
        Proportion=(int)MyFunction.IsNum(Info.substring(n+1));
    }
    private void GetRange(String Range)throws MyError{
        int i=Range.indexOf('|');
        if(i==-1)
            throw new MyError(15);
        SetRange(Range.substring(0,i),true);
        SetRange(Range.substring(i+1),false);
    }
    private void SetRange(String OneRange,boolean IsX)throws MyError{
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
    private void GetArrayZ(){
        ArrayZ=new double[PointX+1][PointY+1];
        StepX=(Xup-Xdown)/(double)PointX;
        StepY=(Ymax-Ymin)/(double)PointY;
        Zmin=0;
        Zmax=0;
        HaveErr=false;
        for(int i=0;i<=PointX;i++)
            for(int j=0;j<=PointY;j++){
                try{
                    ComTree.SetVar("x",Xdown+i*StepX);
                    ComTree.SetVar("y",Ymin+j*StepY);
                    ArrayZ[i][j]=ComTree.ComputeTree(ComTree.Root);
                    if(ArrayZ[i][j]>Zmax)
                        Zmax=ArrayZ[i][j];
                    if(ArrayZ[i][j]<Zmin)
                        Zmin=ArrayZ[i][j];
                }catch(MyError Err){
                    HaveErr=true;
                }
            }
    }
    //********************供外部的接口********************//
    public void ToBeLarge(){
        this.Proportion=this.Proportion*1.5;
        repaint();
    }
    public void ToBeSmall(){
        this.Proportion=this.Proportion/1.5;
        repaint();
    }
    public void ToBeMore(){
        if(PointX<=Integer.MAX_VALUE/2 && PointY<=Integer.MAX_VALUE/2){
            PointX*=2;
            PointY*=2;
            GetArrayZ();//生成结果矩阵
            repaint();
        }
    }
    public void ToBeLess(){
        if(PointX>=2 && PointY>=2){
            PointX/=2;
            PointY/=2;
            GetArrayZ();//生成结果矩阵
            repaint();
        }
    }
}
