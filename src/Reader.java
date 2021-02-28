import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Reader implements Reader_I{

    private final int MAX_ENTRIES_IN;
    private final ConcurrentLinkedQueue<String> list;
    private final BufferedReader reader;

    Reader(String file_name) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file_name));
        list = new ConcurrentLinkedQueue<>();
        MAX_ENTRIES_IN = 1000;
    }

    Reader(String file_name, int _size) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file_name));
        list = new ConcurrentLinkedQueue<>();
        MAX_ENTRIES_IN = _size;
    }

    private void fillArray() throws IOException {
        while(list.size() != MAX_ENTRIES_IN){
            try {
                list.add(read());
            }catch (EOFException e){
                break;
            }
        }
    }

    @Override
    public String getNext() throws EOFException {
        if(list.isEmpty()) {
            try {
                fillArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
