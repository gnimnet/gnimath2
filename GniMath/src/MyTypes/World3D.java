package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *��ά����ϵ����                    *
 ************************************/
import javax.microedition.lcdui.*;
import MyTypes.*;

public class World3D extends Canvas{
    protected boolean ChangeMode=true;//ģʽѡ��
    protected boolean Pressed=false;//��������
    protected double Proportion;//���ű���
    protected int Xplus,Yplus;//����ƽ����
    protected double Angle1,Angle2;//�ǶȲ���
    protected int OldPressX,OldPressY;//���簴�µ���Ļ����
    protected double AngleBox1,AngleBox2;//��ʱ�ǶȲ���(For Test Box)
    //********************���캯��********************//
    public World3D() {
        this.setTitle("3D����ϵ");
        Proportion=50;
        Xplus=this.getWidth()/2;
        Yplus=this.getHeight()/2;
        AngleBox1=Angle1=Math.toRadians(-125);
        AngleBox2=Angle2=Math.toRadians(-65);
    }
    //********************�ع��麯��********************//
    protected void paint(Graphics g) {
        InitScreen(g);//��ʼ����Ļ
        if(Pressed){
            g.setColor(255,0,0);
            DrawBox(g);
        }
        else{
            g.setColor(0,0,0);
            DrawBox(g);
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
        this.setTitle("3D����ϵ");
        Pressed=false;
        Angle1=AngleBox1;
        Angle2=AngleBox2;
        repaint();
    }
    public void keyReleased(int key){//�������ı����귶Χ
        ChangeRange(key);
    }
    //********************�Զ��庯��********************//
    protected double GetSx(double fx,double fy,double fz){//��3ά����ת����2ά�����x
        return (-fx*Math.sin(Angle1)+fy*Math.cos(Angle1))*Proportion+Xplus;
    }
    protected double GetSy(double fx,double fy,double fz){//��3ά����ת����2ά�����y
        return (-fx*Math.cos(Angle2)*Math.cos(Angle1)-fy*Math.cos(Angle2)*Math.sin(Angle1)+fz*Math.sin(Angle2))*Proportion+Yplus;
    }
    protected void Line3D(Graphics g,double x1,double y1,double z1,double x2,double y2,double z2){//������άֱ��
        g.drawLine((int)GetSx(x1,y1,z1),(int)GetSy(x1,y1,z1),(int)GetSx(x2,y2,z2),(int)GetSy(x2,y2,z2));
    }
    protected void InitScreen(Graphics g){//��ʼ������
        SetColor(g,0);//�趨����ɫ
        g.fillRect(0,0,this.getWidth(),this.getHeight());//��ʼ������
    }
    protected void SetColor(Graphics g,int Type){//��ͼ��ɫ�趨����
        switch(Type){
            case 0:g.setColor(255,255,255);break;//��ɫ(����ɫ)
            case 1:g.setColor(0,0,0);break;//��ɫ(Ĭ����ɫ)
            case 2:g.setColor(255,0,0);break;//��ɫ
            case 3:g.setColor(0,255,0);break;//��ɫ
            case 4:g.setColor(0,0,255);break;//��ɫ
            case -1:g.setColor(0,0,0);break;//��ɫ(ͼ��ɫ)
            case -2:g.setColor(0,255,0);break;//ǳ��ɫ(��Χ��)
            case -3:g.setColor(0,127,0);break;//����ɫ(��Ϣɫ)
            case -4:g.setColor(255,0,0);break;//��ɫ(��Χ��Ϣɫ)
            default:g.setColor(0,0,0);break;//Ĭ��ɫ(��ɫ)
        }
    }
    protected void ChangeRange(int Key){//�ı����귶Χ����
        //int Key ---- ���µİ���
        int code=this.getGameAction(Key);
        if(ChangeMode){//ƽ��ģʽ
            switch(code){
                case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                case Canvas.UP:Yplus-=this.getHeight()/16;repaint();break;
                case Canvas.DOWN:Yplus+=this.getHeight()/16;repaint();break;
                case Canvas.LEFT:Xplus-=this.getWidth()/16;repaint();break;
                case Canvas.RIGHT:Xplus+=this.getWidth()/16;repaint();break;
            }
        }
        else{//��ת
            switch(code){
                case Canvas.FIRE:ChangeMode=!ChangeMode;repaint();break;
                case Canvas.UP:Angle2-=Math.PI/16;repaint();break;
                case Canvas.DOWN:Angle2+=Math.PI/16;repaint();break;
                case Canvas.LEFT:Angle1-=Math.PI/16;repaint();break;
                case Canvas.RIGHT:Angle1+=Math.PI/16;repaint();break;
            }
        }
    }
    //********************3D���Ժк���********************//
    private double GetSxForBox(double fx,double fy,double fz){
        return (-fx*Math.sin(AngleBox1)+fy*Math.cos(AngleBox1))*64+this.getWidth()/2;
    }
    private double GetSyForBox(double fx,double fy,double fz){
        return (-fx*Math.cos(AngleBox2)*Math.cos(AngleBox1)-fy*Math.cos(AngleBox2)*Math.sin(AngleBox1)+fz*Math.sin(AngleBox2))*64+this.getHeight()/2;
    }
    private void Line3DForBox(Graphics g,int x1,int y1,int z1,int x2,int y2,int z2){
        g.drawLine((int)GetSxForBox(x1,y1,z1),(int)GetSyForBox(x1,y1,z1),(int)GetSxForBox(x2,y2,z2),(int)GetSyForBox(x2,y2,z2));
    }
    protected void DrawBox(Graphics g){
        Line3DForBox(g,0,0,0,0,0,1);
        Line3DForBox(g,0,1,0,0,1,1);
        Line3DForBox(g,1,1,0,1,1,1);
        Line3DForBox(g,1,0,0,1,0,1);
        Line3DForBox(g,0,0,1,0,1,1);
        Line3DForBox(g,0,1,1,1,1,1);
        Line3DForBox(g,1,1,1,1,0,1);
        Line3DForBox(g,1,0,1,0,0,1);
        Line3DForBox(g,0,0,0,0,1,0);
        Line3DForBox(g,0,1,0,1,1,0);
        Line3DForBox(g,1,1,0,1,0,0);
        Line3DForBox(g,1,0,0,0,0,0);
        Line3DForBox(g,1,1,0,0,0,0);
        Line3DForBox(g,1,0,0,0,1,0);
        g.drawString("x",(int)GetSxForBox(1,0,0),(int)GetSyForBox(1,0,0),Graphics.TOP | Graphics.LEFT);
        g.drawString("y",(int)GetSxForBox(0,1,0),(int)GetSyForBox(0,1,0),Graphics.TOP | Graphics.LEFT);
        g.drawString("z",(int)GetSxForBox(0,0,1),(int)GetSyForBox(0,0,1),Graphics.TOP | Graphics.LEFT);
    }
}
