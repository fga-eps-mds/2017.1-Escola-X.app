package helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Alumn;
import model.Person;

public class JSONParser {

    public static ArrayList<Person> persons = new ArrayList<>();

    public static ArrayList<Person> parseArrayFeed(JSONArray jsonArray) {

        JSONObject obj = null;
        persons.clear();
        Alumn alumn;
        try {

            for(int aux = 0;aux<jsonArray.length();aux++) {

                obj = jsonArray.getJSONObject(aux);
                Person person = new Person();
                person.setNameAlumn(obj.getString("name"));
                person.setPhone(obj.getString("companyName"));
                person.setDescription_strike("operatingSystem");
                /*mobile.setName(obj.getString("name"));
                mobile.setCompanyName(obj.getString("companyName"));
                mobile.setOperatingSystem(obj.getString("operatingSystem"));
                mobile.setProcessor(obj.getString("processor"));
                mobile.setBackCamera(obj.getString("backCamera"));
                mobile.setFrontCamera(obj.getString("frontCamera"));
                mobile.setRam(obj.getString("ram"));
                mobile.setRom(obj.getString("rom"));
                mobile.setScreenSize(obj.getString("screenSize"));
                mobile.setUrl(obj.getString("url"));
                mobile.setBattery(obj.getString("battery"));*/


                persons.add(person);

            }
            return persons;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}
