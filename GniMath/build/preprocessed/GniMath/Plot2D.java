package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *Plot2D绘制Canvas类                *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class Plot2D extends World2D{
    public int ProgramMode=0;//程序模式选择(0为默认模式)
    private StrComTree[] ComStrList;//计算表达式数组
    private String[] StrComArray;//表达式数组
    private int[][] LineColor;//线色
    private double FuncXmin,FuncXmax;//二分求解的X范围
    private int FuncStep;//解方程步骤
    private int FuncNowSx;//选择x的线位置
    private boolean FuncLeftNegtive;//如果左边为负值
    private boolean DrawHaveErr;//绘图时出错
    /********************构造函数********************/
    public Plot2D(String ComStr,String Range)throws MyError{
        InitComStrListSize(ComStr);//获取表达式组及其颜色
        GetRange(Range);//获取X和Y的范围
    }
    /********************动作捕获虚函数********************/
    protected void paint(Graphics g){
        boolean HaveErr=false;
        this.SetRangeTitle();
        this.InitScreen(g);
        this.DrawLineXY(g);
        DrawHaveErr=false;
        try {
            PrintFunc(g);//绘制函数图像
        } catch (MyError Err) {
            DrawHaveErr=true;//绘制时出错
        }
        PrintInfo(g,DrawHaveErr);//输出信息
    }
    public void keyReleased(int key){
        if(ProgramMode==0){
            this.ChangeRange(key);
        }
        else if(ProgramMode==1){//解方程模式
            int code=this.getGameAction(key);
            switch(code){
                case Canvas.FIRE:GoToNextStep();repaint();break;
                case Canvas.LEFT:
                    if(FuncNowSx>this.getWidth()/32)
                        FuncNowSx-=this.getWidth()/32;
                    repaint();
                    break;
                case Canvas.RIGHT:
                    if(FuncNowSx<(31*this.getWidth())/32)
                    FuncNowSx+=this.getWidth()/32;
                    repaint();
                    break;
            }
        }
    }
    /********************自定义内部函数********************/
    private void PrintInfo(Graphics g,boolean HaveErr){//输出信息函数
        SetColor(g,-3);
        if(HaveErr)
            g.drawString("模式:"+GetProgramModeStr()+",计算中有错误",10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("模式:"+GetProgramModeStr(),10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        if(ProgramMode==0){
            if(!this.ChangeMode)
                for(int i=0;i<ComStrList.length;i++){//输出函数
                    g.setColor(LineColor[i][0],LineColor[i][1],LineColor[i][2]);//按颜色设定
                    g.drawString("f"+(i+1)+":"+ComStrList[i].ComStr,10,(i+2)*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
                }
        }
        else if(ProgramMode==1){
            g.setColor(LineColor[0][0],LineColor[0][1],LineColor[0][2]);//按颜色设定
            g.drawString("步骤"+FuncStep+":"+GetStepName(),10,2*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
            g.drawLine(FuncNowSx,0,FuncNowSx,this.getHeight());
        }
    }
    private void PrintFunc(Graphics g)throws MyError{
        double Oldy=0,Newy=0;
        boolean OldErr=false,NewErr=false;
        for(int i=0;i<ComStrList.length;i++){
            g.setColor(LineColor[i][0],LineColor[i][1],LineColor[i][2]);//按颜色设定
            ComStrList[i].SetVar("x",SxtoFx(0));
            try{
                Oldy=ComStrList[i].ComputeTree(ComStrList[i].Root);
            }
            catch(MyError Err){
                OldErr=true;
                DrawHaveErr=true;
            }
            for(int drawx=1;drawx<=this.getWidth();drawx++){
                ComStrList[i].SetVar("x",SxtoFx(drawx));
                try{
                    Newy=ComStrList[i].ComputeTree(ComStrList[i].Root);
                    NewErr=false;
                }
                catch(MyError Err){
                    NewErr=true;
                    DrawHaveErr=true;
                }
                if(OldErr==false && NewErr==false)
                    if(Oldy>=Ymin && Oldy<=Ymax && Newy>=Ymin && Newy<=Ymax)
                        g.drawLine(drawx-1,(int)FytoSy(Oldy),drawx,(int)FytoSy(Newy));
                Oldy=Newy;
                OldErr=NewErr;
            }
        }
    }
    private String GetProgramModeStr(){//返回运行模式字符串
        switch(ProgramMode){
            case 0:
                if(this.ChangeMode)
                    return "图像变换(平移)";
                else
                    return "图像变换(缩放)";
            case 1:
                return "求解方程";
            default:
                return "错误";
        }
    }
    private void InitComStrListSize(String ComStr)throws MyError{//截取表达式组并获取信息
        int i,j=0;
        int StrCount=0;//表达式数
        String GoodStr=MyFunction.DelSpace(MyFunction.MyTranStr(ComStr));//删除空白字符
        char[]ch=GoodStr.toCharArray();//截取字符
        for(i=0;i<ch.length;i++)
            if(ch[i]==';')
                StrCount++;//累计语句数
        if(StrCount==0||ch[ch.length-1]!=';')
            throw new MyError(13);
        StrComArray=new String[StrCount];
        ComStrList=new StrComTree[StrCount];
        LineColor=new int[StrCount][3];
        for(i=0;i<GoodStr.length();i++)
            if(GoodStr.charAt(i)==';'){
                StrComArray[j++]=GoodStr.substring(0,i);
                if(i==GoodStr.length()-1)
                    break;
                else
                    GoodStr=GoodStr.substring(i+1);
                i=-1;
            }
        GetComStrInfo();//获取信息
    }
    private void GetComStrInfo()throws MyError{//获取截取的表达式组信息
        for(int i=0;i<StrComArray.length;i++){
            ComStrList[i]=new StrComTree(FindColor(StrComArray[i],i));
            ComStrList[i].AddVar("x");
            ComStrList[i].AddVar("pi",java.lang.Math.PI);//注册常量PI
            ComStrList[i].AddVar("e",java.lang.Math.E);//注册常量E
        }
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
            throw new MyError(14);
        if(WithColorStr.indexOf('[')!=0)//颜色信息有误
            throw new MyError(14);
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
    private String GetStepName(){
        switch(FuncStep){
            case 1:return "选择求解下限";
            case 2:return "选择求解上限";
            case 3:
                try {
                    return "求解x="+MyFunction.CutNumStr(GetFuncAns(),14);
                } catch (MyError Err) {
                    return "求解错误!["+Err.GetErrNum()+"]"+Err.GetErrMsg();
                }
            case -1:return "请选择异号的上下限!";
            case -2:return "上限应大于下限!";
            case -3:return "求解错误!";
            default:return "步骤未知...";
        }
    }
    private double GetFuncAns()throws MyError{//二分计算,LeftNegtive=true当左边函数值为负
        double Left=FuncXmin;
        double Right=FuncXmax;
        double half=(Right+Left)/2;
        double tmp;
        while(Right-Left>1E-15){
            half=(Right+Left)/2;
            ComStrList[0].SetVar("x",half);
            tmp=ComStrList[0].ComputeTree(ComStrList[0].Root);
            if(tmp==0)
                return half;
            else if(tmp>0){
                if(FuncLeftNegtive)
                    Right=half;
                else
                    Left=half;
            }
            else{
                if(FuncLeftNegtive)
                    Left=half;
                else
                    Right=half;
            }
        }
        return half;
    }
    public void InitFuncAns(){//初始化求解方程的参量
        FuncStep=1;//从第一步开始
        this.ChangeMode=true;
        FuncNowSx=this.getWidth()/4;//初始化选择线位置
    }
    private void GoToNextStep(){//步骤转移
        if(FuncStep==1){
            FuncXmin=SxtoFx(FuncNowSx);
            FuncStep++;
        }
        else if(FuncStep==2){
            FuncXmax=SxtoFx(FuncNowSx);
            if(FuncXmax<=FuncXmin){
                FuncStep=-2;
                return;
            }
            double Y1,Y2;
                try {
                    ComStrList[0].SetVar("x",FuncXmax);
                    Y1=ComStrList[0].ComputeTree(ComStrList[0].Root);
                    ComStrList[0].SetVar("x",FuncXmin);
                    Y2=ComStrList[0].ComputeTree(ComStrList[0].Root);
                } catch (MyError ex) {
                    FuncStep=-3;
                    return;
                }
            if((Y1>0&&Y2<0)||(Y2>0&&Y1<0)){
                if(Y2<0)
                    FuncLeftNegtive=true;
                else
                    FuncLeftNegtive=false;
                FuncStep++;
            }
            else
                FuncStep=-1;
        }
        else{
            FuncStep=1;
        }
    }
}
