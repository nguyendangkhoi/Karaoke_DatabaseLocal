package com.it.darkluke.karaokeapp.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.it.darkluke.karaokeapp.Model.Song;
import com.it.darkluke.karaokeapp.R;

/**
 * Created by HoLo on 09/02/2017.
 */

public class AddSongDialog extends DialogFragment {

    private static final String TAG = AddSongDialog.class.getSimpleName();
    EditText edtSongId;
    EditText edtSongName;
    EditText edtSongAuthor;

    Song mSong = new Song();

    AddSongListener mAddSongListener;

    public AddSongDialog() {
    }

    public void setAddSongListener(AddSongListener addSongListener) {
        mAddSongListener = addSongListener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setMessage("Thêm bài hát")
                .setPositiveButton("Thêm",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //TODO nothing to do here
                            }
                        }
                )
                .setNegativeButton("Hủy",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //TODO nothing to do here
                            }
                        }
                );

        View view = getActivity().getLayoutInflater().inflate(R.layout.add_song_dialog, null);

        edtSongId = (EditText) view.findViewById(R.id.edtSongId);
        edtSongName = (EditText) view.findViewById(R.id.edtSongName);
        edtSongAuthor = (EditText) view.findViewById(R.id.edtSongAuthor);


        dialogBuilder.setView(view);


        return dialogBuilder.create();

    }


    @Override
    public void onStart() {
        super.onStart();

        AlertDialog aD = (AlertDialog) getDialog();

        if (null != aD) {
            Button positiveButton = aD.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValidSong()) {
                        Log.e(TAG, "isValidSong");
                        mAddSongListener.onPositiveAction(mSong);
                        dismiss();
                    } else {
                        Log.e(TAG, "isNotValidSong");
                    }
                }
            });


            Button negativeButton = aD.getButton(Dialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "setNegativeButton - onClick");
                    dismiss();
                    mAddSongListener.onNegativeAction();
                }
            });


        }
    }

    public boolean isValidSong() {

        String songId = edtSongId.getText().toString();
        if (songId.isEmpty()) {
            edtSongId.setError("Mục này không được bỏ trống");
            return false;
        } else {
            edtSongId.setError(null);
            mSong.setKey(songId);
        }

        String songName = edtSongName.getText().toString();
        if (songName.isEmpty()) {
            edtSongName.setError("Mục này không được bỏ trống");
            return false;
        } else {
            edtSongName.setError(null);
            mSong.setName(songName);
        }


        String songAuthor = edtSongAuthor.getText().toString();
        if (songAuthor.isEmpty()) {
            edtSongAuthor.setError("Mục này không được bỏ trống");
            return false;
        } else {
            edtSongAuthor.setError(null);
            mSong.setAuthor(songAuthor);
        }

        return true;
    }
}
