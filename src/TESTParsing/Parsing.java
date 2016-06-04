package TESTParsing;

import util.Stack;

import static TESTParsing.Util_Parsing.*;

/**
 * Created by zprde on 2016/5/10.
 */
public class Parsing {
    private Stack<String> stack;
    private String ch,fileName = "out.txt";
    private Boolean isError = false;
    private int line;

    public Parsing(){

    }

    public Parsing(String fileName){
        this.fileName = fileName;
    }


//    语法分析开始
    public void start(){
        stack = getStack(fileName);
        if(stack != null){
            getNext();
            pro();
            if(!isError){
                System.out.println("语法分析成功，程序无语法错误");
            } else{
                System.out.println("存在语法错误，分析失败");
            }
        }
    }


//    1)程序函数
//    <pro> => { <dec_list><stat_list> }
//    First(<dec_list><stat_list>) = { { }
    private void pro(){
        if(ch.equals("{")){
            getNext();
            dec_list();
            getNext();
            stat_list();
            getNext();
            if(!ch.equals("}")){
                System.out.println("程序缺少符号: }");
                error();
            }
        } else{
            System.out.println("程序缺少符号: {");
            error();
        }
    }

//    2)声明列表
//    <dec_list> => <dec_stat><dec_list> | E
//    First(<dec_stat><dec_list>) = { int }
//    Follow(dec_list) = { if,while,for,read,write,{,},;,(,ID,NUM }
    private void dec_list(){
        if(!isError){
            String[] first = {"if", "while", "for", "read", "write", "{", "}, ;", "(", "ID"};
            if(ch.equals("int")){
                dec_stat();
                getNext();
                dec_list();
            } else if(belong(ch,first)){
                push();
                return;
            } else{
                System.out.println("声明列表出错！");
                error();
            }
        }
    }

//    3)声明语句
//    <dec_stat> => int ID;
//    First(dec_stat) = {int}
    private void dec_stat(){
        if(!isError){
            if(ch.equals("int")){
                getNext();
                if(ch.equals("ID")){
                    getNext();
                    if(!ch.equals(";")){
                        System.out.println("声明语句缺少符号 ;");
                        error();
                    }
                } else{
                    System.out.println("声明语句缺少关键字 ID");
                    error();
                }
            } else{
                System.out.println("声明语句缺少关键字 int");
                error();
            }
        }
    }


//    4)状态列表
//    <stat_list> => <stat><stat_list> | E
//    First(<stat><stat_list>) = { if,while,for,read,write,{,;,ID,}.
//    Follow(<stat_list>) = { } }
    private void stat_list(){
        if(!isError){
            String[] first = {"if", "while", "for", "read", "write", "{", ";", "(", "NUM", "ID"};
            if(ch.equals("}")){
                push();
                return;
            } else if(belong(ch,first)){
                stat();
                getNext();
                stat_list();
            } else{
                System.out.println("状态列表出错!");
                error();
            }
        }
    }

//    5)状态语句
//    <stat> => <if_stat> | <for_stat> | <while_stat> | <read_stat> | <write_stat>
//                    | <com_stat> | <exp_stat>
//    First(<com_list>) = { { }
//    First( <exp>; ) = { ID, NUM, ( }
    private void stat(){
        String[] first = { "(", "ID", "NUM", ";" };
        if(!isError){
            if(ch.equals("if")){
                if_stat();
            } else if(ch.equals("for")){
                for_stat();
            } else if(ch.equals("while")){
                while_stat();
            } else if(ch.equals("read")){
                read_stat();
            } else if(ch.equals("write")){
                write_stat();
            } else if(ch.equals("{")){
                com_list();
            } else if(belong(ch,first)){
                exp_stat();
            } else {
                System.out.println("状态语句出错!");
                error();
            }
        }
    }

//    6)if语句
//    <if_stat> => if ( <exp> ) <stat> | if ( <exp> ) <stat> else <stat>
//    First(<if_stat>) = { if }
    private void if_stat(){
        if(!isError){
            if(ch.equals("if")){
                getNext();
                if(ch.equals("(")){
                    getNext();
                    exp();
                    getNext();
                    if(ch.equals(")")){
                        getNext();
                        stat();
                        if(stack.getTop().equals("else")){
                            getNext();
                            getNext();
                            stat();
                        }
                    } else{
                        System.out.println("if语句缺少符号 ) ");
                        error();
                    }
                } else{
                    System.out.println("if语句缺少符号 ( ");
                    error();
                }
            } else {
                System.out.println("if语句缺少关键字 for ");
                error();
            }
        }
    }

//    7)while语句
//    <while_stat> => while ( <exp> ) <stat>
//    First(<while>) = { while }
    private void while_stat(){
        if(!isError){
            if(ch.equals("while")){
                getNext();
                if(ch.equals("(")){
                    getNext();
                    exp();
                    getNext();
                    if(ch.equals(")")){
                        getNext();
                        stat();
                    } else{
                        System.out.println("while语句缺少符号 ) ");
                        error();
                    }
                } else{
                    System.out.println("while语句缺少符号 ( ");
                    error();
                }
            } else {
                System.out.println("while语句缺少符号 while ");
                error();
            }
        }
    }

//    8)for语句
//    <for_stat> => for ( <exp>; <exp>; <exp> ) <stat>
//    First(<for_stat>) = { for }
    private void for_stat(){
        if(!isError){
            if(ch.equals("for")){
                getNext();
                if(ch.equals("(")){
                    getNext();
                    exp();
                    getNext();
                    if(ch.equals(";")){
                        getNext();
                        exp();
                        getNext();
                        if(ch.equals(";")){
                            getNext();
                            exp();
                            getNext();
                            if(ch.equals(")")){
                                getNext();
                                stat();
                            } else{
                                System.out.println("for语句缺少符号 ) ");
                                error();
                            }
                        } else{
                            System.out.println("for语句缺少符号 ;（第二个） ");
                            error();
                        }
                    } else {
                        System.out.println("for语句缺少符号 ;（第一个） ");
                        error();
                    }
                } else{
                    System.out.println("for语句缺少符号 ( ");
                    error();
                }
            } else{
                System.out.println("for语句缺少关键字 ID ");
                error();
            }
        }
    }

//    9)read语句
//    <read_stat> => read ID;
//    First(<read_stat>) = { read }
    private void read_stat(){
        if(!isError){
            if(ch.equals("read")){
                getNext();
                if(!ch.equals("ID")){
                    System.out.println("read语句缺少关键字 ID ");
                    error();
                } else{
                    getNext();
                    if(!ch.equals(";")){
                        System.out.println("read缺少符号 ; ");
                        error();
                    }
                }
            } else {
                System.out.println("read语句缺少关键字 read ");
                error();
            }
        }
    }

//    10)write语句
//    <write_stat> => write ID;
//    First(<write_stat>) = { write }
    private void write_stat(){
        if(!isError){
            if(ch.equals("write")){
                getNext();
                exp();
                getNext();
                if(!ch.equals(";")){
                    System.out.println("write语句缺少关键字 ;");
                    error();
                }
            } else {
                System.out.println("write语句缺少关键字 write ");
                error();
            }
        }
    }

//    11)复合语句
//    <com_list> => { <stat_list> }
//    First(<com_list>) = { { }
    private void com_list(){
        if(!isError){
            if(ch.equals("{")){
                getNext();
                stat_list();
                getNext();
                if(!ch.equals("}")){
                    System.out.println("复合语句缺少符号 } ");
                    error();
                }
            } else {
                System.out.println("表达式缺少符号 { ");
                error();
            }
        }
    }

//    12)表达式语句
//    <exp_stat> => <exp>; | ;
//    First( <exp>; ) = { ID, NUM, ( }
//    First(;) = { ; }
    private void exp_stat(){
        String[] first = { "(", "ID", "NUM" };
        if(!isError){
            if(belong(ch,first)){
                exp();
                getNext();
                if(!ch.equals(";")){
                    System.out.println("表达式缺少符号 ; ");
                    error();
                }
            } else if(!ch.equals(";")){
                System.out.println("表达式缺少符号 ; ");
                error();
            }
        }
    }

//    13)表达式
//    <exp> => ID = <bool_exp> | <bool_exp>
//    first(ID = <bool_exp>) = { ID }
//    first(<bool_exp>) = { ID, NUM, ( }
    private void exp (){
        String[] first = { "(", "ID", "NUM" };
        if(!isError){
            if(ch.equals("ID") && stack.getTop().equals("=")){
                getNext();
                getNext();
                bool_exp();
            } else if(belong(ch,first)){
                bool_exp();
            } else{
                System.out.println("表达式出错,表达式开头应为ID,NUM或(");
                error();
            }
        }
    }

//    14)布尔表达式
//    <bool_exp> => <add_exp> <bool_exp1>
//    first(<add_exp><bool_exp1>) = { ID, NUM, ( }
    private void bool_exp(){
        String[] first = { "(", "ID", "NUM" };
        if(!isError){
            if(belong(ch,first)){
                add_exp();
                getNext();
                bool_exp1();
            } else{
                System.out.println("布尔表达式缺少符号: ( ID NUM");
                error();
            }
        }
    }

//    14.1)bool_exp1
//    <bool_exp1> => (>|<|>=|<=|==|!=) <add_exp ><bool_exp1> | E
//    First< (>|<|>=|<=|==|!=) <add_exp > > = { >, <, >=, <=, ==, != }
//    Follow(<bool_exp1>) = { ; ) }
    private void bool_exp1(){
        String[] first = { ">", "<", ">=", "<=", "==", "!="};
        if(!isError){
            if(belong(ch,first)){
                getNext();
                add_exp();
                getNext();
                bool_exp1();
            } else{
                push();
                return;
            }
        } else {
            System.out.println("布尔表达式出错");
            error();
        }
    }


//    15)加法表达式
//    <add_exp> => <term> <add_exp1>
//    First(<term><add_exp1>) = { (, ID, NUM }
    private void add_exp(){
        String[] first = { "(", "ID", "NUM" };
        if(!isError){
            if(belong(ch,first)){
                term();
                getNext();
                add_exp1();
            } else {
                push();
                error();
            }
        }
    }

//    15.1)add_exp1
//    <add_exp1> => +<term><add_exp1> | -<term><add_exp1> | E
//    First<(+|-)<add_exp1>> = { +, - }
//    Follow(<add_exp1>) = { ;, ), >, <, >=, <=, ==, != }
    private void add_exp1(){
        String[] follow = { ";", ")", ">", "<", ">=", "<=", "==", "!="};
        if(!isError){
            if(ch.equals("+") || ch.equals("-")){
                getNext();
                term();
                getNext();
                add_exp1();
            } else{
                push();
                return;
            }

        }
    }

//    16)乘法表达式
//    <term> = <factor><term1>
//    First(<factor><term1>) = { (, ID, NUM }
    private void term(){
        String[] first = { "(", "ID", "NUM" };
        if(!isError){
            if(belong(ch,first)){
                factor();
                getNext();
                term1();
            } else{
                System.out.println("乘法表达式缺少符号: （ ID NUM");
                error();
            }
        }
    }

//    16.1)term1
//    <term1> => (*|/)<factor><term1> | E
//    First((*|/)<factor><term1>) = { *, / }
//    Follow(<term1>) = { +, -, ;, ), >, <, >=, <=, ==, != }
    private void term1(){
        String[] follow = {"+","-", ";", ")", ">", "<", ">=", "<=", "==", "!="};
        if(!isError){
            if(ch.equals("*")||ch.equals("/")){
                getNext();
                factor();
                getNext();
                term1();
            } else {
                push();
                return;
            }
        }
    }

//    17)最小因子
//    <factor> => ( <exp> ) | ID | NUM
//    First( (<exp>) ) = { ( }
//    First( ID ) = { ID }
//    First( NUM ) = {NUM}
    private void factor(){
        if(!isError){
            if(ch.equals("(")){
                getNext();
                exp();
                getNext();
                if(!ch.equals(")")){
                    System.out.println("右括号不匹配");
                    error();
                }
            }
        } else if(!ch.equals("ID")&&!ch.equals("NUM")){
            System.out.println("最小因子出错，无有效ID或NUM");
            error();
        }
    }



    private void error(){
        System.out.println("第 "+line +" 行"+ch+"  出错");
        isError = true;
    }

    public static Boolean belong(String ch,String[] str){
        for(String s : str)
            if(ch.equals(s))
                return true;
        return false;
    }

    public void getNext(){
        line++;
        System.out.println("line:\t"+line+"\tout\t"+ch);
        if(stack.getTop() != null)
            ch = stack.pop();
        else {
            System.out.println("文件已到末尾");
            ch = "EOF";
        }
    }

    public void push(){
        stack.push(ch);
        line--;
        System.out.println("line:\t"+line+"\tpush\t"+ch);
    }

}


