package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *��������ȫ��ʹ�õ��Զ��庯��      *
 ************************************/
public class MyFunction {
    /********************�Զ��庯�������********************/
    static private char[] EngChS={'��','��','��','��','��','��',
                                     '��','��','��','��','��','��',
                                     '��','��','��','��','��','��',
                                     '��','��','��','��','��','��',
                                     '��','��'};//ȫ��Сд��ĸ��
    static private char[] EngChB={'��','��','��','��','��','��',
                                     '��','��','��','��','��','��',
                                     '��','��','��','��','��','��',
                                     '��','��','��','��','��','��',
                                     '��','��'};//ȫ�Ǵ�д��ĸ��
    static private char[] NumCh={'��','��','��','��','��',
                                    '��','��','��','��','��'};//ȫ�����ֱ�
    static private char[] SymCh={'��','+','��','-','��','*','��','/',
                                    '��','(','��',')','��','.'};//ȫ&��Ƿ��ű�
    /********************�ⲿ�Զ��庯��********************/
    static public String MyTranStr(String Str){//ת���ַ���Ϊ��׼�ַ���(���)
        char[] Str2Char;//�ַ�����ʱ�����ַ�����
        Str2Char=Str.toCharArray();//���ַ���ת��Ϊ�ַ�
        for(int i=0;i<Str2Char.length;i++){//���ַ�ɨ��
            Str2Char[i]=GetStdCh(Str2Char[i]);//ȫ���ַ�ת��
            if(Str2Char[i]>='A'&&Str2Char[i]<='Z'){//����Ǵ�д
                Str2Char[i]=(char)((int)Str2Char[i]-((int)'A'-(int)'a'));//ת��ΪСд
            }
        }
        return String.valueOf(Str2Char);//�ع��ַ���
    }
    static public String CheckStr(String Str){//��鲢���طǷ��ַ�
        String Rtn="";//��ʼ��Ϊ�޷Ƿ��ַ�
        char[] ch=Str.toCharArray();
        for(int i=0;i<Str.length();i++)
            if(!((ch[i]>='a'&&ch[i]<='z')||(ch[i]>='0'&&ch[i]<='9')||
                (ch[i]=='+')||(ch[i]=='-')||(ch[i]=='*')||(ch[i]=='/')||
                (ch[i]=='^')||(ch[i]=='(')||(ch[i]==')')||(ch[i]=='.')))
                Rtn=Rtn+ch[i];//�ַ��Ƿ�������뷵���ַ���
        return Rtn;
    }
    static public String DelSpace(String Str){//ɾ���ַ����е����С��ա��ַ�
        int i=0;
        String Rtn=new String(Str);//��ȡ�ַ���
        while(i<Rtn.length()){
            if(Rtn.substring(i,i+1).equals(" ")||Rtn.substring(i,i+1).equals("\n")||
                    Rtn.substring(i,i+1).equals("\t"))//����ǡ��ա��ַ�
                Rtn=Rtn.substring(0,i)+Rtn.substring(i+1);//��ɾ��
            else
                i++;//������ɨ����һ��
        }
        return Rtn;
    }
    static public double IsNum(String NumStr)throws MyError{//���NumStrΪһ�������򷵻ط��򱨴�
        int i,j=0;
        char[]ch=NumStr.toCharArray();
        if(NumStr.equals(""))
            throw new MyError(19);
        if(NumStr.equals("."))
            throw new MyError(18);
        if((ch[0]<'0'||ch[0]>'9')&&ch[0]!='+'&&ch[0]!='-')
            throw new MyError(18);
        for(i=1;i<ch.length;i++){
            if((ch[i]<'0'||ch[i]>'9')&&ch[i]!='.')
                throw new MyError(18);
            if(ch[i]=='.')
                j++;
        }
        if(j>1)
            throw new MyError(18);
        return Double.parseDouble(NumStr);
    }
    static public int IsInt(String IntStr)throws MyError{//���IntStrΪһ�������򷵻ط��򱨴�
        if(IntStr.equals(""))
            throw new MyError(19);
        if(IntStr.charAt(0)=='+'||IntStr.charAt(0)=='-'||(IntStr.charAt(0)>='0'&&IntStr.charAt(0)<='9')){
            for(int i=1;i<IntStr.length();i++)
                if(IntStr.charAt(i)>'9'||IntStr.charAt(i)<'0')
                    throw new MyError(18);
            return Integer.parseInt(IntStr);
        }
        else
            throw new MyError(18);
    }
    static public String CutNumStr(double Num){//��ȡһ�������ַ���(Ĭ�ϱ���2λС��)
        String TheStr=Double.toString(Num);
        int i=TheStr.lastIndexOf('.');//ȡС����λ��
        int j=TheStr.lastIndexOf('E');//ȡָ������λ��
        if(i==-1)//���Ϊ��������ֱ�����
            return TheStr;
        else if(j==-1)//��С����û��ָ��������
            return TheStr.substring(0,i+3<TheStr.length()-1?i+3:TheStr.length());
        else//��С������ָ������
            return TheStr.substring(0,i+3<TheStr.length()-1?i+3:TheStr.length())+
                    TheStr.substring(j);
    }
    static public String CutNumStr(double Num,int AP){//APΪ����С��λ��(Ҫ������1λ)
        String TheStr=Double.toString(Num);
        int i=TheStr.lastIndexOf('.');
        int j=TheStr.lastIndexOf('E');
        int k=(AP>=1?AP:1);
        if(i==-1)
            return TheStr;
        else if(j==-1)
            return TheStr.substring(0,i+k+1<TheStr.length()-1?i+k+1:TheStr.length());
        else
            return TheStr.substring(0,i+k+1<TheStr.length()-1?i+k+1:TheStr.length())+
                    TheStr.substring(j);
    }
    static public String CutNumStr(String TheStr){//��ȡһ�������ַ���(Ĭ�ϱ���2λС��)
        int i=TheStr.lastIndexOf('.');//ȡС����λ��
        int j=TheStr.lastIndexOf('E');//ȡָ������λ��
        if(i==-1)//���Ϊ��������ֱ�����
            return TheStr;
        else if(j==-1)//��С����û��ָ��������
            return TheStr.substring(0,i+3<TheStr.length()-1?i+3:TheStr.length());
        else//��С������ָ������
            return TheStr.substring(0,i+3<TheStr.length()-1?i+3:TheStr.length())+
                    TheStr.substring(j);
    }
    static public String CutNumStr(String TheStr,int AP){//APΪ����С��λ��(Ҫ������1λ)
        int i=TheStr.lastIndexOf('.');
        int j=TheStr.lastIndexOf('E');
        int k=(AP>=1?AP:1);
        if(i==-1)
            return TheStr;
        else if(j==-1)
            return TheStr.substring(0,i+k+1<TheStr.length()-1?i+k+1:TheStr.length());
        else
            return TheStr.substring(0,i+k+1<TheStr.length()-1?i+k+1:TheStr.length())+
                    TheStr.substring(j);
    }
    /********************��ѧ����********************/
    static private int compare(double a1, double a2){
        final double d = 1.0E-20;
        if(a1<a2-d)return -1;//a1<a2
        else if(a1>a2+d)return 1;//a1>a2
        else return 0;//a1=a2
    }
    static public double exp(double x){        
        double lastItem = 1;
        double result = 1;
        double lastResult;
        int i;
        for(i=1;true;i++){
            lastResult = result;
            lastItem = (lastItem * x) / i;            
            result += lastItem;
            if(result==Double.NaN || result==Double.NEGATIVE_INFINITY ||
                    result==Double.POSITIVE_INFINITY || compare(result, lastResult)==0)
                break;
        }        
        return result;
    }
    static private double ln2(double t){
        double lastDenominator;
        double lastmolecule;
        double result; 
        double lastResult;
        double x;
        int i;
        if(compare(t,0.0)<=0)
            return Double.NaN;
        if(compare(t,1.0)==0)
            return 0.0;
        x=(t-1)/(t+1);
        lastmolecule=x;
        lastDenominator=1;
        result=2*x;
        for(i=1;true;i++){
            lastResult=result;
            lastmolecule=lastmolecule * x * x;
            lastDenominator+=2;
            result+=(2*lastmolecule)/lastDenominator;
            if(result==Double.NaN ||result==Double.NEGATIVE_INFINITY ||
                    result==Double.POSITIVE_INFINITY ||compare(result, lastResult) == 0)
                break;
        }
        return result;
    }
    static public double log(double t){
        double result;
        if(compare(t,0.0)<=0)
            return Double.NaN;
        if(compare(t,1.0)==0)
            return 0.0;
        if(compare(t, 0.5)<0){
            final double LN2 = 0.69314718055994530941723212145818;
            double x=t;
            double n=0;
            int i;
            while(compare(x, 1.0)<0){
                x=x*2.0;
                n++;
            }
            result = ln2(x) - n * LN2;
        }
        else if(compare(t, 2.0) > 0)
            result = -1 * log(1.0/t);
        else
            result = ln2(t);
        return result;
    }
    static public double pow(double x, double y){
        double floor;
        if(compare(x, 1.0)==0||compare(y,0.0)==0)
            return 1.0;
        else if(compare(x,0.0)==0)
            return 0.0;
        else if(compare(y,1.0)==0)
            return x;
        floor=Math.floor(y);
        if(compare(floor,y)==0){          
            int i;
            double result=1;
            boolean yIsNegative=false;
            if(compare(y,0.0)<0){
                yIsNegative=true;
                floor=Math.abs(floor);
            }
            for(i=0;i<floor;i++)
                result*=x;
            if(yIsNegative)
                result=1.0/result;
            return result;
        }
        else
            return exp(y*log(x));
    }
    static public double atan(double x){
        double left=-Math.PI/2,right=Math.PI/2,result=0;
        if(x==Double.POSITIVE_INFINITY)
            return Math.PI/2;
        if(x==Double.NEGATIVE_INFINITY)
            return -Math.PI/2;
        while(right-left>1.0E-15){
            result=(left+right)/2;
            if(Math.tan(result)>x)
                right=result;
            else
                left=result;
        }
        return result;
    }
    static public double asin(double x){
        double left=-Math.PI/2,right=Math.PI/2,result=0;
        if(x>1||x<-1)
            return Double.NaN;
        while(right-left>1.0E-15){
            result=(left+right)/2;
            if(Math.sin(result)>x)
                right=result;
            else
                left=result;
        }
        return result;
    }
    static public double acos(double x){
        double left=0,right=Math.PI,result=0;
        if(x>1||x<-1)
            return Double.NaN;
        while(right-left>1.0E-15){
            result=(left+right)/2;
            if(Math.cos(result)>x)
                left=result;
            else
                right=result;
        }
        return result;
    }
    /********************�ڲ��Զ��庯��********************/
    static private char GetStdCh(char ch){//ȫ���ַ�ת������
        char Rtn=ch;
        for(int k=0;k<SymCh.length;k+=2)
            if(ch==SymCh[k])
                return SymCh[k+1];
        for(int i=0;i<EngChS.length;i++){//Ӣ����ĸȫ��ת��
            if(ch==EngChS[i])return (char)((int)'a'+i);
            if(ch==EngChB[i])return (char)((int)'a'+i);
        }
        for(int j=0;j<NumCh.length;j++)//����ȫ��ת��
            if(ch==NumCh[j])return (char)((int)'0'+j);
        return Rtn;
    }
}
