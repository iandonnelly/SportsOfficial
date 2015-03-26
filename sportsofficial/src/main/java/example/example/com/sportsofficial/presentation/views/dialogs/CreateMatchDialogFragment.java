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

public class CreateMatchDialogFragment extends DialogFragment {
    public interface CreateMatchDialogListener {
        public void onCreateMatchDialogPositiveClicked(String homeTeam, String awayTeam);
    }

    private CreateMatchDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.create_match_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.create_match_title)
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
                        EditText homeTeamField = (EditText) view.findViewById(R.id.home_team_name);
                        EditText awayTeamField = (EditText) view.findViewById(R.id.away_team_name);
                        EditText leagueField = (EditText) view.findViewById(R.id.league);
                        EditText tournamentField = (EditText) view.findViewById(R.id.tournament);
                        EditText notesField = (EditText) view.findViewById(R.id.notes);

                        // Get inputted values
                        String homeTeam = homeTeamField.getText().toString();
                        String awayTeam = awayTeamField.getText().toString();
                        String league = leagueField.getText().toString();
                        String tournament = tournamentField.getText().toString();
                        String notes = notesField.getText().toString();

                        // Ensure inputted values
                        if (TextUtils.isEmpty(homeTeam)) {
                            homeTeamField.setError("Invalid home team name");
                            return;
                        }

                        if (TextUtils.isEmpty(awayTeam)) {
                            awayTeamField.setError("Invalid away team name");
                            return;
                        }

                        mListener.onCreateMatchDialogPositiveClicked(homeTeam, awayTeam);
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
            mListener = (CreateMatchDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CreateMatchDialogListener");
        }
    }
}
