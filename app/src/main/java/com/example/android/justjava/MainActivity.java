package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

/**
 * This app displays an order form to order coffee.
 */
public
class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    /**
     * This method is called when the order button is clicked.
     */

    public int quantity = 2;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public
    void increment(View view) {
        if (quantity == 100)
            // Exit method early because there's nothing left to do
            return;
        quantity += 1;

//        if (quantity <100)
//            quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public
    void decrement(View view) {
        if (quantity == 1)
            // Exit method early because there's nothing left to do
            return;
        quantity -= 1;

//        if (quantity > 1)
//            quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public
    void submitOrder(View view) {
        // Find the user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String   name      = nameField.getText().toString();

        // figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean  hasWhippedCream      = whippedCreamCheckBox.isChecked();

        // figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean  hasChocolate      = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
//        displayMessage(createOrderSummary(name, price, hasWhippedCream, hasChocolate));

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null){
//            startActivity(intent);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculate the Price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return total price
     **/
    private
    int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffee
        int basePrice = 5;

        // Add 1$ if the user wants whipped cream
        if (addWhippedCream)
            basePrice += 1;

        // Add 2$ if the user wants chocolate
        if (addChocolate)
            basePrice += 2;

        // total Price
        return quantity * basePrice;
    }

    /**
     * Create Summary of the order
     *
     * @param price of the order
     * @return text Summary
     */

    private
    String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name,name);
//        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd whipped cream? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
//        priceMessage += "\nThank you!";
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * This method displays the given text on the screen.
     *
     * @param message order summary
     */
    private
    void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


    /**
     * This method displays the given quantity value on the screen.
     *
     * @param numberOfCoffees quantity of coffee
     */
    private
    void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private
    void calculatePrice(int quantity) {
        int price = quantity * 5;
    }


}