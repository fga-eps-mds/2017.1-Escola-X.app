package helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.Person;

public class JSONParser {

    private static String url = "http://api.androidhive.info/contacts/";

    public List<Person> getDataOnHeroku () {

        HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();
        String jsonStr = httpHandlerHelper.makeServiceCall(url);
        List<Person> personList = new ArrayList<Person>();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                //NOME DO JSON
                JSONArray jsonArray = jsonObj.getJSONArray("contacts");

                for (int aux = 0; aux < jsonArray.length(); aux++) {
                    JSONObject datas = jsonArray.getJSONObject(aux);

                    Person person = new Person();

                    person.setNameAlumn("nameAlumn");

                    personList.add(person);
                }
            } catch (final JSONException e) {

            }
        }
        return personList;
    }
}
