package focus;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SortStringsIncrease extends SortStrings {

    SortStringsIncrease(ArrayList<String> in, String out) throws IOException {
        super(in, out);
    }

    @Override
    public String getNext() throws OrderViolationException {
        int minDeviation = Integer.MAX_VALUE;
        Reader_I reader = null;
        String minStr = "";
        if(lastWrittenStr!=null) minStr = lastWrittenStr;

        for (Map.Entry<Reader_I, String> entry: map.entrySet()) {
            int currentDeviation = entry.getValue().compareTo(minStr);
            if (currentDeviation < minDeviation) {
                reader = entry.getKey();
                minStr = entry.getValue();
                minDeviation = currentDeviation;
            }
        }

        try {
            assert reader != null;
            map.replace(reader, reader.getNext());
        }
        catch (EOFException e){
            map.remove(reader);
        }

        if(lastWrittenStr!=null && minStr.compareTo(lastWrittenStr) < 0) throw new OrderViolationException();

        return minStr;
    }

}