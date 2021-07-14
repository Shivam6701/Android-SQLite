package com.example.sqliteexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Data extends AppCompatActivity {
    TextView tvData;
    EditText etdel,etERow,etEName,etEpno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        etdel = (EditText) findViewById(R.id.etdel);
        etEName = (EditText) findViewById(R.id.etEName);
        etERow = (EditText) findViewById(R.id.etERow);
        etEpno = (EditText) findViewById(R.id.etEpno);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setMovementMethod(new ScrollingMovementMethod());

        setDataToTextView();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
    }



    public void setDataToTextView(){
        try {
            ContactsDB db =new ContactsDB(this);
            db.open();
            tvData.setText(db.getData());
            db.close();
        }catch (SQLException e)
        {
            Toast.makeText(Data.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void btnDeleteData(View v)
    {
        try {
            ContactsDB db =new ContactsDB(this);
            db.open();
            db.deleteEntry(etdel.getText().toString().trim());
            db.close();
            etdel.setText("");
            setDataToTextView();
            Toast.makeText(Data.this, "Successfully Deleted!!", Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(Data.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void btnEditData(View v)
    {
        try {
            String r =etERow.getText().toString().trim();
            String n=etEName.getText().toString().trim();
            String p =etEpno.getText().toString().trim();
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.updateEntry(r,n,p);
            db.close();
            setDataToTextView();
            etEpno.setText("");
            etERow.setText("");
            etEName.setText("");
            Toast.makeText(Data.this, "Successfully Updated!!", Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(Data.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}