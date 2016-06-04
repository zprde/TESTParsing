package util;

/**
 * Created by zprde on 2016/5/10.
 */
public abstract class DataStructure<T> {

    private int size;
    private Class c;
    private Boolean isStatic = true;
    public int s;



    public Boolean canAdd(T t){
        if(size != 0 && s>=size){
            System.err.println("空间已满，无法添加！");
            return false;
        }
        else if(!t.getClass().equals(c)){
            System.err.println("数据：\""+t+"\"("+t.getClass()+")  与初始节点类型"+c+"不匹配！！");
            if(isStatic){
                System.err.println("  添加失败");
                return false;
            }
        }
        return true;
    }

    public void setClass(Class c){
        this.c = c;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setStrict(Boolean b){
        isStatic = b;
    }
    public Boolean getStrict(){
        return isStatic;
    }

    public abstract T[] getAllData();
}
