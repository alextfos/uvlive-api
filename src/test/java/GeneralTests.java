import com.google.gson.Gson;
import es.uvlive.controllers.form.GetMessagesForm;

import java.util.HashMap;


public class GeneralTests {
    private static HashMap<Integer, String> map = new HashMap<>();

    public static void main(String args[]) {
        map.put(1,"asdf");
        GetMessagesForm form = new GetMessagesForm();

        form.setIdConversation(1);
        form.setTimestamp(1234556);

        String json = new Gson().toJson(form);

        System.out.println(json);
    }
}
