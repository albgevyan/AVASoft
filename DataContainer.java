/**
 * author Albert Gevorgyan
 * @version 1.0
 */
public class DataContainer{

    private Object[] data;

    public DataContainer(String... data){
        this.data = data;
    }

    public DataContainer(Object... data){
        this.data = data;
    }

    public StringBuilder toStringBuilder(){
        StringBuilder result = new StringBuilder();
        for(Object value : this.data){
            result.append('\'').append(value).append("\', ");
        }

        result.deleteCharAt(result.length()-1);
        result.deleteCharAt(result.length()-1);
        return result;
    }
}