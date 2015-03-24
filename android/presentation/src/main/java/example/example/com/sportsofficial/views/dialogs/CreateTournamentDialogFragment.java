package example.example.com.sportsofficial.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.models.Date;

public class CreateTournamentDialogFragment extends DialogFragment {
    public interface DialogClickListener {
        public void onPositiveClick(String name, String venue, String country, String city,
                                    String address, Date date);
        public void onNegativeClick();
    }

    private DialogClickListener callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (DialogClickListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.create_tournament_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.create_tournament_title)
                .setPositiveButton(R.string.create, null)
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
                        EditText venueField = (EditText) view.findViewById(R.id.venue);
                        EditText countryField = (EditText) view.findViewById(R.id.country);
                        EditText cityField = (EditText) view.findViewById(R.id.city);
                        EditText addressField = (EditText) view.findViewById(R.id.address);
                        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);

                        // Get inputted values
                        String name = nameField.getText().toString();
                        String venue = venueField.getText().toString();
                        String country = countryField.getText().toString();
                        String city = cityField.getText().toString();
                        String address = addressField.getText().toString();
                        Date date = new Date(datePicker.getYear(), datePicker.getMonth(),
                                datePicker.getDayOfMonth());

                        // Ensure inputted values
                        if (TextUtils.isEmpty(name)) {
                            nameField.setError("Invalid name");
                            return;
                        }

                        if (TextUtils.isEmpty(venue)) {
                            venueField.setError("Invalid venue");
                            return;
                        }

                        if (TextUtils.isEmpty(country)) {
                            countryField.setError("Invalid country");
                            return;
                        }

                        if (TextUtils.isEmpty(city)) {
                            countryField.setError("Invalid city");
                            return;
                        }

                        if (TextUtils.isEmpty(address)) {
                            countryField.setError("Invalid address");
                            return;
                        }

                        callback.onPositiveClick(name, venue, country, city, address, date);
                        dialog.dismiss();
                    }
                });
            }
        });

        return dialog;
    }
}