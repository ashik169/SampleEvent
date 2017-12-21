package com.qinnovation.sample.ui.speaker;

import android.os.Handler;

/**
 * Created by qinnovation on 12/17/17.
 */

public interface ISpeakerDataManager {
    void getLocalSpeakerDetailList(Handler handler);

    void getSpeakerDetailList(Handler handler);

    void getOfflineSpeakerList(Handler handler, int count);
}
