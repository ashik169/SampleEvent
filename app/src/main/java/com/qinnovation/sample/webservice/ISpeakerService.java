package com.qinnovation.sample.webservice;

import com.qinnovation.sample.master.model.MenuDetail;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by qinnovation on 12/17/17.
 */

public interface ISpeakerService {

    @GET("indexrs/index.php/app/speakers_fetch/")
    Call<List<SpeakerDetail>> getSpeakerList();
}
