package util;

/**
 * Created by zprde on 2016/5/10.
 */
public class Stack<T> extends DataStructure {
    Node<T> top;

    public Stack(T t){
        setClass(t.getClass());
        push(t);
    }


    public T pop(){
        T data = getTop();
        if(top != null)
            top = top.next;
        return data;
    }

    public void push(T t){
        if(canAdd(t)){
            if(top == null)
                top = new Node(t);
            else {
                Node node = top;
                top = new Node(t);
                top.next = node;
            }
        }
    }

    public void addBottom(T t){
        if(canAdd(t)){
            Node node = top;
            if(node == null)
                node = new Node(t);
            else{
                while(node.next != null)
                    node = node.next;
                node.next = new Node(t);
            }
        }
    }

    public T getTop(){
        T data = null;
        if(top != null)
            data = top.data;
        return data;
    }

    @Override
    public T[] getAllData() {
        T[] t = null;
        return t;
    }
}
