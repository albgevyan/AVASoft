package wrapper;

/** Stores the name, datatype and maximum length of a column.
 * @author Albert Gevorgyan
 * @version 1.0
 */

public class DatabaseObjectCharacteristics{
    public String name;
    public String dtype;
    public int limit;

    public DatabaseObjectCharacteristics(String name, String dtype, int limit){
        this.name = name;
        this.dtype = dtype;
        this.limit = limit;
    }

    public StringBuilder toStringBuilder(){
        StringBuilder result = new StringBuilder();
        return result.append(this.name).append(' ').append(this.dtype).append('(').append(this.limit).append(')');
    }
}