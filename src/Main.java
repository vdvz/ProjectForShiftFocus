import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Sort sort = null;
        Sort.MODE MODE = null;
        Sort.TYPE TYPE = null;
        String out = null;
        ArrayList<String> in = new ArrayList<>();
        for (String s: args) {
            switch (s) {
                case "-a" -> MODE = Sort.MODE.DECREASE;
                case "-d" -> MODE = Sort.MODE.INCREASE;
                case "-s" -> TYPE = Sort.TYPE.STRINGS;
                case "-i" -> TYPE = Sort.TYPE.INTEGERS;
                default -> {
                    if (out == null) {
                        out = s;
                        break;
                    }
                    in.add(s);
                }
            }
        }

        if(out==null) throw new IllegalArgumentException();
        if(in.size()<1) throw new IllegalArgumentException();
        if(TYPE == Sort.TYPE.INTEGERS){
            try {
                sort = new SortInt(out, in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(TYPE == Sort.TYPE.STRINGS){
            try {
                sort = new SortStr(out, in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        assert sort!=null;
        if(MODE!=null) sort.setSortMode(MODE);

        sort.sort();

    }

}
