package controller;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import escola_x.escola_x.R;
import helper.JSONParser;
import model.Person;

public class MainActivity extends ListFragment {

    private ArrayList<Person> persons;
    String url = "http://androidtutorialpoint.com/api/MobileJSONArray.json";
    private final String EXTRA_JSON_OBJECT = "mobileObject";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        persons = JSONParser.parseArrayFeed(response);

                        pDialog.hide();
                        MobileAdapter adapter = new MobileAdapter(mMobileList);
                        setListAdapter(adapter);



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(getActivity()).add(jsonArrayReq);


    }

    private class MobileAdapter extends ArrayAdapter<Person> {
        public MobileAdapter(ArrayList<Person> persons) {
            super(getActivity(), 0, persons);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one


            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.category_list_item_1, null);
            }
            Person person = persons.get(position);


            TextView nameTextView =
                    (TextView) convertView.findViewById(R.id.textview_name);
            nameTextView.setText(person.getNameAlumn());

            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(),ParseJSONArrayObject.class);
                    Bundle args = new Bundle();
                    //args.putSerializable(EXTRA_JSON_MOBILE_OBJECT, mMobileList.get(position));
                    i.putExtra(EXTRA_JSON_OBJECT, persons.get(position));
                    startActivity(i);

                }
            });


            return convertView;
        }



    }
}
