package com.langfox.langfoxandroid;

//source of startActivity() from fragment
//http://blog.csdn.net/guolin_blog/article/details/8744943


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class WordbookFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * there is one ListView in the menu layout
     */

    private ListView menuList;

    /**
     * create an adapter for the ListView
     */

    private ArrayAdapter<String> adapter;

    /**
     * data to fill up the ListView
     */

    private String[] menuItems = {"Courses in another language", "Available Courses by Learning Path", "Available Courses by Categories", "Test page"};

    /**
     * Initialize the data in adapter when fragment is attached to activity
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, menuItems);
    }

    /**
     * Inflate the layout of this fragment
     * Set adapter to the ListView
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wordbook, container, false);
        menuList = (ListView) view.findViewById(R.id.menu_list);
        menuList.setAdapter(adapter);
        menuList.setOnItemClickListener(this);
        return view;
    }

    /**
     * deal with onItemClick event
     * start corresponding Activity
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        if (position == 0) {
            Log.w("langfoxApp", "Log test, position == 0");
            intent = new Intent(getActivity(), WorkbookActivityNewLan.class);
        } else if (position == 1) {
            Log.w("langfoxApp", "Log test, position == 1");
            intent = new Intent(getActivity(), WorkbookActivityPath.class);
        } else if (position == 2) {
            intent = new Intent(getActivity(), WorkbookActivityCat.class);
        } else if (position == 3) {
            Log.w("langfoxApp", "Log test, position == 3");
            intent = new Intent(getActivity(), SQLiteTestActivity.class);

        }
        startActivity(intent);


    }


    public WordbookFragment() {
        // Required empty public constructor
    }
}
