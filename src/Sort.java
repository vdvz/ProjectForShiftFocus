import jdk.jshell.spi.ExecutionControl;

public abstract class Sort {

    enum MODE{
        INCREASE,
        DECREASE
    }

    enum TYPE{
        INTEGERS,
        STRINGS
    }

    void setSortMode(SortInt.MODE currentMode){ }

    void sort(){ }

}
