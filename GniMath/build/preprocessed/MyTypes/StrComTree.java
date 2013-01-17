package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *��������ɨ����ʽ�������ʽ��    *
 ************************************/
import MyTypes.*;

public class StrComTree {
    /********************�������********************/
    public String ComStr;//���ʽ
    public T_ListNode Head;//��������
    public T_TreeNode Root;//���ɵı��ʽ�������ĸ��ڵ�
    private T_VarList Vars;//��������
    private T_MyStack NumStack=new T_MyStack(50);//���ֶ�ջ
    private T_MyStack SymStack=new T_MyStack(30);//���Ŷ�ջ
    private String[] FuncStr={"sin","cos","tan","asin","acos","atan",
                              "abs","log","exp","sqrt"};//֧����ֵ������
    private String[] SymStrArr={"+","-","*","/","^"};//֧�ַ��ű�
    /********************���캯��********************/
    public StrComTree(String ComputeStr)throws MyError{//���ʽ�����캯��
        /********************�ַ�������********************/
        if(ComputeStr.equals(""))
            throw new MyError(11);
        if(!MyFunction.CheckStr(ComputeStr).equals(""))//���Ƿ��ַ�
            throw new MyError(1,"����ʶ����ַ�:"+MyFunction.CheckStr(ComputeStr));
        ComStr=ComputeStr;//������ʽ
        /********************����������********************/
        Head=CreateList(ComputeStr);//��ȡ������
        CheckList(Head);//��鵥����
        SetList(Head);//����������
        //PrintList(Head);
        /********************������********************/
        Root=CreateTree();
        //PrintTree(Root);
        OptimizeTree(Root);//�Ż���
        //PrintTree(Root);
        Vars=null;//��ʼ�������б�Ϊ��
    }
    /********************�Զ����ڲ�����********************/
    /*--------------------����غ���--------------------*/
    private T_TreeNode CreateTree()throws MyError{//��ȡת����ϵ��ַ���Ϊ������
        T_TreeNode TreeTmp;
        T_ListNode Pos=Head;//������ɨ��ָ��
        boolean SearchMode=true;//true--���ֻ�Ŀ���㺯����false--��Ŀ�����
        while(Pos!=null){
            if(SearchMode){//�������ֻ�Ŀ���㺯��
                switch(Pos.Type){
                    case 1://����
                        TreeTmp=new T_TreeNode(Double.parseDouble(Pos.WordStr));
                        NumStack.Push(TreeTmp);
                        SearchMode=false;
                        break;
                    case 2://��Ŀ�����
                        throw new MyError(4);
                    case 3://��ĸ
                        if(FuncSupport(Pos.WordStr)){//����
                            while(!CanPushSym(Pos.WordStr))
                                ComputeStack();
                            TreeTmp=new T_TreeNode(Pos.WordStr);
                            SymStack.Push(TreeTmp);
                            SearchMode=true;
                        }
                        else{//����
                            TreeTmp=new T_TreeNode(Pos.WordStr);
                            NumStack.Push(TreeTmp);
                            SearchMode=false;
                        }
                        break;
                    case 4://������
                        TreeTmp=new T_TreeNode(Pos.WordStr);
                        SymStack.Push(TreeTmp);
                        break;
                    case 5://������
                        while(!SymStack.GetTop().NodeStr.equals("("))
                            ComputeStack();
                        SymStack.Pop();//����������
                        break;
                }
            }
            else{//���Ҷ�Ŀ�����
                switch(Pos.Type){
                    case 1://����
                        throw new MyError(6);
                    case 2://��Ŀ�����
                        while(!CanPushSym(Pos.WordStr))
                            ComputeStack();
                        TreeTmp=new T_TreeNode(Pos.WordStr);
                        SymStack.Push(TreeTmp);
                        SearchMode=true;
                        break;
                    case 3://��ĸ
                        throw new MyError(6);
                    case 4://������
                        throw new MyError(6);
                    case 5://������
                        while(!SymStack.GetTop().NodeStr.equals("("))
                            ComputeStack();
                        SymStack.Pop();//����������
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
        if(NowRoot.Left==null && NowRoot.Right==null){//Ҷ�ӽڵ�Ϊ���������
            if(NowRoot.NodeStr.equals(""))
                return NowRoot.NodeNum;//ȡ����
            else
                return GetVar(NowRoot.NodeStr);//ȡ����
        }
        else{//��Ҷ�ӽڵ�
            if(NowRoot.Right==null)//һԪ����
                return FuncDef(NowRoot.NodeStr,ComputeTree(NowRoot.Left));
            else//��Ԫ����
                return FuncDef(NowRoot.NodeStr,ComputeTree(NowRoot.Left),ComputeTree(NowRoot.Right));
        }
    }
    private String OptimizeTree(T_TreeNode NowRoot)throws MyError{
        String LeftRtn,RightRtn;
        if(NowRoot.Left==null && NowRoot.Right==null){//Ҷ�ӽڵ�Ϊ���������
            if(NowRoot.NodeStr.equals(""))
                return Double.toString(NowRoot.NodeNum);//ȡ����
            else
                return "";//�����򲻿��Ż�
        }
        else{//��Ҷ�ӽڵ�
            if(NowRoot.Right==null){//һԪ����
                LeftRtn=OptimizeTree(NowRoot.Left);
                if(LeftRtn.equals(""))
                    return "";//�����Ż�
                else{
                    NowRoot.NodeNum=FuncDef(NowRoot.NodeStr,Double.parseDouble(LeftRtn));
                    NowRoot.NodeStr="";
                    NowRoot.Left=null;
                    return Double.toString(NowRoot.NodeNum);
                }
            }
            else{//��Ԫ����
                LeftRtn=OptimizeTree(NowRoot.Left);
                RightRtn=OptimizeTree(NowRoot.Right);
                if(LeftRtn.equals("")||RightRtn.equals(""))
                    return "";//�����Ż�
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
        if(NowRoot.Left==null && NowRoot.Right==null){//Ҷ�ӽڵ�Ϊ���������
            if(NowRoot.NodeStr.equals(""))
                System.out.print("    "+NowRoot.NodeNum);
            else
                System.out.print("    "+NowRoot.NodeStr);
        }
        else{//��Ҷ�ӽڵ�
            if(NowRoot.Right==null){//һԪ����
                System.out.print("    "+NowRoot.NodeStr+" | ");
                PrintTree(NowRoot.Left);
                System.out.print(" | ");
            }
            else{//��Ԫ����
                PrintTree(NowRoot.Left);
                System.out.print("    "+NowRoot.NodeStr);
                PrintTree(NowRoot.Right);
            }
        }
    }
    /*--------------------������غ���--------------------*/
    private T_ListNode CreateList(String GoodStr){//���ʽ�ȡ����
        T_ListNode CreateList,NodePos,NodeTmp;
        char[] StrCh=(GoodStr+"#").toCharArray();//�����ֹ����ת��Ϊ�ַ�����
        int Pos=0;//ɨ��λ��
        int PosTmp=0;//��ʱɨ��ָ��
        int NowType=GetChType(StrCh[Pos]);//��ȡ��һ���ַ�������
        /********************��ȡ����ͷ********************/
        while(GetChType(StrCh[++PosTmp])==NowType){//���ҵ���һ����ͬ���ַ�
            if(StrCh[Pos]=='('||StrCh[Pos]==')')
                break;//�����򵥶�Ϊһ���ڵ�
            if(PosTmp>=StrCh.length-1)
                break;//����ַ���ɨ�����������
        }
        CreateList=new T_ListNode(GoodStr.substring(Pos,PosTmp));//��ȡͬ���ַ�
        CreateList.Type=NowType;
        NodePos=CreateList;
        Pos=PosTmp;
        /********************��ȡ������********************/
        while(Pos<StrCh.length-1){
            NowType=GetChType(StrCh[Pos]);//��ȡ��ǰ�ַ�������
            while(GetChType(StrCh[++PosTmp])==NowType){//���ҵ���һ����ͬ���ַ�
                if(StrCh[Pos]=='('||StrCh[Pos]==')')
                    break;//�����򵥶�Ϊһ���ڵ�
                if(PosTmp>=StrCh.length-1)
                    break;//����ַ���ɨ�����������
            }
            NodePos.Next=NodeTmp=new T_ListNode(GoodStr.substring(Pos,PosTmp));//��ȡͬ���ַ�
            NodeTmp.Type=NowType;
            NodePos=NodeTmp;
            Pos=PosTmp;
        }
        return CreateList;
    }
    private void CheckList(T_ListNode ListHead)throws MyError{//�Բ鵥������
        T_ListNode Pos=ListHead;
        while(Pos!=null){
            if(Pos.Type==1){//���������ȷ��
                /*----------���ֻ��С������ַ���----------*/
                if(Pos.WordStr.equals("."))
                    throw new MyError(12);
                /*----------������С����----------*/
                int Pcount=0;
                char[] StrCh=Pos.WordStr.toCharArray();
                for(int i=0;i<StrCh.length-1;i++)
                    if(StrCh[i]=='.')
                        Pcount++;
                if(Pcount>=2)
                    throw new MyError(3);
            }
            else if(Pos.Type==2){//��������ȷ��
                if(!SymSupport(Pos.WordStr))
                    throw new MyError(1,"����ʶ����ַ�:"+Pos.WordStr);
            }
            Pos=Pos.Next;
        }
    }
    private void SetList(T_ListNode ListHead)throws MyError{//������������
        T_ListNode Pos=ListHead;
        /********************�ϲ������ŵ�������********************/
        while(Pos!=null){
            if(Pos.WordStr.equals("("))//��������������ܲ���������
                if(Pos.Next.WordStr.equals("-")||Pos.Next.WordStr.equals("+"))//�жϷ���
                    if(Pos.Next.Next.Type==1){//�жϺ����Ƿ�������
                        Pos.Next.Next.WordStr=Pos.Next.WordStr+Pos.Next.Next.WordStr;//�ϲ����ŵ�����
                        Pos.Next=Pos.Next.Next;//ɾ���������
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
    /*--------------------�����жϺ���--------------------*/
    private int GetChType(char ch){//�����ַ�����
        if(ch>='0'&&ch<='9')return 1;//����
        if(ch=='.')return 1;//С����
        if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='^')return 2;//��Ŀ����
        if(ch>='a'&&ch<='z')return 3;//��ĸ
        if(ch=='(')return 4;//������
        if(ch==')')return 5;//������
        return 0;//��������
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
    /*--------------------������غ���--------------------*/
    private void ComputeStack()throws MyError{//����ջѹջ����
        T_TreeNode NumA,NumB,Sym;
        Sym=SymStack.Pop();
        if(Sym.NodeStr.equals("("))
            throw new MyError(5);
        if(FuncSupport(Sym.NodeStr)){//������Ŀ����
            NumA=NumStack.Pop();
            Sym.Left=NumA;
            NumStack.Push(Sym);
        }
        else{//��Ŀ�����
            NumB=NumStack.Pop();
            NumA=NumStack.Pop();
            Sym.Left=NumA;
            Sym.Right=NumB;
            NumStack.Push(Sym);
        }
    }
    private boolean CanPushSym(String SymStr)throws MyError{
        if(SymStack.GetElmNum()==0)
            return true;//ջ���޷���������ѹ��
        if(OSLv(SymStr)>ISLv(SymStack.GetTop().NodeStr))//��ջ���Ƚ����ȼ�
            return true;//ջ�����ȼ�����ջ�����ȼ��ſ���ջ������true
        else
            return false;//���򷵻�false
    }
    private int ISLv(String SymStr){//ջ�����ȼ�(In Stack Level)
        if(SymStr.equals("("))return 0;
        if(SymStr.equals("+")||SymStr.equals("-"))return 2;
        if(SymStr.equals("*")||SymStr.equals("/"))return 4;
        if(SymStr.equals("^"))return 6;
        if(FuncSupport(SymStr))return 8;
        return -1;
    }
    private int OSLv(String SymStr){//ջ�����ȼ�(Out Stack Level)
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
    /********************�ⲿ����********************/
    /*--------------------������غ���--------------------*/
    public void AddVar(String Str)throws MyError{//��ӱ���
        T_VarList Pos=Vars;
        if(HaveVar(Str))
            throw new MyError(10);
        if(Vars==null){
            Vars=new T_VarList(Str);
        }
        else{
            while(Pos.Next!=null)
                Pos=Pos.Next;//�ҵ�����β
            Pos.Next=new T_VarList(Str);
        }
    }
    public void AddVar(String Str,double Num)throws MyError{//��ӱ���(����ֵ)
        T_VarList Pos=Vars;
        if(HaveVar(Str))
            throw new MyError(10);
        if(Vars==null){
            Vars=new T_VarList(Str,Num);
        }
        else{
            while(Pos.Next!=null)
                Pos=Pos.Next;//�ҵ�����β
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
            Pos=Pos.Next;//������һ��
        }
        throw new MyError(7);
    }
    public double GetVar(String Str)throws MyError{
        T_VarList Pos=Vars;
        while(Pos!=null){
            if(Pos.VarStr.equals(Str))
                return Pos.VarNum;
            Pos=Pos.Next;//������һ��
        }
        throw new MyError(7,"�����ڵı���!["+Str+"]");
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
            Pos=Pos.Next;//������һ��
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
