package example.example.com.sportsofficial.presentation.views.dialogs;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import example.example.com.sportsofficial.R;

public class AddSportDialogFragment extends DialogFragment {
    public interface AddSportDialogListener {
        public void onAddSportDialogPositiveClicked(String name, int singleClick, int doubleClick,
                                                    int longClick);
    }

    private AddSportDialogListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_sport_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.add_sport_title)
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get input fields
                        EditText nameField = (EditText) view.findViewById(R.id.name);
                        EditText singleClickField = (EditText) view.findViewById(R.id.single_click_value);
                        EditText doubleClickField = (EditText) view.findViewById(R.id.double_click_value);
                        EditText longClickField = (EditText) view.findViewById(R.id.long_click_value);

                        // Get inputted values
                        String name = nameField.getText().toString();
                        String singleClick = singleClickField.getText().toString();
                        String doubleClick = doubleClickField.getText().toString();
                        String longClick = longClickField.getText().toString();

                        // Ensure inputted values
                        if (TextUtils.isEmpty(name)) {
                            nameField.setError("Invalid name");
                            return;
                        }

                        if (TextUtils.isEmpty(singleClick)) {
                            nameField.setError("Invalid single click value");
                            return;
                        }

                        if (TextUtils.isEmpty(doubleClick)) {
                            nameField.setError("Invalid double click value");
                            return;
                        }

                        if (TextUtils.isEmpty(longClick)) {
                            nameField.setError("Invalid long click value");
                            return;
                        }

                        // Convert values
                        int singleClickValue = Integer.valueOf(singleClick);
                        int doubleClickValue = Integer.valueOf(doubleClick);
                        int longClickValue = Integer.valueOf(longClick);

                        mListener.onAddSportDialogPositiveClicked(name, singleClickValue,
                                doubleClickValue, longClickValue);
                        dialog.dismiss();
                    }
                });
            }
        });

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (AddSportDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddSportDialogListener");
        }
    }
}
