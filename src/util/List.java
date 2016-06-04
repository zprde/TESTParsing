package util;

/**
 * Created by adrian on 16-5-9.
 */
public class List<T> extends DataStructure {

    private Node<T> list;
    public List(T t){
        setClass(t.getClass());
        addNode(t);
    }

    public T getNode(){
        T data = null;
        if(list != null){
            data = list.data;
            list = list.next;
            s--;
        }
        return data;
    }




    public void addNode(T t){
        if(canAdd(t)){
            if(list == null)
                list = new Node(t);
            else{
                Node node = list;
                while(node.next !=null)
                    node = node.next;
                node.next = new Node(t);
            }
            s++;
        }
    }

    public void addTopNode(T t){
        if(canAdd(t)){
            if(list == null)
                addNode(t);
            else{
                Node node = list;
                list = new Node(t);
                list.next = node;
            }
            s++;
        }
    }

    @Override
    public T[] getAllData() {
        T[] t = (T[])new Object[1000];
        System.out.println(t.length);
        return t;
    }
}
