package core;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import exceptions.InvalidInputFormatException;

import java.io.IOException;

public class DBUserAdapter  extends TypeAdapter<DBUser>{

    public DBUser read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return null;
        }

        reader.beginObject();
        reader.nextName();
        String username = reader.nextString();
        reader.nextName();
        String password = reader.nextString();
        reader.nextName();
        DBUser.Privilege[] privileges = DBUser.privilegesFromString(reader.nextString());
        reader.endObject();

        try {
            return new DBUser(username, password, privileges);
        }
        catch (InvalidInputFormatException e){
            System.out.println("Warning the program encountered an error; the user may be corrupt.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void write(JsonWriter writer, DBUser user) throws IOException{
        if (user == null) {
            writer.nullValue();
            return;
        }
        writer.beginObject();
        writer.name("username").value(user.username);
        writer.name("password").value(user.password);
        writer.name("privileges").value(user.privilegesToString());
        writer.endObject();
    }
}
