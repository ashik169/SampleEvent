package com.qinnovation.sample.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.qinnovation.sample.R;
import com.qinnovation.sample.helper.recyclerview.BaseRecyclerViewAdapter;
import com.qinnovation.sample.helper.recyclerview.OnRecyclerViewClickListener;
import com.qinnovation.sample.helper.recyclerview.OnViewClickListener;
import com.qinnovation.sample.master.model.MenuDetail;
import com.qinnovation.sample.utils.DrawableUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qinnovation on 12/13/17.
 */

public class MenuGridAdapter extends BaseRecyclerViewAdapter<MenuDetail> implements OnViewClickListener {

    int[] colors = new int[]{R.color.md_green_500, R.color.md_blue_500, R.color.md_red_500, R.color.md_orange_500, R.color.md_amber_500, R.color.md_purple_500};
    private Context context;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;
    private int themeFlag = 2;

    public MenuGridAdapter(Context context, OnRecyclerViewClickListener onRecyclerViewClickListener) {

        this.context = context;
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_menu_item_view, parent, false);
        return new GridVH(view, this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        GridVH holder = (GridVH) viewHolder;

        MenuDetail gridMenu = getItem(position);

        int menuIconDrawable = gridMenu.getMenuIconDrawable();
        int menuIconColor = gridMenu.getMenuIconColor();
        if (menuIconDrawable == 0 || menuIconColor == 0) {
            applyImage(gridMenu, position);
        }

        holder.imgIcon.setImageResource(gridMenu.getMenuIconDrawable());

        if (themeFlag == 0) {
            holder.imgIcon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, gridMenu.getMenuIconColor()), PorterDuff.Mode.SRC_ATOP));
            Drawable drawable = DrawableUtil.applyDrawableColor(ContextCompat.getDrawable(holder.imgIcon.getContext(), R.drawable.circle_shape),
                    ContextCompat.getColor(holder.imgIcon.getContext(), android.R.color.transparent));
            holder.imgIcon.setBackground(drawable);
//                int colorIndex = position % colors.length;
//                holder.imgIcon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, colors[colorIndex]), PorterDuff.Mode.SRC_ATOP));
        } else if (themeFlag == 1) {
            holder.imgIcon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, R.color.md_grey_600), PorterDuff.Mode.SRC_ATOP));
            Drawable drawable = DrawableUtil.applyDrawableColor(ContextCompat.getDrawable(holder.imgIcon.getContext(), R.drawable.circle_shape),
                    ContextCompat.getColor(holder.imgIcon.getContext(), android.R.color.transparent));
            holder.imgIcon.setBackground(drawable);
        } else {
            holder.imgIcon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP));
            Drawable drawable = DrawableUtil.applyDrawableColor(ContextCompat.getDrawable(holder.imgIcon.getContext(), R.drawable.circle_shape),
                    ContextCompat.getColor(holder.imgIcon.getContext(), gridMenu.getMenuIconColor()));
            holder.imgIcon.setBackground(drawable);
        }

        int count = gridMenu.getCount();
        holder.textCount.setText(count > 9 ? "9+" : String.valueOf(count));
        holder.textCount.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
        if (holder.textCount.getVisibility() == View.VISIBLE) {
            holder.startAnimation(holder.textCount);
        }
        holder.text1.setText(gridMenu.getName());
    }

    private void applyImage(MenuDetail menuDetail, int position) {
        int id = menuDetail.getId();
        switch (id) {
            case 1:
                // Exhibitors
                menuDetail.setMenuIconDrawable(R.drawable.ic_person_white_36dp);
                menuDetail.setMenuIconColor(R.color.md_green_500);
                break;
            case 2:
                // Sessions
                menuDetail.setMenuIconDrawable(R.drawable.ic_history_white_36dp);
                menuDetail.setMenuIconColor(R.color.md_teal_500);
                break;
            case 3:
                // Speakers
                menuDetail.setMenuIconDrawable(R.drawable.ic_record_voice_over_white_36dp);
                menuDetail.setMenuIconColor(R.color.md_red_500);
                break;
            case 4:
                // Attendees
                menuDetail.setMenuIconDrawable(R.drawable.ic_person_white_36dp);
                menuDetail.setMenuIconColor(R.color.md_teal_500);
                break;
            case 5:
                // Notes
                menuDetail.setMenuIconDrawable(R.drawable.ic_note_add_white_36dp);
                menuDetail.setMenuIconColor(R.color.md_amber_500);
                break;
            case 6:
                // Inbox
                menuDetail.setMenuIconDrawable(R.drawable.ic_mail_black_36dp);
                menuDetail.setMenuIconColor(R.color.colorPrimary);
                menuDetail.setCount(5);
                break;
            case 7:
                // Sponsors
                menuDetail.setMenuIconDrawable(R.drawable.ic_person_white_36dp);
                menuDetail.setMenuIconColor(R.color.md_amber_500);
                break;
            default:
                // Others
                if (menuDetail.getMenuIconDrawable() == 0) {
                    menuDetail.setMenuIconDrawable(R.drawable.ic_info_white_36dp);
                }
                int colorIndex = position % colors.length;
                menuDetail.setMenuIconColor(colors[colorIndex]);
                break;
        }
    }

    public void setThemeFlag(int themeFlag) {

        this.themeFlag = themeFlag;
        notifyDataSetChanged();
    }

    @Override
    public void onViewClick(ViewGroup parent, View view, int position) {
        if (onRecyclerViewClickListener != null) {
            onRecyclerViewClickListener.onItemViewClick(getItem(position), parent, view, position);
        }
    }


    static class GridVH extends RecyclerView.ViewHolder {

        private final OnViewClickListener viewClickListener;

        @BindView(R.id.img_icon)
        ImageView imgIcon;

        @BindView(android.R.id.text1)
        TextView text1;

        @BindView(R.id.text_count)
        TextView textCount;

        private ScaleAnimation scaleAnimation;

        GridVH(View itemView, OnViewClickListener viewClickListener) {
            super(itemView);
            this.viewClickListener = viewClickListener;
            ButterKnife.bind(this, itemView);

            initAnimation();
        }

        void startAnimation(View view) {
            if (view == null) {
                return;
            }
            view.startAnimation(scaleAnimation);
        }

        private void initAnimation() {
            scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setRepeatMode(Animation.REVERSE);
            scaleAnimation.setRepeatCount(1);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

        @OnClick(R.id.item_view)
        public void onItemClick(View view) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            viewClickListener.onViewClick((ViewGroup) itemView, view, position);

        }


    }
}

