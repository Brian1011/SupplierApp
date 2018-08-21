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
                //change the buying price
                buying_price = Integer.parseInt(text_buying_price.getText().toString());
                calculate_selling();
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
                calculated_tax = Integer.parseInt(text_tax.getText().toString());
                calculate_selling();
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
                //set profit to the new value
                calculated_profit = Integer.parseInt(text_profit.getText().toString());
                //recalulate total again
                calculate_selling();
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
                //initialize the quantity to the new value
                quantity = Integer.parseInt(text_quantity.getText().toString());
                //recalculate total again
                calculate_selling();
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
