package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *2维参数曲线绘制程序               *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class LineXY extends World2D{
    /********************定义变量********************/
    private StrComTree[] fxt;//x=f(t)计算树
    private StrComTree[] fyt;//y=f(t)计算树
    private String[] StrComArrayX;//x表达式f(t)
    private String[] StrComArrayY;//y表达式f(t)
    private int[][] LineColor;//线色
    private double Tmin,Tmax,Tdt;//t范围
    private boolean DrawHaveErr;//绘图时出错
    /********************构造函数********************/
    public LineXY(String Ft,String tInfo,String RangeStr) throws MyError{
        InitSize(Ft);//根据条件初始化相关变量
        GetRange(RangeStr);//初始化绘制范围
        InitInfot(tInfo);//初始化t的范围和变化率
    }
    /********************Canvas虚函数********************/
    protected void paint(Graphics g) {
        this.SetRangeTitle();
        this.InitScreen(g);//初始化屏幕
        this.DrawLineXY(g);//绘制坐标轴
        DrawHaveErr=false;
        try{
            DrawLine(g);//绘制参数曲线
        }
        catch(MyError Err){
            DrawHaveErr=true;
        }
        PrintInfo(g,DrawHaveErr);
    }
    /********************自定义内部函数********************/
    private void DrawLine(Graphics g)throws MyError{
        double Oldx=0,Newx=0;
        double Oldy=0,Newy=0;
        boolean OldErr=false,NewErr=false;
        for(int i=0;i<fxt.length;i++){
            g.setColor(LineColor[i][0],LineColor[i][1],LineColor[i][2]);//按颜色设定
            fxt[i].SetVar("t",Tmin);
            fyt[i].SetVar("t",Tmin);
            try{
                Oldx=fxt[i].ComputeTree(fxt[i].Root);
                Oldy=fyt[i].ComputeTree(fyt[i].Root);
            }
            catch(MyError Err){
                OldErr=true;
                DrawHaveErr=true;
            }
            for(double t=(Tmin+Tdt);t<Tmax;t+=Tdt){
                fxt[i].SetVar("t",t);
                fyt[i].SetVar("t",t);
                try{
                    Newx=fxt[i].ComputeTree(fxt[i].Root);
                    Newy=fyt[i].ComputeTree(fyt[i].Root);
                    NewErr=false;
                }
                catch(MyError Err){
                    NewErr=true;
                    DrawHaveErr=true;
                }
                if(OldErr==false && NewErr==false)
                    if(Oldy>=Ymin && Oldy<=Ymax && Newy>=Ymin && Newy<=Ymax &&
                            Oldx>=Xdown && Oldx<=Xup && Newx>=Xdown && Newx<=Xup)
                        g.drawLine((int)FxtoSx(Oldx),(int)FytoSy(Oldy),(int)FxtoSx(Newx),(int)FytoSy(Newy));
                Oldx=Newx;
                Oldy=Newy;
                OldErr=NewErr;
            }
        }
    }
    private void PrintInfo(Graphics g,boolean HaveErr){//输出信息函数
        SetColor(g,-3);
        if(HaveErr)
            g.drawString("t:["+Tmin+","+Tmax+"],dt:"+Tdt+",计算中有错误...",10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("t:["+Tmin+","+Tmax+"],dt:"+Tdt,10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        if(!this.ChangeMode)
            for(int i=0;i<StrComArrayX.length;i++){//输出函数
                g.setColor(LineColor[i][0],LineColor[i][1],LineColor[i][2]);//按颜色设定
                g.drawString("fx(t)"+(i+1)+":"+StrComArrayX[i],10,(i*2+2)*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
                g.drawString("fy(t)"+(i+1)+":"+StrComArrayY[i],10,(i*2+3)*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
            }
    }
    private void InitSize(String FtStr)throws MyError{
        int i,j=0,k;
        int StrCount=0;//表达式数
        String GoodStr=MyFunction.DelSpace(MyFunction.MyTranStr(FtStr));//删除空白字符
        char[]ch=GoodStr.toCharArray();//截取字符
        for(i=0;i<ch.length;i++)
            if(ch[i]==';')
                StrCount++;//累计语句数
        if(StrCount==0||ch[ch.length-1]!=';')
            throw new MyError(45);
        StrComArrayX=new String[StrCount];
        StrComArrayY=new String[StrCount];
        fxt=new StrComTree[StrCount];
        fyt=new StrComTree[StrCount];
        LineColor=new int[StrCount][3];
        for(i=0;i<GoodStr.length();i++)
            if(GoodStr.charAt(i)==';'){
                StrComArrayX[j++]=GoodStr.substring(0,i);
                k=StrComArrayX[j-1].indexOf('|');
                if(k==-1)
                    throw new MyError(46);
                StrComArrayY[j-1]=StrComArrayX[j-1].substring(k+1);
                StrComArrayX[j-1]=StrComArrayX[j-1].substring(0,k);
                if(i==GoodStr.length()-1)
                    break;
                else
                    GoodStr=GoodStr.substring(i+1);
                i=-1;
            }
        GetComStrInfo();//获取信息
    }
    private void GetComStrInfo()throws MyError{//获取截取的表达式组信息
        for(int i=0;i<StrComArrayX.length;i++){
            StrComArrayX[i]=FindColor(StrComArrayX[i],i);
            fxt[i]=new StrComTree(StrComArrayX[i]);
            fxt[i].AddVar("t");
            fxt[i].AddVar("pi",java.lang.Math.PI);//注册常量PI
            fxt[i].AddVar("e",java.lang.Math.E);//注册常量E
            fyt[i]=new StrComTree(StrComArrayY[i]);
            fyt[i].AddVar("t");
            fyt[i].AddVar("pi",java.lang.Math.PI);//注册常量PI
            fyt[i].AddVar("e",java.lang.Math.E);//注册常量E
        }
    }
    private void InitInfot(String Info)throws MyError{
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
            throw new MyError(40);
        i=Info.indexOf('(');
        j=Info.indexOf(',');
        m=Info.indexOf(')');
        n=Info.indexOf('|');
        if(i>j||j>m||m>n||(m!=n-1))
            throw new MyError(40);
        Tmin=MyFunction.IsNum(Info.substring(i+1,j));
        Tmax=MyFunction.IsNum(Info.substring(j+1,m));
        Tdt=MyFunction.IsNum(Info.substring(n+1));
        if(Tmin>=Tmax)
            throw new MyError(43);
        if(Tdt/(Tmax-Tmin)>=1||Tdt<=0)
            throw new MyError(44);
    }
    private String FindColor(String WithColorStr,int Index)throws MyError{//将含颜色信息的字符串解析并返回表达式部分
        int i,j=0,k=0;
        for(i=0;i<WithColorStr.length();i++){
            if(WithColorStr.charAt(i)=='[')
                j++;
            if(WithColorStr.charAt(i)==']')
                k++;
        }
        if(j==0&&k==0){//无颜色信息(默认红色线)
            LineColor[Index][0]=255;
            LineColor[Index][1]=0;
            LineColor[Index][2]=0;
            return WithColorStr;
        }
        if(j!=1||k!=1)//颜色信息有误
            throw new MyError(41);
        if(WithColorStr.indexOf('[')!=0)//颜色信息有误
            throw new MyError(41);
        SetColorStr(WithColorStr.substring(1,WithColorStr.indexOf(']')),Index);
        return WithColorStr.substring(WithColorStr.indexOf(']')+1);
    }
    private void SetColorStr(String ColorStr,int Index)throws MyError{//设定颜色信息
        int i,j=0;
        for(i=0;i<ColorStr.length();i++)
            if(ColorStr.charAt(i)==',')
                j++;
        if(j!=2)//颜色信息有误
            throw new MyError(14);
        i=ColorStr.indexOf(',');
        j=ColorStr.lastIndexOf(',');
        LineColor[Index][0]=Is255Num(ColorStr.substring(0,i));
        LineColor[Index][1]=Is255Num(ColorStr.substring(i+1,j));
        LineColor[Index][2]=Is255Num(ColorStr.substring(j+1));
    }
    private int Is255Num(String NumStr)throws MyError{//如果NumStr为一个0--255的整数则返回否则报错
        int Rtn;
        char[]ch=NumStr.toCharArray();
        if(ch.length>3)
            throw new MyError(14);
        for(int i=0;i<ch.length;i++){
            if(ch[i]<'0'||ch[i]>'9')
                throw new MyError(14);
        }
        Rtn=Integer.parseInt(NumStr);
        if(Rtn>255)
            throw new MyError(14);
        else
            return Rtn;
    }
}
