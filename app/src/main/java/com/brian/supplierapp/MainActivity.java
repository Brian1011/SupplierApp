package com.brian.supplierapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //declare the variables
    int buying_price=0, selling_price=0, calculated_tax=0, calculated_profit=0, quantity=0, item_profit=0, item_tax=0, item_selling=0;
    EditText text_buying_price, text_tax, text_profit,text_quantity;
    TextView text_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Listen to the edit text
        text_buying_price = (EditText)findViewById(R.id.textfield_buying_price);
        text_tax = (EditText)findViewById(R.id.texfield_tax);
        text_profit = (EditText)findViewById(R.id.texfield_profit);
        text_quantity = (EditText)findViewById(R.id.texfield_quantity);
        text_total = (TextView)findViewById(R.id.textview_total);

        //initialize the values to the ones in the textfield
        buying_price = Integer.parseInt(text_buying_price.getText().toString());
        calculated_tax = Integer.parseInt(text_tax.getText().toString());
        calculated_profit = Integer.parseInt(text_profit.getText().toString());
        quantity = Integer.parseInt(text_quantity.getText().toString());

        //listen to buying price and initialize buying price
        text_buying_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //check if buying price is empty
                if(text_buying_price.getText().toString().isEmpty()){
                    //reset to 0
                    text_buying_price.setError("Buying price cannot be empty");
                }else{
                    //calculate
                    buying_price = Integer.parseInt(text_buying_price.getText().toString());
                    calculate_selling();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //listen to tax
        text_tax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //make sure its not empty

                //check if buying price is empty
                if(text_tax.getText().toString().isEmpty()){
                    //reset to 0
                    text_tax.setError("Tax cannot be empty");
                }else{
                    //calulate the
                    int hold = Integer.parseInt(text_tax.getText().toString());//initialize tax

                    //tax cannot be more than 100
                    if(hold>100){
                        text_tax.setError("Tax cannot be more than 100");
                        text_tax.setText("16");
                        text_tax.requestFocus();
                        return;
                    }

                    //get the tax from input
                    calculated_tax = Integer.parseInt(text_tax.getText().toString());//initialize tax
                    calculate_selling();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //listen to profit
        text_profit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(text_profit.getText().toString().isEmpty()){
                    //reset to 0
                    text_profit.setError("Profit cannot be empty");
                }else{
                    //set profit to the new value
                    calculated_profit = Integer.parseInt(text_profit.getText().toString());
                    //recalulate total again
                    calculate_selling();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //listen to quantity
        text_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(text_quantity.getText().toString().isEmpty()){
                    //reset to 0
                    text_quantity.setError("Quantity cannot be empty");
                }else{
                    //initialize the quantity to the new value
                    quantity = Integer.parseInt(text_quantity.getText().toString());
                    //recalculate total again
                    calculate_selling();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //calculate the total selling price
    public void calculate_selling(){
        item_profit = buying_price * calculated_profit/100;
        item_tax = buying_price * calculated_tax/100;
        item_selling = buying_price+item_profit+item_tax;//quantity is 1
        selling_price = (buying_price+item_profit+item_tax)*quantity;
        //text_total.setText("Ksh: "+selling_price);

        //format the texfields to display commas
        String formatted_total = format_with_decimal(selling_price);
        text_total.setText("Ksh: "+formatted_total);
    }

    //a method that displays commas
    public String format_with_decimal(int money){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(money);
    }

    //a method that checks tax and profit is not more than a 100
    public int percentages(int value){
        int new_value;
        if(value >100){
            Toast.makeText(getApplicationContext(),"Percent Cannot be more than a 100",Toast.LENGTH_SHORT).show();
            new_value =100;
        }else if(value < 0){
            Toast.makeText(getApplicationContext(),"Percent Cannot be less than 0!",Toast.LENGTH_SHORT).show();
            new_value = 0;
        }else{
            new_value = value;
        }
        return new_value;
    }

    //A method that checks if the texfields are null
    public boolean check_null(String data){
        if(data == null){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
