package com.habibu.zaki.justjava;

import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //global variavel to show quantity values
    int quantity = 2;
    //private Spinner sizeSpinner, extraSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
     Method to increment the values of quantity when clicked
     */
    public void increment(View view)
    {
        /*
            Control the possibility of more than 50 order number of coffee cups
         */
        if(quantity == 50)
        {
            Toast.makeText(this, "You could not make coffee order more than 50 cups", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }
    /*
     Method to decrement the values of quantity when clicked
     */
    public void decrement(View view)
    {
        if(quantity == 1)
        {
            Toast.makeText(this, "You could not make a coffee order less than 1 cup", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1 ;
        display(quantity);
    }
    /*
      Method to response for the onClick action that submit order btn
     */
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipeed_cream_checkebox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkebox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox sugarCheckBox = (CheckBox) findViewById(R.id.sugar_checkebox);
        boolean hasSugar = sugarCheckBox.isChecked();

        CheckBox nosugarCheckBox = (CheckBox) findViewById(R.id.nosugar_checkebox);
        boolean hasNosugar = nosugarCheckBox.isChecked();



        Spinner sizeSpinner = (Spinner) findViewById(R.id.size_array);
        sizeSpinner.setOnItemSelectedListener(new SpinnerOnItemSelectedListner());

        Spinner extraSpinner = (Spinner) findViewById(R.id.extra_array);
        extraSpinner.setOnItemSelectedListener(new SpinnerOnItemSelectedListner());

        EditText editText = (EditText) findViewById(R.id.order_name_field);
        String orderName = editText.getText().toString();
        editText.setText("");

        /*
            Validade inputText if empty
         */
        if(orderName.matches(""))
        {
            Toast.makeText(this, "Please fill in your name", Toast.LENGTH_LONG).show();
            return;
        }
        int price = calculatePrice (hasWhippedCream, hasChocolate);
        String orderMessage = createOrderSummary (price, hasWhippedCream,hasChocolate,hasSugar,hasNosugar,sizeSpinner,extraSpinner,orderName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for "+ orderName);
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);

        if(intent.resolveActivity (getPackageManager()) != null)
        {
            startActivity(intent);
        }


    }
    protected void display(int number)
    {
        TextView quantityOfCoffees;
        quantityOfCoffees = (TextView) findViewById(R.id.quantity_text_view);
        quantityOfCoffees.setText("" + number);
    }
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, boolean addSugar, boolean noSugar, Spinner sizeSpinner,Spinner extraSpinner,String orderName)
    {
        /*
            Checking the check boxes if selected
         */
        String whippedCreamAdded = addWhippedCream ? "Yes" : "No";
        String chocolateAdded = addChocolate ? "Yes": "No";
        String sugarAdded = addSugar ? "Yes":"No";
        String nosugarAdded = noSugar ? "Yes" : "No";
        String cupSize = String.valueOf(sizeSpinner.getSelectedItem());
        String extra = String.valueOf(extraSpinner.getSelectedItem());

        String priceMessage = "|---------------------------------------|";
        priceMessage +="\nName "+ orderName ;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nNo. of Cups "+ quantity;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nW. Cream added ? " + whippedCreamAdded;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nChocolate added ? " + chocolateAdded ;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nSugar added? " + sugarAdded ;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nNo sugar selected ? " + nosugarAdded ;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nCoffee cup size ? " + cupSize ;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nExtra options ? " + extra ;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\nTotal price: $" + price ;
        priceMessage += "\n\n|---------------------------------------|";
        priceMessage +="\n\n\nThank you!";

        return priceMessage;
    }
    private int calculatePrice(boolean addWhipped, boolean addChocolate)
    {
        int basePrice = 5;
        /*
        * Check which options added
        * */
        if(addWhipped)
        {
            basePrice = basePrice + 2;
        }
        if(addChocolate)
        {
            basePrice = basePrice + 1;
        }
        return quantity * basePrice;
    }
}
