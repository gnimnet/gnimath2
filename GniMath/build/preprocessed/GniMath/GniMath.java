package GniMath;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import MyTypes.*;

public class GniMath extends MIDlet implements CommandListener {
    
    public GniMath() {
        initialize();
    }
    
    private Form FrmPlot2D;//GEN-BEGIN:MVDFields
    private Command Cmd_OK;
    private org.netbeans.microedition.lcdui.SplashScreen FrmSplash;
    private List FrmMain;
    private Command Cmd_Exit;
    private Command Cmd_Back;
    private TextField Txt_Plot2D_Str;
    private TextField Txt_Plot2D_Range;
    private Form FrmStrCom;
    private TextField Txt_StrCom_Str;
    private TextField Txt_StrCom_Ans;
    private Form FrmLineXY;
    private Form FrmAbout;
    private StringItem About_Version;
    private StringItem About_Designer;
    private TextField Txt_LineXY_Str;
    private TextField Txt_LineXY_tInfo;
    private TextField Txt_LineXY_xyRange;
    private Image GniMath;
    private StringItem About_BugReport;
    private Form FrmColor3D;
    private TextField Txt_Color_Str;
    private TextField Txt_Color_Range;
    private TextField Txt_Color_Info;
    private Form FrmPlot3D;
    private TextField Txt_Plot3D_Str;
    private TextField Txt_Plot3D_Range;
    private TextField Txt_Plot3D_Mov;
    private Form FrmMatrix;
    private TextField Txt_Matrix_Cmd;
    private TextField Txt_Matrix_Ans;
    private Form FrmLinerFunc;
    private TextField Txt_Func_Matrix1;
    private TextField Txt_Func_Matrix2;
    private TextField Txt_Func_Ans;
    private TextField Txt_StrCom_Range;
    private Form FrmLineXYZ;
    private TextField Txt_LineXYZ_Str;
    private TextField Txt_LineXYZ_tInfo;
    private TextField Txt_LineXYZ_Mov;
    private Form FrmNearLine;
    private TextField Txt_NearLine_Str;
    private TextField Txt_NearLine_Lv;
    private StringItem stringItem1;
    private Form FrmLinearPro;
    private TextField Txt_LinearPro_A;
    private TextField Txt_LinearPro_B;
    private TextField Txt_LinearPro_C;
    private TextField Txt_LinearPro_Ans;//GEN-END:MVDFields
    //公用按钮
    private Command Cmd_Canvas_Back;
    //Plot2D相关
    private Plot2D MyPlot2D;
    private Command Cmd_Plot2D_Mode0;
    private Command Cmd_Plot2D_Mode1;
    //参数曲线
    private LineXY MyLineXY;
    //样本点插值程序
    private Curvilinear MyCurvilinear;
    private Command Cmd_Linear_ModeChange;
    //曲线拟合程序
    private NearLine MyNearLine;
    private Command Cmd_NearLine_ModeChange;
    //色彩深度三维绘制程序
    private Color3D MyColor3D;
    private Command Cmd_Color3D_ModeChange;
    //三维图象绘制
    private Plot3D MyPlot3D;
    private Command Cmd_Plot3D_ToL;//放大命令
    private Command Cmd_Plot3D_ToS;//缩小命令
    private Command Cmd_Plot3D_More;//提高精度命令
    private Command Cmd_Plot3D_Less;//降低精度命令
    //三维参数曲线绘制
    private LineXYZ MyLineXYZ;
    private Command Cmd_LineXYZ_ModeChange;//切换模式
    //矩阵计算
    private SolvelMatrix MySolvelMatrix;
    //硬件信息测试
    private TestCanvas MyTestCanvas;
    
//GEN-LINE:MVDMethods

    /** Called by the system to indicate that a command has been invoked on a particular displayable.//GEN-BEGIN:MVDCABegin
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:MVDCABegin
    // Insert global pre-action code here
        if (displayable == FrmPlot2D) {//GEN-BEGIN:MVDCABody
            if (command == Cmd_OK) {//GEN-END:MVDCABody
                // Insert pre-action code here
                try{
                    MyPlot2D=new Plot2D(Txt_Plot2D_Str.getString(),
                            Txt_Plot2D_Range.getString());
                    Cmd_Canvas_Back = new Command("返回",Command.BACK,1);//定义并初始化返回按钮
                    Cmd_Plot2D_Mode0=new Command("变换模式",Command.ITEM,2);
                    Cmd_Plot2D_Mode1=new Command("求解方程f1",Command.ITEM,2);
                    MyPlot2D.addCommand(Cmd_Canvas_Back);
                    MyPlot2D.addCommand(Cmd_Plot2D_Mode0);
                    MyPlot2D.addCommand(Cmd_Plot2D_Mode1);
                    MyPlot2D.setCommandListener(this);
                    getDisplay().setCurrent(MyPlot2D);
                }catch(MyError Err){
                    getDisplay().setCurrent(GetErrorAlert(Err.GetErrType()+"["+Err.GetErrNum()+"]"+
                            Err.GetErrMsg()), get_FrmPlot2D()); 
                }catch(Exception OtherErr){
                    getDisplay().setCurrent(GetErrorAlert(OtherErr.toString()), get_FrmPlot2D()); 
                }
                // Do nothing//GEN-LINE:MVDCAAction4
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase4
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction11
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase11
        } else if (displayable == FrmMain) {
            if (command == FrmMain.SELECT_COMMAND) {
                switch (get_FrmMain().getSelectedIndex()) {
                    case 0://GEN-END:MVDCACase11
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmStrCom());//GEN-LINE:MVDCAAction13
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase13
                    case 1://GEN-END:MVDCACase13
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmPlot2D());//GEN-LINE:MVDCAAction15
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase15
                    case 2://GEN-END:MVDCACase15
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmLineXY());//GEN-LINE:MVDCAAction33
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase33
                    case 10://GEN-END:MVDCACase33
                        // Insert pre-action code here
                        MyTestCanvas=new TestCanvas();
                        Cmd_Canvas_Back = new Command("返回",Command.BACK,1);
                        MyTestCanvas.addCommand(Cmd_Canvas_Back);
                        MyTestCanvas.setCommandListener(this);
                        getDisplay().setCurrent(MyTestCanvas);
                        // Do nothing//GEN-LINE:MVDCAAction29
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase29
                    case 11://GEN-END:MVDCACase29
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmAbout());//GEN-LINE:MVDCAAction31
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase31
                    case 4://GEN-END:MVDCACase31
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmColor3D());//GEN-LINE:MVDCAAction51
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase51
                    case 5://GEN-END:MVDCACase51
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmPlot3D());//GEN-LINE:MVDCAAction59
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase59
                    case 8://GEN-END:MVDCACase59
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmMatrix());//GEN-LINE:MVDCAAction68
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase68
                    case 7://GEN-END:MVDCACase68
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmLinerFunc());//GEN-LINE:MVDCAAction77
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase77
                    case 6://GEN-END:MVDCACase77
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmLineXYZ());//GEN-LINE:MVDCAAction86
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase86
                    case 3://GEN-END:MVDCACase86
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmNearLine());//GEN-LINE:MVDCAAction94
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase94
                    case 9://GEN-END:MVDCACase94
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_FrmLinearPro());//GEN-LINE:MVDCAAction101
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase101
                }
            } else if (command == Cmd_Exit) {//GEN-END:MVDCACase101
                // Insert pre-action code here
                exitMIDlet();//GEN-LINE:MVDCAAction17
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase17
        } else if (displayable == FrmSplash) {
            if (command == FrmSplash.DISMISS_COMMAND) {//GEN-END:MVDCACase17
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction6
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase6
        } else if (displayable == FrmStrCom) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase6
                // Insert pre-action code here
                try{
                    String MyComStr=MyFunction.DelSpace(MyFunction.MyTranStr(Txt_StrCom_Str.getString()));
                    int pos;
                    if((pos=MyComStr.indexOf('|'))!=-1){//指令计算
                        Txt_StrCom_Ans.setString(SolveCom.GetAns(MyComStr.substring(0,pos),MyComStr.substring(pos+1)));
                    }
                    else if(MyComStr.indexOf('x')==-1){//无变量x的表达式,计算表达式值
                        StrComTree StrCom=new StrComTree(MyComStr);
                        StrCom.AddVar("pi",java.lang.Math.PI);//注册常量PI
                        StrCom.AddVar("e",java.lang.Math.E);//注册常量E
                        Txt_StrCom_Ans.setString(""+StrCom.ComputeTree(StrCom.Root));
                    }
                    else{//含有x的表达式,计算积分
                        StrComTree StrCom=new StrComTree(MyFunction.DelSpace(MyFunction.MyTranStr(Txt_StrCom_Str.getString())));
                        StrCom.AddVar("x");//注册变量x
                        StrCom.AddVar("pi",java.lang.Math.PI);//注册常量PI
                        StrCom.AddVar("e",java.lang.Math.E);//注册常量E
                        int i,j=0,k=0,m=0,n=0;
                        for(i=0;i<Txt_StrCom_Range.getString().length();i++){
                            if(Txt_StrCom_Range.getString().charAt(i)=='(')
                                j++;
                            else if(Txt_StrCom_Range.getString().charAt(i)==',')
                                k++;
                            else if(Txt_StrCom_Range.getString().charAt(i)==')')
                                m++;
                            else if(Txt_StrCom_Range.getString().charAt(i)=='|')
                                n++;
                        }
                        if(j!=1||k!=1||m!=1||n!=1){
                            Txt_StrCom_Ans.setString("积分数据不正确!");
                        }
                        else{
                            j=Txt_StrCom_Range.getString().indexOf('(');
                            k=Txt_StrCom_Range.getString().indexOf(',');
                            m=Txt_StrCom_Range.getString().indexOf(')');
                            n=Txt_StrCom_Range.getString().indexOf('|');
                            if(j>k||k>m||m>n||j!=0||n!=m+1){
                                Txt_StrCom_Ans.setString("积分数据不正确!");
                            }
                            else{
                                double Xmin=MyFunction.IsNum(Txt_StrCom_Range.getString().substring(j+1,k));
                                double Xmax=MyFunction.IsNum(Txt_StrCom_Range.getString().substring(k+1,m));
                                double Xstep=MyFunction.IsNum(Txt_StrCom_Range.getString().substring(n+1));
                                double sum=0;
                                StrCom.SetVar("x",Xmin);
                                sum+=StrCom.ComputeTree(StrCom.Root);
                                StrCom.SetVar("x",Xmax);
                                sum+=StrCom.ComputeTree(StrCom.Root);
                                for(double x=Xstep;x<Xmax;x+=Xstep){
                                    StrCom.SetVar("x",x);
                                    sum=sum+2*StrCom.ComputeTree(StrCom.Root);
                                }
                                sum=sum*Xstep/2;
                                Txt_StrCom_Ans.setString("积分为"+Double.toString(sum));
                            }
                        }
                    }
                }catch(MyError Err){
                    Txt_StrCom_Ans.setString("["+Err.GetErrNum()+"]"+Err.GetErrMsg());
                }catch(Exception OtherErr){
                    Txt_StrCom_Ans.setString(OtherErr.toString());
                }
                // Do nothing//GEN-LINE:MVDCAAction21
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase21
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction22
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase22
        } else if (displayable == FrmLineXY) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase22
                // Insert pre-action code here
                try{
                    MyLineXY=new LineXY(Txt_LineXY_Str.getString(),
                        Txt_LineXY_tInfo.getString(),Txt_LineXY_xyRange.getString());
                    Cmd_Canvas_Back = new Command("返回",Command.BACK,1);//定义并初始化返回按钮
                    MyLineXY.addCommand(Cmd_Canvas_Back);
                    MyLineXY.setCommandListener(this);
                    getDisplay().setCurrent(MyLineXY);
                }
                catch(MyError Err){
                    getDisplay().setCurrent(GetErrorAlert(Err.GetErrType()+"["+Err.GetErrNum()+"]"+
                            Err.GetErrMsg()), get_FrmLineXY()); 
                }catch(Exception OtherErr){
                    getDisplay().setCurrent(GetErrorAlert(OtherErr.toString()), get_FrmLineXY()); 
                }
                // Do nothing//GEN-LINE:MVDCAAction39
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase39
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction40
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase40
        } else if (displayable == FrmAbout) {
            if (command == Cmd_Back) {//GEN-END:MVDCACase40
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction41
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase41
        } else if (displayable == FrmColor3D) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase41
                // Insert pre-action code here
                try{
                    MyColor3D=new Color3D(Txt_Color_Str.getString(),Txt_Color_Range.getString(),Txt_Color_Info.getString());
                    Cmd_Canvas_Back = new Command("返回",Command.BACK,1);//定义并初始化返回按钮
                    Cmd_Color3D_ModeChange= new Command("切换",Command.ITEM,1);
                    MyColor3D.addCommand(Cmd_Canvas_Back);
                    MyColor3D.addCommand(Cmd_Color3D_ModeChange);
                    MyColor3D.setCommandListener(this);
                    getDisplay().setCurrent(MyColor3D);
                }
                catch(MyError Err){
                    getDisplay().setCurrent(GetErrorAlert(Err.GetErrType()+"["+Err.GetErrNum()+"]"+
                            Err.GetErrMsg()), get_FrmColor3D()); 
                }catch(Exception OtherErr){
                    getDisplay().setCurrent(GetErrorAlert(OtherErr.toString()), get_FrmColor3D()); 
                }
                // Do nothing//GEN-LINE:MVDCAAction53
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase53
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction54
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase54
        } else if (displayable == FrmPlot3D) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase54
                // Insert pre-action code here
                try{
                    MyPlot3D=new Plot3D(Txt_Plot3D_Str.getString(),Txt_Plot3D_Range.getString(),Txt_Plot3D_Mov.getString());
                    Cmd_Canvas_Back = new Command("返回",Command.BACK,1);//定义并初始化返回按钮
                    Cmd_Plot3D_ToL=new Command("放大图像",Command.ITEM,1);
                    Cmd_Plot3D_ToS= new Command("缩小图像",Command.ITEM,1);
                    Cmd_Plot3D_More=new Command("提高精度",Command.ITEM,1);
                    Cmd_Plot3D_Less= new Command("降低精度",Command.ITEM,1);
                    MyPlot3D.addCommand(Cmd_Canvas_Back);
                    MyPlot3D.addCommand(Cmd_Plot3D_ToL);
                    MyPlot3D.addCommand(Cmd_Plot3D_ToS);
                    MyPlot3D.addCommand(Cmd_Plot3D_More);
                    MyPlot3D.addCommand(Cmd_Plot3D_Less);
                    MyPlot3D.setCommandListener(this);
                    getDisplay().setCurrent(MyPlot3D);
                }catch(MyError Err){
                    getDisplay().setCurrent(GetErrorAlert(Err.GetErrType()+"["+Err.GetErrNum()+"]"+
                            Err.GetErrMsg()), get_FrmPlot3D()); 
                }catch(Exception OtherErr){
                    getDisplay().setCurrent(GetErrorAlert(OtherErr.toString()), get_FrmPlot3D()); 
                }
                // Do nothing//GEN-LINE:MVDCAAction62
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase62
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction63
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase63
        } else if (displayable == FrmMatrix) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase63
                // Insert pre-action code here
                String MatrixRtnStr;
                try{
                    MatrixRtnStr=MySolvelMatrix.DoCmd(Txt_Matrix_Cmd.getString());
                    Txt_Matrix_Ans.setString(MatrixRtnStr);
                }catch(MyError Err){
                    Txt_Matrix_Ans.setString("["+Err.GetErrNum()+"]"+Err.GetErrMsg());
                }catch(Exception OtherErr){
                    Txt_Matrix_Ans.setString(OtherErr.toString());
                }
                // Do nothing//GEN-LINE:MVDCAAction70
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase70
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction71
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase71
        } else if (displayable == FrmLinerFunc) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase71
                // Insert pre-action code here
                try{
                    T_Matrix A=new T_Matrix(Txt_Func_Matrix1.getString());
                    T_Matrix B=new T_Matrix(Txt_Func_Matrix2.getString());
                    if(A.Cols==A.Rows){
                        if(B.Cols!=1 || B.Rows!=A.Rows)
                            throw new MyError(76);
                        T_Matrix C=T_Matrix.MatrixR(A);
                        if(C==null)
                            throw new MyError(77);
                        T_Matrix D=T_Matrix.MatrixMul(C,B);
                        Txt_Func_Ans.setLabel("线性方程组的解:");
                        String Rtn="";
                        for(int i=0;i<D.Rows;i++)
                            Rtn=Rtn+"x["+(i+1)+"]"+D.MatrixNum[i][0]+"\n";
                        Txt_Func_Ans.setString(Rtn);
                    }
                    else if(A.Cols<A.Rows){
                        T_Matrix C=T_Matrix.MatrixT(A);
                        T_Matrix D=T_Matrix.MatrixR(T_Matrix.MatrixMul(C,A));
                        if(D==null)
                            throw new MyError(77);
                        T_Matrix E=T_Matrix.MatrixMul(D,T_Matrix.MatrixMul(C,B));
                        Txt_Func_Ans.setLabel("矛盾方程组的解:");
                        String Rtn="";
                        for(int i=0;i<E.Rows;i++)
                            Rtn=Rtn+"x["+(i+1)+"]"+E.MatrixNum[i][0]+"\n";
                        Txt_Func_Ans.setString(Rtn);
                    }
                    else
                        throw new MyError(75);
                }catch(MyError Err){
                    Txt_Func_Ans.setString("["+Err.GetErrNum()+"]"+Err.GetErrMsg());
                }catch(Exception OtherErr){
                    Txt_Func_Ans.setString(OtherErr.toString());
                }
                // Do nothing//GEN-LINE:MVDCAAction79
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase79
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction80
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase80
        } else if (displayable == FrmLineXYZ) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase80
                // Insert pre-action code here
                try{
                    MyLineXYZ=new LineXYZ(Txt_LineXYZ_Str.getString(),Txt_LineXYZ_tInfo.getString(),Txt_LineXYZ_Mov.getString());
                    Cmd_Canvas_Back = new Command("返回",Command.BACK,1);//定义并初始化返回按钮
                    Cmd_Plot3D_ToL=new Command("放大图像",Command.ITEM,1);
                    Cmd_Plot3D_ToS= new Command("缩小图像",Command.ITEM,1);
                    Cmd_Plot3D_More=new Command("提高精度",Command.ITEM,1);
                    Cmd_Plot3D_Less= new Command("降低精度",Command.ITEM,1);
                    Cmd_LineXYZ_ModeChange= new Command("切换",Command.OK,1);
                    MyLineXYZ.addCommand(Cmd_Canvas_Back);
                    MyLineXYZ.addCommand(Cmd_LineXYZ_ModeChange);
                    MyLineXYZ.addCommand(Cmd_Plot3D_ToL);
                    MyLineXYZ.addCommand(Cmd_Plot3D_ToS);
                    MyLineXYZ.addCommand(Cmd_Plot3D_More);
                    MyLineXYZ.addCommand(Cmd_Plot3D_Less);
                    MyLineXYZ.setCommandListener(this);
                    getDisplay().setCurrent(MyLineXYZ);
                }catch(MyError Err){
                    getDisplay().setCurrent(GetErrorAlert(Err.GetErrType()+"["+Err.GetErrNum()+"]"+
                            Err.GetErrMsg()), get_FrmLineXYZ()); 
                }catch(Exception OtherErr){
                    getDisplay().setCurrent(GetErrorAlert(OtherErr.toString()), get_FrmLineXYZ()); 
                }
                // Do nothing//GEN-LINE:MVDCAAction88
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase88
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction89
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase89
        } else if (displayable == FrmNearLine) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase89
                // Insert pre-action code here
                try{
                    String Str=Txt_NearLine_Str.getString();
                    int count=0;
                    int Lv=Integer.parseInt(Txt_NearLine_Lv.getString());
                    for(int i=0;i<Str.length();i++)
                        if(Str.charAt(i)==';')
                            count++;
                    if(count==Lv+1){
                        MyCurvilinear=new Curvilinear(Txt_NearLine_Str.getString());
                        Cmd_Canvas_Back = new Command("返回",Command.BACK,1);//定义并初始化返回按钮
                        Cmd_Linear_ModeChange=new Command("切换",Command.ITEM,2);
                        MyCurvilinear.addCommand(Cmd_Canvas_Back);
                        MyCurvilinear.addCommand(Cmd_Linear_ModeChange);
                        MyCurvilinear.setCommandListener(this);
                        getDisplay().setCurrent(MyCurvilinear);
                    }
                    else{
                        MyNearLine=new NearLine(Txt_NearLine_Str.getString(),Txt_NearLine_Lv.getString());
                        Cmd_Canvas_Back = new Command("返回",Command.BACK,1);//定义并初始化返回按钮
                        Cmd_NearLine_ModeChange=new Command("切换",Command.ITEM,1);
                        MyNearLine.addCommand(Cmd_Canvas_Back);
                        MyNearLine.addCommand(Cmd_NearLine_ModeChange);
                        MyNearLine.setCommandListener(this);
                        getDisplay().setCurrent(MyNearLine);
                    }
                }
                catch(MyError Err){
                    getDisplay().setCurrent(GetErrorAlert(Err.GetErrType()+"["+Err.GetErrNum()+"]"+
                            Err.GetErrMsg()), get_FrmNearLine());
                }catch(Exception OtherErr){
                    getDisplay().setCurrent(GetErrorAlert(OtherErr.toString()), get_FrmNearLine()); 
                }
                // Do nothing//GEN-LINE:MVDCAAction96
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase96
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction97
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase97
        } else if (displayable == FrmLinearPro) {
            if (command == Cmd_OK) {//GEN-END:MVDCACase97
                // Insert pre-action code here
                String StrA=MyFunction.DelSpace(MyFunction.MyTranStr(Txt_LinearPro_A.getString()));
                String StrB=MyFunction.DelSpace(MyFunction.MyTranStr(Txt_LinearPro_B.getString()));
                String StrC=MyFunction.DelSpace(MyFunction.MyTranStr(Txt_LinearPro_C.getString()));
                try{
                    T_Matrix A=new T_Matrix(StrA);
                    T_Matrix B=new T_Matrix(StrB);
                    T_Matrix C=new T_Matrix(StrC);
                    if(A.Rows!=B.Rows)
                        throw new MyError(100);
                    if(A.Cols!=C.Cols)
                        throw new MyError(101);
                    if(B.Cols!=1)
                        throw new MyError(102);
                    if(C.Rows!=1)
                        throw new MyError(103);
                    double[] b=new double[B.Rows];
                    for(int i=0;i<b.length;i++)
                        b[i]=B.MatrixNum[i][0];
                    double[] c=new double[C.Cols];
                    for(int i=0;i<c.length;i++)
                        c[i]=C.MatrixNum[0][i];
                    String AnsStr="";
                    //求解最大值
                    LinearProgramming L=new LinearProgramming(A.MatrixNum,b,c);
                    AnsStr=AnsStr+"最大值:"+L.v+"\n";
                    for(int i=0;i<L.x.length;i++)
                        AnsStr=AnsStr+"x["+(i+1)+"]="+L.x[i]+"\n";
                    //求解最小值
                    for(int i=0;i<c.length;i++)
                        c[i]=-c[i];//反转目标系数
                    L=new LinearProgramming(A.MatrixNum,b,c);
                    AnsStr=AnsStr+"最小值:"+(-L.v)+"\n";
                    for(int i=0;i<L.x.length;i++)
                        AnsStr=AnsStr+"x["+(i+1)+"]="+L.x[i]+"\n";
                    Txt_LinearPro_Ans.setString(AnsStr);
                }
                catch(MyError Err){
                    Txt_LinearPro_Ans.setString("["+Err.GetErrNum()+"]"+Err.GetErrMsg());
                }catch(Exception OtherErr){
                    Txt_LinearPro_Ans.setString(OtherErr.toString());
                }
                // Do nothing//GEN-LINE:MVDCAAction104
                // Insert post-action code here
            } else if (command == Cmd_Back) {//GEN-LINE:MVDCACase104
                // Insert pre-action code here
                getDisplay().setCurrent(get_FrmMain());//GEN-LINE:MVDCAAction105
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase105
        }//GEN-END:MVDCACase105
    // Insert global post-action code here
        else if(displayable == MyPlot2D){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmPlot2D());
                MyPlot2D=null;
            }
            else if(command == Cmd_Plot2D_Mode0){
                MyPlot2D.ProgramMode=0;
                MyPlot2D.repaint();
            }
            else if(command == Cmd_Plot2D_Mode1){
                MyPlot2D.ProgramMode=1;
                MyPlot2D.InitFuncAns();
                MyPlot2D.repaint();
            }
        }
        else if(displayable == MyLineXY){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmLineXY());
                MyLineXY=null;
            }
        }
        else if(displayable == MyCurvilinear){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmNearLine());
                MyCurvilinear=null;
            }
            if(command == Cmd_Linear_ModeChange){
                MyCurvilinear.InfoMode=!MyCurvilinear.InfoMode;//切换显示信息
                MyCurvilinear.repaint();
            }
        }
        else if(displayable == MyNearLine){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmNearLine());
                MyNearLine=null;
            }
            if(command == Cmd_NearLine_ModeChange){
                MyNearLine.InfoMode=!MyNearLine.InfoMode;//切换显示信息
                MyNearLine.repaint();
            }
        }
        else if(displayable == MyColor3D){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmColor3D());
                MyTestCanvas=null;
            }
            if(command == Cmd_Color3D_ModeChange){
                MyColor3D.ProgramMode=!MyColor3D.ProgramMode;
                MyColor3D.repaint();
            }
        }
        else if(displayable == MyPlot3D){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmPlot3D());
                MyPlot3D=null;
            }
            if(command == Cmd_Plot3D_ToL){
                MyPlot3D.ToBeLarge();
            }
            if(command == Cmd_Plot3D_ToS){
                MyPlot3D.ToBeSmall();
            }
            if(command == Cmd_Plot3D_More){
                MyPlot3D.ToBeMore();
            }
            if(command == Cmd_Plot3D_Less){
                MyPlot3D.ToBeLess();
            }
        }
        else if(displayable == MyLineXYZ){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmLineXYZ());
                MyLineXYZ=null;
            }
            if(command == Cmd_LineXYZ_ModeChange){
                MyLineXYZ.InfoMode=!MyLineXYZ.InfoMode;
                MyLineXYZ.repaint();
            }
            if(command == Cmd_Plot3D_ToL){
                MyLineXYZ.ToBeLarge();
            }
            if(command == Cmd_Plot3D_ToS){
                MyLineXYZ.ToBeSmall();
            }
            if(command == Cmd_Plot3D_More){
                MyLineXYZ.ToBeMore();
            }
            if(command == Cmd_Plot3D_Less){
                MyLineXYZ.ToBeLess();
            }
        }
        else if(displayable == MyTestCanvas){
            if(command == Cmd_Canvas_Back){
                getDisplay().setCurrent(get_FrmMain());
                MyTestCanvas=null;
            }
        }
}//GEN-LINE:MVDCAEnd

    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
        About_Version = new StringItem("\u7A0B\u5E8F\u7248\u672C:", "2.0.0(Final)");//GEN-BEGIN:MVDInitInit
        Txt_LinearPro_Ans = new TextField("\u89C4\u5212\u7ED3\u679C:", null, 120, TextField.ANY);
        Txt_LineXYZ_Mov = new TextField("\u5E73\u79FB\u53CA\u7F29\u653E\u6BD4:\n(Xmov,Ymov)|Proportion", "(0,0)|50", 120, TextField.ANY);
        Txt_StrCom_Str = new TextField("\u8868\u8FBE\u5F0F\u6216\u547D\u4EE4:\nComStr OR Cmd|Data", "x*x", 1000, TextField.ANY);
        Txt_Matrix_Cmd = new TextField("\u547D\u4EE4\u53CA\u5176\u53C2\u6570:\nCommand|Data...", "add|(1,2,3)(2,1,2)(1,3,4)", 1000, TextField.ANY);
        Txt_Plot3D_Str = new TextField("\u8868\u8FBE\u5F0Ff(x,y):", "sin(x)*cos(y)", 1000, TextField.ANY);
        Txt_LinearPro_B = new TextField("\u7EA6\u675F\u6761\u4EF6\u5E38\u6570:", "(-2)(-4)(10)", 120, TextField.ANY);
        Cmd_Exit = new Command("\u9000\u51FA", Command.EXIT, 1);
        Txt_LineXYZ_tInfo = new TextField("\u53C2\u91CFt\u8303\u56F4:\n (min,max)|dt", "(-10,10)|0.2", 120, TextField.ANY);
        Txt_Color_Range = new TextField("\u7ED8\u5236\u8303\u56F4:\n(Xrange)|(Yrange)", "(-4,4)|(-4,4)", 120, TextField.ANY);
        Txt_Func_Matrix2 = new TextField("\u5E38\u6570\u77E9\u9635B:", "(11)(2)(8)", 1000, TextField.ANY);
        Txt_LineXY_Str = new TextField("\u8868\u8FBE\u5F0F:\n[color]fx1(t)|fy1(t);\n[color]fx2(t)|fy2(t);...", "[0,0,255](30*t)/(1+t*t*t)|(30*t*t)/(1+t*t*t);(15*t)/(1+t*t*t)|(15*t*t)/(1+t*t*t);", 1000, TextField.ANY);
        Txt_LinearPro_C = new TextField("\u76EE\u6807\u51FD\u6570\u7CFB\u6570:", "(-2,-1)", 120, TextField.ANY);
        Txt_StrCom_Range = new TextField("\u79EF\u5206\u533A\u95F4\u53CA\u6B65\u957F:\n(Xrange)|Step", "(0,5)|0.0001", 120, TextField.ANY);
        Txt_Plot3D_Range = new TextField("\u8BA1\u7B97\u8303\u56F4:\n(Xrange)|(Yrange)", "(-4,4)|(-4,4)", 120, TextField.ANY);
        try {
            GniMath = Image.createImage("/Pics/GniMath2.0.png");
        } catch (java.io.IOException exception) {
            exception.printStackTrace();
        }
        Txt_Color_Info = new TextField("\u53C2\u6570:\n(Zrange)|Step", "(-1,1)|5", 120, TextField.ANY);
        Txt_Plot3D_Mov = new TextField("\u5E73\u79FB\u53CA\u7F29\u653E\u6BD4:\n(Xmov,Ymov)|Proportion", "(0,0)|20", 120, TextField.ANY);
        Txt_Func_Ans = new TextField("\u7ED3\u679C:", null, 3000, TextField.ANY);
        Txt_Func_Matrix1 = new TextField("\u65B9\u7A0B\u7EC4\u7CFB\u6570\u77E9\u9635A:", "(2,3,1)(1,2,-1)(1,-1,3)", 1000, TextField.ANY);
        Txt_LinearPro_A = new TextField("\u7EA6\u675F\u6761\u4EF6\u7CFB\u6570:", "(0,-1)(-2,1)(1,1)", 120, TextField.ANY);
        Cmd_OK = new Command("\u786E\u5B9A", Command.OK, 1);
        About_Designer = new StringItem("\u7A0B\u5E8F\u8BBE\u8BA1:", "Ming");
        About_BugReport = new StringItem("\u9519\u8BEF\u62A5\u544A(Bug Report):", "GnimReport@gmail.com");
        Txt_LineXY_xyRange = new TextField("\u7ED8\u5236\u8303\u56F4:\n(Xrange)|(Yrange)", "(-20,20)|(-20,20)", 120, TextField.ANY);
        Txt_StrCom_Ans = new TextField("\u7ED3\u679C:", null, 120, TextField.ANY);
        Txt_LineXYZ_Str = new TextField("\u8868\u8FBE\u5F0F:\nfx(t)|fy(t)|fz(t)", "sin(t)|cos(t)|t*0.1", 1000, TextField.ANY);
        Txt_LineXY_tInfo = new TextField("\u53C2\u91CFt\u8303\u56F4:\n(min,max)|dt", "(-10,10)|0.1", 120, TextField.ANY);
        Txt_NearLine_Lv = new TextField("\u62DF\u5408\u6B21\u6570:", "2", 120, TextField.DECIMAL);
        Txt_Color_Str = new TextField("\u8868\u8FBE\u5F0Ff(x,y):", "sin(x)*cos(y)", 1000, TextField.ANY);
        Txt_Plot2D_Str = new TextField("\u8868\u8FBE\u5F0F:\n[color]Str;...", "[0,0,255]sin(x);cos(x);", 1000, TextField.ANY);
        Txt_Plot2D_Range = new TextField("\u7ED8\u5236\u8303\u56F4:\n(Xrange)|(Yrange)", "(-10,10)|(-10,10)", 120, TextField.ANY);
        Txt_NearLine_Str = new TextField("\u6837\u672C\u70B9\u6570\u636E:\npoint1;point2;...", "(3,1);(1,-3);(5,2);(6,4);", 120, TextField.ANY);
        Txt_Matrix_Ans = new TextField("\u7ED3\u679C\u663E\u793A:", null, 5000, TextField.ANY);
        getDisplay().setCurrent(get_FrmSplash());//GEN-END:MVDInitInit
        // Insert post-init code here
        MySolvelMatrix=new SolvelMatrix();
    }//GEN-LINE:MVDInitEnd
    
    /**
     * This method should return an instance of the display.
     */
    public Display getDisplay() {//GEN-FIRST:MVDGetDisplay
        return Display.getDisplay(this);
    }//GEN-LAST:MVDGetDisplay
    
    /**
     * This method should exit the midlet.
     */
    public void exitMIDlet() {//GEN-FIRST:MVDExitMidlet
        getDisplay().setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    }//GEN-LAST:MVDExitMidlet

    /** This method returns instance for FrmPlot2D component and should be called instead of accessing FrmPlot2D field directly.//GEN-BEGIN:MVDGetBegin2
     * @return Instance for FrmPlot2D component
     */
    public Form get_FrmPlot2D() {
        if (FrmPlot2D == null) {//GEN-END:MVDGetBegin2
            // Insert pre-init code here
            FrmPlot2D = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit2
                Txt_Plot2D_Str,
                Txt_Plot2D_Range
            });
            FrmPlot2D.addCommand(Cmd_OK);
            FrmPlot2D.addCommand(get_Cmd_Back());
            FrmPlot2D.setCommandListener(this);//GEN-END:MVDGetInit2
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd2
        return FrmPlot2D;
    }//GEN-END:MVDGetEnd2
    /** This method returns instance for FrmSplash component and should be called instead of accessing FrmSplash field directly.//GEN-BEGIN:MVDGetBegin5
     * @return Instance for FrmSplash component
     */
    public org.netbeans.microedition.lcdui.SplashScreen get_FrmSplash() {
        if (FrmSplash == null) {//GEN-END:MVDGetBegin5
            // Insert pre-init code here
            FrmSplash = new org.netbeans.microedition.lcdui.SplashScreen(getDisplay());//GEN-BEGIN:MVDGetInit5
            FrmSplash.setCommandListener(this);
            FrmSplash.setFullScreenMode(true);
            FrmSplash.setImage(GniMath);//GEN-END:MVDGetInit5
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd5
        return FrmSplash;
    }//GEN-END:MVDGetEnd5

    /** This method returns instance for FrmMain component and should be called instead of accessing FrmMain field directly.//GEN-BEGIN:MVDGetBegin7
     * @return Instance for FrmMain component
     */
    public List get_FrmMain() {
        if (FrmMain == null) {//GEN-END:MVDGetBegin7
            // Insert pre-init code here
            FrmMain = new List(null, Choice.IMPLICIT, new String[] {//GEN-BEGIN:MVDGetInit7
                "\u8868\u8FBE\u5F0F\u8BA1\u7B97",
                "F(x)\u56FE\u8C61\u7ED8\u5236",
                "F(t)\u4E8C\u7EF4\u53C2\u6570\u66F2\u7EBF\u7ED8\u5236",
                "\u66F2\u7EBF\u62DF\u5408\u53CA\u591A\u9879\u5F0F\u63D2\u503C",
                "\u8272\u5F69\u6DF1\u5EA6\u4E09\u7EF4\u56FE\u8C61\u7ED8\u5236",
                "F(x,y)\u4E09\u7EF4\u56FE\u8C61\u7ED8\u5236",
                "F(t)\u4E09\u7EF4\u53C2\u6570\u66F2\u7EBF\u7ED8\u5236",
                "\u89E3\u7EBF\u6027\u65B9\u7A0B\u7EC4",
                "\u77E9\u9635\u8BA1\u7B97",
                "\u7EBF\u6027\u89C4\u5212",
                "#\u5C4F\u5E55\u4FE1\u606F#",
                "#\u5173\u4E8E#"
            }, new Image[] {
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            });
            FrmMain.addCommand(Cmd_Exit);
            FrmMain.setCommandListener(this);
            FrmMain.setSelectedFlags(new boolean[] {
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
            });//GEN-END:MVDGetInit7
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd7
        return FrmMain;
    }//GEN-END:MVDGetEnd7

    /** This method returns instance for Cmd_Back component and should be called instead of accessing Cmd_Back field directly.//GEN-BEGIN:MVDGetBegin10
     * @return Instance for Cmd_Back component
     */
    public Command get_Cmd_Back() {
        if (Cmd_Back == null) {//GEN-END:MVDGetBegin10
            // Insert pre-init code here
            Cmd_Back = new Command("\u540E\u9000", Command.BACK, 1);//GEN-LINE:MVDGetInit10
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd10
        return Cmd_Back;
    }//GEN-END:MVDGetEnd10

    /** This method returns instance for FrmStrCom component and should be called instead of accessing FrmStrCom field directly.//GEN-BEGIN:MVDGetBegin20
     * @return Instance for FrmStrCom component
     */
    public Form get_FrmStrCom() {
        if (FrmStrCom == null) {//GEN-END:MVDGetBegin20
            // Insert pre-init code here
            FrmStrCom = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit20
                Txt_StrCom_Str,
                Txt_StrCom_Range,
                Txt_StrCom_Ans
            });
            FrmStrCom.addCommand(Cmd_OK);
            FrmStrCom.addCommand(get_Cmd_Back());
            FrmStrCom.setCommandListener(this);//GEN-END:MVDGetInit20
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd20
        return FrmStrCom;
    }//GEN-END:MVDGetEnd20

    /** This method returns instance for FrmLineXY component and should be called instead of accessing FrmLineXY field directly.//GEN-BEGIN:MVDGetBegin25
     * @return Instance for FrmLineXY component
     */
    public Form get_FrmLineXY() {
        if (FrmLineXY == null) {//GEN-END:MVDGetBegin25
            // Insert pre-init code here
            FrmLineXY = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit25
                Txt_LineXY_Str,
                Txt_LineXY_tInfo,
                Txt_LineXY_xyRange
            });
            FrmLineXY.addCommand(Cmd_OK);
            FrmLineXY.addCommand(get_Cmd_Back());
            FrmLineXY.setCommandListener(this);//GEN-END:MVDGetInit25
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd25
        return FrmLineXY;
    }//GEN-END:MVDGetEnd25
 
    /** This method returns instance for FrmAbout component and should be called instead of accessing FrmAbout field directly.//GEN-BEGIN:MVDGetBegin27
     * @return Instance for FrmAbout component
     */
    public Form get_FrmAbout() {
        if (FrmAbout == null) {//GEN-END:MVDGetBegin27
            // Insert pre-init code here
            FrmAbout = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit27
                About_Version,
                About_Designer,
                About_BugReport,
                get_stringItem1()
            });
            FrmAbout.addCommand(get_Cmd_Back());
            FrmAbout.setCommandListener(this);//GEN-END:MVDGetInit27
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd27
        return FrmAbout;
    }//GEN-END:MVDGetEnd27

    /** This method returns instance for FrmColor3D component and should be called instead of accessing FrmColor3D field directly.//GEN-BEGIN:MVDGetBegin52
     * @return Instance for FrmColor3D component
     */
    public Form get_FrmColor3D() {
        if (FrmColor3D == null) {//GEN-END:MVDGetBegin52
            // Insert pre-init code here
            FrmColor3D = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit52
                Txt_Color_Str,
                Txt_Color_Range,
                Txt_Color_Info
            });
            FrmColor3D.addCommand(Cmd_OK);
            FrmColor3D.addCommand(get_Cmd_Back());
            FrmColor3D.setCommandListener(this);//GEN-END:MVDGetInit52
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd52
        return FrmColor3D;
    }//GEN-END:MVDGetEnd52

    /** This method returns instance for FrmPlot3D component and should be called instead of accessing FrmPlot3D field directly.//GEN-BEGIN:MVDGetBegin61
     * @return Instance for FrmPlot3D component
     */
    public Form get_FrmPlot3D() {
        if (FrmPlot3D == null) {//GEN-END:MVDGetBegin61
            // Insert pre-init code here
            FrmPlot3D = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit61
                Txt_Plot3D_Str,
                Txt_Plot3D_Range,
                Txt_Plot3D_Mov
            });
            FrmPlot3D.addCommand(Cmd_OK);
            FrmPlot3D.addCommand(get_Cmd_Back());
            FrmPlot3D.setCommandListener(this);//GEN-END:MVDGetInit61
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd61
        return FrmPlot3D;
    }//GEN-END:MVDGetEnd61

    /** This method returns instance for FrmMatrix component and should be called instead of accessing FrmMatrix field directly.//GEN-BEGIN:MVDGetBegin69
     * @return Instance for FrmMatrix component
     */
    public Form get_FrmMatrix() {
        if (FrmMatrix == null) {//GEN-END:MVDGetBegin69
            // Insert pre-init code here
            FrmMatrix = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit69
                Txt_Matrix_Cmd,
                Txt_Matrix_Ans
            });
            FrmMatrix.addCommand(Cmd_OK);
            FrmMatrix.addCommand(get_Cmd_Back());
            FrmMatrix.setCommandListener(this);//GEN-END:MVDGetInit69
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd69
        return FrmMatrix;
    }//GEN-END:MVDGetEnd69

    /** This method returns instance for FrmLinerFunc component and should be called instead of accessing FrmLinerFunc field directly.//GEN-BEGIN:MVDGetBegin78
     * @return Instance for FrmLinerFunc component
     */
    public Form get_FrmLinerFunc() {
        if (FrmLinerFunc == null) {//GEN-END:MVDGetBegin78
            // Insert pre-init code here
            FrmLinerFunc = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit78
                Txt_Func_Matrix1,
                Txt_Func_Matrix2,
                Txt_Func_Ans
            });
            FrmLinerFunc.addCommand(Cmd_OK);
            FrmLinerFunc.addCommand(get_Cmd_Back());
            FrmLinerFunc.setCommandListener(this);//GEN-END:MVDGetInit78
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd78
        return FrmLinerFunc;
    }//GEN-END:MVDGetEnd78

    /** This method returns instance for FrmLineXYZ component and should be called instead of accessing FrmLineXYZ field directly.//GEN-BEGIN:MVDGetBegin87
     * @return Instance for FrmLineXYZ component
     */
    public Form get_FrmLineXYZ() {
        if (FrmLineXYZ == null) {//GEN-END:MVDGetBegin87
            // Insert pre-init code here
            FrmLineXYZ = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit87
                Txt_LineXYZ_Str,
                Txt_LineXYZ_tInfo,
                Txt_LineXYZ_Mov
            });
            FrmLineXYZ.addCommand(Cmd_OK);
            FrmLineXYZ.addCommand(get_Cmd_Back());
            FrmLineXYZ.setCommandListener(this);//GEN-END:MVDGetInit87
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd87
        return FrmLineXYZ;
    }//GEN-END:MVDGetEnd87

    /** This method returns instance for FrmNearLine component and should be called instead of accessing FrmNearLine field directly.//GEN-BEGIN:MVDGetBegin95
     * @return Instance for FrmNearLine component
     */
    public Form get_FrmNearLine() {
        if (FrmNearLine == null) {//GEN-END:MVDGetBegin95
            // Insert pre-init code here
            FrmNearLine = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit95
                Txt_NearLine_Str,
                Txt_NearLine_Lv
            });
            FrmNearLine.addCommand(Cmd_OK);
            FrmNearLine.addCommand(get_Cmd_Back());
            FrmNearLine.setCommandListener(this);//GEN-END:MVDGetInit95
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd95
        return FrmNearLine;
    }//GEN-END:MVDGetEnd95

    /** This method returns instance for stringItem1 component and should be called instead of accessing stringItem1 field directly.//GEN-BEGIN:MVDGetBegin102
     * @return Instance for stringItem1 component
     */
    public StringItem get_stringItem1() {
        if (stringItem1 == null) {//GEN-END:MVDGetBegin102
            // Insert pre-init code here
            stringItem1 = new StringItem("\u8D21\u732E\u8005\u540D\u5355:", "Ming(fe3000@qq.com)\nZhangZhen(mfzz1134@163.com)");//GEN-LINE:MVDGetInit102
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd102
        return stringItem1;
    }//GEN-END:MVDGetEnd102

    /** This method returns instance for FrmLinearPro component and should be called instead of accessing FrmLinearPro field directly.//GEN-BEGIN:MVDGetBegin103
     * @return Instance for FrmLinearPro component
     */
    public Form get_FrmLinearPro() {
        if (FrmLinearPro == null) {//GEN-END:MVDGetBegin103
            // Insert pre-init code here
            FrmLinearPro = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit103
                Txt_LinearPro_A,
                Txt_LinearPro_B,
                Txt_LinearPro_C,
                Txt_LinearPro_Ans
            });
            FrmLinearPro.addCommand(Cmd_OK);
            FrmLinearPro.addCommand(get_Cmd_Back());
            FrmLinearPro.setCommandListener(this);//GEN-END:MVDGetInit103
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd103
        return FrmLinearPro;
    }//GEN-END:MVDGetEnd103
   
            
    public Alert GetErrorAlert(String Msg){
        return new Alert("错误!",Msg, null, null);
    }
    
    public void startApp() {
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
}
