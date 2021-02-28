package focus;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SortStringsDecrease extends SortStrings{

    SortStringsDecrease(ArrayList<String> in, String out) throws IOException {
        super(in, out);
    }

    @Override
    public String getNext() throws OrderViolationException {
        int maxDeviation = Integer.MIN_VALUE;
        Reader_I reader = null;
        String maxStr;
        if(lastWrittenStr!=null) {
            maxStr = lastWrittenStr;
        } else maxStr = map.values().iterator().next();

        for (Map.Entry<Reader_I, String> entry: map.entrySet()) {
            int currentDeviation = entry.getValue().compareTo(maxStr);
            if (currentDeviation > maxDeviation) {
                reader = entry.getKey();
                maxStr = entry.getValue();
                maxDeviation = currentDeviation;
            }
        }

        try {
            assert reader != null;
            map.replace(reader, reader.getNext());
        }
        catch (EOFException e){
            map.remove(reader);
        }

        if(lastWrittenStr!=null && maxStr.compareTo(lastWrittenStr) > 0) throw new OrderViolationException();

        return maxStr;
    }
}
