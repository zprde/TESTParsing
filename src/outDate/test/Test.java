package outDate.test;

import outDate.parsing.TESTParsing;
import outDate.util.FileUtil;

public class Test {
	
	
	public static void main(String[] args){
		FileUtil fileUtil = new FileUtil("out.txt");
		TESTParsing.fileUtil = fileUtil;
		TESTParsing.pro(fileUtil.pop());
		
	}
}
