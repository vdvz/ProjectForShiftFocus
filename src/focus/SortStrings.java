package focus;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class SortStrings extends Sort{

    final Map<Reader_I, String> map = new HashMap<>();
    private final Writer_I writer;

    String lastWrittenStr = null;

    SortStrings(ArrayList<String> in, String out) throws IOException {

        writer = new Writer(out);

        for (String s : in) {
            Reader_I reader = new Reader(s);
            try {
                map.put(reader, reader.getNext());
            }
            catch(EOFException ignored){ }
        }
    }

    @Override
    public void sort(){
        while(!map.isEmpty()){
            try{
                String toWrite = getNext();
                writer.write(toWrite);
                lastWrittenStr = toWrite;
            } catch (OrderViolationException ignored){ }
        }
        writer.atExit();
    }

    public abstract String getNext() throws OrderViolationException;

}
