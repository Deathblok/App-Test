package edu.msu.nguye450.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String COMMUNITY_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    public ListView listView; // view that will display the list of items from URL
    public ArrayList<JSONObject> jsonObjectList; // list of all json objects

    private RequestQueue requestQueue; // Used with Volley to handle network requests

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonObjectList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private ArrayList<JSONObject> getJsonObjectList(){
        return jsonObjectList;

    }
    private void displayJSONList(){

        MyAdapter adapter = new MyAdapter(this, getJsonObjectList());
        listView = (ListView) findViewById(R.id.json_listview);
        listView.setAdapter(adapter);
    }

    /***
     *
     * @param jsonObjectList JSON List obtained from URL filtered by the requirement.
     *                       Sorts results first by "listId"
     *                       then by name if both objects have the same "listId"
     */
    private void sortJSONList(ArrayList<JSONObject> jsonObjectList){

        jsonObjectList.sort((o1, o2) -> {
            try {

                int int_1 = Integer.parseInt(o1.getString("listId"));
                int int_2 = Integer.parseInt(o2.getString("listId"));

                if (int_1 == int_2) {
                    return o1.getString("name").compareTo(o2.getString("name"));
                    // compare by "name"
                } else {
                    return Integer.compare(int_1, int_2);
                    // compare by "listId"
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    /***
     * Create a JSON Array Request to obtain json data from provided URL and display it
     * Make sure valid items are displayed to the user based on the following below:
     *
     * Display all the items grouped by "listId"
     * Sort the results first by "listId" then by "name" when displaying.
     * Filter out any items where "name" is blank or null.
     *
     *
     */
    private void jsonParse(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(COMMUNITY_URL, response -> {

            for (int i = 0; i < response.length(); i++){

                try{
                    JSONObject jsonObject = response.getJSONObject(i);

                    String id = jsonObject.getString("id");
                    String list_id = jsonObject.getString("listId");
                    String name = jsonObject.getString("name");

                    // filtering out any names where "name is blank or null
                    if (name.equals("") || name.equals("null")){
                        continue;
                    }
                    else{
                        jsonObjectList.add(jsonObject);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
            sortJSONList(jsonObjectList); // grouping by listID and sorting by listID and name

            displayJSONList(); // displaying JSON list using ListView
        }, Throwable::printStackTrace);

        requestQueue.add(jsonArrayRequest);
    }
}