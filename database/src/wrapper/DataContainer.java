package wrapper;

/**
 * author Albert Gevorgyan
 * @version 1.0
 */
public class DataContainer{

    private Object[] data;
    private int length;

    public DataContainer(String... data){
        this.data = data;
        this.length = data.length;
    }
    
    public int length(){return this.length;}

    public Object valAt(int index){return this.data[index];}
    
    public DataContainer(Object... data){
        this.data = data;
        this.length = data.length;
    }

    public StringBuilder toStringBuilder(char delimitor){
        StringBuilder result = new StringBuilder();
        for(Object value : this.data){
            result.append(delimitor).append(value).append(delimitor).append(", ");
        }

        result.deleteCharAt(result.length()-1);
        result.deleteCharAt(result.length()-1);
        return result;
    }

    public StringBuilder toConditionalColumnSet(){
        StringBuilder result = new StringBuilder();
        for(Object value : this.data){
            result.append(value).append("=,");
        }

        result.deleteCharAt(result.length()-1);
        return result;
    }

    public StringBuilder toCommaSeparatedSet(){
        StringBuilder result = new StringBuilder();
        for(Object value : this.data){
            result.append(value).append(", ");
        }

        result.deleteCharAt(result.length()-1);
        result.deleteCharAt(result.length()-1);
        return result;
    }
}