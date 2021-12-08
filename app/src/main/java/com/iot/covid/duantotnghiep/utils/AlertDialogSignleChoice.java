package com.iot.covid.duantotnghiep.utils;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.iot.covid.duantotnghiep.login.Login;

import java.util.HashSet;
import java.util.Set;

public class AlertDialogSignleChoice {

private UserLocalStore userLocalStore;
    public  void showAlertDialog(final Activity activity)  {
        userLocalStore = new UserLocalStore(activity);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Set Title.
        builder.setTitle("Select login");


        // Add a list
        final String[] login = {"Doctor", "Patient"};

        int checkedItem = 1; // Sheep
        final Set<String> selectedItems = new HashSet<String>();
        selectedItems.add(login[checkedItem]);

        builder.setSingleChoiceItems(login, checkedItem, (dialog, which) -> {
            // Do Something...
            selectedItems.clear();
            selectedItems.add(login[which]);
        });

        //
        builder.setCancelable(true);

        // Create "Yes" button with OnClickListener.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(selectedItems.isEmpty()) {
                    return;
                }
                String login = selectedItems.iterator().next();
                if (login.equals("Doctor")){
                    dialog.dismiss();
                    Toast.makeText(activity,"You select " + login, Toast.LENGTH_SHORT).show();
                    userLocalStore.setTypeLogin(login);
                    activity.startActivity(new Intent(activity, Login.class));
                }
                if (login.equals("Patient")){
                    dialog.dismiss();
                    Toast.makeText(activity,"You select " + login, Toast.LENGTH_SHORT).show();
                    userLocalStore.setTypeLogin(login);
                   // activity.startActivity(new Intent(activity, com.iot.covid.duantotnghiep.patient.login.Login.class));
                }
                // Close Dialog
                // Do something, for example: Call a method of Activity...
            }
        });

        // Create "Cancel" button with OnClickListener.
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(activity,"You choose Cancel button",
                        Toast.LENGTH_SHORT).show();
                //  Cancel
                dialog.cancel();
            }
        });

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }

}