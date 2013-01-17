package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *该类用于定义自定义错误类          *
 ************************************/
public class MyError extends Exception{
    private int ErrNum;//错误编码
    private String ErrMsg;//错误信息
    /********************构造函数********************/
    public MyError(int Num,String Msg) {//错误信息构造(含自定义错误信息)
        ErrNum=Num;
        ErrMsg=Msg;
    }
    public MyError(int Num) {//错误信息构造(默认错误信息)
        ErrNum=Num;
        switch(Num){
            //扫描表达式错误
            case 1:ErrMsg="不可识别的字符!";break;
            case 2:ErrMsg="额外的操作数!";break;
            case 3:ErrMsg="多余的小数点!";break;
            case 4:ErrMsg="缺失操作数!";break;
            case 5:ErrMsg="缺失配对的右括号!";break;
            case 6:ErrMsg="缺失运算符!";break;
            case 7:ErrMsg="不存在的变量!";break;
            case 8:ErrMsg="不支持的函数!";break;
            case 9:ErrMsg="除数为零!";break;
            case 10:ErrMsg="重复添加的变量!";break;
            case 11:ErrMsg="空表达式!";break;
            case 12:ErrMsg="单独出现的小数点!";break;
            //截取表达式错误
            case 13:ErrMsg="缺失表达式分号!";break;
            case 14:ErrMsg="表达式颜色信息有误!";break;
            //2D世界系坐标范围错误
            case 15:ErrMsg="范围数据有误!";break;
            case 16:ErrMsg="x上限应大于下限!";break;
            case 17:ErrMsg="y上限应大于下限!";break;
            //数字转化错误
            case 18:ErrMsg="字符转化数字错误!";break;
            case 19:ErrMsg="空串不能转化为数字!";break;
            //计算堆栈错误
            case 20:ErrMsg="堆栈满,不能压入!";break;
            case 21:ErrMsg="堆栈空,不能弹出!";break;
            case 22:ErrMsg="堆栈空,不能取顶!";break;
            //曲线插值错误
            case 30:ErrMsg="请至少输入两组数据!";break;
            case 31:ErrMsg="数据格式有误!";break;
            case 32:ErrMsg="不应有重复的X数据!";break;
            case 33:ErrMsg="缺失分号!";break;
            //参数曲线错误
            case 40:ErrMsg="数据格式有误!";break;
            case 41:ErrMsg="x上限应大于下限!";break;
            case 42:ErrMsg="y上限应大于下限!";break;
            case 43:ErrMsg="t上限应大于下限!";break;
            case 44:ErrMsg="t的步长不合适!";break;
            case 45:ErrMsg="表达式缺失分号!";break;
            case 46:ErrMsg="表达式缺失竖线分隔符!";break;
            //色彩3D错误
            case 50:ErrMsg="数据格式有误!";break;
            case 51:ErrMsg="z上限应大于下限!";break;
            case 52:ErrMsg="步长过大或过小!";break;
            //Plot3D错误
            case 55:ErrMsg="数据格式有误!";break;
            //矩阵构造错误
            case 60:ErrMsg="数据格式有误!";break;
            case 61:ErrMsg="数据括号有误!";break;
            case 62:ErrMsg="列数不匹配!";break;
            case 63:ErrMsg="不允许出现空行!";break;
            //矩阵计算指令执行错误
            case 65:ErrMsg="缺失指令|数据分隔符!";break;
            case 66:ErrMsg="定义空间满,不能添加矩阵!";break;
            case 67:ErrMsg="矩阵不是方阵,不能求阶乘和逆!";break;
            case 68:ErrMsg="行数和列数不相同,不能计算加减!";break;
            case 69:ErrMsg="行数和列数不对应,不能计算乘法!";break;
            case 70:ErrMsg="错误的矩阵序号!";break;
            case 71:ErrMsg="矩阵编号不存在!";break;
            case 72:ErrMsg="阶数小于1!";break;
            case 73:ErrMsg="阶数过大!";break;
            //解线性方程组错误
            case 75:ErrMsg="系数矩阵行小于列!";break;
            case 76:ErrMsg="常数矩阵应为1*N的矩阵!";break;
            case 77:ErrMsg="DetA=0不能求解!";break;
            //三维参数曲线错误
            case 80:ErrMsg="t上限应大于下限!";break;
            case 81:ErrMsg="t的步长不合适!";break;
            case 82:ErrMsg="参数t信息有误!";break;
            case 84:ErrMsg="表达式信息分割符有误!";break;
            //曲线拟合错误
            case 85:ErrMsg="请至少输入两组数据!";break;
            case 86:ErrMsg="数据格式有误!";break;
            case 87:ErrMsg="不应有重复的X数据!";break;
            case 88:ErrMsg="缺失分号!";break;
            case 89:ErrMsg="阶数应为整数!";break;
            case 90:ErrMsg="计算拟合的逆矩阵不存在!";break;
            case 91:ErrMsg="拟合的次数应小于样本点数!";break;
            case 92:ErrMsg="拟合的次数应大于等于零!";break;
            //指令计算错误
            case 95:ErrMsg="不支持的指令!";break;
            case 96:ErrMsg="参数个数不正确!";break;
            case 97:ErrMsg="排列组合的n不应小于k!";break;
            case 98:ErrMsg="该计算参数不应小于0!";break;
            case 99:ErrMsg="分解的因数不应小于2!";break;
            //线性规划错误
            case 100:ErrMsg="系数与常数信息不对应!";break;
            case 101:ErrMsg="系数与目标函数信息不对应!";break;
            case 102:ErrMsg="常数信息有误!";break;
            case 103:ErrMsg="目标函数信息有误!";break;
            case 104:ErrMsg="解为无穷!";break;
            case 105:ErrMsg="无解!";break;
            //未知
            default:ErrMsg="未知错误!";
        }
    }
    /********************外部调用函数********************/
    public int GetErrNum() {//获取错误编码
        return ErrNum;
    }
    public String GetErrMsg() {//获取错误信息
        return ErrMsg;
    }
    public String GetErrType() {//获取错误类型
        if(ErrNum>=1 && ErrNum<=12)return "表达式扫描错误";
        if(ErrNum>=13 && ErrNum<=14)return "表达式截取错误";
        if(ErrNum>=15 && ErrNum<=17)return "2D坐标系错误";
        if(ErrNum>=18 && ErrNum<=19)return "数字转化错误";
        if(ErrNum>=20 && ErrNum<=22)return "堆栈错误";
        if(ErrNum>=30 && ErrNum<=33)return "曲线插值错误";
        if(ErrNum>=40 && ErrNum<=46)return "参数曲线错误";
        if(ErrNum>=50 && ErrNum<=52)return "色彩3D错误";
        if(ErrNum>=55 && ErrNum<=55)return "Plot3D错误";
        if(ErrNum>=60 && ErrNum<=63)return "矩阵构造错误";
        if(ErrNum>=65 && ErrNum<=73)return "矩阵计算指令执行错误";
        if(ErrNum>=74 && ErrNum<=77)return "解线性方程组错误";
        if(ErrNum>=80 && ErrNum<=84)return "三维参数曲线错误";
        if(ErrNum>=85 && ErrNum<=92)return "曲线拟合错误";
        if(ErrNum>=95 && ErrNum<=99)return "指令计算错误";
        if(ErrNum>=100 && ErrNum<=105)return "线性规划错误";
        return "未知类型错误";
    }
}
