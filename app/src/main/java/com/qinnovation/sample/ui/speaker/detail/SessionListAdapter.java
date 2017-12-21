package com.qinnovation.sample.ui.speaker.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qinnovation.sample.R;
import com.qinnovation.sample.helper.recyclerview.BaseRecyclerViewAdapter;
import com.qinnovation.sample.helper.recyclerview.OnRecyclerViewClickListener;
import com.qinnovation.sample.helper.recyclerview.OnViewClickListener;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qinnovation on 12/17/17.
 */

public class SessionListAdapter extends BaseRecyclerViewAdapter<SpeakerDetail> implements OnViewClickListener {

    private Context context;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;


    SessionListAdapter(Context context, OnRecyclerViewClickListener onRecyclerViewClickListener) {

        this.context = context;
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.speaker_item_view, parent, false);
        return new SpeakerVH(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SpeakerVH holder = (SpeakerVH) viewHolder;

        SpeakerDetail item = getItem(position);

        holder.textName.setText(item.getName());
        holder.textMail.setText(item.getEmail());

        Picasso.with(holder.imgSpeaker.getContext()).load(item.getImage())
                .placeholder(R.drawable.q_logo)
                .into(holder.imgSpeaker);
    }

    @Override
    public void onViewClick(ViewGroup parent, View view, int position) {
        if (onRecyclerViewClickListener != null) {
            onRecyclerViewClickListener.onItemViewClick(getItem(position), parent, view, position);
        }
    }

    static class SpeakerVH extends RecyclerView.ViewHolder {

        private final OnViewClickListener viewClickListener;

        @BindView(R.id.img_speaker)
        ImageView imgSpeaker;

        @BindView(R.id.text_name)
        TextView textName;

        @BindView(R.id.text_mail)
        TextView textMail;


        public SpeakerVH(View itemView, OnViewClickListener viewClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.viewClickListener = viewClickListener;
        }

        @OnClick(R.id.item_view)
        public void onItemClick(View view) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }
            if (viewClickListener != null)
                viewClickListener.onViewClick((ViewGroup) itemView, view, position);
        }
    }

}
