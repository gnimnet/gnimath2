package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *ƽ�����ɫ��3Dͼ                  *
 ***********************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class Color3D extends World2D{
    private String ComStr;//���ʽ
    private StrComTree ComTree;//������
    private double MaxZ;
    private double MinZ;
    private int Step;//���㲽��
    private boolean Repaint=true;
    public boolean ProgramMode=true;//����ģʽ
    private int SxLine=this.getWidth()/2,SyLine=this.getHeight()/2;//ɨ����λ��
    //********************���캯��********************//
    public Color3D(String Func3DStr,String RangeStr,String Info) throws MyError{
        GetComTree(Func3DStr);//���ɼ�����
        GetRange(RangeStr);//��ȡ����ϵ��Χ
        InitInfo(Info);//��ʼ��Z��Χ��Ϣ������
    }
    //********************�ع���ͼ����********************//
    protected void paint(Graphics g){
        boolean DrawHaveErr=false;
        if(Repaint){
            this.SetRangeTitle();
            this.InitScreen(g);
            try {
                PrintFunc(g);//����ͼ��
            } catch (MyError Err) {
                DrawHaveErr=true;//����ʱ����
            }
            this.DrawLineXY(g);
        }else
            Repaint=true;
        if(this.ProgramMode){
            if(!this.ChangeMode)
                PrintInfo(g,DrawHaveErr);//�����Ϣ
        }
        else{
            g.setColor(0,255,0);
            if(this.ChangeMode)
                g.drawString("�쿴��ģʽ:20",10,10,g.TOP|g.LEFT);
            else
                g.drawString("�쿴��ģʽ:2",10,10,g.TOP|g.LEFT);
            g.setColor(255,255,255);
            g.drawLine(SxLine,0,SxLine,this.getHeight());
            g.drawLine(0,SyLine,this.getWidth(),SyLine);
            double x,y,z;
            x=SxtoFx(SxLine);
            y=SytoFy(SyLine);
            try{
                ComTree.SetVar("x",x);
                ComTree.SetVar("y",y);
                z=ComTree.ComputeTree(ComTree.Root);
                this.setTitle("("+MyFunction.CutNumStr(x)+","
                    +MyFunction.CutNumStr(y)+","+MyFunction.CutNumStr(z)+")");
            }catch(MyError Err){
                //do nothing
            }
        }
    }
    public void pointerDragged(int Sx,int Sy){//��ʾ����ѡ�е������ֵ�����
        double x,y,z;
        if(!this.ChangeMode && Sy<=6){
            double tmp=((double)Sx/(double)this.getWidth())*(MaxZ-MinZ)+MinZ;
            this.setTitle("����ɫ��Ӧzֵ:"+MyFunction.CutNumStr(tmp,7));
            return;
        }
        x=SxtoFx(Sx);
        y=SytoFy(Sy);
        try{
            ComTree.SetVar("x",x);
            ComTree.SetVar("y",y);
            z=ComTree.ComputeTree(ComTree.Root);
            this.setTitle("("+MyFunction.CutNumStr(x)+","
                +MyFunction.CutNumStr(y)+","+MyFunction.CutNumStr(z)+")");
        }catch(MyError Err){
            //do nothing
        }
    }
    public void pointerPressed(int Sx,int Sy){//��ʾ����ѡ�е������ֵ�����
        double x,y,z;
        if(!this.ChangeMode && Sy<=6){
            double tmp=((double)Sy/(double)this.getWidth())*(MaxZ-MinZ)+MinZ;
            this.setTitle("����ɫ��Ӧzֵ:"+tmp);
            return;
        }
        x=SxtoFx(Sx);
        y=SytoFy(Sy);
        try{
            ComTree.SetVar("x",x);
            ComTree.SetVar("y",y);
            z=ComTree.ComputeTree(ComTree.Root);
            this.setTitle("("+MyFunction.CutNumStr(x)+","
                +MyFunction.CutNumStr(y)+","+MyFunction.CutNumStr(z)+")");
        }catch(MyError Err){
            //do nothing
        }
    }
    public void keyReleased(int key){
        int code=this.getGameAction(key);
        if(this.ProgramMode){
            if(this.ChangeMode && code==Canvas.FIRE){
                ChangeMode=!ChangeMode;
                Repaint=false;
                repaint();
            }
            else
                this.ChangeRange(key);
        }
        else{
            if(this.ChangeMode){
                switch(code){
                    case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                    case Canvas.UP:
                        if(SyLine>20)
                            SyLine-=20;
                        repaint();
                        break;
                    case Canvas.DOWN:
                        if(SyLine<this.getHeight()-20)
                            SyLine+=20;
                        repaint();
                        break;
                    case Canvas.LEFT:
                        if(SxLine>20)
                            SxLine-=20;
                        repaint();
                        break;
                    case Canvas.RIGHT:
                        if(SxLine<this.getWidth()-20)
                            SxLine+=20;
                        repaint();
                        break;
                }
            }
            else{
                switch(code){
                    case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                    case Canvas.UP:
                        if(SyLine>2)
                            SyLine-=2;
                        repaint();
                        break;
                    case Canvas.DOWN:
                        if(SyLine<this.getHeight()-2)
                            SyLine+=2;
                        repaint();
                        break;
                    case Canvas.LEFT:
                        if(SxLine>2)
                            SxLine-=2;
                        repaint();
                        break;
                    case Canvas.RIGHT:
                        if(SxLine<this.getWidth()-2)
                            SxLine+=2;
                        repaint();
                        break;
                }
            }
        }
    }
    //********************�Զ��庯��********************//
    private void PrintFunc(Graphics g)throws MyError{
        double tmp;
        for(int i=0;i<this.getWidth();i+=Step){
            for(int j=0;j<this.getHeight();j+=Step){
                ComTree.SetVar("x",SxtoFx(i));
                ComTree.SetVar("y",SytoFy(j));
                tmp=(ComTree.ComputeTree(ComTree.Root)-MinZ)/(MaxZ-MinZ);
                if(tmp<0)
                    g.setColor(0,64,64);
                else if(tmp>1)
                    g.setColor(191,191,0);
                else
                    g.setColor((int)(255*tmp),0,(int)(255-255*tmp));
                g.fillRect(i,j,Step,Step);
            }
        }
    }
    private void PrintInfo(Graphics g,boolean HaveErr){
        int w=this.getWidth();
        for(int i=0;i<w;i++){
            g.setColor(255*i/w,0,255-255*i/w);
            g.drawLine(i,0,i,5);
        }
        g.setColor(255,255,255);
        g.drawLine(0,6,w,6);
        for(int j=0;j<this.getWidth();j=j+5)
            g.drawLine(j,4,j,5);
        for(int j=0;j<this.getWidth();j=j+50){
            g.drawLine(j,2,j,5);
            double Num=((double)j/(double)w)*(MaxZ-MinZ)+MinZ;
            g.drawString(MyFunction.CutNumStr(Num),j,5,Graphics.TOP | Graphics.LEFT);
        }
        if(HaveErr)
            g.drawString("f(x,y)="+ComStr+",�������д���",10,g.getFont().getHeight()+10,Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("f(x,y)="+ComStr,10,g.getFont().getHeight()+10,Graphics.TOP | Graphics.LEFT);
        g.drawString("zɫ�ʷ�Χ("+MinZ+","+MaxZ+")",10,g.getFont().getHeight()*2+12,Graphics.TOP | Graphics.LEFT);
        g.drawString("���Ʋ���:"+Step+"����",10,g.getFont().getHeight()*3+14,Graphics.TOP | Graphics.LEFT);
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
            throw new MyError(50);
        i=Info.indexOf('(');
        j=Info.indexOf(',');
        m=Info.indexOf(')');
        n=Info.indexOf('|');
        if(i>j||j>m||m>n||(m!=n-1))
            throw new MyError(50);
        MinZ=MyFunction.IsNum(Info.substring(i+1,j));
        MaxZ=MyFunction.IsNum(Info.substring(j+1,m));
        Step=(int)MyFunction.IsNum(Info.substring(n+1));
        if(MinZ>=MaxZ)
            throw new MyError(51);
        if(Step>this.getWidth()/4||Step>this.getHeight()/4||Step<1)
            throw new MyError(52);
    }
}
