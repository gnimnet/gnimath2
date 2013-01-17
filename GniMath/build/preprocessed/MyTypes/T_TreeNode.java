package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *表达式扫描树类                    *
 ************************************/
public class T_TreeNode{//表达式树结点类
    public String NodeStr;//结点字符
    public double NodeNum;//结点数(Left==NULL && Right==NULL时使用数)
    public T_TreeNode Left;//左操作数
    public T_TreeNode Right;//右操作数
    public T_TreeNode(){//初始化树结点
        NodeStr="";
        NodeNum=0;
        Left=null;
        Right=null;
    }
    public T_TreeNode(String Str){//初始化树结点
        NodeStr=Str;
        NodeNum=0;
        Left=null;
        Right=null;
    }
    public T_TreeNode(double Num){//初始化树结点
        NodeStr="";
        NodeNum=Num;
        Left=null;
        Right=null;
    }
}
