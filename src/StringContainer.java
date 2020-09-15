import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StringContainer {

    FileReader fileReader;
    BufferedReader reader;

    private static final int MAX_STRINGS_IN_ARRAY = 1000;
    private final ConcurrentLinkedQueue<String> list;

    StringContainer(String file) throws FileNotFoundException {
        fileReader = new FileReader(file);
        reader = new BufferedReader(fileReader);
        list = new ConcurrentLinkedQueue<>();
    }

    private void fillArray() throws IOException {
        while(list.size() != MAX_STRINGS_IN_ARRAY){
            try {
                list.add(read());
            }catch (EOFException e){
                break;
            }catch (NumberFormatException ignored){
            }
        }
    }

    public String getNext() throws IOException {
        if(list.isEmpty()) fillArray();
        String sub = list.poll();
        if(sub == null) throw new EOFException();
        return sub;
    }

    private String read() throws IOException {
        String line = reader.readLine();
        if(line==null){
            throw new EOFException();
        }
        return line;
    }

}

