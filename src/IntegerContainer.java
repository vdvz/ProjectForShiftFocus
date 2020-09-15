import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IntegerContainer {

    FileReader fileReader;
    BufferedReader reader;

    private static final int MAX_INTEGERS_IN_ARRAY = 1000;
    private final ConcurrentLinkedQueue<Integer> list;

    IntegerContainer(String file) throws FileNotFoundException {
        fileReader = new FileReader(file);
        reader = new BufferedReader(fileReader);
        list = new ConcurrentLinkedQueue<>();
    }

    private void fillArray() throws IOException {
        while(list.size() != MAX_INTEGERS_IN_ARRAY){
            try {
                list.add(read());
            }catch (EOFException e){
                break;
            }catch (NumberFormatException ignored){
            }
        }
    }

    public Integer getNext() throws IOException {
        if(list.isEmpty()) fillArray();
        Integer sub = list.poll();
        if(sub == null) throw new EOFException();
        return sub;
    }

    private int read() throws IOException {
        String line = reader.readLine();
        if(line==null){
            throw new EOFException();
        }
        return Integer.parseInt(line);
    }

}
