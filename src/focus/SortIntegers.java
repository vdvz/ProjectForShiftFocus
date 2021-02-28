package focus;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class SortIntegers extends Sort{

    final Map<Reader_I, Integer> map = new HashMap<>();
    private final Writer_I writer;
    Integer lastWrittenInt = null;

    SortIntegers(ArrayList<String> in, String out) throws IOException {

        writer = new Writer(out);

        for (String s : in) {
            Reader_I reader = new Reader(s);
            try {
                map.put(reader, Integer.valueOf(reader.getNext()));
            } catch (IllegalArgumentException e){
                while(true){
                    try{
                        map.put(reader, Integer.valueOf(reader.getNext()));
                        break;
                    }catch(IllegalArgumentException ignored){
                    }
                }
            }catch (EOFException ignored){
            }
        }
    }

    @Override
    public void sort(){
        while(!map.isEmpty()){
            try{
                Integer toWrite = getNext();
                writer.write(String.valueOf(toWrite));
                lastWrittenInt = toWrite;
            }
            catch (IllegalArgumentException | OrderViolationException ignored){
            }
        }
        writer.atExit();
    }

    public abstract Integer getNext() throws OrderViolationException;

}
