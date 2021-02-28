package focus;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static Sort sort = null;

    static void parseArguments(String ... args) throws IllegalArgumentException, IOException {
        MODE mode = MODE.INCREASE;
        TYPE type = null;
        String out = null;
        ArrayList<String> in = new ArrayList<>();
        for (String s: args) {
            switch (s) {
                case "-a" -> mode = MODE.INCREASE;
                case "-d" -> mode = MODE.DECREASE;
                case "-s" -> type = TYPE.STRINGS;
                case "-i" -> type = TYPE.INTEGERS;
                default -> {
                    if (out == null) {
                        out = s;
                    } else in.add(s);
                }
            }
        }

        if(out==null) throw new IllegalArgumentException();
        if(in.size()<1) throw new IllegalArgumentException();
        if(TYPE.INTEGERS.equals(type)){
            if(mode.equals(MODE.INCREASE)){
                sort = new SortIntegersIncrease(in, out);
            }
            if(mode.equals(MODE.DECREASE)){
                sort = new SortIntegersDecrease(in, out);
            }
        }else if(TYPE.STRINGS.equals(type)){
            if(mode.equals(MODE.INCREASE)){
                sort = new SortStringsIncrease(in, out);
            }
            if(mode.equals(MODE.DECREASE)){
                sort = new SortStringsDecrease(in, out);
            }
        } else throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        try {
            parseArguments(args);
            sort.sort();
        }catch (IllegalArgumentException e){
            System.out.println("Введены неверные аргументы");
            e.printStackTrace();
        }catch (FileNotFoundException e){
            System.out.println("Такого файла не существует");
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
