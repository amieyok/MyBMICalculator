package com.mobile.mybmicalculator;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import static com.mobile.mybmicalculator.PersonalBmi.colHeight;
import static com.mobile.mybmicalculator.PersonalBmi.colName;
import static com.mobile.mybmicalculator.PersonalBmi.colWeight;
import static java.sql.Types.NULL;

public class BMIMainActivity extends AppCompatActivity {

    //widget
    TextView txtName, txtHeight, txtWeight, txtBMI, txtBMIStatus;
    EditText edtName, edtHeight, edtWeight, edtBMIStatus;
    Button btnCalculate, btnSave;

    //vars
    String strName, strHeight, strWeight, strStatus;
    Double height, weight, bmi;

    PersonalBmi personalBmi;
    WebServiceCall wsc = new WebServiceCall();
    JSONObject jsnObj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmimain);

        txtName = (TextView) findViewById(R.id.txtName);
        txtHeight = (TextView) findViewById(R.id.txtHeight);
        txtWeight = (TextView) findViewById(R.id.txtWeight);
        txtBMI = (TextView) findViewById(R.id.txtBMI);
        txtBMIStatus = (TextView) findViewById(R.id.txtBMIStatus);

        edtName = (EditText) findViewById(R.id.edtName);
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        edtBMIStatus = (EditText) findViewById(R.id.edtBMIStatus);

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnSave = (Button) findViewById(R.id.btnSave);

        personalBmi = new PersonalBmi(getApplicationContext());


        /*Cursor resultSet = personalBmi.getReadableDatabase().rawQuery("SELECT * FROM BMI;", null );


        //totalPrice = resultSet.getString(resultSet.getColumnIndex("SUM(expenses_price)"));

        if(resultSet.moveToFirst())
        {

            do
            {
                strName = resultSet.getString(resultSet.getColumnIndex("name"));
                weight = resultSet.getDouble(resultSet.getColumnIndex("weight"));
                height = resultSet.getDouble(resultSet.getColumnIndex("height"));
                strStatus = resultSet.getString(resultSet.getColumnIndex("status"));

                strHeight = strHeight.toString();
                strWeight = strWeight.toString();



            }
            while(resultSet.moveToNext());
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edtName.setText(strName);
                edtHeight.setText(strHeight);
                edtWeight.setText(strWeight);
                edtBMIStatus.setText(strStatus);
            }
        });*/

    }

    public void fnCalculate (View vw)
    {
        strName = edtName.getText().toString();
        strHeight = edtHeight.getText().toString();
        strWeight = edtWeight.getText().toString();

        height = Double.parseDouble(strHeight);
        weight = Double.parseDouble(strWeight);

        height = height/100;
        bmi = weight / (height * height);

        txtBMI.setText("Hello " + strName + ", Your BMI is " + bmi + ".");

    }

    public void fnSave(View vw)
    {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                strName = edtName.getText().toString();
                strHeight = edtHeight.getText().toString();
                strWeight = edtWeight.getText().toString();
                strStatus = edtBMIStatus.getText().toString();

                height = Double.parseDouble(strHeight);
                weight = Double.parseDouble(strWeight);

                String strQry = "Insert into BMI values ('"+strName+"','"+weight+"','"+height+"','"+NULL+"');";
                personalBmi.fnExecuteSql(strQry,getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast showSuccess = Toast.makeText(getApplicationContext(), "Information Successfully Saved!",Toast.LENGTH_SHORT);
                        showSuccess.show();
                    }
                });
            }
        };
        Thread thread = new Thread(run);
        thread.start();
    }
}
