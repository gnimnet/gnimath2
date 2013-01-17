package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *曲线拟合程序                      *
 ************************************/
import MyTypes.*;
import javax.microedition.lcdui.*;

public class NearLine extends World2D{
    private double[]ArrLineDataX;//X样本点
    private double[]ArrLineDataY;//Y样本点
    private int FuncLv;//多项式次数
    private T_Matrix MaData;//拟合系数数据
    private T_Matrix MaY;//Y常数矩阵
    private T_Matrix FuncC;//多项式系数矩阵
    public boolean InfoMode=false;//显示信息--true
    public NearLine(String DataStr,String LvStr)throws MyError{
        GetLineData(DataStr);//获取数字信息
        CheckData();//检查数据正确性
        GetRangeXY();//获取最大最小值
        GetAllMatrix(LvStr);//按次数计算拟合曲线
    }
    /********************Canvas虚函数********************/
    public void paint(Graphics g) {
        if(!InfoMode){
            this.SetRangeTitle();
            this.InitScreen(g);//初始化屏幕
            this.DrawLineXY(g);//绘制坐标轴
            DrawFunc(g);//绘制插值函数
            DrawPoint(g);//绘制样本点
        }
        else{
            this.setTitle("拟合结果参数");//显示当前页信息
            this.InitScreen(g);//初始化屏幕
            PrintInfo(g);//输出信息
        }
    }
    public void pointerDragged(int x,int y){//显示触摸选中点的坐标值及误差
        if(!InfoMode)
            this.SetPressTitle(x,y);
    }
    public void pointerPressed(int x,int y){//显示触摸选中点的坐标值及误差
        if(!InfoMode)
            this.SetPressTitle(x,y);
    }
    public void pointerReleased(int x,int y){//触摸离开恢复显示坐标范围
        if(!InfoMode)
            this.SetRangeTitle();
    }
    public void keyReleased(int key){
        if(!InfoMode)
            this.ChangeRange(key);
    }
    /********************自定义内部函数********************/
    private void GetLineData(String MyData)throws MyError{
        char[]DataCh=MyData.toCharArray();
        int i,j=0,k=0;
        if(MyData.charAt(MyData.length()-1)!=';')
            throw new MyError(88);
        for(i=0;i<DataCh.length;i++){
            if(DataCh[i]==';')
                j++;
        }
        if(j<=1)//要求至少两组数据
            throw new MyError(85);
        ArrLineDataX=new double[j];
        ArrLineDataY=new double[j];
        j=0;
        for(i=0;i<DataCh.length;i++){
            if(DataCh[i]==';'){
                SetLineData(MyData.substring(k,i),j++);
                k=i+1;
            }
        }
    }
    private void SetLineData(String OneDate,int Index)throws MyError{
        int i,j=0,k=0,l=0;
        for(i=0;i<OneDate.length();i++){
            if(OneDate.charAt(i)=='(')
                j++;
            if(OneDate.charAt(i)==')')
                k++;
            if(OneDate.charAt(i)==',')
                l++;
        }
        if(j!=1||k!=1||l!=1)//范围信息有误
            throw new MyError(86);
        if(OneDate.indexOf('(')!=0)
            throw new MyError(86);
        if(OneDate.indexOf(')')!=OneDate.length()-1)
            throw new MyError(86);
        ArrLineDataX[Index]=MyFunction.IsNum(OneDate.substring(1,OneDate.indexOf(',')));//获得x
        ArrLineDataY[Index]=MyFunction.IsNum(OneDate.substring(OneDate.indexOf(',')+1,OneDate.length()-1));//获得y
    }
    private void CheckData()throws MyError{
        int i,j;
        for(i=0;i<ArrLineDataX.length-1;i++)
            for(j=i+1;j<ArrLineDataX.length;j++){
                if(ArrLineDataX[i]==ArrLineDataX[j])
                    throw new MyError(87);
            }
    }
    private void GetRangeXY(){
        double MaxTmpX=ArrLineDataX[0];
        double MinTmpX=ArrLineDataX[0];
        double MaxTmpY=ArrLineDataY[0];
        double MinTmpY=ArrLineDataY[0];
        for(int i=1;i<ArrLineDataX.length;i++){
            if(MaxTmpX<ArrLineDataX[i])
                MaxTmpX=ArrLineDataX[i];
            if(MinTmpX>ArrLineDataX[i])
                MinTmpX=ArrLineDataX[i];
            if(MaxTmpY<ArrLineDataY[i])
                MaxTmpY=ArrLineDataY[i];
            if(MinTmpY>ArrLineDataY[i])
                MinTmpY=ArrLineDataY[i];
        }
        Xup=MaxTmpX+(MaxTmpX-MinTmpX)/5;
        Xdown=MinTmpX-(MaxTmpX-MinTmpX)/10;
        Ymax=MaxTmpY+(MaxTmpY-MinTmpY)/10;
        Ymin=MinTmpY-(MaxTmpY-MinTmpY)/10;
    }
    private void GetAllMatrix(String Lv)throws MyError{
        int i,j;
        if(Lv.indexOf('.')!=-1)
            throw new MyError(89);
        FuncLv=Integer.parseInt(Lv);//获取拟合次数
        if(FuncLv>=ArrLineDataX.length)
            throw new MyError(91);
        if(FuncLv<0)
            throw new MyError(92);
        //初始化Y矩阵
        MaY=new T_Matrix(ArrLineDataY.length,1);
        for(i=0;i<MaY.Rows;i++)
            MaY.MatrixNum[i][0]=ArrLineDataY[i];
        //获取拟合系数矩阵
        MaData=new T_Matrix(ArrLineDataX.length,FuncLv+1);
        for(i=0;i<MaData.Rows;i++)
            for(j=0;j<MaData.Cols;j++)
                MaData.MatrixNum[i][j]=MyFunction.pow(ArrLineDataX[i],j);
        //计算拟合系数矩阵的转置
        T_Matrix TmpDataT=T_Matrix.MatrixT(MaData);
        //计算转置矩阵与拟合系数矩阵的乘积
        T_Matrix TmpMul=T_Matrix.MatrixMul(TmpDataT,MaData);
        //计算成绩的逆矩阵
        T_Matrix TmpMulR=T_Matrix.MatrixR(TmpMul);
        if(TmpMulR==null)
            throw new MyError(90);//拟合逆矩阵不存在
        else
            FuncC=T_Matrix.MatrixMul(TmpMulR,T_Matrix.MatrixMul(TmpDataT,MaY));//计算系数矩阵
    } 
    private void DrawFunc(Graphics g){
        double Oldy,Newy;
        g.setColor(255,0,0);
        Oldy=GetFunc(0);
        for(int drawx=1;drawx<=this.getWidth();drawx++){
            Newy=GetFunc(drawx);//用系数计算
            if(Oldy>=Ymin && Oldy<=Ymax && Newy>=Ymin && Newy<=Ymax)
                g.drawLine(drawx-1,(int)FytoSy(Oldy),drawx,(int)FytoSy(Newy));
            Oldy=Newy;
        }
    }
    private double GetFunc(int ScreenX){//根据均差表计算数值
        int i,j;
        double Rtn=0,Tmp;
        double X=SxtoFx(ScreenX);
        for(i=0;i<=FuncLv;i++){
            Tmp=MyFunction.pow(X,i);
            Tmp=Tmp*FuncC.MatrixNum[i][0];
            Rtn=Rtn+Tmp;
        }
        return Rtn;
    }
    private void DrawPoint(Graphics g){
        int Sx,Sy;
        g.setColor(0,0,255);
        for(int i=0;i<ArrLineDataX.length;i++){
            Sx=(int)FxtoSx(ArrLineDataX[i])-2;
            Sy=(int)FytoSy(ArrLineDataY[i])-2;
            g.drawRect(Sx,Sy,4,4);
            if(!this.ChangeMode)
                g.drawString("("+MyFunction.CutNumStr(ArrLineDataX[i])+","
                    +MyFunction.CutNumStr(ArrLineDataY[i])+")",Sx,Sy,Graphics.BOTTOM | Graphics.LEFT);
        }
    }
    private void PrintInfo(Graphics g){
        g.setColor(0,127,0);
        g.drawString("拟合多项式系数:",10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        for(int i=0;i<=FuncLv;i++){
            g.drawString("c"+i+":"+MyFunction.CutNumStr(FuncC.MatrixNum[i][0],7),10,(i+2)*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        }

    }
}
