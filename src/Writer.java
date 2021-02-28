import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer implements Writer_I {

    private final int MAX_ENTRIES_OUT;
    private StringBuilder targetStr = new StringBuilder();
    private final BufferedWriter writer;
    private int countWrittenSymbols;

    Writer(String file_name) throws IOException {
        MAX_ENTRIES_OUT = 1000;
        FileWriter fileWriter = new FileWriter(file_name);
        writer = new BufferedWriter(fileWriter);
    }

    Writer(String file_name, int _size) throws IOException {
        MAX_ENTRIES_OUT = _size;
        FileWriter fileWriter = new FileWriter(file_name);
        writer = new BufferedWriter(fileWriter);
    }

    @Override
    public void write(String str){
        countWrittenSymbols = 0;
        if(countWrittenSymbols == MAX_ENTRIES_OUT) {
            writeAndClearBuilder();
            countWrittenSymbols = 0;
        }

        targetStr.append(str).append("\n");
        countWrittenSymbols+=1;

    }

    public void atExit(){
        if(countWrittenSymbols!=0){
            writeAndClearBuilder();
        }
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
