package MyTypes;
/************************************
 *Author:Ming                       *
 *E-Mail:marchariot@qq.com          *
 ************************************
 *�������ڶ����Զ��������          *
 ************************************/
public class MyError extends Exception{
    private int ErrNum;//�������
    private String ErrMsg;//������Ϣ
    /********************���캯��********************/
    public MyError(int Num,String Msg) {//������Ϣ����(���Զ��������Ϣ)
        ErrNum=Num;
        ErrMsg=Msg;
    }
    public MyError(int Num) {//������Ϣ����(Ĭ�ϴ�����Ϣ)
        ErrNum=Num;
        switch(Num){
            //ɨ����ʽ����
            case 1:ErrMsg="����ʶ����ַ�!";break;
            case 2:ErrMsg="����Ĳ�����!";break;
            case 3:ErrMsg="�����С����!";break;
            case 4:ErrMsg="ȱʧ������!";break;
            case 5:ErrMsg="ȱʧ��Ե�������!";break;
            case 6:ErrMsg="ȱʧ�����!";break;
            case 7:ErrMsg="�����ڵı���!";break;
            case 8:ErrMsg="��֧�ֵĺ���!";break;
            case 9:ErrMsg="����Ϊ��!";break;
            case 10:ErrMsg="�ظ���ӵı���!";break;
            case 11:ErrMsg="�ձ��ʽ!";break;
            case 12:ErrMsg="�������ֵ�С����!";break;
            //��ȡ���ʽ����
            case 13:ErrMsg="ȱʧ���ʽ�ֺ�!";break;
            case 14:ErrMsg="���ʽ��ɫ��Ϣ����!";break;
            //2D����ϵ���귶Χ����
            case 15:ErrMsg="��Χ��������!";break;
            case 16:ErrMsg="x����Ӧ��������!";break;
            case 17:ErrMsg="y����Ӧ��������!";break;
            //����ת������
            case 18:ErrMsg="�ַ�ת�����ִ���!";break;
            case 19:ErrMsg="�մ�����ת��Ϊ����!";break;
            //�����ջ����
            case 20:ErrMsg="��ջ��,����ѹ��!";break;
            case 21:ErrMsg="��ջ��,���ܵ���!";break;
            case 22:ErrMsg="��ջ��,����ȡ��!";break;
            //���߲�ֵ����
            case 30:ErrMsg="������������������!";break;
            case 31:ErrMsg="���ݸ�ʽ����!";break;
            case 32:ErrMsg="��Ӧ���ظ���X����!";break;
            case 33:ErrMsg="ȱʧ�ֺ�!";break;
            //�������ߴ���
            case 40:ErrMsg="���ݸ�ʽ����!";break;
            case 41:ErrMsg="x����Ӧ��������!";break;
            case 42:ErrMsg="y����Ӧ��������!";break;
            case 43:ErrMsg="t����Ӧ��������!";break;
            case 44:ErrMsg="t�Ĳ���������!";break;
            case 45:ErrMsg="���ʽȱʧ�ֺ�!";break;
            case 46:ErrMsg="���ʽȱʧ���߷ָ���!";break;
            //ɫ��3D����
            case 50:ErrMsg="���ݸ�ʽ����!";break;
            case 51:ErrMsg="z����Ӧ��������!";break;
            case 52:ErrMsg="����������С!";break;
            //Plot3D����
            case 55:ErrMsg="���ݸ�ʽ����!";break;
            //���������
            case 60:ErrMsg="���ݸ�ʽ����!";break;
            case 61:ErrMsg="������������!";break;
            case 62:ErrMsg="������ƥ��!";break;
            case 63:ErrMsg="��������ֿ���!";break;
            //�������ָ��ִ�д���
            case 65:ErrMsg="ȱʧָ��|���ݷָ���!";break;
            case 66:ErrMsg="����ռ���,������Ӿ���!";break;
            case 67:ErrMsg="�����Ƿ���,������׳˺���!";break;
            case 68:ErrMsg="��������������ͬ,���ܼ���Ӽ�!";break;
            case 69:ErrMsg="��������������Ӧ,���ܼ���˷�!";break;
            case 70:ErrMsg="����ľ������!";break;
            case 71:ErrMsg="�����Ų�����!";break;
            case 72:ErrMsg="����С��1!";break;
            case 73:ErrMsg="��������!";break;
            //�����Է��������
            case 75:ErrMsg="ϵ��������С����!";break;
            case 76:ErrMsg="��������ӦΪ1*N�ľ���!";break;
            case 77:ErrMsg="DetA=0�������!";break;
            //��ά�������ߴ���
            case 80:ErrMsg="t����Ӧ��������!";break;
            case 81:ErrMsg="t�Ĳ���������!";break;
            case 82:ErrMsg="����t��Ϣ����!";break;
            case 84:ErrMsg="���ʽ��Ϣ�ָ������!";break;
            //������ϴ���
            case 85:ErrMsg="������������������!";break;
            case 86:ErrMsg="���ݸ�ʽ����!";break;
            case 87:ErrMsg="��Ӧ���ظ���X����!";break;
            case 88:ErrMsg="ȱʧ�ֺ�!";break;
            case 89:ErrMsg="����ӦΪ����!";break;
            case 90:ErrMsg="������ϵ�����󲻴���!";break;
            case 91:ErrMsg="��ϵĴ���ӦС����������!";break;
            case 92:ErrMsg="��ϵĴ���Ӧ���ڵ�����!";break;
            //ָ��������
            case 95:ErrMsg="��֧�ֵ�ָ��!";break;
            case 96:ErrMsg="������������ȷ!";break;
            case 97:ErrMsg="������ϵ�n��ӦС��k!";break;
            case 98:ErrMsg="�ü��������ӦС��0!";break;
            case 99:ErrMsg="�ֽ��������ӦС��2!";break;
            //���Թ滮����
            case 100:ErrMsg="ϵ���볣����Ϣ����Ӧ!";break;
            case 101:ErrMsg="ϵ����Ŀ�꺯����Ϣ����Ӧ!";break;
            case 102:ErrMsg="������Ϣ����!";break;
            case 103:ErrMsg="Ŀ�꺯����Ϣ����!";break;
            case 104:ErrMsg="��Ϊ����!";break;
            case 105:ErrMsg="�޽�!";break;
            //δ֪
            default:ErrMsg="δ֪����!";
        }
    }
    /********************�ⲿ���ú���********************/
    public int GetErrNum() {//��ȡ�������
        return ErrNum;
    }
    public String GetErrMsg() {//��ȡ������Ϣ
        return ErrMsg;
    }
    public String GetErrType() {//��ȡ��������
        if(ErrNum>=1 && ErrNum<=12)return "���ʽɨ�����";
        if(ErrNum>=13 && ErrNum<=14)return "���ʽ��ȡ����";
        if(ErrNum>=15 && ErrNum<=17)return "2D����ϵ����";
        if(ErrNum>=18 && ErrNum<=19)return "����ת������";
        if(ErrNum>=20 && ErrNum<=22)return "��ջ����";
        if(ErrNum>=30 && ErrNum<=33)return "���߲�ֵ����";
        if(ErrNum>=40 && ErrNum<=46)return "�������ߴ���";
        if(ErrNum>=50 && ErrNum<=52)return "ɫ��3D����";
        if(ErrNum>=55 && ErrNum<=55)return "Plot3D����";
        if(ErrNum>=60 && ErrNum<=63)return "���������";
        if(ErrNum>=65 && ErrNum<=73)return "�������ָ��ִ�д���";
        if(ErrNum>=74 && ErrNum<=77)return "�����Է��������";
        if(ErrNum>=80 && ErrNum<=84)return "��ά�������ߴ���";
        if(ErrNum>=85 && ErrNum<=92)return "������ϴ���";
        if(ErrNum>=95 && ErrNum<=99)return "ָ��������";
        if(ErrNum>=100 && ErrNum<=105)return "���Թ滮����";
        return "δ֪���ʹ���";
    }
}
