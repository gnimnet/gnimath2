package GniMath;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *指令计算类                        *
 ************************************/
import MyTypes.*;

public class SolveCom {
    static private String[] CmdList={"c",//组合数
    "a",//排列数
    "!",//阶乘
    "r2d",//弧度转换为角度
    "d2r",//角度转换为弧度
    "iss",//判素
    "%",//求余
    "d"};//因数分解
    static int[] inum;//整数参数
    static double[] dnum;//浮点参数
    static public String GetAns(String Cmd,String Data)throws MyError{
        if(!SupportCmd(Cmd))
            throw new MyError(95);
        if(Cmd.equals("c")){
            GetIntNum(2,Data);
            if(inum[0]<0||inum[1]<0)
                throw new MyError(98);
            if(inum[0]>=inum[1])
                return "组合数:"+Cmn(inum[0],inum[1]);
            else
                throw new MyError(97);
        }
        if(Cmd.equals("a")){
            GetIntNum(2,Data);
            if(inum[0]<0||inum[1]<0)
                throw new MyError(98);
            if(inum[0]>=inum[1])
                return "排列数:"+Amn(inum[0],inum[1]);
            else
                throw new MyError(97);
        }
        if(Cmd.equals("!")){
            GetIntNum(1,Data);
            if(inum[0]<0)
                throw new MyError(98);
            return Data+"!="+Mul_Lv(inum[0]);
        }
        if(Cmd.equals("r2d")){
            GetDoubleNum(1,Data);
            return "RtoD:"+Math.toDegrees(dnum[0]);
        }
        if(Cmd.equals("d2r")){
            GetDoubleNum(1,Data);
            return "DtoR:"+Math.toRadians(dnum[0]);
        }
        if(Cmd.equals("iss")){
            GetIntNum(1,Data);
            if(inum[0]<0)
                throw new MyError(98);
            return "判素:"+IsS(inum[0]);
        }
        if(Cmd.equals("%")){
            GetIntNum(2,Data);
            return "余数:"+(inum[0]%inum[1]);
        }
        if(Cmd.equals("d")){
            GetIntNum(1,Data);
            if(inum[0]<2)
                throw new MyError(99);
            return "分解因数:"+DesectNum(inum[0]);
        }
        return "未知错误!!!";
    }
    //自定义函数
    static private boolean SupportCmd(String Cmd){
        for(int i=0;i<CmdList.length;i++)
            if(CmdList[i].equals(Cmd))
                return true;
        return false;
    }
    static private void GetIntNum(int Num,String Data)throws MyError{//获取Num个整数数据
        String Tmp=Data;
        int i,j=0;
        for(i=0;i<Data.length();i++){
            if(Data.charAt(i)==',')
                j++;
        }
        if(j!=Num-1)
            throw new MyError(96);
        inum=new int[Num];
        i=0;
        while((j=Tmp.indexOf(','))!=-1){
            inum[i++]=MyFunction.IsInt(Tmp.substring(0,j));
            Tmp=Tmp.substring(j+1);
        }
        inum[i++]=MyFunction.IsInt(Tmp);
    }
    static private void GetDoubleNum(int Num,String Data)throws MyError{//获取Num个浮点数据
        String Tmp=Data;
        int i,j=0;
        for(i=0;i<Data.length();i++){
            if(Data.charAt(i)==',')
                j++;
        }
        if(j!=Num-1)
            throw new MyError(96);
        dnum=new double[Num];
        i=0;
        while((j=Tmp.indexOf(','))!=-1){
            dnum[i++]=MyFunction.IsNum(Tmp.substring(0,j));
            Tmp=Tmp.substring(j+1);
        }
        dnum[i++]=MyFunction.IsNum(Tmp);
    }
    //计算函数
    static private int MulByLv(int num){//阶乘计算
        int Rtn=1;
        for(int i=2;i<=num;i++)
            Rtn*=i;
        return Rtn;
    }
    static private double Mul_Lv(int num){//阶乘计算
        double Rtn=1;
        for(int i=2;i<=num;i++)
            Rtn*=i;
        return Rtn;
    }
    static private double Amn(int n,int k){//排列数
        return Mul_Lv(n)/Mul_Lv(n-k);
    }
    static private double Cmn(int n,int k){//组合数
        return (Mul_Lv(n)/Mul_Lv(n-k))/Mul_Lv(k);
    }
    static private boolean IsS(int num){
        double half=Math.sqrt(num);
        if(num%2==0)
            return false;
        for(int i=3;i<half;i+=2)
            if(num%i==0)
                return false;
        return true;
    }
    static private String DesectNum(int num){//因数分解
        int c=2;
        String Rtn="";
        while(c!=num){
            if(num%c==0){
                Rtn=Rtn+c+"*";
                num=num/c;
            }
            else
                c++;
        }
        Rtn=Rtn+c;
        return Rtn;
    }
}
