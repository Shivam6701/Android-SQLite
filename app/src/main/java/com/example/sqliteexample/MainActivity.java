package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etName,etPno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=(EditText)findViewById(R.id.etName);
        etPno=(EditText)findViewById(R.id.etPno);

    }
    public void btnSubmit(View v)
    {
        String name=etName.getText().toString().trim();
        String num = etPno.getText().toString().trim();

        try {
            ContactsDB db =new ContactsDB(this);
            db.open();
            db.createEntry(name,num);
            db.close();
            Toast.makeText(MainActivity.this, "Successfully saved!!", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etPno.setText("");
        }catch (SQLException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void btnShowData(View v)  //also create new activity for show data
    {
        startActivity(new Intent(this, Data.class));
    }


}