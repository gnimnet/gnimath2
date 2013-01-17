package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *矩阵类                            *
 ************************************/
import MyTypes.*;

public class T_Matrix {
    public double[][] MatrixNum;//矩阵的值
    public int Rows=0;//矩阵的行数
    public int Cols=0;//矩阵的列数
    //********************构造函数********************//
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
    //********************自定义内部函数********************//
    private void CheckMaStr(String MaStr) throws MyError{
        String tmpStr=MaStr;
        int lineB,lineE;//扫描该行的起始'('和终止')'符位置
        int count;//列数统计
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
        int lineB,lineE;//扫描该行的启示和终止
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
    static private boolean SetMax(T_Matrix M,int RowCol){//把对角线元设为该列最大
        boolean SymChange=false;
        int GetRow=FindMaxRow(M,RowCol);
        if(M.MatrixNum[GetRow][RowCol]<0){
            MulConstRow(M,GetRow,-1);//如果为负则修该为正
            SymChange=!SymChange;
        }
        if(GetRow!=RowCol){
            SwitchArrayRow(M,RowCol,GetRow);
            SymChange=!SymChange;
        }
        return SymChange;
    }
    static private boolean SubToZeroDown(T_Matrix M,int RowCol){//用对角线元消去下面行的元素
        for(int i=RowCol+1;i<M.Rows;i++){
            if(SubToZeroAfterIndex_Row(M,RowCol,i,RowCol))
                return true;//产生全0行
        }
        return false;//无全0行
    }
    static private void ChangeToR(T_Matrix M,T_Matrix R){//把M转换为单位矩阵,R转换为M的逆矩阵(R之前为单位阵)
        int i;
        for(i=0;i<M.Rows-1;i++){//化为上三角行列式
            SetMaxR(M,R,i);//设列主元
            SubToZeroDownR(M,R,i);//消去下面行,使为0
        }
        for(i=M.Rows-1;i>=0;i--)//化为对角行列式
            SubToZeroUpR(M,R,i);
        for(i=0;i<M.Rows;i++)//化为单位矩阵
            MulToOne(M,R,i);
    }
    static private void SetMaxR(T_Matrix M,T_Matrix R,int RowCol){//把对角线元设为该列最大
        int GetRow=FindMaxRow(M,RowCol);
        if(M.MatrixNum[GetRow][RowCol]<0){
            MulConstRow(M,GetRow,-1);//如果为负则修该为正
            MulConstRow(R,GetRow,-1);//同时修正逆矩阵
        }
        if(GetRow!=RowCol){
            SwitchArrayRow(M,RowCol,GetRow);
            SwitchArrayRow(R,RowCol,GetRow);
        }
    }
    static private void SubToZeroDownR(T_Matrix M,T_Matrix R,int RowCol){//用对角线元消去下面行的元素
        for(int i=RowCol+1;i<M.Rows;i++)
            SubToZeroAfterIndex_RowR(M,R,RowCol,i,RowCol);
    }
    static private void SubToZeroUpR(T_Matrix M,T_Matrix R,int RowCol){//用对角线元消去上面行的元素(已经为上三角矩阵)
        for(int i=RowCol-1;i>=0;i--){
            double Tmp=M.MatrixNum[i][RowCol]/M.MatrixNum[RowCol][RowCol];//求消去比例
            M.MatrixNum[i][RowCol]=0;//消去左矩阵值
            for(int j=0;j<R.Cols;j++)
                R.MatrixNum[i][j]=R.MatrixNum[i][j]-R.MatrixNum[RowCol][j]*Tmp;
        }
    }
    static private void MulToOne(T_Matrix M,T_Matrix R,int RowCol){//用把左矩阵对角元素转化为1
        double Tmp=1/M.MatrixNum[RowCol][RowCol];
        MulConstRow(M,RowCol,Tmp);
        MulConstRow(R,RowCol,Tmp);
    }
    //********************行列式相关操作********************//
    static private void SwitchArrayRow(T_Matrix M,int Row1,int Row2){//交换行Row1和Row2
        double tmp;
        for(int i=0;i<M.Cols;i++){
            tmp=M.MatrixNum[Row1][i];
            M.MatrixNum[Row1][i]=M.MatrixNum[Row2][i];
            M.MatrixNum[Row2][i]=tmp;
        }
    }
    static private void SwitchArrayCol(T_Matrix M,int Col1,int Col2){//交换列Col1和Col2
        double tmp;
        for(int i=0;i<M.Rows;i++){
            tmp=M.MatrixNum[i][Col1];
            M.MatrixNum[i][Col1]=M.MatrixNum[i][Col2];
            M.MatrixNum[i][Col2]=tmp;
        }
    }
    static private void MulConstRow(T_Matrix M,int Row,double ConstNum){//行Row=行Row*ConstNum
        for(int i=0;i<M.Cols;i++)
            M.MatrixNum[Row][i]=M.MatrixNum[Row][i]*ConstNum;
    }
    static private void MulConstCol(T_Matrix M,int Col,double ConstNum){//列Col=列Col*ConstNum
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
    static private boolean SubToZeroAfterIndex_Row(T_Matrix M,int Row1,int Row2,int Index){//通过减法把Row2的Index列消为0(下方)
        /*  |   1   2   3   |  <----M   |   1   2   3   |
         *  |   2   5   6   |           |   0   1   0   |   <----结果(消去列)
         *  |   3   8   4   |           |   3   8   4   |
         *  Row1 = 0    Row2 = 1    Index = 0
         */
        boolean BeZero=true;
        double Tmp=M.MatrixNum[Row2][Index]/M.MatrixNum[Row1][Index];//求消去比例
        M.MatrixNum[Row2][Index]=0;
        for(int i=Index+1;i<M.Cols;i++){
            M.MatrixNum[Row2][i]=M.MatrixNum[Row2][i]-M.MatrixNum[Row1][i]*Tmp;
            if(M.MatrixNum[Row2][i]!=0||Math.abs(M.MatrixNum[Row2][i])>1E-20)
                BeZero=false;
        }
        return BeZero;
    }
    static private boolean SubToZeroAfterIndex_RowR(T_Matrix M,T_Matrix R,int Row1,int Row2,int Index){//通过减法把Row2的Index列消为0(下方)同时改变逆矩阵
        /*  |   1   2   3   |  <----M   |   1   0   0   |  <----R
         *  |   2   5   6   |           |   0   1   0   |
         *  |   3   8   4   |           |   0   0   1   |
         *  Row1 = 0    Row2 = 1    Index = 0
        /*  |   1   2   3   | <-结果M   |   1   0   0   |  <-结果R
         *  |   0   1   0   |           |   -2  1   0   |
         *  |   3   8   4   |           |   0   0   1   |
         */
        boolean BeZero=true;
        double Tmp=M.MatrixNum[Row2][Index]/M.MatrixNum[Row1][Index];//求消去比例
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
    static private int FindMaxRow(T_Matrix M,int RowCol){//从对角元RowCol开始往下寻找最大行并返回列号
        double MaxValue=Math.abs(M.MatrixNum[RowCol][RowCol]);
        int Rtn=RowCol;
        for(int i=RowCol+1;i<M.Rows;i++)
            if(Math.abs(M.MatrixNum[i][RowCol])>MaxValue){
                MaxValue=Math.abs(M.MatrixNum[i][RowCol]);
                Rtn=i;
            }
        return Rtn;
    }
    //********************计算接口函数********************//
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
            throw new MyError(67);//非方阵错误
        if(Num<1)
            throw new MyError(72);//阶数小于1错误
        if(Num==Integer.MAX_VALUE)
            throw new MyError(73);//阶数过大
        T_Matrix[] Tmp=new T_Matrix[HowManyNum2(Num)];//初始化2幂阶矩阵变量数组
        int[] Index=new int[Tmp.length];//初始化阶数变量数组
        Tmp[0]=M;//1阶矩阵初值
        Index[0]=1;//阶数初值
        for(int i=1;i<Tmp.length;i++){
            Tmp[i]=MatrixMul(Tmp[i-1],Tmp[i-1]);//通过低阶计算高阶
            Index[i]=Index[i-1]*2;//高阶阶数
        }
        T_Matrix Rtn=Tmp[Tmp.length-1];//设定计算初值为最高阶(该阶是小于等于最终阶数的最高2的幂次阶)
        int Now=Index[Index.length-1];//设定当前阶数
        int Pos=Index.length-2;//设定起始增加的阶
        while(Now!=Num){//没到最终阶则继续计算
            if((long)Now+(long)Index[Pos]<=(long)Num){//如果该增加阶可以加入
                Rtn=MatrixMul(Rtn,Tmp[Pos]);//乘入可增加最高2幂阶矩阵
                Now+=Index[Pos];//计算新的当前阶数
            }
            else
                Pos--;//不能加入则减小增加阶
        }
        return Rtn;//返回最终结果
    }
    static public T_Matrix MatrixR(T_Matrix M){
        T_Matrix Tmp=new T_Matrix(M.MatrixNum,M.Rows,M.Cols);
        T_Matrix Rtn=new T_Matrix(M.Rows,M.Cols);
        double Det=MatrixDet(M);
        int i;
        if(Det==0||Math.abs(Det)<1E-20)//计算Det为0,逆矩阵不存在
            return null;
        for(i=0;i<M.Rows;i++)
            Rtn.MatrixNum[i][i]=1;
        ChangeToR(Tmp,Rtn);//进行初等变换法求逆矩阵
        return Rtn;
    }
    static public double MatrixDet(T_Matrix M){
        int i,j;
        boolean BeZero,Symbol;
        for(i=0;i<M.Rows;i++){//扫描全0行
            BeZero=true;
            for(j=0;j<M.Cols;j++){
                if(M.MatrixNum[i][j]!=0)
                    BeZero=false;
            }
            if(BeZero)
                return 0;
        }
        for(j=0;j<M.Cols;j++){//扫描全0列
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
        for(i=0;i<M.Rows;i++){//化为上三角行列式
            if(SetMax(Tmp,i))//设列主元
                Symbol=!Symbol;//有符号变换
            if(SubToZeroDown(Tmp,i))//消去下面行,使为0
                return 0;//如果产生全0行则返回0
        }
        double Ans=1;
        for(i=0;i<M.Rows;i++)//计算结果
            Ans=Ans*Tmp.MatrixNum[i][i];//对角元素的积
        if(Symbol)
            return -Ans;
        else
            return Ans;
    }
}
