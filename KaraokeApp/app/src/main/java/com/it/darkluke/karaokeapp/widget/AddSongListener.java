package com.it.darkluke.karaokeapp.widget;

import com.it.darkluke.karaokeapp.Model.Song;

/**
 * Created by HoLo on 09/02/2017.
 */

public interface AddSongListener {

    void onNegativeAction();

    void onPositiveAction(Song song);
}
