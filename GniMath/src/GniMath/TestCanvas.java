package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *��ʾӲ����ϢCanvas����            *
 ***********************************/
import javax.microedition.lcdui.*;
public class TestCanvas extends Canvas{
    public TestCanvas(){
        this.setTitle("��Ļ��Ϣ");
    }
    protected void paint(Graphics g) {
        g.setColor(255,255,255);//�趨����ɫ
        g.fillRect(0,0,this.getWidth(),this.getHeight());//��ʼ������
        g.setColor(0,0,0);//�趨����ɫ
        g.drawString("��Ļ���:"+this.getWidth(),10,1*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        g.drawString("��Ļ�߶�:"+this.getHeight(),10,2*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        if(this.hasPointerEvents())
            g.drawString("֧�ִ���:��",10,3*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("֧�ִ���:��",10,3*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        if(this.hasPointerMotionEvents())
            g.drawString("֧����ק:��",10,4*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
        else
            g.drawString("֧����ק:��",10,4*(g.getFont().getHeight()+2),Graphics.TOP | Graphics.LEFT);
    }
}

