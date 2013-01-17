package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *��ֵ���߽���                      *
 ***********************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class Curvilinear extends World2D{
    private double[]ArrLineDataX;//X������
    private double[]ArrLineDataY;//Y������
    private double[]CxTable;//�����
    private double[]FuncC;//����ʽϵ��
    public boolean InfoMode=false;//��ʾ��Ϣ--true
    /********************���캯��********************/
    public Curvilinear(String LineData)throws MyError{
        GetLineData(LineData);//��ȡ������Ϣ
        CheckData();//���������ȷ��
        GetRangeXY();//��ȡ�����Сֵ
        InitData();//�����ֵ����
        GetFuncC();//�������ո���ϵ��
    }
    /********************Canvas�麯��********************/
    public void paint(Graphics g) {
        if(!InfoMode){
            this.SetRangeTitle();
            this.InitScreen(g);//��ʼ����Ļ
            this.DrawLineXY(g);//����������
            DrawFunc(g);//���Ʋ�ֵ����
            DrawPoint(g);//����������
        }
        else{
            this.setTitle("��ֵ�������");//��ʾ��ǰҳ��Ϣ
            this.InitScreen(g);//��ʼ����Ļ
            PrintInfo(g);//�����Ϣ
        }
    }
    public void pointerDragged(int x,int y){//��ʾ����ѡ�е������ֵ�����
        if(!InfoMode)
            this.SetPressTitle(x,y);
    }
    public void pointerPressed(int x,int y){//��ʾ����ѡ�е������ֵ�����
        if(!InfoMode)
            this.SetPressTitle(x,y);
    }
    public void pointerReleased(int x,int y){//�����뿪�ָ���ʾ���귶Χ
        if(!InfoMode)
            this.SetRangeTitle();
    }
    public void keyReleased(int key){
        if(!InfoMode)
            this.ChangeRange(key);
    }
    /********************�Զ����ڲ�����********************/
    private void GetLineData(String MyData)throws MyError{
        char[]DataCh=MyData.toCharArray();
        int i,j=0,k=0;
        if(MyData.charAt(MyData.length()-1)!=';')
            throw new MyError(33);
        for(i=0;i<DataCh.length;i++){
            if(DataCh[i]==';')
                j++;
        }
        if(j<=1)//Ҫ��������������
            throw new MyError(30);
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
        if(j!=1||k!=1||l!=1)//��Χ��Ϣ����
            throw new MyError(31);
        if(OneDate.indexOf('(')!=0)
            throw new MyError(31);
        if(OneDate.indexOf(')')!=OneDate.length()-1)
            throw new MyError(31);
        ArrLineDataX[Index]=MyFunction.IsNum(OneDate.substring(1,OneDate.indexOf(',')));//���x
        ArrLineDataY[Index]=MyFunction.IsNum(OneDate.substring(OneDate.indexOf(',')+1,OneDate.length()-1));//���y
    }
    private void CheckData()throws MyError{
        int i,j;
        for(i=0;i<ArrLineDataX.length-1;i++)
            for(j=i+1;j<ArrLineDataX.length;j++){
                if(ArrLineDataX[i]==ArrLineDataX[j])
                    throw new MyError(32);
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
    private void InitData(){//��������
        int i,j;
        CxTable=new double[ArrLineDataY.length];
        for(i=0;i<ArrLineDataY.length;i++)
            CxTable[i]=ArrLineDataY[i];
        for(j=1;j<ArrLineDataY.length;j++)
            for(i=ArrLineDataY.length-1;i>=j;i--)
                CxTable[i]=(CxTable[i]-CxTable[i-1])/(ArrLineDataX[i]-ArrLineDataX[i-j]);
    }
    private void DrawFunc(Graphics g){
        double Oldy,Newy;
        g.setColor(255,0,0);
        Oldy=GetFunc(0);
        for(int drawx=1;drawx<=this.getWidth();drawx++){
            Newy=GetFunc2(drawx);//��ϵ������
            if(Oldy>=Ymin && Oldy<=Ymax && Newy>=Ymin && Newy<=Ymax)
                g.drawLine(drawx-1,(int)FytoSy(Oldy),drawx,(int)FytoSy(Newy));
            Oldy=Newy;
        }
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
        g.drawString("��������:",10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        g.drawString("����ʽϵ��:",10+this.getWidth()/2,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        for(int i=0;i<ArrLineDataX.length;i++){
            g.drawString("d"+i+":"+MyFunction.CutNumStr(CxTable[i],7),10,(i+2)*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
            g.drawString("x^"+i+":"+MyFunction.CutNumStr(FuncC[i],7),10+this.getWidth()/2,(i+2)*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        }

    }
    private double GetFunc(int ScreenX){//���ݾ���������ֵ
        int i,j;
        double Rtn=0,Tmp;
        double X=SxtoFx(ScreenX);
        for(i=0;i<ArrLineDataX.length;i++){
            Tmp=CxTable[i];
            for(j=0;j<i;j++)
                Tmp=Tmp*(X-ArrLineDataX[j]);
            Rtn=Rtn+Tmp;
        }
        return Rtn;
    }
    private double GetFunc2(int ScreenX){//����ϵ����������ֵ
        int i,j;
        double Rtn=0,Tmp;
        double X=SxtoFx(ScreenX);
        for(i=0;i<FuncC.length;i++){
            Tmp=FuncC[i];
            for(j=0;j<i;j++)
                Tmp=Tmp*X;
            Rtn=Rtn+Tmp;
        }
        return Rtn;
    }
    private void GetFuncC(){//���ݾ�������ɶ���ʽϵ��
        FuncC=new double[ArrLineDataX.length];
        for(int i=0;i<FuncC.length;i++)
            FuncC[i]=0;
        FuncC[0]=ArrLineDataY[0];
        for(int j=1;j<FuncC.length;j++)
            GetFuncCbyIndex(j);
    }
    private void GetFuncCbyIndex(int index){//����index���ϵ��
        int i,j;
        double[] NumArray=new double[ArrLineDataX.length];
        double[] NumArrayTmp1=new double[ArrLineDataX.length];//�ͳ�λ����(����ArrLineDataX[i])
        double[] NumArrayTmp2=new double[ArrLineDataX.length];//�߳�λ����(ֱ��λ��1)
        for(i=0;i<index;i++)
            NumArray[i]=0;
        NumArray[0]=-ArrLineDataX[0];
        NumArray[1]=1;
        for(i=1;i<index;i++){
            for(j=0;j<NumArray.length;j++)
                NumArrayTmp1[j]=(-ArrLineDataX[i])*NumArray[j];//�����λ
            NumArrayTmp2[0]=0;
            for(j=0;j<NumArray.length-1;j++)
                NumArrayTmp2[j+1]=NumArray[j];//�����λ
            for(j=0;j<NumArray.length;j++)
                NumArray[j]=NumArrayTmp1[j]+NumArrayTmp2[j];//ϵ�����
        }
        for(i=0;i<NumArray.length;i++)
            FuncC[i]=FuncC[i]+NumArray[i]*CxTable[index];//�����������ĸ���ϵ��
    }
}
