import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SortIntegersIncrease extends SortIntegers {


    SortIntegersIncrease(ArrayList<String> in, String out) throws FileNotFoundException, IOException {
        super(in, out);
    }

    @Override
    public Integer getNext() throws OrderViolationException {
        Integer minInt = Integer.MAX_VALUE;
        Reader_I reader = null;
        for (Map.Entry<Reader_I, Integer> entry: map.entrySet()) {
            if(entry.getValue() < minInt){
                minInt = entry.getValue();
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

        if(lastWrittenInt!=null && minInt < lastWrittenInt) throw new OrderViolationException();

        return minInt;
    }
}