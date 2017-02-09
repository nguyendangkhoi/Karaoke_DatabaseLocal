package com.it.darkluke.karaokeapp.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
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
                                Log.e(TAG, "setPositiveButton - onClick");

                                if (isValidSong()) {
                                    mAddSongListener.onPositiveAction(mSong);
                                    dismiss();
                                }

                            }
                        }
                )
                .setNegativeButton("Hủy",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.e(TAG, "setNegativeButton - onClick");
                                dismiss();
                                mAddSongListener.onNegativeAction();
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


    public boolean isValidSong() {

        String songId = edtSongId.getText().toString();
        if (songId.isEmpty()) {
            edtSongId.setError("Mục này không được bỏ trống");
            return false;
        } else {
            edtSongId.setError(null);
            Log.e(TAG, "songId");
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
