package com.example.jagad.assignment6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SqlClass db;
    SQLiteDatabase emp_table;
    TextView inserted_rows;
    int valuecount;
    long set_values;
    ListView mylistview;
    Cursor cursor1 = null;

    String[] name;          //FOR GETTING DATA FROM TEXT FILES
    int[] salary;
    String[] gender;
    int[] age;
    String[] colors;
    String[] diet;
    String[] food1;
    String[] food2;

    String[] getname;           //TO CONVERT ARRAYLIST TO ARRAY
    String[] getsalary;
    String[] getgender;
    String[] getage;
    String[] getcolors;
    String[] getdiet;
    String[] getfood1;
    String[] getfood2;

    int[] getgenderimages;      //FOR COOPYING IMAGES
    int[] getcolorimages;
    int[] getdietimages;

    ArrayList<String> thelist_name;     //TO GET DATA FROM CURSOR
    ArrayList<String> thelist_salary;
    ArrayList<String> thelist_gender;
    ArrayList<String> thelist_age;
    ArrayList<String> thelist_colors;
    ArrayList<String> thelist_diet;
    ArrayList<String> thelist_food1;
    ArrayList<String> thelist_food2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inserted_rows = (TextView)findViewById(R.id.inserted_text);
        mylistview = (ListView)findViewById(R.id.mylistview);

        getData();              //METHOD TO GET DATA FROM TEXT FILES
        boolean deletedatabase = MainActivity.this.deleteDatabase("employees.db");

        db = new SqlClass(this, "employees.db", null, 1);
        emp_table = db.getReadableDatabase();

        //INSERTING DATA INTO TABLE:-
        for(valuecount=0;valuecount<100;valuecount++)       //CURSOR LOOP
        {
            ContentValues values = new ContentValues();

            values.put("name",name[valuecount]);
            values.put("salary",salary[valuecount]);
            values.put("gender",gender[valuecount]);
            values.put("age",age[valuecount]);
            values.put("favorite_color",colors[valuecount]);
            values.put("diet",diet[valuecount]);
            values.put("favorite_food_1",food1[valuecount]);
            values.put("favorite_food_2",food2[valuecount]);

            set_values = emp_table.insert("employees",null,values);
        }
        if(set_values>0)
            Toast.makeText(MainActivity.this, String.valueOf(set_values), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "data not inserted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){      //MENU WITH QUESTIONS
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showall:
                //select * from employees
                CustomAdapter customAdapterAll;
                Cursor cursorAll = emp_table.query("employees", null, null, null, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorAll.getCount()));
                customAdapterAll = writeCurser(cursorAll);
                mylistview.setAdapter(customAdapterAll);
                break;
            case R.id.milton1:
                //MILTON 1:-
                CustomAdapter customAdapterM1;
                Cursor cursorM1 = emp_table.query("employees", null, "favorite_food_1=? or favorite_food_2=?", new String[]{"chicken", "chicken"}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorM1.getCount()));
                customAdapterM1 = writeCurser(cursorM1);
                mylistview.setAdapter(customAdapterM1);
                break;
            case R.id.milton2:
                //MILTON 2:-
                CustomAdapter customAdapterM2;
                Cursor cursorM2 = emp_table.query("employees", null, "favorite_food_1=? or favorite_food_2=?", new String[]{"goat", "goat"}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorM2.getCount()));
                customAdapterM2 = writeCurser(cursorM2);
                mylistview.setAdapter(customAdapterM2);
                break;
            case R.id.milton3:
                //MILTON 3:-
                CustomAdapter customAdapterM3;
                Cursor cursorM3 = emp_table.query("employees", null, "salary<=?", new String[]{String.valueOf(60000)}, null, null, null );
                inserted_rows.setText("entries:"+String.valueOf(cursorM3.getCount()));
                customAdapterM3 = writeCurser(cursorM3);
                mylistview.setAdapter(customAdapterM3);
                break;
            case R.id.milton4:
                //MILTON 4:-
                CustomAdapter customAdapterM4;
                Cursor cursorM4 = emp_table.query("employees", null, "age<?", new String[]{String.valueOf(38)}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorM4.getCount()));
                customAdapterM4 = writeCurser(cursorM4);
                mylistview.setAdapter(customAdapterM4);
                break;
            case R.id.milton5:
                //MILTON 5:-
                CustomAdapter customAdapterM5;
                Cursor cursorM5 = emp_table.query("employees", null, "favorite_color=? and gender=?", new String[]{"blue", "female"}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorM5.getCount()));
                customAdapterM5 = writeCurser(cursorM5);
                mylistview.setAdapter(customAdapterM5);
                break;
            case R.id.grunt1:
                //GRUNT 1:-
                CustomAdapter customAdapterG1;
                Cursor cursorG1 = emp_table.query("employees", null, "salary>?", new String[]{String.valueOf(70000)}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorG1.getCount()));
                customAdapterG1 = writeCurser(cursorG1);
                mylistview.setAdapter(customAdapterG1);
                break;
            case R.id.grunt2:
                //GRUNT 2:-
                CustomAdapter customAdapterG2;
                Cursor cursorG2 = emp_table.query("employees", null, "age>? and diet=?", new String[]{(String.valueOf(40)), "poultry"}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorG2.getCount()));
                customAdapterG2 = writeCurser(cursorG2);
                mylistview.setAdapter(customAdapterG2);
                break;
            case R.id.grunt3:
                //GRUNT 3:-
                CustomAdapter customAdapterG3;
                Cursor cursorG3 = emp_table.query("employees", null, "favorite_food_1=? and age<?", new String[]{"turkey", String.valueOf(40)}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorG3.getCount()));
                customAdapterG3 = writeCurser(cursorG3);
                mylistview.setAdapter(customAdapterG3);
                break;
            case R.id.grunt4:
                //GRUNT 4:-
                CustomAdapter customAdapterG4;
                Cursor cursorG4 = emp_table.query("employees", null, "gender=? and favorite_color=? and diet=?", new String[]{"male", "red", "redMeat"}, null, null, null);
                inserted_rows.setText("entries:"+String.valueOf(cursorG4.getCount()));
                customAdapterG4 = writeCurser(cursorG4);
                mylistview.setAdapter(customAdapterG4);
                break;
            case R.id.grunt5:
                //GRUNT 5:-
                CustomAdapter customAdapterG5;
                Cursor cursorG5 = emp_table.rawQuery("select * from employees where gender = ? and name in(select name from employees where favorite_food_1 = ? or favorite_food_2 = ?)", new String[]{"female","tofu", "tofu"});
                inserted_rows.setText("entries:"+String.valueOf(cursorG5.getCount()));
                customAdapterG5 = writeCurser(cursorG5);
                mylistview.setAdapter(customAdapterG5);
                break;
            case R.id.both1:
                //BOTH 1:-
                CustomAdapter customAdapterB1;
                Cursor cursorB1 = emp_table.rawQuery("select * from employees where name like 'M%'", null);
                inserted_rows.setText("entries:"+String.valueOf(cursorB1.getCount()));
                customAdapterB1 = writeCurser(cursorB1);
                mylistview.setAdapter(customAdapterB1);
                break;
            case R.id.both2:
                //BOTH 2:-
                CustomAdapter customAdapterB2;
                Cursor cursorB2a = emp_table.rawQuery("select * from(select * from employees where gender=? and favorite_color=? and diet=? union select * from employees where gender=? and favorite_color=? and diet=?)A where A.age>?", new String[]{"female", "yellow", "redMeat", "male", "blue", "poultry", (String.valueOf(30))});
                inserted_rows.setText("entries:"+String.valueOf(cursorB2a.getCount()));
                customAdapterB2 = writeCurser(cursorB2a);
                mylistview.setAdapter(customAdapterB2);
                break;
        }
        return true;
    }

    public void getData()           //METHOD TO GET DATA FROM TEXT FILES
    {
        //name:-
        name = new String[100];
        String temp;
        int i = 0;
        InputStream inputStream = null;
        try {
            inputStream = MainActivity.this.getResources().getAssets().open("names.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    name[i]=temp;
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, name[99], Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //salary:-
        salary = new int[100];
        i=0;

        try {
            inputStream = MainActivity.this.getResources().getAssets().open("salary.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    salary[i]=Integer.parseInt(temp);
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, String.valueOf(salary[99]), Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //gender:-
        gender = new String[100];
        i=0;
        try {
            inputStream = MainActivity.this.getResources().getAssets().open("gender.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    gender[i]=temp;
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, gender[99], Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //age:-
        age = new int[100];
        i=0;
        try {
            inputStream = MainActivity.this.getResources().getAssets().open("age.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    age[i]=Integer.parseInt(temp);
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, String.valueOf(age[99]), Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //colors:-
        colors = new String[100];
        i=0;
        try {
            inputStream = MainActivity.this.getResources().getAssets().open("colors.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    colors[i]=temp;
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, colors[99], Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //diet:-
        diet = new String[100];
        i=0;
        try {
            inputStream = MainActivity.this.getResources().getAssets().open("diet.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    diet[i]=temp;
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, diet[99], Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //food:-
        food1 = new String[100];
        i=0;
        try {
            inputStream = MainActivity.this.getResources().getAssets().open("food.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    food1[i]=temp;
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, food[99], Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //food2:-
        food2 = new String[100];
        i=0;
        try {
            inputStream = MainActivity.this.getResources().getAssets().open("food2.txt");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            if(inputStream!= null)
            {
                while ((temp = bfr.readLine())!=null)
                {
                    food2[i]=temp;
                    i++;
                }
                inputStream.close();
                //Toast.makeText(MainActivity.this, food2[99], Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public CustomAdapter writeCurser(Cursor cursor)     //MEHTOD TO COPY DATA TO LISTVIEW
    {
        int count = cursor.getCount();
        Toast.makeText(MainActivity.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        if(count == 0)
        {
            Toast.makeText(MainActivity.this, "no data in cursor", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cursor.moveToFirst();
            thelist_name = new ArrayList<>();
            thelist_salary = new ArrayList<>();
            thelist_gender = new ArrayList<>();
            thelist_age = new ArrayList<>();
            thelist_colors = new ArrayList<>();
            thelist_diet = new ArrayList<>();
            thelist_food1 = new ArrayList<>();
            thelist_food2 = new ArrayList<>();

            while (cursor.moveToNext())
            {
                thelist_name.add(cursor.getString(0));      //GETTING DATA FROM CURSOR
                thelist_salary.add(cursor.getString(1));
                thelist_gender.add(cursor.getString(2));
                thelist_age.add(cursor.getString(3));
                thelist_colors.add(cursor.getString(4));
                thelist_diet.add(cursor.getString(5));
                thelist_food1.add(cursor.getString(6));
                thelist_food2.add(cursor.getString(7));
            }

            getname = new String[thelist_name.size()];
            getsalary = new String[thelist_salary.size()];
            getgender = new String[thelist_gender.size()];
            getage = new String[thelist_age.size()];
            getcolors = new String[thelist_colors.size()];
            getdiet = new String[thelist_diet.size()];
            getfood1 = new String[thelist_food1.size()];
            getfood2 = new String[thelist_food2.size()];

            thelist_name.toArray(getname);                  //COPING DATA FROM ARRAYLIST TO ARRAY
            thelist_salary.toArray(getsalary);
            thelist_gender.toArray(getgender);
            thelist_age.toArray(getage);
            thelist_colors.toArray(getcolors);
            thelist_diet.toArray(getdiet);
            thelist_food1.toArray(getfood1);
            thelist_food2.toArray(getfood2);

            getgenderimages = new int[getgender.length];

            //COPPYING IMAGES TO ARRAYS
            for(int k=0; k<getgender.length;k++)
            {
                if(getgender[k].toString().contentEquals("male"))
                    getgenderimages[k]=R.drawable.male;
                else if(getgender[k].toString().contentEquals("female"))
                    getgenderimages[k]=R.drawable.famale;

            }

            getdietimages = new int[getdiet.length];

            for(int l=0;l<getdiet.length;l++)
            {
                if(getdiet[l].toString().contentEquals("poultry"))
                    getdietimages[l]=R.drawable.poultry;
                else if(getdiet[l].toString().contentEquals("vegetarian"))
                    getdietimages[l]=R.drawable.vegetarian;
                else if(getdiet[l].toString().contentEquals("redMeat"))
                    getdietimages[l]=R.drawable.redmeat;
            }

            getcolorimages = new int[getcolors.length];

            for(int m=0;m<getcolors.length;m++)
            {
                if(getcolors[m].toString().contentEquals("orange"))
                    getcolorimages[m]=R.color.orange;
                if(getcolors[m].toString().contentEquals("green"))
                    getcolorimages[m]=R.color.green;
                if(getcolors[m].toString().contentEquals("red"))
                    getcolorimages[m]=R.color.red;
                if(getcolors[m].toString().contentEquals("yellow"))
                    getcolorimages[m]=R.color.yellow;
                if(getcolors[m].toString().contentEquals("blue"))
                    getcolorimages[m]=R.color.blue;
            }
        }

        CustomAdapter customAdapter = new CustomAdapter();
        return customAdapter;
    }

    //CUSTOMADAPTER CLASS
    public class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return getname.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.list_view_structure,null);
            //SETTING DATA TO LISTVIEW
            TextView names = (TextView)convertView.findViewById(R.id.names);
            TextView age = (TextView)convertView.findViewById(R.id.age);
            TextView salary = (TextView)convertView.findViewById(R.id.salary);
            ImageView gender = (ImageView) convertView.findViewById(R.id.gender);
            ImageView colors = (ImageView)convertView.findViewById(R.id.colors);
            ImageView diet = (ImageView) convertView.findViewById(R.id.diet);
            TextView food1 = (TextView)convertView.findViewById(R.id.food1);
            TextView food2 = (TextView)convertView.findViewById(R.id.food2);

            names.setText(getname[position]);
            age.setText("Age:"+getage[position]);
            salary.setText("Salary:"+getsalary[position]);
            gender.setImageResource(getgenderimages[position]);
            colors.setImageResource(getcolorimages[position]);
            diet.setImageResource(getdietimages[position]);
            food1.setText(" "+getfood1[position]);
            food2.setText(" "+getfood2[position]);
            return convertView;
        }
    }

}
