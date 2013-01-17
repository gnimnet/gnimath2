package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *表达式扫描链表类                  *
 ************************************/
public class T_ListNode{//单词链表
    public String WordStr;//单词字符串
    public T_ListNode Next;//下一个单词
    public int Type;//单词类型
    public T_ListNode(){//初始化链表节点
        WordStr="";
        Next=null;
        Type=0;
    }
    public T_ListNode(String Str){//初始化链表节点
        WordStr=Str;
        Next=null;
        Type=0;
    }
}
