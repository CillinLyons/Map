package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Cillin on 17/03/2016.
 */

public class DialogBox extends Activity
{

    public static void emptyFields(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String title = "Empty Field(s)";
        String message = "Please ensure all fields are contain data";

        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void NonMatchingPasswords(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Incorrect Password";
        String emptyFieldsMessage = "Please ensure the passwords your entering match.";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void RegistrationError(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Registration Error";
        String emptyFieldsMessage = "Please ensure to enter all fields correctly and try again";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }
    public static void GardaRegistrationError(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Garda Registration Error";
        String emptyFieldsMessage = "Please ensure to enter all fields correctly and that you are using the correct garda password";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void UserRegistrationError(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Garda Registration Error";
        String emptyFieldsMessage = "Please ensure to enter all fields correctly and that you are using the correct garda password";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void IncorrectCredentials(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Incorrect Credentials";
        String emptyFieldsMessage = "Incorrect username or password enter. Please try again";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void IncorrectNeighborhoodCredentials(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Incorrect Credentials";
        String emptyFieldsMessage = "Incorrect username or password enter. Please try again";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void NotLoggedIn(final Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Not Logged In";
        String emptyFieldsMessage = "You must be logged in to enter data into the newsfeed. Press OK to be brought to the login page";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void NoConnection(final Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Database Connection Failure";
        String emptyFieldsMessage = "Sorry, there was an error connecting to the database. Please log in again";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void NeighborhoodAlreadyRegistered(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Registration Error";
        String emptyFieldsMessage = "This neighborhood is already registered. You can login to the account on the neighborhood login page.";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void UserAlreadyRegistered(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Registration Error";
        String emptyFieldsMessage = "This user email address is already registered. You can login to the account on the user login page.";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    public static void GardaAlreadyRegistered(Context context)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        String emptyFields = "Registration Error";
        String emptyFieldsMessage = "This garda email address is already registered. You can login to the account on the garda login page.";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }
}
