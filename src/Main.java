import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Sort sort = null;
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
                        break;
                    }
                    in.add(s);
                }
            }
        }

        if(out==null) throw new IllegalArgumentException();
        if(in.size()<1) throw new IllegalArgumentException();
        if(type.equals(TYPE.INTEGERS)){
            try {
                if(mode.equals(MODE.INCREASE)){
                    sort = new SortIntegersIncrease(in, out);
                }else{
                    sort = new SortIntegersDecrease(in, out);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals(TYPE.STRINGS)){
            try {
                if(mode.equals(MODE.INCREASE)){
                    sort = new SortStringsIncrease(in, out);
                }else{
                    sort = new SortStringsDecrease(in, out);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        assert sort != null;
        sort.sort();

    }

}
