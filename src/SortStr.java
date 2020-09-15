import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SortStr extends Sort{

    private MODE sortMode = MODE.INCREASE;

    @Override
    public void setSortMode(MODE currentMode) {
        this.sortMode = currentMode;
    }

    private static final int MAX_OUT_STR = 1000;
    StringBuilder targetStr = new StringBuilder();
    FileWriter fileWriter;
    BufferedWriter writer;

    private final Map<StringContainer, String> map = new ConcurrentHashMap<>();

    SortStr(String out, ArrayList<String> in) throws FileNotFoundException, IOException {
        fileWriter = new FileWriter(out);
        writer = new BufferedWriter(fileWriter);

        for (String s : in) {
            StringContainer ar = new StringContainer(s);
            try {
                map.put(ar, ar.getNext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sort(){
        int countOutInt = 0;
        String lastWrittenStr = "";
        while(!map.isEmpty()){
            if(countOutInt == MAX_OUT_STR) {
                writeAndClearBuilder();
                countOutInt = 0;
            }

            String value = getMinAndUpdate();
            if(value.compareTo(lastWrittenStr) < 0) continue;
            lastWrittenStr = value;
            System.out.println(lastWrittenStr);
            targetStr.append(lastWrittenStr).append("\n");
            countOutInt+=1;
        }

        writeAndClearBuilder();
        closeBuilder();
    }

    private String getMinAndUpdate(){
        int minDeviation = Integer.MAX_VALUE;
        StringContainer container = null;
        String returningStr = "";

        for (Map.Entry<StringContainer, String> entry: map.entrySet()) {
            int currentDeviation = entry.getValue().compareTo(returningStr);
            if (currentDeviation < minDeviation) {
                container = entry.getKey();
                returningStr = entry.getValue();
                minDeviation = currentDeviation;
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

        return returningStr;
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
