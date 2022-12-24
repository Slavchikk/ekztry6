package com.example.ekztry6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.SearchView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Connection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    AdapterRecipe pAdapter;
    private   List<Recipe> recipeList = new ArrayList<>();
    Spinner spinner;
    ListView newSortList;
    private  List<Recipe> sortRecipe = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        newSortList = findViewById(R.id.lvData);
        pAdapter = new AdapterRecipe(MainActivity.this,recipeList);
        ListView idProd = findViewById(R.id.lvData);
        idProd.setAdapter(pAdapter);
        searchView = findViewById(R.id.searchs);

        new GetProduct().execute();

        String[] items = {"по умолч","по тим"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item,items);
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sort(sortRecipe);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {

                sortRecipe = sortRecipe.stream().filter(x->x.fullRecipe.toLowerCase(Locale.ROOT).contains(searchView.getQuery().toString().toLowerCase(Locale.ROOT))).collect(Collectors.toList());
                Sort(sortRecipe);
                return true;
            }
        });
    }
    public void GoToDef(View view)
    {

        searchView.setQuery("",false);
        newSortList.setAdapter(null);




        pAdapter = new AdapterRecipe(MainActivity.this,recipeList);
        newSortList.setAdapter(pAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Sort(List<Recipe> list)
    {
        newSortList.setAdapter(null);
        switch (spinner.getSelectedItemPosition())
        {

            case 0:

                  Collections.sort(list,Comparator.comparing(Recipe::getIdRecipe));

                break;
            case 1:
                Collections.sort(list, Comparator.comparing(Recipe::getTimeGot));
                break;
        }
        SetAdapter(list);

    }
    public void SetAdapter(List<Recipe> list)
    {
        pAdapter = new AdapterRecipe(MainActivity.this,list);
        newSortList.setAdapter(pAdapter);
        pAdapter.notifyDataSetInvalidated();
    }
    public class GetProduct extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/КарзиновВА/api/Recipes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line = "";

                while((line = reader.readLine())!= null)
                {
                    result.append(line);
                }
                return  result.toString();
            } catch (Exception e) {
                return  null;
            }

        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Recipe tempP = new Recipe(
                            jsonObject.getInt("idRecipe"),
                            jsonObject.getString("fullRecipe"),
                            jsonObject.getString("fullRecipe"),
                            jsonObject.getString("timeGot"),
                            jsonObject.getString("image")
                    );

                    recipeList.add(tempP);
                    sortRecipe.add(tempP);
                    pAdapter.notifyDataSetInvalidated();
                }
            }
            catch (Exception e)
            {

            }
        }

    }
}