package communicationautomation;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class WriteToFileHandlerTest {
    private String folderPath;
    private String file;
    private WriteToFileHandler writeHandler;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        file = "file content";
        folderPath = folder.newFolder().getPath();
        writeHandler = new WriteToFileHandler(folderPath);
    }

    @Test
    public void deliver() {
        writeHandler.deliver(file, 1, "type", "email");
        String readPath = folderPath + "/type/email-1.txt";
        String readData = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(readPath))) {
            readData = bufferedReader.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(file, readData);

        writeHandler.deliver(file, 2, "type", "email");
        readPath = folderPath + "/type/email-2.txt";
        readData = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(readPath))) {
            readData = bufferedReader.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(file, readData);
    }

    @Test
    public void getOutputDir() {
        assertEquals(folderPath, writeHandler.getOutputDir());
    }

    @Test
    public void testEquals() {
        assertFalse(writeHandler.equals(null));
        WriteToFileHandler same = new WriteToFileHandler(folderPath);
        assertTrue(writeHandler.equals(writeHandler));
        assertTrue(writeHandler.equals(same));
        assertFalse(writeHandler.equals("write handler"));
        WriteToFileHandler different = new WriteToFileHandler(folderPath + "/fake");
        assertFalse(writeHandler.equals(different));
    }

    @Test
    public void testHashCode() {
        WriteToFileHandler same = new WriteToFileHandler(folderPath);
        assertEquals(writeHandler.hashCode(), same.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("WriteToFileHandler{" +
            "outputPath='" + folderPath + '\'' +
            '}', writeHandler.toString());
    }
}