package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *���ʽɨ������                    *
 ************************************/
public class T_TreeNode{//���ʽ�������
    public String NodeStr;//����ַ�
    public double NodeNum;//�����(Left==NULL && Right==NULLʱʹ����)
    public T_TreeNode Left;//�������
    public T_TreeNode Right;//�Ҳ�����
    public T_TreeNode(){//��ʼ�������
        NodeStr="";
        NodeNum=0;
        Left=null;
        Right=null;
    }
    public T_TreeNode(String Str){//��ʼ�������
        NodeStr=Str;
        NodeNum=0;
        Left=null;
        Right=null;
    }
    public T_TreeNode(double Num){//��ʼ�������
        NodeStr="";
        NodeNum=Num;
        Left=null;
        Right=null;
    }
}
