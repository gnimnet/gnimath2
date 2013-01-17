package GniMath;
/************************************
 *Author:ZhangZhen                  *
 *E-Mail:mfzz1134@163.com           *
 ************************************
 *线性规划程序                      *
 ************************************/
import MyTypes.*;

public class LinearProgramming {
    public double x[];
    public double v;
    LinearProgramming(double A[][], double b[],double c[])throws MyError{
        double NA[][]=new double[b.length][c.length<<1];
        double Nc[]=new double[c.length<<1];
        for(int i=0;i<b.length;i++){
            for(int j=0;j<c.length;j++){
                NA[i][j<<1]=A[i][j];
                NA[i][(j<<1)+1]=-A[i][j];
            }
        }
        for(int j=0;j<c.length;j++){
            Nc[j<<1]=c[j];
            Nc[(j<<1)+1]=-c[j];
        }
        Simplex S=new Simplex(NA,b,Nc);
        double temp[]=S.computeBestResulotion();
        this.x=new double[c.length];
        for(int i=0;i<x.length;i++){
            this.x[i]=temp[i<<1]-temp[(i<<1)+1];
        }
        v=S.getMax();
    }
}