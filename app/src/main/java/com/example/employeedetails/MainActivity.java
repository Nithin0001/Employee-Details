package com.example.employeedetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.example.employeedetails.databinding.ActivityMainBinding;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ActivityMainBinding binding;
    SearchView searchView;
    int[] empId;
    String[] firstName;
    String[] lastName;
    String[] email;
    String[] imgURL;
    String temp;
    ArrayList<Employee> employeeArrayList;
    final String baseURL = "https://reqres.in/api/users?page=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.srcView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        loadData();
        storeDataArr();
        setDataOnListView();
    }

    //Fetching data from the API
    public void loadData(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(baseURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    temp = scanner.nextLine();
                }
            }
        } catch (Exception e) {
            Log.i("urlTag", e.toString());
        }
    }

    //Parsing the JSON data
    public void storeDataArr(){
        try{
            JSONObject obj = new JSONObject(temp);
            JSONArray arr = obj.getJSONArray("data");
            temp = "";
            int dataLength = arr.length();
            empId = new int[dataLength];
            firstName = new String[dataLength];
            lastName = new String[dataLength];
            email = new String[dataLength];
            imgURL = new String[dataLength];
            for (int i = 0; i < dataLength; i++) {
                empId[i] = Integer.parseInt(String.valueOf(arr.getJSONObject(i).getInt("id")));
                firstName[i] = arr.getJSONObject(i).getString("first_name");
                lastName[i] = arr.getJSONObject(i).getString("last_name");
                email[i] = arr.getJSONObject(i).getString("email");
                imgURL[i] = arr.getJSONObject(i).getString("avatar");
            }
        } catch (Exception e) {
            Log.i("jsonTag", e.toString());
        }
    }

    //Setting the data on the list view
    public void setDataOnListView(){
        int n = email.length;
        employeeArrayList = new ArrayList<>();
        for(int i = 0;i < n;i++){
            Employee employee = new Employee(empId[i], firstName[i], lastName[i], email[i], imgURL[i]);
            employeeArrayList.add(employee);
        }
        Adapter adapter = new Adapter(MainActivity.this, employeeArrayList);
        binding.listView.setAdapter(adapter);
    }

    //Searching by first name
    private void filterList(String text){
        ArrayList<Employee> arrayList = new ArrayList<>();
        for(Employee employee : employeeArrayList){
            if(employee.getFirstName().toLowerCase().contains(text.toLowerCase())){
                arrayList.add(employee);
            }
        }
        if(arrayList.isEmpty()){
            Toast.makeText(MainActivity.this, "Employee not found", Toast.LENGTH_SHORT).show();
        } else {
            Adapter adapter = new Adapter(MainActivity.this, arrayList);
            binding.listView.setAdapter(adapter);
        }
    }
}
