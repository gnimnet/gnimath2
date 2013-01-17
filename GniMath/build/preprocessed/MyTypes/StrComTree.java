package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *该类用于扫描表达式构建表达式树    *
 ************************************/
import MyTypes.*;

public class StrComTree {
    /********************定义变量********************/
    public String ComStr;//表达式
    public T_ListNode Head;//单词链表
    public T_TreeNode Root;//生成的表达式计算树的根节点
    private T_VarList Vars;//变量链表
    private T_MyStack NumStack=new T_MyStack(50);//数字堆栈
    private T_MyStack SymStack=new T_MyStack(30);//符号堆栈
    private String[] FuncStr={"sin","cos","tan","asin","acos","atan",
                              "abs","log","exp","sqrt"};//支持数值函数表
    private String[] SymStrArr={"+","-","*","/","^"};//支持符号表
    /********************构造函数********************/
    public StrComTree(String ComputeStr)throws MyError{//表达式树构造函数
        /********************字符串处理********************/
        if(ComputeStr.equals(""))
            throw new MyError(11);
        if(!MyFunction.CheckStr(ComputeStr).equals(""))//检查非法字符
            throw new MyError(1,"不可识别的字符:"+MyFunction.CheckStr(ComputeStr));
        ComStr=ComputeStr;//保存表达式
        /********************单词链处理********************/
        Head=CreateList(ComputeStr);//获取单词链
        CheckList(Head);//检查单词链
        SetList(Head);//修正单词链
        //PrintList(Head);
        /********************构建树********************/
        Root=CreateTree();
        //PrintTree(Root);
        OptimizeTree(Root);//优化树
        //PrintTree(Root);
        Vars=null;//初始化变量列表为空
    }
    /********************自定义内部函数********************/
    /*--------------------树相关函数--------------------*/
    private T_TreeNode CreateTree()throws MyError{//截取转换完毕的字符串为单词链
        T_TreeNode TreeTmp;
        T_ListNode Pos=Head;//单词链扫描指针
        boolean SearchMode=true;//true--数字或单目运算函数，false--二目运算符
        while(Pos!=null){
            if(SearchMode){//查找数字或单目运算函数
                switch(Pos.Type){
                    case 1://数字
                        TreeTmp=new T_TreeNode(Double.parseDouble(Pos.WordStr));
                        NumStack.Push(TreeTmp);
                        SearchMode=false;
                        break;
                    case 2://二目运算符
                        throw new MyError(4);
                    case 3://字母
                        if(FuncSupport(Pos.WordStr)){//函数
                            while(!CanPushSym(Pos.WordStr))
                                ComputeStack();
                            TreeTmp=new T_TreeNode(Pos.WordStr);
                            SymStack.Push(TreeTmp);
                            SearchMode=true;
                        }
                        else{//变量
                            TreeTmp=new T_TreeNode(Pos.WordStr);
                            NumStack.Push(TreeTmp);
                            SearchMode=false;
                        }
                        break;
                    case 4://左括号
                        TreeTmp=new T_TreeNode(Pos.WordStr);
                        SymStack.Push(TreeTmp);
                        break;
                    case 5://右括号
                        while(!SymStack.GetTop().NodeStr.equals("("))
                            ComputeStack();
                        SymStack.Pop();//弹出左括号
                        break;
                }
            }
            else{//查找二目运算符
                switch(Pos.Type){
                    case 1://数字
                        throw new MyError(6);
                    case 2://二目运算符
                        while(!CanPushSym(Pos.WordStr))
                            ComputeStack();
                        TreeTmp=new T_TreeNode(Pos.WordStr);
                        SymStack.Push(TreeTmp);
                        SearchMode=true;
                        break;
                    case 3://字母
                        throw new MyError(6);
                    case 4://左括号
                        throw new MyError(6);
                    case 5://右括号
                        while(!SymStack.GetTop().NodeStr.equals("("))
                            ComputeStack();
                        SymStack.Pop();//弹出左括号
                        break;
                }
            }
            Pos=Pos.Next;
        }
        while(SymStack.GetElmNum()!=0){
            ComputeStack();
        }
        if(NumStack.GetElmNum()==1)
            return NumStack.GetTop();
        else
            throw new MyError(2);
    }
    public double ComputeTree(T_TreeNode NowRoot)throws MyError{
        if(NowRoot.Left==null && NowRoot.Right==null){//叶子节点为常数或变量
            if(NowRoot.NodeStr.equals(""))
                return NowRoot.NodeNum;//取数字
            else
                return GetVar(NowRoot.NodeStr);//取变量
        }
        else{//非叶子节点
            if(NowRoot.Right==null)//一元运算
                return FuncDef(NowRoot.NodeStr,ComputeTree(NowRoot.Left));
            else//二元运算
                return FuncDef(NowRoot.NodeStr,ComputeTree(NowRoot.Left),ComputeTree(NowRoot.Right));
        }
    }
    private String OptimizeTree(T_TreeNode NowRoot)throws MyError{
        String LeftRtn,RightRtn;
        if(NowRoot.Left==null && NowRoot.Right==null){//叶子节点为常数或变量
            if(NowRoot.NodeStr.equals(""))
                return Double.toString(NowRoot.NodeNum);//取数字
            else
                return "";//变量则不可优化
        }
        else{//非叶子节点
            if(NowRoot.Right==null){//一元运算
                LeftRtn=OptimizeTree(NowRoot.Left);
                if(LeftRtn.equals(""))
                    return "";//不可优化
                else{
                    NowRoot.NodeNum=FuncDef(NowRoot.NodeStr,Double.parseDouble(LeftRtn));
                    NowRoot.NodeStr="";
                    NowRoot.Left=null;
                    return Double.toString(NowRoot.NodeNum);
                }
            }
            else{//二元运算
                LeftRtn=OptimizeTree(NowRoot.Left);
                RightRtn=OptimizeTree(NowRoot.Right);
                if(LeftRtn.equals("")||RightRtn.equals(""))
                    return "";//不可优化
                else{
                    NowRoot.NodeNum=FuncDef(NowRoot.NodeStr,Double.parseDouble(LeftRtn),Double.parseDouble(RightRtn));
                    NowRoot.NodeStr="";
                    NowRoot.Left=null;
                    NowRoot.Right=null;
                    return Double.toString(NowRoot.NodeNum);
                }
            }
        }
    }
    private void PrintTree(T_TreeNode NowRoot){
        if(NowRoot.Left==null && NowRoot.Right==null){//叶子节点为常数或变量
            if(NowRoot.NodeStr.equals(""))
                System.out.print("    "+NowRoot.NodeNum);
            else
                System.out.print("    "+NowRoot.NodeStr);
        }
        else{//非叶子节点
            if(NowRoot.Right==null){//一元运算
                System.out.print("    "+NowRoot.NodeStr+" | ");
                PrintTree(NowRoot.Left);
                System.out.print(" | ");
            }
            else{//二元运算
                PrintTree(NowRoot.Left);
                System.out.print("    "+NowRoot.NodeStr);
                PrintTree(NowRoot.Right);
            }
        }
    }
    /*--------------------链表相关函数--------------------*/
    private T_ListNode CreateList(String GoodStr){//单词截取函数
        T_ListNode CreateList,NodePos,NodeTmp;
        char[] StrCh=(GoodStr+"#").toCharArray();//添加终止符并转化为字符数组
        int Pos=0;//扫描位置
        int PosTmp=0;//临时扫描指针
        int NowType=GetChType(StrCh[Pos]);//获取第一个字符的类型
        /********************截取链表头********************/
        while(GetChType(StrCh[++PosTmp])==NowType){//查找到下一个非同类字符
            if(StrCh[Pos]=='('||StrCh[Pos]==')')
                break;//括号则单独为一个节点
            if(PosTmp>=StrCh.length-1)
                break;//如果字符串扫描完成则跳出
        }
        CreateList=new T_ListNode(GoodStr.substring(Pos,PosTmp));//截取同类字符
        CreateList.Type=NowType;
        NodePos=CreateList;
        Pos=PosTmp;
        /********************截取链表体********************/
        while(Pos<StrCh.length-1){
            NowType=GetChType(StrCh[Pos]);//获取当前字符的类型
            while(GetChType(StrCh[++PosTmp])==NowType){//查找到下一个非同类字符
                if(StrCh[Pos]=='('||StrCh[Pos]==')')
                    break;//括号则单独为一个节点
                if(PosTmp>=StrCh.length-1)
                    break;//如果字符串扫描完成则跳出
            }
            NodePos.Next=NodeTmp=new T_ListNode(GoodStr.substring(Pos,PosTmp));//截取同类字符
            NodeTmp.Type=NowType;
            NodePos=NodeTmp;
            Pos=PosTmp;
        }
        return CreateList;
    }
    private void CheckList(T_ListNode ListHead)throws MyError{//自查单词链表
        T_ListNode Pos=ListHead;
        while(Pos!=null){
            if(Pos.Type==1){//检查数字正确性
                /*----------检查只有小数点的字符串----------*/
                if(Pos.WordStr.equals("."))
                    throw new MyError(12);
                /*----------检查多余小数点----------*/
                int Pcount=0;
                char[] StrCh=Pos.WordStr.toCharArray();
                for(int i=0;i<StrCh.length-1;i++)
                    if(StrCh[i]=='.')
                        Pcount++;
                if(Pcount>=2)
                    throw new MyError(3);
            }
            else if(Pos.Type==2){//检查符号正确性
                if(!SymSupport(Pos.WordStr))
                    throw new MyError(1,"不可识别的字符:"+Pos.WordStr);
            }
            Pos=Pos.Next;
        }
    }
    private void SetList(T_ListNode ListHead)throws MyError{//修正单词链表
        T_ListNode Pos=ListHead;
        /********************合并正负号到数字中********************/
        while(Pos!=null){
            if(Pos.WordStr.equals("("))//碰到左括号则可能产生正负号
                if(Pos.Next.WordStr.equals("-")||Pos.Next.WordStr.equals("+"))//判断符号
                    if(Pos.Next.Next.Type==1){//判断后面是否是数字
                        Pos.Next.Next.WordStr=Pos.Next.WordStr+Pos.Next.Next.WordStr;//合并符号到数字
                        Pos.Next=Pos.Next.Next;//删除多余符号
                    }
            Pos=Pos.Next;
        }
    }
    private void PrintList(T_ListNode ListHead){//For Debug
        T_ListNode NodePos=ListHead;
        while(NodePos!=null){
            System.out.print("    ["+NodePos.Type+"]"+NodePos.WordStr);
            NodePos=NodePos.Next;
        }
        System.out.println();
    }
    /*--------------------处理判断函数--------------------*/
    private int GetChType(char ch){//返回字符类型
        if(ch>='0'&&ch<='9')return 1;//数字
        if(ch=='.')return 1;//小数点
        if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='^')return 2;//二目运算
        if(ch>='a'&&ch<='z')return 3;//字母
        if(ch=='(')return 4;//左括号
        if(ch==')')return 5;//右括号
        return 0;//其他符号
    }
    private boolean FuncSupport(String FuncName){
        for(int i=0;i<FuncStr.length;i++)
            if(FuncStr[i].equals(FuncName))
                return true;
        return false;
    }
    private boolean SymSupport(String SymName){
        for(int i=0;i<SymStrArr.length;i++)
            if(SymStrArr[i].equals(SymName))
                return true;
        return false;
    }
    /*--------------------计算相关函数--------------------*/
    private void ComputeStack()throws MyError{//符号栈压栈函数
        T_TreeNode NumA,NumB,Sym;
        Sym=SymStack.Pop();
        if(Sym.NodeStr.equals("("))
            throw new MyError(5);
        if(FuncSupport(Sym.NodeStr)){//函数单目运算
            NumA=NumStack.Pop();
            Sym.Left=NumA;
            NumStack.Push(Sym);
        }
        else{//二目运算符
            NumB=NumStack.Pop();
            NumA=NumStack.Pop();
            Sym.Left=NumA;
            Sym.Right=NumB;
            NumStack.Push(Sym);
        }
    }
    private boolean CanPushSym(String SymStr)throws MyError{
        if(SymStack.GetElmNum()==0)
            return true;//栈内无符号则允许压入
        if(OSLv(SymStr)>ISLv(SymStack.GetTop().NodeStr))//与栈顶比较优先级
            return true;//栈外优先级高于栈内优先级才可入栈，返回true
        else
            return false;//否则返回false
    }
    private int ISLv(String SymStr){//栈内优先级(In Stack Level)
        if(SymStr.equals("("))return 0;
        if(SymStr.equals("+")||SymStr.equals("-"))return 2;
        if(SymStr.equals("*")||SymStr.equals("/"))return 4;
        if(SymStr.equals("^"))return 6;
        if(FuncSupport(SymStr))return 8;
        return -1;
    }
    private int OSLv(String SymStr){//栈内优先级(Out Stack Level)
        if(SymStr.equals("("))return 9;
        if(SymStr.equals("+")||SymStr.equals("-"))return 1;
        if(SymStr.equals("*")||SymStr.equals("/"))return 3;
        if(SymStr.equals("^"))return 5;
        if(FuncSupport(SymStr))return 7;
        return -1;
    }
    private double FuncDef(String MyFunc,double Num)throws MyError{
        if(MyFunc.equals("sin"))return java.lang.Math.sin(Num);
        if(MyFunc.equals("cos"))return java.lang.Math.cos(Num);
        if(MyFunc.equals("tan"))return java.lang.Math.tan(Num);
        if(MyFunc.equals("asin"))return MyFunction.asin(Num);
        if(MyFunc.equals("acos"))return MyFunction.acos(Num);
        if(MyFunc.equals("atan"))return MyFunction.atan(Num);
        if(MyFunc.equals("abs"))return java.lang.Math.abs(Num);
        if(MyFunc.equals("log"))return MyFunction.log(Num);
        if(MyFunc.equals("exp"))return MyFunction.exp(Num);
        if(MyFunc.equals("sqrt"))return java.lang.Math.sqrt(Num);
        throw new MyError(8);
    }
    private double FuncDef(String MyFunc,double NumA,double NumB)throws MyError{
        if(MyFunc.equals("+"))return (NumA+NumB);
        if(MyFunc.equals("-"))return (NumA-NumB);
        if(MyFunc.equals("*"))return (NumA*NumB);
        if(MyFunc.equals("/")){
            if(NumB==0)
                return Double.NaN;
            else
                return (NumA/NumB);
        }
        if(MyFunc.equals("^"))return MyFunction.pow(NumA,NumB);
        return 0;
    }
    /********************外部函数********************/
    /*--------------------变量相关函数--------------------*/
    public void AddVar(String Str)throws MyError{//添加变量
        T_VarList Pos=Vars;
        if(HaveVar(Str))
            throw new MyError(10);
        if(Vars==null){
            Vars=new T_VarList(Str);
        }
        else{
            while(Pos.Next!=null)
                Pos=Pos.Next;//找到链表尾
            Pos.Next=new T_VarList(Str);
        }
    }
    public void AddVar(String Str,double Num)throws MyError{//添加变量(含数值)
        T_VarList Pos=Vars;
        if(HaveVar(Str))
            throw new MyError(10);
        if(Vars==null){
            Vars=new T_VarList(Str,Num);
        }
        else{
            while(Pos.Next!=null)
                Pos=Pos.Next;//找到链表尾
            Pos.Next=new T_VarList(Str,Num);
        }
    }
    public void SetVar(String Str,double Num)throws MyError{
        T_VarList Pos=Vars;
        while(Pos!=null){
            if(Pos.VarStr.equals(Str)){
                Pos.VarNum=Num;
                return;
            }
            Pos=Pos.Next;//查找下一个
        }
        throw new MyError(7);
    }
    public double GetVar(String Str)throws MyError{
        T_VarList Pos=Vars;
        while(Pos!=null){
            if(Pos.VarStr.equals(Str))
                return Pos.VarNum;
            Pos=Pos.Next;//查找下一个
        }
        throw new MyError(7,"不存在的变量!["+Str+"]");
    }
    public void DelVar(String Str)throws MyError{
        T_VarList Pos=Vars;
        if(Vars.VarStr.equals(Str)){
            Vars=Vars.Next;
            return;
        }
        while(Pos.Next!=null){
            if(Pos.Next.VarStr.equals(Str)){
                Pos.Next=Pos.Next.Next;
                return;
            }
            Pos=Pos.Next;//查找下一个
        }
        throw new MyError(7);
    }
    public boolean HaveVar(String Str){
        T_VarList Pos=Vars;
        while(Pos!=null){
            if(Pos.VarStr.equals(Str))
                return true;
            Pos=Pos.Next;
        }
        return false;
    }
}
