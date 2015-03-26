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

public class RemoveSportDialogFragment extends DialogFragment {
    public interface RemoveSportDialogListener {
        public void onRemoveSportDialogPositiveClicked(String name);
    }

    private RemoveSportDialogListener mListener;
    private List<String> mSports;

    private Spinner mSportField;

    public static RemoveSportDialogFragment newInstance(ArrayList<String> sports) {
        RemoveSportDialogFragment fragment = new RemoveSportDialogFragment();

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
        final View view = inflater.inflate(R.layout.remove_sport_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.remove_sport_title)
                .setPositiveButton(R.string.remove, null)
                .setNegativeButton(R.string.cancel, null)
                .create();

        // Get a handle on the fields
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
                        // Get input fields
                        String sport = mSportField.getSelectedItem().toString();

                        // Return selection
                        mListener.onRemoveSportDialogPositiveClicked(sport);
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
            mListener = (RemoveSportDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RemoveSportDialogListener");
        }
    }
}