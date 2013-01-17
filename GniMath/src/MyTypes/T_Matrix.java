package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *������                            *
 ************************************/
import MyTypes.*;

public class T_Matrix {
    public double[][] MatrixNum;//�����ֵ
    public int Rows=0;//���������
    public int Cols=0;//���������
    //********************���캯��********************//
    public T_Matrix(String MaStr) throws MyError{
        CheckMaStr(MaStr);
        InitMatrix(MaStr);
    }
    public T_Matrix(int MaRows,int MaCols){
        Rows=MaRows;
        Cols=MaCols;
        MatrixNum=new double[MaRows][MaCols];
        for(int i=0;i<MaRows;i++)
            for(int j=0;j<MaCols;j++)
                MatrixNum[i][j]=0;
    }
    public T_Matrix(double[][] Num,int MaRows,int MaCols){
        Rows=MaRows;
        Cols=MaCols;
        MatrixNum=new double[MaRows][MaCols];
        for(int i=0;i<MaRows;i++)
            for(int j=0;j<MaCols;j++)
                MatrixNum[i][j]=Num[i][j];
    }
    //********************�Զ����ڲ�����********************//
    private void CheckMaStr(String MaStr) throws MyError{
        String tmpStr=MaStr;
        int lineB,lineE;//ɨ����е���ʼ'('����ֹ')'��λ��
        int count;//����ͳ��
        while(!tmpStr.equals("")){
            Rows++;
            lineB=tmpStr.indexOf('(');
            lineE=tmpStr.indexOf(')');
            if(lineB+1==lineE)
                throw new MyError(63);
            if(lineB!=0)
                throw new MyError(60);
            if(lineB>lineE)
                throw new MyError(61);
            count=1;
            for(int i=1;i<lineE;i++)
                if(tmpStr.charAt(i)==',')
                    count++;
            if(Cols==0)
                Cols=count;
            else if(count!=Cols)
                throw new MyError(62);
            tmpStr=tmpStr.substring(lineE+1);
        }
    }
    private void InitMatrix(String MaStr) throws MyError{
        MatrixNum=new double[Rows][Cols];
        String tmpStr=MaStr;
        String cutStr;
        int lineB,lineE;//ɨ����е���ʾ����ֹ
        int posB,posE;
        int count,SetRow=0;
        while(!tmpStr.equals("")){
            lineB=tmpStr.indexOf('(');
            lineE=tmpStr.indexOf(')');
            posB=1;
            count=0;
            while(posB<lineE){
                for(posE=posB;posE<lineE;posE++){
                    if(tmpStr.charAt(posE)==',')
                        break;
                }
                MatrixNum[SetRow][count++]=MyFunction.IsNum(tmpStr.substring(posB,posE));
                posB=posE+1;
            }
            tmpStr=tmpStr.substring(lineE+1);
            SetRow++;
        }
    }
    static private int HowManyNum2(int Num){
        int i=0,j=1;
        while(j<=Num && j>0){
            i++;
            j*=2;
        }
        return i;
    }
    static private boolean SetMax(T_Matrix M,int RowCol){//�ѶԽ���Ԫ��Ϊ�������
        boolean SymChange=false;
        int GetRow=FindMaxRow(M,RowCol);
        if(M.MatrixNum[GetRow][RowCol]<0){
            MulConstRow(M,GetRow,-1);//���Ϊ�����޸�Ϊ��
            SymChange=!SymChange;
        }
        if(GetRow!=RowCol){
            SwitchArrayRow(M,RowCol,GetRow);
            SymChange=!SymChange;
        }
        return SymChange;
    }
    static private boolean SubToZeroDown(T_Matrix M,int RowCol){//�öԽ���Ԫ��ȥ�����е�Ԫ��
        for(int i=RowCol+1;i<M.Rows;i++){
            if(SubToZeroAfterIndex_Row(M,RowCol,i,RowCol))
                return true;//����ȫ0��
        }
        return false;//��ȫ0��
    }
    static private void ChangeToR(T_Matrix M,T_Matrix R){//��Mת��Ϊ��λ����,Rת��ΪM�������(R֮ǰΪ��λ��)
        int i;
        for(i=0;i<M.Rows-1;i++){//��Ϊ����������ʽ
            SetMaxR(M,R,i);//������Ԫ
            SubToZeroDownR(M,R,i);//��ȥ������,ʹΪ0
        }
        for(i=M.Rows-1;i>=0;i--)//��Ϊ�Խ�����ʽ
            SubToZeroUpR(M,R,i);
        for(i=0;i<M.Rows;i++)//��Ϊ��λ����
            MulToOne(M,R,i);
    }
    static private void SetMaxR(T_Matrix M,T_Matrix R,int RowCol){//�ѶԽ���Ԫ��Ϊ�������
        int GetRow=FindMaxRow(M,RowCol);
        if(M.MatrixNum[GetRow][RowCol]<0){
            MulConstRow(M,GetRow,-1);//���Ϊ�����޸�Ϊ��
            MulConstRow(R,GetRow,-1);//ͬʱ���������
        }
        if(GetRow!=RowCol){
            SwitchArrayRow(M,RowCol,GetRow);
            SwitchArrayRow(R,RowCol,GetRow);
        }
    }
    static private void SubToZeroDownR(T_Matrix M,T_Matrix R,int RowCol){//�öԽ���Ԫ��ȥ�����е�Ԫ��
        for(int i=RowCol+1;i<M.Rows;i++)
            SubToZeroAfterIndex_RowR(M,R,RowCol,i,RowCol);
    }
    static private void SubToZeroUpR(T_Matrix M,T_Matrix R,int RowCol){//�öԽ���Ԫ��ȥ�����е�Ԫ��(�Ѿ�Ϊ�����Ǿ���)
        for(int i=RowCol-1;i>=0;i--){
            double Tmp=M.MatrixNum[i][RowCol]/M.MatrixNum[RowCol][RowCol];//����ȥ����
            M.MatrixNum[i][RowCol]=0;//��ȥ�����ֵ
            for(int j=0;j<R.Cols;j++)
                R.MatrixNum[i][j]=R.MatrixNum[i][j]-R.MatrixNum[RowCol][j]*Tmp;
        }
    }
    static private void MulToOne(T_Matrix M,T_Matrix R,int RowCol){//�ð������Խ�Ԫ��ת��Ϊ1
        double Tmp=1/M.MatrixNum[RowCol][RowCol];
        MulConstRow(M,RowCol,Tmp);
        MulConstRow(R,RowCol,Tmp);
    }
    //********************����ʽ��ز���********************//
    static private void SwitchArrayRow(T_Matrix M,int Row1,int Row2){//������Row1��Row2
        double tmp;
        for(int i=0;i<M.Cols;i++){
            tmp=M.MatrixNum[Row1][i];
            M.MatrixNum[Row1][i]=M.MatrixNum[Row2][i];
            M.MatrixNum[Row2][i]=tmp;
        }
    }
    static private void SwitchArrayCol(T_Matrix M,int Col1,int Col2){//������Col1��Col2
        double tmp;
        for(int i=0;i<M.Rows;i++){
            tmp=M.MatrixNum[i][Col1];
            M.MatrixNum[i][Col1]=M.MatrixNum[i][Col2];
            M.MatrixNum[i][Col2]=tmp;
        }
    }
    static private void MulConstRow(T_Matrix M,int Row,double ConstNum){//��Row=��Row*ConstNum
        for(int i=0;i<M.Cols;i++)
            M.MatrixNum[Row][i]=M.MatrixNum[Row][i]*ConstNum;
    }
    static private void MulConstCol(T_Matrix M,int Col,double ConstNum){//��Col=��Col*ConstNum
        for(int i=0;i<M.Rows;i++)
            M.MatrixNum[i][Col]=M.MatrixNum[i][Col]*ConstNum;
    }
    static private void SubByRow(T_Matrix M,int Row1,int Row2,double Num){//Row1=Row1-Row2*Num
        for(int i=0;i<M.Cols;i++){
            M.MatrixNum[Row1][i]=M.MatrixNum[Row1][i]-M.MatrixNum[Row2][i]*Num;
        }
    }
    static private void SubByCol(T_Matrix M,int Col1,int Col2,double Num){//Col1=Col1-Col2*Num
        for(int i=0;i<M.Rows;i++){
            M.MatrixNum[i][Col1]=M.MatrixNum[i][Col1]-M.MatrixNum[i][Col2]*Num;
        }
    }
    static private boolean SubToZeroAfterIndex_Row(T_Matrix M,int Row1,int Row2,int Index){//ͨ��������Row2��Index����Ϊ0(�·�)
        /*  |   1   2   3   |  <----M   |   1   2   3   |
         *  |   2   5   6   |           |   0   1   0   |   <----���(��ȥ��)
         *  |   3   8   4   |           |   3   8   4   |
         *  Row1 = 0    Row2 = 1    Index = 0
         */
        boolean BeZero=true;
        double Tmp=M.MatrixNum[Row2][Index]/M.MatrixNum[Row1][Index];//����ȥ����
        M.MatrixNum[Row2][Index]=0;
        for(int i=Index+1;i<M.Cols;i++){
            M.MatrixNum[Row2][i]=M.MatrixNum[Row2][i]-M.MatrixNum[Row1][i]*Tmp;
            if(M.MatrixNum[Row2][i]!=0||Math.abs(M.MatrixNum[Row2][i])>1E-20)
                BeZero=false;
        }
        return BeZero;
    }
    static private boolean SubToZeroAfterIndex_RowR(T_Matrix M,T_Matrix R,int Row1,int Row2,int Index){//ͨ��������Row2��Index����Ϊ0(�·�)ͬʱ�ı������
        /*  |   1   2   3   |  <----M   |   1   0   0   |  <----R
         *  |   2   5   6   |           |   0   1   0   |
         *  |   3   8   4   |           |   0   0   1   |
         *  Row1 = 0    Row2 = 1    Index = 0
        /*  |   1   2   3   | <-���M   |   1   0   0   |  <-���R
         *  |   0   1   0   |           |   -2  1   0   |
         *  |   3   8   4   |           |   0   0   1   |
         */
        boolean BeZero=true;
        double Tmp=M.MatrixNum[Row2][Index]/M.MatrixNum[Row1][Index];//����ȥ����
        M.MatrixNum[Row2][Index]=0;
        for(int i=Index+1;i<M.Cols;i++){
            M.MatrixNum[Row2][i]=M.MatrixNum[Row2][i]-M.MatrixNum[Row1][i]*Tmp;
            if(M.MatrixNum[Row2][i]!=0||Math.abs(M.MatrixNum[Row2][i])>1E-20)
                BeZero=false;
        }
        for(int i=0;i<R.Cols;i++){
            R.MatrixNum[Row2][i]=R.MatrixNum[Row2][i]-R.MatrixNum[Row1][i]*Tmp;
        }
        return BeZero;
    }
    static private int FindMaxRow(T_Matrix M,int RowCol){//�ӶԽ�ԪRowCol��ʼ����Ѱ������в������к�
        double MaxValue=Math.abs(M.MatrixNum[RowCol][RowCol]);
        int Rtn=RowCol;
        for(int i=RowCol+1;i<M.Rows;i++)
            if(Math.abs(M.MatrixNum[i][RowCol])>MaxValue){
                MaxValue=Math.abs(M.MatrixNum[i][RowCol]);
                Rtn=i;
            }
        return Rtn;
    }
    //********************����ӿں���********************//
    static public T_Matrix MatrixAdd(T_Matrix M1,T_Matrix M2)throws MyError{
        if(M1.Rows==M2.Rows && M1.Cols==M2.Cols){
            T_Matrix Rtn=new T_Matrix(M1.Rows,M1.Cols);
            for(int i=0;i<Rtn.Rows;i++)
                for(int j=0;j<Rtn.Cols;j++)
                    Rtn.MatrixNum[i][j]=M1.MatrixNum[i][j]+M2.MatrixNum[i][j];
            return Rtn;
        }
        throw new MyError(69);
    }
    static public T_Matrix MatrixSub(T_Matrix M1,T_Matrix M2)throws MyError{
        if(M1.Rows==M2.Rows && M1.Cols==M2.Cols){
            T_Matrix Rtn=new T_Matrix(M1.Rows,M1.Cols);
            for(int i=0;i<Rtn.Rows;i++)
                for(int j=0;j<Rtn.Cols;j++)
                    Rtn.MatrixNum[i][j]=M1.MatrixNum[i][j]-M2.MatrixNum[i][j];
            return Rtn;
        }
        throw new MyError(69);
    }
    static public T_Matrix MatrixMul(T_Matrix M1,T_Matrix M2)throws MyError{
        if(M1.Cols==M2.Rows){
            T_Matrix Rtn=new T_Matrix(M1.Rows,M2.Cols);
            for(int i=0;i<Rtn.Rows;i++)
                for(int j=0;j<Rtn.Cols;j++)
                    for(int k=0;k<M1.Cols;k++)
                        Rtn.MatrixNum[i][j]=Rtn.MatrixNum[i][j]+M1.MatrixNum[i][k]*M2.MatrixNum[k][j];
            return Rtn;
        }
        throw new MyError(69);
    }
    static public T_Matrix MatrixT(T_Matrix M){
        T_Matrix Rtn=new T_Matrix(M.Cols,M.Rows);
        for(int i=0;i<Rtn.Rows;i++)
            for(int j=0;j<Rtn.Cols;j++)
                Rtn.MatrixNum[i][j]=M.MatrixNum[j][i];
        return Rtn;
    }
    static public T_Matrix MatrixMulConst(T_Matrix M,double constNum){
        T_Matrix Rtn=new T_Matrix(M.Rows,M.Cols);
        for(int i=0;i<Rtn.Rows;i++)
            for(int j=0;j<Rtn.Cols;j++)
                Rtn.MatrixNum[i][j]=M.MatrixNum[i][j]*constNum;
        return Rtn;
    }
    static public T_Matrix MatrixFactorial(T_Matrix M,int Num)throws MyError{
        if(M.Cols!=M.Rows)
            throw new MyError(67);//�Ƿ������
        if(Num<1)
            throw new MyError(72);//����С��1����
        if(Num==Integer.MAX_VALUE)
            throw new MyError(73);//��������
        T_Matrix[] Tmp=new T_Matrix[HowManyNum2(Num)];//��ʼ��2�ݽ׾����������
        int[] Index=new int[Tmp.length];//��ʼ��������������
        Tmp[0]=M;//1�׾����ֵ
        Index[0]=1;//������ֵ
        for(int i=1;i<Tmp.length;i++){
            Tmp[i]=MatrixMul(Tmp[i-1],Tmp[i-1]);//ͨ���ͽ׼���߽�
            Index[i]=Index[i-1]*2;//�߽׽���
        }
        T_Matrix Rtn=Tmp[Tmp.length-1];//�趨�����ֵΪ��߽�(�ý���С�ڵ������ս��������2���ݴν�)
        int Now=Index[Index.length-1];//�趨��ǰ����
        int Pos=Index.length-2;//�趨��ʼ���ӵĽ�
        while(Now!=Num){//û�����ս����������
            if((long)Now+(long)Index[Pos]<=(long)Num){//��������ӽ׿��Լ���
                Rtn=MatrixMul(Rtn,Tmp[Pos]);//������������2�ݽ׾���
                Now+=Index[Pos];//�����µĵ�ǰ����
            }
            else
                Pos--;//���ܼ������С���ӽ�
        }
        return Rtn;//�������ս��
    }
    static public T_Matrix MatrixR(T_Matrix M){
        T_Matrix Tmp=new T_Matrix(M.MatrixNum,M.Rows,M.Cols);
        T_Matrix Rtn=new T_Matrix(M.Rows,M.Cols);
        double Det=MatrixDet(M);
        int i;
        if(Det==0||Math.abs(Det)<1E-20)//����DetΪ0,����󲻴���
            return null;
        for(i=0;i<M.Rows;i++)
            Rtn.MatrixNum[i][i]=1;
        ChangeToR(Tmp,Rtn);//���г��ȱ任���������
        return Rtn;
    }
    static public double MatrixDet(T_Matrix M){
        int i,j;
        boolean BeZero,Symbol;
        for(i=0;i<M.Rows;i++){//ɨ��ȫ0��
            BeZero=true;
            for(j=0;j<M.Cols;j++){
                if(M.MatrixNum[i][j]!=0)
                    BeZero=false;
            }
            if(BeZero)
                return 0;
        }
        for(j=0;j<M.Cols;j++){//ɨ��ȫ0��
            BeZero=true;
            for(i=0;i<M.Rows;i++){
                if(M.MatrixNum[i][j]!=0)
                    BeZero=false;
            }
            if(BeZero)
                return 0;
        }
        T_Matrix Tmp=new T_Matrix(M.MatrixNum,M.Rows,M.Cols);
        Symbol=false;
        for(i=0;i<M.Rows;i++){//��Ϊ����������ʽ
            if(SetMax(Tmp,i))//������Ԫ
                Symbol=!Symbol;//�з��ű任
            if(SubToZeroDown(Tmp,i))//��ȥ������,ʹΪ0
                return 0;//�������ȫ0���򷵻�0
        }
        double Ans=1;
        for(i=0;i<M.Rows;i++)//������
            Ans=Ans*Tmp.MatrixNum[i][i];//�Խ�Ԫ�صĻ�
        if(Symbol)
            return -Ans;
        else
            return Ans;
    }
}
