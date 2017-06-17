package helper;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Parent;

public class JSONParserHelper {

    public List<Parent> setParents() {

        String url = "http://api.androidhive.info/contacts/";
        HttpHandlerHelper sh = new HttpHandlerHelper();
        String jsonStr = sh.makeServiceCall(url);
        List<Parent> parentList = new ArrayList<Parent>();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray parents = jsonObj.getJSONArray("parents");

                for (int aux = 0; aux < parents.length(); aux++) {
                    JSONObject parentsJSONObject = parents.getJSONObject(aux);

                    Parent parent = new Parent();

                    parent.setIdParent(Integer.parseInt(parentsJSONObject.getString("id")));
                    parent.setName(parentsJSONObject.getString("name"));
                    parent.setPhone(parentsJSONObject.getString("phone"));

                    parentList.add(parent);
                }
            } catch (final JSONException e) {
                Log.e("", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("", "Couldn't get json from server.");
        }

        return parentList;
    }
}
