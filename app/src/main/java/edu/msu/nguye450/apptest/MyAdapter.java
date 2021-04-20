package edu.msu.nguye450.apptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context; //context
    private ArrayList<JSONObject> items; //data source of the list adapter

    //public constructor
    public MyAdapter(Context context, ArrayList<JSONObject> items) {
        this.context = context;
        this.items = items;
    }

    /***
     * Get amount of items in the Array list
     * @return number of items in array
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /***
     * Get the current item based on the position
     * @param position
     * @return Object returned based on the position inputted
     */
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    /***
     * Get the current item ID with the specified position in the list
     * @param position
     * @return returns Id of the of the specified position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_text, parent, false);
        }

        JSONObject current_JSONObject = (JSONObject) getItem(position); // current JSON object

        TextView textViewID = (TextView)
                convertView.findViewById(R.id.id_title);
        TextView textViewListID = (TextView)
                convertView.findViewById(R.id.listId_title);
        TextView textViewName = (TextView)
                convertView.findViewById(R.id.name_title);


        // Setting id, listId, and name textviews' text based on the JSON object values
        try {
            textViewID.setText(current_JSONObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            textViewListID.setText(current_JSONObject.getString("listId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            textViewName.setText(current_JSONObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

}
