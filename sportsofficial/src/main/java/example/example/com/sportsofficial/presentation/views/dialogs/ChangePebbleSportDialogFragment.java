package example.example.com.sportsofficial.presentation.views.dialogs;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import example.example.com.sportsofficial.R;

public class ChangePebbleSportDialogFragment extends DialogFragment {
    public interface ChangePebbleSportDialogListener {
        public void onChangePebbleSportPositiveClicked(String sport);
    }

    private ChangePebbleSportDialogListener mListener;
    private List<String> mSports;

    private Spinner mSportField;


    public static ChangePebbleSportDialogFragment newInstance(ArrayList<String> sports) {
        ChangePebbleSportDialogFragment fragment = new ChangePebbleSportDialogFragment();

        Bundle args = new Bundle();
        args.putStringArrayList("sports", sports);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSports = getArguments().getStringArrayList("sports");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.change_pebble_sport_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.change_pebble_sport_title)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create();

        // Get handles on fields
        mSportField = (Spinner) view.findViewById(R.id.sport_selection);

        // Add items to sports selection
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, mSports);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSportField.setAdapter(adapter);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get inputted value
                        String sport = mSportField.getSelectedItem().toString();

                        mListener.onChangePebbleSportPositiveClicked(sport);
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
            mListener = (ChangePebbleSportDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ChangePebbleSportDialogListener");
        }
    }
}
