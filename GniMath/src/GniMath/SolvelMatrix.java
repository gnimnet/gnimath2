package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *矩阵计算程序                      *
 ************************************/
import MyTypes.*;

public class SolvelMatrix {
    private T_Matrix[] MatixArray;
    private T_Matrix Ans;
    public int MaxNum=50;
    //初始化矩阵数组
    public SolvelMatrix() {
        MatixArray=new T_Matrix[MaxNum];
        for(int i=0;i<MaxNum;i++)
            MatixArray[i]=null;
    }
    //********************自定义内部函数********************//
    private String Cmd_Add(String Data)throws MyError{//添加矩阵指令
        for(int pos=0;pos<MaxNum;pos++){
            if(MatixArray[pos]==null){
                MatixArray[pos]=new T_Matrix(Data);
                return "添加矩阵成功,编号:"+(pos+1);
            }
        }
        return "添加失败,定义空间满!";
    }
    private String Cmd_Del(String Data)throws MyError{//删除矩阵指令
        int DelNum=GetMatrixNo(Data);
        if(MatixArray[DelNum]==null)
            return "该编号["+(DelNum+1)+"]对应矩阵不存在!";
        else{
            MatixArray[DelNum]=null;
            return "删除成功!编号:"+(DelNum+1);
        }
    }
    private String Cmd_Com(String Data)throws MyError{
        int m=0,n=0,k=0;
        for(int i=0;i<Data.length();i++){
            if(Data.charAt(i)==',')
                m++;
            else if(Data.charAt(i)=='(')
                n++;
            else if(Data.charAt(i)==')')
                k++;
        }
        if(Data.charAt(0)=='+'||Data.charAt(0)=='-'||Data.charAt(0)=='*'||
                Data.charAt(0)=='$'||Data.charAt(0)=='^'){//二元运算
            if(m!=1||n!=1||k!=1)
                return "数据应为Sym2(Num1,Num2)";
            if(Data.charAt(1)!='('||Data.charAt(Data.length()-1)!=')'||
                    Data.indexOf(',')<Data.indexOf('(')||Data.indexOf(',')>Data.indexOf(')'))
                return "数据应为Sym(Num1,Num2)";
            m=Data.indexOf(',');
            n=Data.indexOf(')');
            switch(Data.charAt(0)){
                case '+'://计算矩阵和
                    Ans=T_Matrix.MatrixAdd(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MatixArray[GetMatrixNo(Data.substring(m+1,n))]);
                    break;
                case '-'://计算矩阵差
                    Ans=T_Matrix.MatrixSub(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MatixArray[GetMatrixNo(Data.substring(m+1,n))]);
                    break;
                case '*'://计算矩阵积
                    Ans=T_Matrix.MatrixMul(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MatixArray[GetMatrixNo(Data.substring(m+1,n))]);
                    break;
                case '$'://计算矩阵乘以常数
                    Ans=T_Matrix.MatrixMulConst(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MyFunction.IsNum(Data.substring(m+1,n)));
                    break;
                case '^'://计算矩阵阶乘
                    Ans=T_Matrix.MatrixFactorial(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            (int)MyFunction.IsNum(Data.substring(m+1,n)));
                    break;
                default:
                    return "未知的错误发生,产生未知运算符!";
            }
        }
        else if(Data.charAt(0)=='t'||Data.charAt(0)=='r'||Data.charAt(0)=='d'){//一元运算
            if(m!=0||n!=1||k!=1)
                return "数据应为Sym1(Num)";
            if(Data.charAt(1)!='('||Data.charAt(Data.length()-1)!=')')
                return "数据应为Sym(Data1,Data2)";
            switch(Data.charAt(0)){
                case 't'://计算矩阵的转置
                    Ans=T_Matrix.MatrixT(MatixArray[GetMatrixNo(Data.substring(2,Data.length()-1))]);
                    break;
                case 'd'://计算矩阵的转置
                    int MaNum=GetMatrixNo(Data.substring(2,Data.length()-1));
                    double Det=T_Matrix.MatrixDet(MatixArray[MaNum]);
                    return "Det="+Double.toString(Det);
                case 'r'://求逆矩阵
                    Ans=T_Matrix.MatrixR(MatixArray[GetMatrixNo(Data.substring(2,Data.length()-1))]);
                    break;
                default://无法识别的符号
                    return "未知的错误发生,产生未知运算符!";
            }
        }
        else{//不可识别的运算符
            return "不可识别的运算符:"+Data.charAt(0)+"!";
        }
        return "计算完成!\n"+GetAns();
    }
    private String Cmd_Show(String Data)throws MyError{
        if(Data.equals("0"))
            return GetAns();
        int Num=GetMatrixNo(Data);
        System.out.println("Num:"+Num);
        if(MatixArray[Num]==null)
            return "不存在的矩阵编号!";
        else
            return GetMatrixStr(Num);
    }
    private String Cmd_Save(String Data)throws MyError{
        if(Ans==null)
            return "无计算结果,不能保存!";
        for(int pos=0;pos<MaxNum;pos++){
            if(MatixArray[pos]==null){
                MatixArray[pos]=new T_Matrix(Ans.MatrixNum,Ans.Rows,Ans.Cols);
                return "保存结果矩阵成功,编号:"+(pos+1);
            }
        }
        return "保存失败,定义空间满!";
    }
    private int GetMatrixNo(String NumStr)throws MyError{//从整数字符串中截取出数字并判断范围，减一返回
        int Rtn;
        for(int i=0;i<NumStr.length();i++){
            if(NumStr.charAt(i)<'0'||NumStr.charAt(i)>'9')
                throw new MyError(70);
        }
        Rtn=Integer.parseInt(NumStr);
        if(Rtn<1||Rtn>MaxNum)
            throw new MyError(71);
        else
            return Rtn-1;
    }
    private String GetAns(){
        int i,j;
        if(Ans==null)
            return "无计算结果!";
        String Rtn="";
        for(i=0;i<Ans.Rows;i++){
            Rtn=Rtn+"(";
            for(j=0;j<Ans.Cols-1;j++)
                Rtn=Rtn+Ans.MatrixNum[i][j]+",";
            Rtn=Rtn+Ans.MatrixNum[i][j];
            Rtn=Rtn+")\n";
        }
        return Rtn;
    }
    private String GetMatrixStr(int num){
        int i,j;
        String Rtn="";
        if(MatixArray[num]==null)
            return "不存在的矩阵编号!";
        for(i=0;i<MatixArray[num].Rows;i++){
            Rtn=Rtn+"(";
            for(j=0;j<MatixArray[num].Cols-1;j++)
                Rtn=Rtn+MatixArray[num].MatrixNum[i][j]+",";
            Rtn=Rtn+MatixArray[num].MatrixNum[i][j];
            Rtn=Rtn+")\n";
        }
        return Rtn;
    }
    private boolean ExistMatrix(int num){
        if(num>=MaxNum)
            return false;
        if(MatixArray[num]==null)
            return false;
        else
            return true;
    }
    //********************外部接口函数********************//
    public String DoCmd(String InputStr) throws MyError{
        String CmdAndData=MyFunction.DelSpace(MyFunction.MyTranStr(InputStr));
        int pos=CmdAndData.indexOf('|');
        String Cmd;
        String Data;
        if(pos==-1)
            throw new MyError(65);
        Cmd=CmdAndData.substring(0,pos);
        Data=CmdAndData.substring(pos+1);
        if(Cmd.equals("add"))//添加指令
            return Cmd_Add(Data);
        if(Cmd.equals("del"))//删除指令
            return Cmd_Del(Data);
        if(Cmd.equals("com"))//计算指令
            return Cmd_Com(Data);
        if(Cmd.equals("show"))//显示矩阵指令
            return Cmd_Show(Data);
        if(Cmd.equals("save"))//保存结果指令
            return Cmd_Save(Data);
        return "未知命令!";
    }
}
