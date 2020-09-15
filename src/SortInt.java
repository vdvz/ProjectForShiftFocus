import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SortInt extends Sort{

    private MODE sortMode = MODE.INCREASE;

    @Override
    public void setSortMode(MODE currentMode) {
        this.sortMode = currentMode;
    }

    private final Map<IntegerContainer, Integer> map = new ConcurrentHashMap<>();

    private static final int MAX_OUT_INT = 1000;
    StringBuilder targetStr = new StringBuilder();
    FileWriter fileWriter;
    BufferedWriter writer;

    SortInt(String out, ArrayList<String> in) throws FileNotFoundException, IOException {

        fileWriter = new FileWriter(out);
        writer = new BufferedWriter(fileWriter);

        for (String s : in) {
            IntegerContainer ar = new IntegerContainer(s);
            try {
                map.put(ar, ar.getNext());
            }
            catch(EOFException ignored){
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sort(){
        int countOutInt = 0;
        Integer lastWrittenInt = Integer.MIN_VALUE;

        while(!map.isEmpty()){

            if(countOutInt == MAX_OUT_INT) {
                writeAndClearBuilder();
                countOutInt = 0;
            }

            Integer sub = getMinAndUpdate();
            if(sub < lastWrittenInt) continue;
            lastWrittenInt = sub;
            targetStr.append(lastWrittenInt).append("\n");
            countOutInt+=1;
        }

        writeAndClearBuilder();
        closeBuilder();
    }

    private Integer getMinAndUpdate(){
        Integer minInt = Integer.MAX_VALUE;
        IntegerContainer container = null;
        for (Map.Entry<IntegerContainer, Integer> entry: map.entrySet()) {
            if(entry.getValue() < minInt){
                minInt = entry.getValue();
                container = entry.getKey();
            }
        }

        assert container != null;

        try {
            map.replace(container, container.getNext());
        }
        catch (EOFException e){
            map.remove(container);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return minInt;
    }

    private void closeBuilder(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAndClearBuilder(){
        try {
            writer.write(targetStr.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        targetStr = new StringBuilder();
    }

}
