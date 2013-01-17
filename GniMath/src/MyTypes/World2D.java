package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *��ά����ϵ����                    *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class World2D extends Canvas{
    //********************����ϵ����********************//
    protected double Xup;//x������
    protected double Xdown;//x������
    protected double Ymax;//�������ֵ
    protected double Ymin;//������Сֵ
    private double dfx;//fxֵ���
    private double dfy;//fyֵ���
    private int Window_W;//��Ļ�Ŀ��
    private int Window_H;//��Ļ�ĸ߶� 
    private int Axis_X;//y����Ļxֵ
    private int Axis_Y;//x����Ļyֵ
    protected boolean ChangeMode=true;//����任ģʽ
    //********************���캯��********************//
    public World2D() {
        this.setTitle("2D����ϵ");
        Window_W=this.getWidth();//�����Ļ���
        Window_H=this.getHeight();//�����Ļ�߶�
    }
    //********************�麯�����********************//
    protected void paint(Graphics g){//Ĭ�ϻ��ƺ���
        SetRangeTitle();//�趨����Ϊ���귶Χ
        InitScreen(g);//��ʼ������
        DrawLineXY(g);//����������
    }
    public void pointerDragged(int x,int y){//��ʾ����ѡ�е������ֵ�����
        SetPressTitle(x,y);
    }
    public void pointerPressed(int x,int y){//��ʾ����ѡ�е������ֵ�����
        SetPressTitle(x,y);
    }
    public void pointerReleased(int x,int y){//�����뿪�ָ���ʾ���귶Χ
        SetRangeTitle();
    }
    public void keyReleased(int key){//�������ı����귶Χ
        ChangeRange(key);
    }
    //********************����ת������********************//
    protected double SxtoFx(double Sx){//����Ļx�����ú���xֵ
        return Sx*(Xup-Xdown)/(Window_W)+Xdown;
    }
    protected double SytoFy(double Sy){//����Ļy�����ú���yֵ
        if(Ymax!=Ymin)
            return (Window_H-Sy)*(Ymax-Ymin)/Window_H+Ymin;
        else
            return Ymax;
    }
    protected double FxtoSx(double Fx){//�Ӻ���xֵ�����Ļx����
        return (Fx-Xdown)*Window_W/(Xup-Xdown);
    }
    protected double FytoSy(double Fy){//�Ӻ���yֵ�����Ļy����
        if(Ymax!=Ymin)
            return Window_H-(Fy-Ymin)*Window_H/(Ymax-Ymin);
        else
            return ((double)Window_H)/2;
    }
    //********************�Զ��庯��********************//
    protected void InitScreen(Graphics g){//��ʼ������
        SetColor(g,0);//�趨����ɫ
        g.fillRect(0,0,this.getWidth(),this.getHeight());//��ʼ������
    }
    protected void DrawLineXY(Graphics g){//���������ắ��
        SetColor(g,1);//�趨��������ɫ
        //���㵥λ���صĺ������
        dfx=(Xup-Xdown)/(Window_W);
        dfy=(Ymax-Ymin)/(Window_H);
        //����������
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
        //X�������
        for(tmp=Axis_X;tmp<=Window_W-5;tmp+=5)
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-2);
        for(tmp=Axis_X;tmp>=0;tmp-=5)
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-2);
        for(tmp=Axis_X+50;tmp<=Window_W-5;tmp+=50){
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-5);
            if(ChangeMode)
                SetColor(g,-1);//�任ģʽ1ɫ
            else
                SetColor(g,-2);//�任ģʽ2ɫ
            g.drawString(MyFunction.CutNumStr(SxtoFx(tmp)),tmp,Axis_Y-5,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
        for(tmp=Axis_X-50;tmp>=0;tmp-=50){
            g.drawLine(tmp,Axis_Y,tmp,Axis_Y-5);
            if(ChangeMode)
                SetColor(g,-1);//�任ģʽ1ɫ
            else
                SetColor(g,-2);//�任ģʽ2ɫ
            g.drawString(MyFunction.CutNumStr(SxtoFx(tmp)),tmp,Axis_Y-5,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
        //Y�������
        for(tmp=Axis_Y;tmp<=Window_H;tmp+=5)
            g.drawLine(Axis_X,tmp,Axis_X+2,tmp);
        for(tmp=Axis_Y;tmp>=5;tmp-=5)
            g.drawLine(Axis_X,tmp,Axis_X+2,tmp);
        for(tmp=Axis_Y+50;tmp<=Window_H;tmp+=50){
            g.drawLine(Axis_X,tmp,Axis_X+5,tmp);
            if(ChangeMode)
                SetColor(g,-1);//�任ģʽ1ɫ
            else
                SetColor(g,-2);//�任ģʽ2ɫ
            g.drawString(MyFunction.CutNumStr(SytoFy(tmp)),Axis_X+5,tmp,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
        for(tmp=Axis_Y-50;tmp>=5;tmp-=50){
            g.drawLine(Axis_X,tmp,Axis_X+5,tmp);
            if(ChangeMode)
                SetColor(g,-1);//�任ģʽ1ɫ
            else
                SetColor(g,-2);//�任ģʽ2ɫ
            g.drawString(MyFunction.CutNumStr(SytoFy(tmp)),Axis_X+5,tmp,g.LEFT|g.BOTTOM);
            SetColor(g,1);
        }
    }
    protected void SetColor(Graphics g,int Type){//��ͼ��ɫ�趨����
        switch(Type){
            case 0:g.setColor(255,255,255);break;//��ɫ(����ɫ)
            case 1:g.setColor(0,0,0);break;//��ɫ(Ĭ����ɫ)
            case 2:g.setColor(255,0,0);break;//��ɫ
            case 3:g.setColor(0,255,0);break;//��ɫ
            case 4:g.setColor(0,0,255);break;//��ɫ
            case -1:g.setColor(0,0,0);break;//��ɫ(�任ģʽ1��ɫ)
            case -2:g.setColor(0,255,0);break;//ǳ��ɫ(�任ģʽ2��ɫ)
            case -3:g.setColor(0,127,0);break;//����ɫ(��Ϣɫ)
            default:g.setColor(0,0,0);break;//Ĭ��ɫ(��ɫ)
        }
    }
    protected void SetRangeTitle(){//��ʾ���귶Χ
        this.setTitle("x:["+MyFunction.CutNumStr(Xdown)+","+MyFunction.CutNumStr(Xup)+
                "],y:["+MyFunction.CutNumStr(Ymin)+","+MyFunction.CutNumStr(Ymax)+"]");
    }
    protected void SetPressTitle(int Sx,int Sy){//��ʾָ�����꼰�����
        //int Sx ---- ��Ļx����
        //int Sy ---- ��Ļy����
        this.setTitle("("+MyFunction.CutNumStr(SxtoFx(Sx))+"+"+MyFunction.CutNumStr(dfx)+","
            +MyFunction.CutNumStr(SytoFy(Sy))+"+"+MyFunction.CutNumStr(dfy)+")");
    }
    protected void ChangeRange(int Key){//�ı����귶Χ����
        //int Key ---- ���µİ���
        int code=this.getGameAction(Key);
        double Xwidth=Xup-Xdown;//��ȡ������
        double Yheight=Ymax-Ymin;//��ȡ����߶�
        if(ChangeMode){//ƽ��ģʽ
            switch(code){
                case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                case Canvas.UP:Ymax-=Yheight/4;Ymin-=Yheight/4;repaint();break;
                case Canvas.DOWN:Ymax+=Yheight/4;Ymin+=Yheight/4;repaint();break;
                case Canvas.LEFT:Xup+=Xwidth/4;Xdown+=Xwidth/4;repaint();break;
                case Canvas.RIGHT:Xup-=Xwidth/4;Xdown-=Xwidth/4;repaint();break;
            }
        }
        else{//����ģʽ
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
}
