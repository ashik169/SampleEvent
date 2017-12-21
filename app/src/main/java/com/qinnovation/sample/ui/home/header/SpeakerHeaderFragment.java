package com.qinnovation.sample.ui.home.header;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qinnovation.sample.R;
import com.qinnovation.sample.ui.base.BaseFragment;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qinnovation on 12/17/17.
 */

public class SpeakerHeaderFragment extends BaseFragment {

    @BindView(R.id.img_speaker)
    ImageView imgSpeaker;

    @BindView(R.id.text_name)
    TextView textName;

    @BindView(R.id.text_mail)
    TextView textMail;

    public static SpeakerHeaderFragment newInstance(SpeakerDetail speakerDetail) {

        Bundle args = new Bundle();
        args.putParcelable("SPEAKER", speakerDetail);
        SpeakerHeaderFragment fragment = new SpeakerHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_speker_banner_item_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SpeakerDetail speaker = getArguments().getParcelable("SPEAKER");

        if (speaker != null) {
            Picasso.with(getContext()).load(speaker.getImage())
                    .into(imgSpeaker);
            textName.setText(speaker.getName());
            textMail.setText(speaker.getEmail());
        }
    }
}
