package SCHEMAS;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class DatabaseDirectory {
    public static String getDatabaseDirectory(){return Paths.get("database\\production\\database\\SCHEMAS").toAbsolutePath().toString();}

    public static Path getDatabasePath(){return Paths.get("database\\production\\database\\SCHEMAS").toAbsolutePath();}
}
