package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *3维参数曲线绘制程序               *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class LineXYZ extends World3D{
    private String ComStrX;//表达式fx(t)
    private String ComStrY;//表达式fy(t)
    private String ComStrZ;//表达式fz(t)
    private StrComTree ComTreeX;//计算树x
    private StrComTree ComTreeY;//计算树y
    private StrComTree ComTreeZ;//计算树z
    private double Xup,Xdown;//计算的x范围
    private double Ymax,Ymin;//计算的y范围
    private double Zmax,Zmin;//计算的z范围
    private double Stept;//t的计算步长
    private int NumT;//可以计算出的T数目
    private double BeginT,EndT;//t的计算范围
    private double[][]ArrayT;//计算结果矩阵[N][3]
    private boolean Pressed;//按下触摸屏
    private boolean DrawHaveErr;
    public boolean InfoMode=false;
    //********************构造函数********************//
    public LineXYZ(String StrForComXYZ,String tInfoStr,String MovXY)throws MyError{
        GetComTree(StrForComXYZ);//生成计算树
        InitInfoT(tInfoStr);//初始化t的范围和变化率
        InitInfo(MovXY);//获取偏移信息及缩放比
        GetArrayT();
        this.setTitle("范围:("+MyFunction.CutNumStr(Xdown)+","+MyFunction.CutNumStr(Xup)+
                ")("+MyFunction.CutNumStr(Ymin)+","+MyFunction.CutNumStr(Ymax)+
                ")("+MyFunction.CutNumStr(Zmin)+","+MyFunction.CutNumStr(Zmax)+")");
    }
    //********************虚函数重构********************//
    protected void paint(Graphics g) {
        InitScreen(g);//初始化屏幕
        if(InfoMode){//信息模式
            this.setTitle("结果信息");
            SetColor(g,-3);
            if(!this.ChangeMode){
                g.drawString("fx(t)="+ComStrX,10,10+g.getFont().getHeight(),g.LEFT|g.TOP);
                g.drawString("fy(t)="+ComStrY,10,12+2*g.getFont().getHeight(),g.LEFT|g.TOP);
                g.drawString("fz(t)="+ComStrZ,10,14+3*g.getFont().getHeight(),g.LEFT|g.TOP);
                g.drawString("t范围:"+MyFunction.CutNumStr(BeginT,5)+","+
                        MyFunction.CutNumStr(EndT,5),10,16+4*g.getFont().getHeight(),g.LEFT|g.TOP);
                g.drawString("t步长:"+MyFunction.CutNumStr(Stept,5),10,18+5*g.getFont().getHeight(),g.LEFT|g.TOP);
            }
            else{
                g.drawString("x范围("+MyFunction.CutNumStr(Xdown,5)+","+MyFunction.CutNumStr(Xup,5)+
                        ")",10,10+g.getFont().getHeight(),g.LEFT|g.TOP);
                g.drawString("y范围("+MyFunction.CutNumStr(Ymin,5)+","+MyFunction.CutNumStr(Ymax,5)+
                        ")",10,12+2*g.getFont().getHeight(),g.LEFT|g.TOP);
                g.drawString("z范围("+MyFunction.CutNumStr(Zmin,5)+","+MyFunction.CutNumStr(Zmax,5)+
                        ")",10,14+3*g.getFont().getHeight(),g.LEFT|g.TOP);
            }
        }
        else{
            this.setTitle("范围:("+MyFunction.CutNumStr(Xdown)+","+MyFunction.CutNumStr(Xup)+
                    ")("+MyFunction.CutNumStr(Ymin)+","+MyFunction.CutNumStr(Ymax)+
                    ")("+MyFunction.CutNumStr(Zmin)+","+MyFunction.CutNumStr(Zmax)+")");
            if(Pressed){
                g.setColor(255,0,0);
                DrawBox(g);
            }
            else{
                DrawRange(g);
                DrawFunc(g);
                DrawInfo(g);
            }
        }
    }
    public void pointerDragged(int x,int y){
        if(!InfoMode){
            this.setTitle("拖动旋转中...");
            AngleBox1=Angle1+Math.PI*((double)OldPressX-(double)x)/(double)this.getWidth();
            AngleBox2=Angle2+Math.PI*((double)OldPressY-(double)y)/(double)this.getWidth();
            repaint();
        }
    }
    public void pointerPressed(int x,int y){
        if(!InfoMode){
            this.setTitle("请拖动旋转");
            Pressed=true;
            OldPressX=x;
            OldPressY=y;
            repaint();
        }
    }
    public void pointerReleased(int x,int y){
        if(!InfoMode){
            this.setTitle("范围:("+MyFunction.CutNumStr(Xdown)+","+MyFunction.CutNumStr(Xup)+
                    ")("+MyFunction.CutNumStr(Ymin)+","+MyFunction.CutNumStr(Ymax)+
                    ")("+MyFunction.CutNumStr(Zmin)+","+MyFunction.CutNumStr(Zmax)+")");
            Pressed=false;
            Angle1=AngleBox1;
            Angle2=AngleBox2;
            repaint();
        }
    }
    //********************自定义内部函数********************//
    private void GetComTree(String FuncStr) throws MyError{
        int i,j=0;
        for(i=0;i<FuncStr.length();i++){
            if(FuncStr.charAt(i)=='|')
                j++;
        }
        if(j!=2)
            throw new MyError(84);
        i=FuncStr.indexOf('|');
        j=FuncStr.lastIndexOf('|');
        ComStrX=MyFunction.DelSpace(MyFunction.MyTranStr(FuncStr.substring(0,i)));
        ComStrY=MyFunction.DelSpace(MyFunction.MyTranStr(FuncStr.substring(i+1,j)));
        ComStrZ=MyFunction.DelSpace(MyFunction.MyTranStr(FuncStr.substring(j+1)));
        ComTreeX=new StrComTree(ComStrX);
        ComTreeY=new StrComTree(ComStrY);
        ComTreeZ=new StrComTree(ComStrZ);
        ComTreeX.AddVar("t");
        ComTreeY.AddVar("t");
        ComTreeZ.AddVar("t");
        ComTreeX.AddVar("pi",java.lang.Math.PI);//注册常量PI
        ComTreeX.AddVar("e",java.lang.Math.E);//注册常量E
        ComTreeY.AddVar("pi",java.lang.Math.PI);//注册常量PI
        ComTreeY.AddVar("e",java.lang.Math.E);//注册常量E
        ComTreeZ.AddVar("pi",java.lang.Math.PI);//注册常量PI
        ComTreeZ.AddVar("e",java.lang.Math.E);//注册常量E
    }
    private void InitInfoT(String tInfo)throws MyError{
        int i,j=0,k=0,m=0,n=0;
        for(i=0;i<tInfo.length();i++){
            if(tInfo.charAt(i)=='(')
                j++;
            if(tInfo.charAt(i)==')')
                k++;
            if(tInfo.charAt(i)==',')
                m++;
            if(tInfo.charAt(i)=='|')
                n++;
        }
        if(j!=1||k!=1||m!=1||n!=1)//信息有误
            throw new MyError(82);
        i=tInfo.indexOf('(');
        j=tInfo.indexOf(',');
        m=tInfo.indexOf(')');
        n=tInfo.indexOf('|');
        if(i>j||j>m||m>n||(m!=n-1))
            throw new MyError(82);
        BeginT=MyFunction.IsNum(tInfo.substring(i+1,j));
        EndT=MyFunction.IsNum(tInfo.substring(j+1,m));
        Stept=MyFunction.IsNum(tInfo.substring(n+1));
        if(BeginT>=EndT)
            throw new MyError(80);
        if(Stept/(EndT-BeginT)>=1||Stept<=0)
            throw new MyError(81);
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
    private void GetArrayT(){
        NumT=0;
        for(double tmp=BeginT;tmp<EndT;tmp+=Stept)
            NumT++;
        ArrayT=new double[NumT][3];
        Xup=0;
        Xdown=0;
        Ymax=0;
        Ymin=0;
        Zmin=0;
        Zmax=0;
        DrawHaveErr=false;
        for(int i=0;i<NumT;i++)
            try{
                ComTreeX.SetVar("t",BeginT+i*Stept);
                ComTreeY.SetVar("t",BeginT+i*Stept);
                ComTreeZ.SetVar("t",BeginT+i*Stept);
                ArrayT[i][0]=ComTreeX.ComputeTree(ComTreeX.Root);
                ArrayT[i][1]=ComTreeY.ComputeTree(ComTreeY.Root);
                ArrayT[i][2]=ComTreeZ.ComputeTree(ComTreeZ.Root);
                if(ArrayT[i][0]>Xup)
                    Xup=ArrayT[i][0];
                if(ArrayT[i][0]<Xdown)
                    Xdown=ArrayT[i][0];
                if(ArrayT[i][1]>Ymax)
                    Ymax=ArrayT[i][1];
                if(ArrayT[i][1]<Ymin)
                    Ymin=ArrayT[i][1];
                if(ArrayT[i][2]>Zmax)
                    Zmax=ArrayT[i][2];
                if(ArrayT[i][2]<Zmin)
                    Zmin=ArrayT[i][2];
            }catch(MyError Err){
                DrawHaveErr=true;
            }
    }
    //********************绘图函数********************//
    private void DrawFunc(Graphics g){
        SetColor(g,-1);
        for(int i=0;i<NumT-1;i++)
            Line3D(g,ArrayT[i][0],ArrayT[i][1],ArrayT[i][2],ArrayT[i+1][0],ArrayT[i+1][1],ArrayT[i+1][2]);
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
            if(DrawHaveErr)
                g.drawString("旋转模式,计算中有错误",10,10,g.LEFT|g.TOP);
            else
                g.drawString("旋转模式",10,10,g.LEFT|g.TOP);
            SetColor(g,-4);
            g.drawString("fx(t)="+ComStrX,10,10+g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("fy(t)="+ComStrY,10,12+2*g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("fz(t)="+ComStrZ,10,14+3*g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("t范围:"+MyFunction.CutNumStr(BeginT,5)+","+
                    MyFunction.CutNumStr(EndT,5),10,16+4*g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("t步长:"+MyFunction.CutNumStr(Stept,5),10,18+5*g.getFont().getHeight(),g.LEFT|g.TOP);
        }
        else{
            if(DrawHaveErr)
                g.drawString("平移模式,计算中有错误",10,10,g.LEFT|g.TOP);
            else
                g.drawString("平移模式",10,10,g.LEFT|g.TOP);
            g.drawString("x",(int)GetSx(Xup,Ymin,Zmin),(int)GetSy(Xup,Ymin,Zmin),g.LEFT|g.TOP);
            g.drawString("y",(int)GetSx(Xdown,Ymax,Zmin),(int)GetSy(Xdown,Ymax,Zmin),g.LEFT|g.TOP);
            g.drawString("z",(int)GetSx(Xdown,Ymin,Zmax),(int)GetSy(Xdown,Ymin,Zmax),g.LEFT|g.TOP);
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
        Stept/=1.5;
        GetArrayT();//生成结果矩阵
        repaint();
    }
    public void ToBeLess(){
        Stept*=1.5;
        GetArrayT();//生成结果矩阵
        repaint();
    }
}
