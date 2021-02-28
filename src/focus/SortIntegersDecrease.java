package focus;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SortIntegersDecrease extends SortIntegers {

    SortIntegersDecrease(ArrayList<String> in, String out) throws FileNotFoundException, IOException {
        super(in, out);
    }

    @Override
    public Integer getNext() throws OrderViolationException, IllegalArgumentException {
        Integer maxInt = Integer.MIN_VALUE;
        Reader_I reader = null;
        for (Map.Entry<Reader_I, Integer> entry: map.entrySet()) {
            if(entry.getValue() > maxInt){
                maxInt = entry.getValue();
                reader = entry.getKey();
            }
        }

        try{
            assert reader != null;
            map.replace(reader, Integer.valueOf(reader.getNext()));
        }
        catch (EOFException e){
            map.remove(reader);
        }

        if(lastWrittenInt!=null && maxInt > lastWrittenInt) throw new OrderViolationException();

        return maxInt;
    }
}
