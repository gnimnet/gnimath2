package MyTypes;

import MyTypes.*;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *该类用于全局使用的自定义函数      *
 ************************************/
public class T_MyStack {
    private T_TreeNode[] StackItem;
    private int StackMax;
    private int StackTop;
    public T_MyStack(int MaxSize) {
        StackItem=new T_TreeNode[MaxSize];
        StackMax=MaxSize;
        StackTop=0;
    }
    public void Push(T_TreeNode NewNode)throws MyError{
        if(StackTop>=StackMax)
            throw new MyError(20);
        StackItem[StackTop++]=NewNode;
    }
    public T_TreeNode Pop()throws MyError{
        if(StackTop<=0)
            throw new MyError(21);
        return StackItem[--StackTop];
    }
    public T_TreeNode GetTop()throws MyError{
        if(StackTop<=0)
            throw new MyError(22);
        return StackItem[StackTop-1];
    }
    public int GetElmNum(){
        return StackTop;
    }
}
