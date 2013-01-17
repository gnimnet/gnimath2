package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *Plot2D����Canvas��                *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class Plot2D extends World2D{
    public int ProgramMode=0;//����ģʽѡ��(0ΪĬ��ģʽ)
    private StrComTree[] ComStrList;//������ʽ����
    private String[] StrComArray;//���ʽ����
    private int[][] LineColor;//��ɫ
    private double FuncXmin,FuncXmax;//��������X��Χ
    private int FuncStep;//�ⷽ�̲���
    private int FuncNowSx;//ѡ��x����λ��
    private boolean FuncLeftNegtive;//������Ϊ��ֵ
    private boolean DrawHaveErr;//��ͼʱ����
    /********************���캯��********************/
    public Plot2D(String ComStr,String Range)throws MyError{
        InitComStrListSize(ComStr);//��ȡ���ʽ�鼰����ɫ
        GetRange(Range);//��ȡX��Y�ķ�Χ
    }
    /********************���������麯��********************/
    protected void paint(Graphics g){
        boolean HaveErr=false;
        this.SetRangeTitle();
        this.InitScreen(g);
        this.DrawLineXY(g);
        DrawHaveErr=false;
        try {
            PrintFunc(g);//���ƺ���ͼ��
        } catch (MyError Err) {
            DrawHaveErr=true;//����ʱ����
        }
        PrintInfo(g,DrawHaveErr);//�����Ϣ
    }
    public void keyReleased(int key){
        if(ProgramMode==0){
            this.ChangeRange(key);
        }
        else if(ProgramMode==1){//�ⷽ��ģʽ
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
    /********************�Զ����ڲ�����********************/
    private void PrintInfo(Graphics g,boolean HaveErr){//�����Ϣ����
        SetColor(g,-3);
        if(HaveErr)
            g.drawString("ģʽ:"+GetProgramModeStr()+",�������д���",10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("ģʽ:"+GetProgramModeStr(),10,g.getFont().getHeight(),Graphics.TOP | Graphics.LEFT);
        if(ProgramMode==0){
            if(!this.ChangeMode)
                for(int i=0;i<ComStrList.length;i++){//�������
                    g.setColor(LineColor[i][0],LineColor[i][1],LineColor[i][2]);//����ɫ�趨
                    g.drawString("f"+(i+1)+":"+ComStrList[i].ComStr,10,(i+2)*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
                }
        }
        else if(ProgramMode==1){
            g.setColor(LineColor[0][0],LineColor[0][1],LineColor[0][2]);//����ɫ�趨
            g.drawString("����"+FuncStep+":"+GetStepName(),10,2*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
            g.drawLine(FuncNowSx,0,FuncNowSx,this.getHeight());
        }
    }
    private void PrintFunc(Graphics g)throws MyError{
        double Oldy=0,Newy=0;
        boolean OldErr=false,NewErr=false;
        for(int i=0;i<ComStrList.length;i++){
            g.setColor(LineColor[i][0],LineColor[i][1],LineColor[i][2]);//����ɫ�趨
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
    private String GetProgramModeStr(){//��������ģʽ�ַ���
        switch(ProgramMode){
            case 0:
                if(this.ChangeMode)
                    return "ͼ��任(ƽ��)";
                else
                    return "ͼ��任(����)";
            case 1:
                return "��ⷽ��";
            default:
                return "����";
        }
    }
    private void InitComStrListSize(String ComStr)throws MyError{//��ȡ���ʽ�鲢��ȡ��Ϣ
        int i,j=0;
        int StrCount=0;//���ʽ��
        String GoodStr=MyFunction.DelSpace(MyFunction.MyTranStr(ComStr));//ɾ���հ��ַ�
        char[]ch=GoodStr.toCharArray();//��ȡ�ַ�
        for(i=0;i<ch.length;i++)
            if(ch[i]==';')
                StrCount++;//�ۼ������
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
        GetComStrInfo();//��ȡ��Ϣ
    }
    private void GetComStrInfo()throws MyError{//��ȡ��ȡ�ı��ʽ����Ϣ
        for(int i=0;i<StrComArray.length;i++){
            ComStrList[i]=new StrComTree(FindColor(StrComArray[i],i));
            ComStrList[i].AddVar("x");
            ComStrList[i].AddVar("pi",java.lang.Math.PI);//ע�᳣��PI
            ComStrList[i].AddVar("e",java.lang.Math.E);//ע�᳣��E
        }
    }
    private String FindColor(String WithColorStr,int Index)throws MyError{//������ɫ��Ϣ���ַ������������ر��ʽ����
        int i,j=0,k=0;
        for(i=0;i<WithColorStr.length();i++){
            if(WithColorStr.charAt(i)=='[')
                j++;
            if(WithColorStr.charAt(i)==']')
                k++;
        }
        if(j==0&&k==0){//����ɫ��Ϣ(Ĭ�Ϻ�ɫ��)
            LineColor[Index][0]=255;
            LineColor[Index][1]=0;
            LineColor[Index][2]=0;
            return WithColorStr;
        }
        if(j!=1||k!=1)//��ɫ��Ϣ����
            throw new MyError(14);
        if(WithColorStr.indexOf('[')!=0)//��ɫ��Ϣ����
            throw new MyError(14);
        SetColorStr(WithColorStr.substring(1,WithColorStr.indexOf(']')),Index);
        return WithColorStr.substring(WithColorStr.indexOf(']')+1);
    }
    private void SetColorStr(String ColorStr,int Index)throws MyError{//�趨��ɫ��Ϣ
        int i,j=0;
        for(i=0;i<ColorStr.length();i++)
            if(ColorStr.charAt(i)==',')
                j++;
        if(j!=2)//��ɫ��Ϣ����
            throw new MyError(14);
        i=ColorStr.indexOf(',');
        j=ColorStr.lastIndexOf(',');
        LineColor[Index][0]=Is255Num(ColorStr.substring(0,i));
        LineColor[Index][1]=Is255Num(ColorStr.substring(i+1,j));
        LineColor[Index][2]=Is255Num(ColorStr.substring(j+1));
    }
    private int Is255Num(String NumStr)throws MyError{//���NumStrΪһ��0--255�������򷵻ط��򱨴�
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
            case 1:return "ѡ���������";
            case 2:return "ѡ���������";
            case 3:
                try {
                    return "���x="+MyFunction.CutNumStr(GetFuncAns(),14);
                } catch (MyError Err) {
                    return "������!["+Err.GetErrNum()+"]"+Err.GetErrMsg();
                }
            case -1:return "��ѡ����ŵ�������!";
            case -2:return "����Ӧ��������!";
            case -3:return "������!";
            default:return "����δ֪...";
        }
    }
    private double GetFuncAns()throws MyError{//���ּ���,LeftNegtive=true����ߺ���ֵΪ��
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
    public void InitFuncAns(){//��ʼ����ⷽ�̵Ĳ���
        FuncStep=1;//�ӵ�һ����ʼ
        this.ChangeMode=true;
        FuncNowSx=this.getWidth()/4;//��ʼ��ѡ����λ��
    }
    private void GoToNextStep(){//����ת��
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
