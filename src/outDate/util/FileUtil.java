package outDate.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtil {
	private String[] fileString = new String[1000000];
	public int top=0,line=0,n=0;
	
	public FileUtil(String fileName) {
		// TODO Auto-generated constructor stub
		File file = new File(fileName);
		if(!file.canRead()){
			System.out.println("文件不可读！");
		} else{
			loadFile(file);
		}
	}
	
	private void loadFile(File file){
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String eache;
			while((eache = bufferedReader.readLine()) != null){
				int index = eache.indexOf('\t');
				if(index == -1)
					index = eache.length();
				push(eache.substring(0, index));
			}
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTop(){
		if(top <n)
			return fileString[top];
		else
			return null;
	}
	
	public String pop(){
		if(top <n){
			line++;
			return fileString[top++];
		}
		else
			return null;
	}
	
	public Boolean push(String string){
		if(n >= fileString.length)
			return false;
		else {
			fileString[n++] = string;
			return true;
		}
	}
	
}
