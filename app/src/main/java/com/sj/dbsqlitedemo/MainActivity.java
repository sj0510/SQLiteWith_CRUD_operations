package com.sj.dbsqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText firstName,Lastname,Marks,etID;
    Button submitBTN,viewBTN,updateBTN,deleteBTN;

    DatabaseHelper MyDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etID = findViewById(R.id.et_id);
        firstName = findViewById(R.id.et_fName);
        Lastname = findViewById(R.id.et_lName);
        Marks = findViewById(R.id.et_Marks);
        submitBTN = findViewById(R.id.submitBTN);
        viewBTN = findViewById(R.id.viewBTN);
        updateBTN = findViewById(R.id.updateBTN);
        deleteBTN= findViewById(R.id.deleteBTN);

        MyDbHelper = new DatabaseHelper(this);

        submitBTN.setOnClickListener(this);
        viewBTN.setOnClickListener(this);
        updateBTN.setOnClickListener(this);
        deleteBTN.setOnClickListener(this);
    }

    public void showData(String Title, String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitBTN:{
                if (TextUtils.isEmpty(firstName.getText())){
                    firstName.setError("First Name is required.!");

                }else if (TextUtils.isEmpty(Lastname.getText())){
                    Lastname.setError("Last Name is required.!");

                }else if (TextUtils.isEmpty(Marks.getText())){
                    Marks.setError("Marks is required.!");
                }
                else {

                    // Data insert
                    boolean isInserted = MyDbHelper.insertData(firstName.getText().toString(),Lastname.getText().toString(),Marks.getText().toString());

                    if (isInserted){
                        Toast.makeText(MainActivity.this,"Data inserted successfully...",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Unsuccessful..! Data Not inserted.",Toast.LENGTH_LONG).show();
                    }
                }

            break;}

            case R.id.viewBTN: {

                // View Database
                Cursor cur = MyDbHelper.getAllData();

                if (cur.getCount() == 0) {
                    showData("Data Not Found", "Empty DATABASE...");
                }

                StringBuffer stringBuffer = new StringBuffer();
                while (cur.moveToNext()) {

                    stringBuffer.append("ID : " + cur.getString(0) + "\n");
                    stringBuffer.append("FirstName : " + cur.getString(1) + "\n");
                    stringBuffer.append("LastName : " + cur.getString(2) + "\n");
                    stringBuffer.append("Marks : " + cur.getString(3) + "\n\n");
                }

                showData("Data", stringBuffer.toString());
                break;
            }

            case R.id.updateBTN:{

                if (TextUtils.isEmpty(etID.getText())) {
                    etID.setError("ID is required.!");
                }
                else if (TextUtils.isEmpty(firstName.getText())){
                    firstName.setError("First Name is required.!");

                }else if (TextUtils.isEmpty(Lastname.getText())){
                    Lastname.setError("Last Name is required.!");

                }else if (TextUtils.isEmpty(Marks.getText())){
                    Marks.setError("Marks is required.!");
                }
                else {
                    // Update database
                    boolean isUpdate = MyDbHelper.updateDatabase(etID.getText().toString(),firstName.getText().toString(),Lastname.getText().toString(),Marks.getText().toString() );

                    if (isUpdate){
                        Toast.makeText(MainActivity.this,"Database UPDATED Successfully",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Error..! Not Update..?",Toast.LENGTH_LONG).show();
                    }
                }


                break;
            }

            case R.id.deleteBTN:{

                if (TextUtils.isEmpty(etID.getText())) {
                    etID.setError("ID is required.!");
                }
                else {
                    // Delete Data from DB
                    Integer deleteData = MyDbHelper.DeleteDataInDB(etID.getText().toString());

                    if (deleteData>0){
                        Toast.makeText(this,"Data id "+etID.getText().toString()+" has deleted successfully from DB.!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this,"Data of id "+etID.getText().toString()+" NOT FOUND !",Toast.LENGTH_LONG).show();

                    }
                }

                break;

            }

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}