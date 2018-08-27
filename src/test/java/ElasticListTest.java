import es.uvlive.eda.ElasticList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alextfos on 25/08/2018.
 */
public class ElasticListTest {

    private final static ArrayList<String> ELEMENTS = new ArrayList<String>() {{
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
        add("6");
        add("7");
        add("8");
        add("9");
        add("10");
    }};

    private static ElasticList<String> elasticList;

    public static void main(String args[]) {
        elasticList = new ElasticList<>(10, 2, new ElasticList.OnFillBufferCallback<String>() {
            @Override
            public void onBufferFilled(List<String> elements) {
                System.out.println(elements.size() + "Elements: " + elements.get(0) + ";" + elements.get(1));
            }
        });
        elasticList.addAll(ELEMENTS);
        elasticList.add("11");
        elasticList.add("12");
        elasticList.add("13");
        elasticList.add("14");

    }


}
