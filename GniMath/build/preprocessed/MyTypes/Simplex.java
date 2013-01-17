package MyTypes;
/************************************
 *Author:ZhangZhen                  *
 *E-Mail:mfzz1134@163.com           *
 ************************************
 *线性规划计算程序                  *
 ************************************/
/**
 * This is a class which is used to compute the result of a linear programing.
 * If there is no result or the result is infate, it will throw exceptions.
 * @author zhangzhen
 *
 */
public class Simplex {
	/**
	 * This is used to describe how many it has.
	 */
	private int rowCnt;
	/**
	 * This is used to describe how many varible it has.
	 */
	private int colCnt;
	/**
	 * This is used to describe the varibles in the result polynomial.
	 */
	private int N[];
	/**
	 * This is used to describe the ceofficients in the result polynomial.
	 */
	private double coeff[];
	/**
	 * This is used to describe the constant in the equations.
	 */
	private double base[];
	/**
	 * This is used to describe the constant in the result polynomial.
	 */
	private double v;
	/**
	 * This is used to describe the varibles in the left of the equations.
	 */
	private int B[];
	/**
	 * This is the matrix of all the equations.
	 */
	private double M[][];
        /**
         * This is used to store the result;
         */
        private double result[];
	/**
	 * This is used to initilize the stack form for the linear programing.
	 * @param A The matrix of the equations.
	 * @param b The constant in each equations.
	 * @param c The coefficients in the result polynomial.
	 */
	public Simplex(double A[][], double b[],double c[])throws MyError{
            boolean tag=true;
            for(int i=0;i<b.length;i++){
                if(b[i]<0){
                    tag=false;
                    break;
                }
            }
            if(tag){
                this.colCnt=b.length;
                this.rowCnt=c.length;
                this.base=new double[b.length];
                for(int i=0;i<this.base.length;i++) this.base[i]=b[i];
                this.coeff=new double[c.length];
                for(int i=0;i<this.coeff.length;i++) this.coeff[i]=c[i];
                this.M=new double[colCnt][rowCnt];
                for(int i=0;i<colCnt;i++)
                        for(int j=0;j<rowCnt;j++)
                                M[i][j]=A[i][j];
                this.N=new int[rowCnt];
                for(int i=0;i<rowCnt;i++) N[i]=i;
                this.B=new int[colCnt];
                for(int i=0;i<colCnt;i++) B[i]=i+rowCnt;
                v=0;
                return;
            }
            else{
                this.colCnt=b.length;
                this.rowCnt=c.length+1;
                
                this.coeff=new double[c.length+1];
                this.coeff[0]=-1;
                for(int i=1;i<this.coeff.length;i++) this.coeff[i]=0;
                this.v=0;
                this.N=new int[this.rowCnt];
                for(int i=0;i<N.length;i++) N[i]=i;
                
                this.M=new double[colCnt][rowCnt];
                for(int i=0;i<colCnt;i++){
                    M[i][0]=-1;
                }
                for(int i=0;i<colCnt;i++){
                    for(int j=1;j<rowCnt;j++){
                        this.M[i][j]=A[i][j-1];
                    }
                }
                this.B=new int[colCnt];
                for(int i=0;i<B.length;i++) this.B[i]=this.rowCnt+i;
                this.base=b;
                int l=this.colCnt+this.rowCnt-1;
                this.pivot(l, 0);
                boolean loop=true;
                while(loop){
                    int cjCnt=0;
                    for(int i=0;i<this.coeff.length;i++){
                        if(this.coeff[i]<=0){
                            cjCnt++;
                            continue;
                        }
                        else{
                            double delta[]=new double[this.B.length];
                            double min=Double.POSITIVE_INFINITY;
                            int minindex=0;
                            double inf=Double.POSITIVE_INFINITY;
                            for(int k=0;k<this.B.length;k++){
                                if(this.M[k][i]>0){
                                    delta[k]=this.base[k]/this.M[k][i];
                                    if(min>delta[k]){
                                        minindex=k;
                                        min=delta[k];
                                    }
                                } 
                                else{
                                    delta[k]=inf;
                                }
                             }
                             if(min==inf){
                                 throw new MyError(104);
                             }
                             else{
                                this.pivot(B[minindex], N[i]);
                             }
                        }
                    }
                    if(cjCnt==this.coeff.length){
                        loop=false;
                    }
                }
                if(this.v!=0){
                    throw new MyError(105);
                }
                else{
                    this.colCnt=b.length;
                    this.rowCnt=c.length;
                    this.base=new double[b.length];
                    for(int i=0;i<this.base.length;i++) this.base[i]=b[i];
                    this.coeff=new double[c.length];
                    for(int i=0;i<this.coeff.length;i++) this.coeff[i]=c[i];
                    this.M=new double[colCnt][rowCnt];
                    for(int i=0;i<colCnt;i++)
                            for(int j=0;j<rowCnt;j++)
                                    M[i][j]=A[i][j];
                    this.N=new int[rowCnt];
                    for(int i=0;i<rowCnt;i++) N[i]=i;
                    this.B=new int[colCnt];
                    for(int i=0;i<colCnt;i++) B[i]=i+rowCnt;
                    v=0;
                    return;
                }
            }
	}
	/**
	 * The is used to exchange varible l and varible e in N and B.
	 * @param l The varible in N that will be exchanged out.
	 * @param e The vraible in N that will be exchanged in.
	 */
	private void pivot(int out,int in){
		double b[]=new double[base.length];
		double A[][]=new double[base.length][coeff.length];
		double c[]=new double[coeff.length];
		int l=0,e=0;
		for(int i=0;i<N.length;i++){
			if(N[i]==in){
				e=i;
				break;
			}
		}
		for(int j=0;j<B.length;j++){
			if(B[j]==out){
				l=j;
				break;
			}
		}
		//compute the coefficients of the equation for new 
		//basic varible x.
		
		b[l]=base[l]/M[l][e];
		for(int i=0;i<N.length;i++){
			if(i==e)
				continue;
			A[l][i]=M[l][i]/M[l][e];
		}
		A[l][e]=1/M[l][e];
		
		//Compute the coefficients of the remainning constraints.
		for(int i=0;i<B.length;i++){
			if(i==l)
				continue;
			b[i]=base[i]-M[i][e]*b[l];
			for(int j=0;j<N.length;j++){
				if(j==e)
					continue;
				A[i][j]=M[i][j]-M[i][e]*A[l][j];
			}
			A[i][e]=-M[i][e]*A[l][e];
		}
		this.v=this.v+this.coeff[e]*b[l];
		for(int i=0;i<N.length;i++){
			if(i==e)
				continue;
			c[i]=this.coeff[i]-this.coeff[e]*A[l][i];
		}
		c[e]=-this.coeff[e]*A[l][e];
		
		for(int i=0;i<N.length;i++){
			if(i==e) {
				N[i]=out;
				break;
			}
		}
		for(int i=0;i<B.length;i++){
			if(i==l){
				B[i]=in;
				break;
			}
		}
		this.M=A;
		this.base=b;
		this.coeff=c;
	}
        /**
         * 
         * @param args
         */
        public double [] computeBestResulotion()throws MyError{
            this.result=new double[this.coeff.length];
            boolean loop=true;
            while(loop){
                int cjCnt=0;
                for(int i=0;i<this.coeff.length;i++){
                    if(this.coeff[i]<=0){
                        cjCnt++;
                        continue;
                    }
                    else{
                        double delta[]=new double[this.B.length];
                        double min=Double.POSITIVE_INFINITY;
                        int minindex=0;
                        double inf=Double.POSITIVE_INFINITY;
                        for(int k=0;k<this.B.length;k++){
                            if(this.M[k][i]>0){
                                delta[k]=this.base[k]/this.M[k][i];
                                if(min>delta[k]){
                                    minindex=k;
                                    min=delta[k];
                                }
                            } 
                            else{
                                delta[k]=inf;
                            }
                         }
                         if(min==inf){
                             throw new MyError(104);
                         }
                         else{
                            this.pivot(B[minindex], N[i]);
                         }
                    }
                }
                if(cjCnt==this.coeff.length){
                    loop=false;
                }
            }
            for(int i=0;i<this.B.length;i++){
                if(B[i]<this.coeff.length){
                    this.result[this.B[i]]=this.base[i];
                }
            }
            return this.result;
        }
        public double getMax(){
            return this.v;
        }
}