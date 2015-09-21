package com.tioback.arctouch.codingexercise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by renatopb on 21/09/15.
 */
public class SelectStreetDialog extends DialogFragment {

    public static final String STREET_NAME = "street_name";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage(getString(R.string.confirm_street_selection_message, getArguments().getString(STREET_NAME)))
                .setTitle(R.string.confirm_street_selection_title)
                .setPositiveButton(R.string.button_yes_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((StreetSelectDialogListener) getActivity()).onStreetSelect();
//                        dismiss();
                    }
                })
                .setNegativeButton(R.string.button_no_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        getDialog().cancel();
                    }
                });


        return builder.create();
    }
}
