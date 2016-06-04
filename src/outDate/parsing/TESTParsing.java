package outDate.parsing;

import outDate.util.FileUtil;

public class TESTParsing {
	
	public static FileUtil fileUtil;
	
//	1)程序
//	<pro> => { <dec_list><sta_liat> }
	public static void pro(String ch){
		if(ch.equals("{")){
			dec_list(fileUtil.pop());
			sta_list(fileUtil.pop());
			if(!(ch=fileUtil.pop()).equals("}")){
				System.out.println("程序右话括号缺失");
				error(ch);
			}
		} else{
			System.out.println("程序无左花括号");
			error(ch);
		}
	}
	
//	2)声明列表
//	<dec_list> => <dec_stat><dec_list>|E
	public static void dec_list(String ch){
		String[] follow = {"if","for","while","read","write","{","}","(",";","ID","NUM"};
		if(ch.equals("int")){
			dec_stat(ch);
			dec_list(fileUtil.pop());
		} if(belong(ch, follow)){
//			fileUtil.push(ch);
			return;
		}
		else{
			error(ch);
			System.out.println("�����б������int");
		}
	}
	
//	3)声明语句
//	<dec_stat> => int ID;
	public static void dec_stat(String ch){
		if(ch.equals("int")&&(ch=fileUtil.pop()).equals("ID")&&(ch=fileUtil.pop()).equals(";"))
			return;
		else{
			error(ch);
			System.out.println("声明语句int缺失");
		}
	}
	
//	4)�������б�
//	<sta_liat> => <stat><sta_list>
	public static void sta_list(String ch){
		String[] first = {"if","for","while","read","write","{","(",";","ID","NUM"};
		if(belong(ch, first)){
			stat(ch);
			sta_list(fileUtil.pop());
		} else if(ch.equals("}"))
			return;
		else
			error(ch);
	}
	
//	5)���������
//	<stat> => <if_stat>|<for_stat>|<while_stat>|<write_stat>|<read_stat>|<cpm_stat>|<exp_stat>
	public static void stat(String ch){
		String[] first = {";","ID","NUM","("};
		if(ch.equals("if"))
			if_stat(fileUtil.pop());
		else if(ch.equals("for"))
			for_stat(fileUtil.pop());
		else if(ch.equals("while"))
			while_stat(fileUtil.pop());
		else if(ch.equals("write"))
			write_stat(fileUtil.pop());
		else if(ch.equals("read"))
			read_stat(fileUtil.pop());
		else if(ch.equals("{"))
			com_stat(ch);
		else if(belong(ch, first))
			exp_stat(ch);
		else
			error(ch);
	}
	
//	6)if���
//	if => if(<exp>)<stat>[else<stat>]
	public static void if_stat(String ch){
		if(ch.equals("(")){
			exp(fileUtil.pop());
			if((ch = fileUtil.pop()).equals(")")){
				stat(fileUtil.pop());
				if(fileUtil.getTop().equals("else")){
					fileUtil.pop();
					stat(fileUtil.pop());
				} else
					return;
			}
			else
				error(ch);
		} else{
			error(ch);
		}
	}
	
//	7)while���
//	<while_stat> => while (<exp>) <stat>
	public static void while_stat(String ch){
		if(ch.equals("(")){
			exp(fileUtil.pop());
			if((ch = fileUtil.pop()).equals(")")){
				stat(fileUtil.pop());
			}
			else
				error(ch);
		} else
			error(ch);
	}
	
//	8)for���
//	<for_stat> => for (<exp>;<exp>;<exp>)<stat>
	public static void for_stat(String ch){
		if(ch.equals("(")){
			exp(fileUtil.pop());
			if((ch = fileUtil.pop()).equals(";")){
				exp(fileUtil.pop());
				if((ch = fileUtil.pop()).equals(";")){
					exp(fileUtil.pop());
					if((ch = fileUtil.pop()).equals("")){
						stat(fileUtil.pop());
					} else
						error(ch);
				} else 
					error(ch);
			} else 
				error(ch);
		} else
			error(ch);
	}
	
//	9)write���
//	<write_stat> => write <exp>
	public static void write_stat(String ch){
		exp(fileUtil.pop());
	}
	
//	10)read���
	public static void read_stat(String ch){
		exp(fileUtil.pop());
	}
	
//	11)�������
//	<com_stat> => {<sta_list>}
	public static void com_stat(String ch){
		if(ch.equals("{")){
			sta_list(fileUtil.pop());
			if(!(ch = fileUtil.pop()).equals("}"))
				error(ch);
		} else
			error(ch);
	}
	
//	12)������
//	<exp_stat> => <exp>;|;
	public static void exp_stat(String ch){
		String[] first = {"ID","NUM","("};
		if(belong(ch, first)){
			exp(ch);
			if(!(ch = fileUtil.pop()).equals(";"))
				error(ch);
		} else if(!ch.equals(";"))
			error(ch);
	}
	
//	13)��ֵ���
//	<exp> => ID=<bool_exp>|<bool_exp>
	public static void exp(String ch){
		String[] first = {"ID","NUM","("};
		if(ch.equals("ID")&&fileUtil.getTop().equals("=")){
			fileUtil.pop();
			bool_exp(fileUtil.pop());
		} else if(belong(ch, first))
			bool_exp(ch);
		else
			error(ch);
	}
	
//	14)�ж����
//	<bool_exp> => <add_exp><bool_exp1>
	public static void bool_exp(String ch){
		String[] first = {"ID","NUM","("};
		if(belong(ch, first)){
			add_exp(ch);
			bool_exp1(fileUtil.pop());
		} else
			error(ch);
	}
	
//	14.1)�ж����(��)
//	<bool_exp1> => E|( >|<|>=|<=|==|!= ) <add_exp>
	public static void bool_exp1(String ch){
		String[] first = {">","<",">=","<=","==","!="};
		if(belong(ch, first))
			add_exp(fileUtil.pop());
		else if(ch.equals(";"))
			return;
		else
			error(ch);
	}
	
//	15)�ӷ��������
//	add_exp => <term><add_exp1>
	public static void add_exp(String ch){
		String[] first = {"ID","NUM","("};
		if(belong(ch, first)){
			term(ch);
			add_exp(fileUtil.pop());
		} else
			error(ch);
	}
	
//	15.1)�ӷ��������(��)
	public static void add_exp1(String ch){
		String[] follow = {">","<",">=","<=","==","!=",";",")"};
		if(ch.equals("+") || ch.equals("-")){
			factor(fileUtil.pop());
			add_exp1(fileUtil.pop());
		} else if(belong(ch, follow))
			return;
		else
			error(ch);
	}
	
//	16)�˷��������
//	<term> => <factor><term1>
	public static void term(String ch){
		String[] first = {"ID","NUM","("};
		if(belong(ch, first)){
			factor(ch);
			term1(fileUtil.pop());
		} else
			error(ch);
	}
	
//	16.1)�˷��������(��)
//	<term1> => *<factor><term1> | /<factor> |E
	public static void term1(String ch){
		String[] follow = {">","<",">=","<=","==","!=",";",")","+","-"};
		if(ch.equals("*") || ch.equals("/")){
			factor(ch);
			term1(fileUtil.pop());
		} else if(belong(ch, follow))
			return;
		else
			error(ch);
		
	}
	
//	17)��С����
//	<factor> => (<exp>) | ID | NUM
	public static void factor(String ch){
		if(ch.equals("(")){
			exp(fileUtil.pop());
			if(!(ch = fileUtil.pop()).equals(""))
				error(ch);
		} else if(!ch.equals("ID")&&!ch.equals("NUM"))
			error(ch);
	}
	
//	����
	public static void error(String ch){
		System.out.println("��"+fileUtil.top+"��\tsomething ERROR!!\t"+ch);
	}
	
//	���Ƿ�Ϊfirst����follow��
	public static Boolean belong(String ch,String[] str){
		for(String s : str)
			if(ch.equals(s))
				return true;
		return false;
	}
	
}
