package tk.pminer.emailgenerator;

/**
 * Created by PMiner on 25/7/2017.
 * EmailGenerator
 */

public class Model {
    private String name;
    private int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    Model(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

}
