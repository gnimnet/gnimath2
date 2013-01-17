package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *����������                      *
 ************************************/
import MyTypes.*;

public class SolvelMatrix {
    private T_Matrix[] MatixArray;
    private T_Matrix Ans;
    public int MaxNum=50;
    //��ʼ����������
    public SolvelMatrix() {
        MatixArray=new T_Matrix[MaxNum];
        for(int i=0;i<MaxNum;i++)
            MatixArray[i]=null;
    }
    //********************�Զ����ڲ�����********************//
    private String Cmd_Add(String Data)throws MyError{//��Ӿ���ָ��
        for(int pos=0;pos<MaxNum;pos++){
            if(MatixArray[pos]==null){
                MatixArray[pos]=new T_Matrix(Data);
                return "��Ӿ���ɹ�,���:"+(pos+1);
            }
        }
        return "���ʧ��,����ռ���!";
    }
    private String Cmd_Del(String Data)throws MyError{//ɾ������ָ��
        int DelNum=GetMatrixNo(Data);
        if(MatixArray[DelNum]==null)
            return "�ñ��["+(DelNum+1)+"]��Ӧ���󲻴���!";
        else{
            MatixArray[DelNum]=null;
            return "ɾ���ɹ�!���:"+(DelNum+1);
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
                Data.charAt(0)=='$'||Data.charAt(0)=='^'){//��Ԫ����
            if(m!=1||n!=1||k!=1)
                return "����ӦΪSym2(Num1,Num2)";
            if(Data.charAt(1)!='('||Data.charAt(Data.length()-1)!=')'||
                    Data.indexOf(',')<Data.indexOf('(')||Data.indexOf(',')>Data.indexOf(')'))
                return "����ӦΪSym(Num1,Num2)";
            m=Data.indexOf(',');
            n=Data.indexOf(')');
            switch(Data.charAt(0)){
                case '+'://��������
                    Ans=T_Matrix.MatrixAdd(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MatixArray[GetMatrixNo(Data.substring(m+1,n))]);
                    break;
                case '-'://��������
                    Ans=T_Matrix.MatrixSub(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MatixArray[GetMatrixNo(Data.substring(m+1,n))]);
                    break;
                case '*'://��������
                    Ans=T_Matrix.MatrixMul(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MatixArray[GetMatrixNo(Data.substring(m+1,n))]);
                    break;
                case '$'://���������Գ���
                    Ans=T_Matrix.MatrixMulConst(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            MyFunction.IsNum(Data.substring(m+1,n)));
                    break;
                case '^'://�������׳�
                    Ans=T_Matrix.MatrixFactorial(MatixArray[GetMatrixNo(Data.substring(2,m))],
                            (int)MyFunction.IsNum(Data.substring(m+1,n)));
                    break;
                default:
                    return "δ֪�Ĵ�����,����δ֪�����!";
            }
        }
        else if(Data.charAt(0)=='t'||Data.charAt(0)=='r'||Data.charAt(0)=='d'){//һԪ����
            if(m!=0||n!=1||k!=1)
                return "����ӦΪSym1(Num)";
            if(Data.charAt(1)!='('||Data.charAt(Data.length()-1)!=')')
                return "����ӦΪSym(Data1,Data2)";
            switch(Data.charAt(0)){
                case 't'://��������ת��
                    Ans=T_Matrix.MatrixT(MatixArray[GetMatrixNo(Data.substring(2,Data.length()-1))]);
                    break;
                case 'd'://��������ת��
                    int MaNum=GetMatrixNo(Data.substring(2,Data.length()-1));
                    double Det=T_Matrix.MatrixDet(MatixArray[MaNum]);
                    return "Det="+Double.toString(Det);
                case 'r'://�������
                    Ans=T_Matrix.MatrixR(MatixArray[GetMatrixNo(Data.substring(2,Data.length()-1))]);
                    break;
                default://�޷�ʶ��ķ���
                    return "δ֪�Ĵ�����,����δ֪�����!";
            }
        }
        else{//����ʶ��������
            return "����ʶ��������:"+Data.charAt(0)+"!";
        }
        return "�������!\n"+GetAns();
    }
    private String Cmd_Show(String Data)throws MyError{
        if(Data.equals("0"))
            return GetAns();
        int Num=GetMatrixNo(Data);
        System.out.println("Num:"+Num);
        if(MatixArray[Num]==null)
            return "�����ڵľ�����!";
        else
            return GetMatrixStr(Num);
    }
    private String Cmd_Save(String Data)throws MyError{
        if(Ans==null)
            return "�޼�����,���ܱ���!";
        for(int pos=0;pos<MaxNum;pos++){
            if(MatixArray[pos]==null){
                MatixArray[pos]=new T_Matrix(Ans.MatrixNum,Ans.Rows,Ans.Cols);
                return "����������ɹ�,���:"+(pos+1);
            }
        }
        return "����ʧ��,����ռ���!";
    }
    private int GetMatrixNo(String NumStr)throws MyError{//�������ַ����н�ȡ�����ֲ��жϷ�Χ����һ����
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
            return "�޼�����!";
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
            return "�����ڵľ�����!";
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
    //********************�ⲿ�ӿں���********************//
    public String DoCmd(String InputStr) throws MyError{
        String CmdAndData=MyFunction.DelSpace(MyFunction.MyTranStr(InputStr));
        int pos=CmdAndData.indexOf('|');
        String Cmd;
        String Data;
        if(pos==-1)
            throw new MyError(65);
        Cmd=CmdAndData.substring(0,pos);
        Data=CmdAndData.substring(pos+1);
        if(Cmd.equals("add"))//���ָ��
            return Cmd_Add(Data);
        if(Cmd.equals("del"))//ɾ��ָ��
            return Cmd_Del(Data);
        if(Cmd.equals("com"))//����ָ��
            return Cmd_Com(Data);
        if(Cmd.equals("show"))//��ʾ����ָ��
            return Cmd_Show(Data);
        if(Cmd.equals("save"))//������ָ��
            return Cmd_Save(Data);
        return "δ֪����!";
    }
}
