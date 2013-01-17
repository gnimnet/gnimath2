package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *表达式变量链表类                  *
 ************************************/
public class T_VarList {
    public String VarStr;//变量字符串
    public double VarNum;//变量数值
    public T_VarList Next;//下一个变量
    public T_VarList(String Str){//初始化链表节点
        VarStr=Str;
        Next=null;
    }
    public T_VarList(String Str,double Num){//初始化链表节点
        VarStr=Str;
        VarNum=Num;
        Next=null;
    }
}
