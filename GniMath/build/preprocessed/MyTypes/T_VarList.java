package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *���ʽ����������                  *
 ************************************/
public class T_VarList {
    public String VarStr;//�����ַ���
    public double VarNum;//������ֵ
    public T_VarList Next;//��һ������
    public T_VarList(String Str){//��ʼ������ڵ�
        VarStr=Str;
        Next=null;
    }
    public T_VarList(String Str,double Num){//��ʼ������ڵ�
        VarStr=Str;
        VarNum=Num;
        Next=null;
    }
}
