package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *���ʽɨ��������                  *
 ************************************/
public class T_ListNode{//��������
    public String WordStr;//�����ַ���
    public T_ListNode Next;//��һ������
    public int Type;//��������
    public T_ListNode(){//��ʼ������ڵ�
        WordStr="";
        Next=null;
        Type=0;
    }
    public T_ListNode(String Str){//��ʼ������ڵ�
        WordStr=Str;
        Next=null;
        Type=0;
    }
}
