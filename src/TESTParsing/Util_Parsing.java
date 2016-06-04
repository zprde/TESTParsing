package TESTParsing;

import util.Stack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by zprde on 2016/5/10.
 */
public class Util_Parsing {

    public static Stack getStack(String filName){
        Stack stack = null;
        File file = new File(filName);
        if(!file.exists()){
            System.err.println("文件不存在!!");
            return null;
        }
        if(!file.canRead()){
            System.err.println("文件不可读！！");
            return null;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String eache;
            while((eache = br.readLine()) != null){
                int index = eache.indexOf('\t');
                if(index == -1)
                    index = eache.length();
                if(stack == null)
                    stack = new Stack(eache.substring(0, index));
                else
                    stack.addBottom(eache.substring(0, index));
            }
            br.close();
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("读取文件出错！");
            return null;
        }
        return stack;
    }
}
