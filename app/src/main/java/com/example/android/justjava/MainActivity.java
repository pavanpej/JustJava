package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int pricePerCup = 5;
    int whippedCreamPrice = 1, chocolatePrice = 2;
    boolean isWhippedCreamChecked, isChocolateCheckedBox;
    String name;
    CheckBox whippedCreamCheckedBox, chocolateCheckedBox;
    EditText nameTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whippedCreamCheckedBox = (CheckBox) findViewById(R.id.whippedCream);
        chocolateCheckedBox = (CheckBox) findViewById(R.id.chocolate);
        nameTextField = (EditText) findViewById(R.id.nameTextField);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String summary = orderSummary();
        displayMessage(summary);
        String emailSubject = "Just Java order for " + name;
        String emailAddresses[] = {};
        composeEmail(emailAddresses, emailSubject, summary);
    }

    /**
     * This method is used to create and send an email.
     */
    public void composeEmail(String[] addresses, String subject, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100)
            quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1)
            quantity -= 1;
        displayQuantity(quantity);
    }

    /*
     *Calculates the price for the given quantity
     *
     * @return The total price
     */
    protected int calculatePrice() {

        int basePrice = pricePerCup;

        if (whippedCreamCheckedBox.isChecked())
            basePrice += whippedCreamPrice;
        if (chocolateCheckedBox.isChecked())
            basePrice += chocolatePrice;

        return (basePrice * quantity);
    }

    protected String orderSummary() {

        isWhippedCreamChecked = whippedCreamCheckedBox.isChecked();
        isChocolateCheckedBox = chocolateCheckedBox.isChecked();

        name = nameTextField.getText().toString();
        String summary = "Name: " + name;
        summary += "\nOrder: " + quantity;

        summary += "\nAdd " + whippedCreamCheckedBox.getText() + "? ";
        if (isWhippedCreamChecked)
            summary += "Yes.";
        else
            summary += "No.";

        summary += "\nAdd " + chocolateCheckedBox.getText() + "? ";
        if (isChocolateCheckedBox)
            summary += "Yes.";
        else
            summary += "No.";

        summary += "\nTotal Price: $" + calculatePrice();
        summary += "\nThank you!";

        return summary;

    }

    //Display Methods

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantityTextView);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderTextView = (TextView) findViewById(R.id.orderTextView);
        orderTextView.setText(message);
    }
}
