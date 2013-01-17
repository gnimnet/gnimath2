package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *Plot3D����Canvas��                *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class Plot3D extends World3D{
    private String ComStr;//���ʽ
    private StrComTree ComTree;//������
    private double Xup,Xdown;//�����x��Χ
    private double Ymax,Ymin;//�����y��Χ
    private double[][]ArrayZ;//����������
    private double Zmax,Zmin;//z�ı仯��Χ
    private double StepX;//X����
    private double StepY;//Y����
    private boolean Pressed;//���´�����
    private int PointX,PointY;//X,Y�����ϵĵ���
    private boolean HaveErr;
    //********************���캯��********************//
    public Plot3D(String FuncStr,String Range,String MovXY) throws MyError{
        GetComTree(FuncStr);//���ɼ�����
        GetRange(Range);//��ȡ����ϵ��Χ
        InitInfo(MovXY);//��ȡƫ����Ϣ�����ű�
        PointX=10;
        PointY=10;
        GetArrayZ();//���ɽ������
        this.setTitle(this.ComStr);
    }
    //********************�ع��麯��********************//
    protected void paint(Graphics g) {
        InitScreen(g);//��ʼ����Ļ
        if(Pressed){
            g.setColor(255,0,0);
            DrawBox(g);
        }
        else{
            DrawFunc(g);
        }
    }
    public void pointerDragged(int x,int y){
        this.setTitle("�϶���ת��...");
        AngleBox1=Angle1+Math.PI*((double)OldPressX-(double)x)/(double)this.getWidth();
        AngleBox2=Angle2+Math.PI*((double)OldPressY-(double)y)/(double)this.getWidth();
        repaint();
    }
    public void pointerPressed(int x,int y){
        this.setTitle("���϶���ת");
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
    /********************�Զ����ڲ�����********************/
    private void DrawFunc(Graphics g){
        DrawFuncGrid(g);//�����߻���
        DrawRange(g);//��Χ����
        DrawInfo(g);//�����Ϣ
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
                g.drawString("��תģʽ,�������д���",10,10,g.LEFT|g.TOP);
            else
                g.drawString("��תģʽ",10,10,g.LEFT|g.TOP);
            SetColor(g,-4);
            g.drawString("x��Χ:"+MyFunction.CutNumStr(Xdown,5)+","+
                    MyFunction.CutNumStr(Xup,5),10,10+g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("y��Χ:"+MyFunction.CutNumStr(Ymin,5)+","+
                    MyFunction.CutNumStr(Ymax,5),10,12+2*g.getFont().getHeight(),g.LEFT|g.TOP);
            g.drawString("z��Χ:"+MyFunction.CutNumStr(Zmin,5)+","+
                    MyFunction.CutNumStr(Zmax,5),10,14+3*g.getFont().getHeight(),g.LEFT|g.TOP);
        }
        else{
            if(HaveErr)
                g.drawString("ƽ��ģʽ,�������д���",10,10,g.LEFT|g.TOP);
            else
                g.drawString("ƽ��ģʽ",10,10,g.LEFT|g.TOP);
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
        ComTree.AddVar("pi",java.lang.Math.PI);//ע�᳣��PI
        ComTree.AddVar("e",java.lang.Math.E);//ע�᳣��E
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
        if(j!=1||k!=1||m!=1||n!=1)//��Ϣ����
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
        if(j!=1||k!=1||l!=1)//��Χ��Ϣ����
            throw new MyError(15);
        if(OneRange.indexOf('(')!=0)
            throw new MyError(15);
        if(OneRange.indexOf(')')!=OneRange.length()-1)
            throw new MyError(15);
        if(IsX){
            Xup=MyFunction.IsNum(OneRange.substring(OneRange.indexOf(',')+1,OneRange.length()-1));//���x������
            Xdown=MyFunction.IsNum(OneRange.substring(1,OneRange.indexOf(',')));//���x������
            if(Xup<=Xdown)
                throw new MyError(16);
        }
        else{
            Ymax=MyFunction.IsNum(OneRange.substring(OneRange.indexOf(',')+1,OneRange.length()-1));//���y������
            Ymin=MyFunction.IsNum(OneRange.substring(1,OneRange.indexOf(',')));//���y������
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
    //********************���ⲿ�Ľӿ�********************//
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
            GetArrayZ();//���ɽ������
            repaint();
        }
    }
    public void ToBeLess(){
        if(PointX>=2 && PointY>=2){
            PointX/=2;
            PointY/=2;
            GetArrayZ();//���ɽ������
            repaint();
        }
    }
}
