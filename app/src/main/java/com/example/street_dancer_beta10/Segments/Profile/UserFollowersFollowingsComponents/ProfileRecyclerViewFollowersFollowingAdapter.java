package com.example.street_dancer_beta10.Segments.Profile.UserFollowersFollowingsComponents;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.Model.ProfileFollowersFollowingsModel;

import java.util.List;

public class ProfileRecyclerViewFollowersFollowingAdapter extends RecyclerView.Adapter<ProfileRecyclerViewFollowersFollowingAdapter.MyViewHolder> {

    Context context;
    Dialog dialog;

    private List<ProfileFollowersFollowingsModel> profileFollowersFollowingsModels;
    private boolean isFollowersFocused;


    public ProfileRecyclerViewFollowersFollowingAdapter(Context context, List<ProfileFollowersFollowingsModel> profileFollowersFollowingsModels, boolean isFollowersFocused) {
        this.context = context;
        this.profileFollowersFollowingsModels = profileFollowersFollowingsModels;
        this.isFollowersFocused = isFollowersFocused;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_profile_followers_followings_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_followers);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if (isFollowersFocused) {
            holder.button.setText("following");
        } else {
            holder.button.setText("remove");
        }
        holder.username.setText(profileFollowersFollowingsModels.get(position).getUserName());
        holder.username_name.setText(profileFollowersFollowingsModels.get(position).getName());
        holder.profile.setImageResource(profileFollowersFollowingsModels.get(position).getUserProfile());

        holder.item_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name = (TextView) dialog.findViewById(R.id.dialog_name);
                TextView dialog_status = (TextView) dialog.findViewById(R.id.dialog_status);
                ImageView dialog_image = (ImageView) dialog.findViewById(R.id.dialog_image);
                dialog_name.setText(profileFollowersFollowingsModels.get(position).getName());
                dialog_status.setText(profileFollowersFollowingsModels.get(position).getUserName());
                dialog_image.setImageResource(profileFollowersFollowingsModels.get(position).getUserProfile());
                Toast.makeText(context, "Test Click " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });

        /*holder.followers_followings_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked on " + profileFollowersFollowingsModels.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return profileFollowersFollowingsModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout item_followers;
        private TextView username;
        private TextView username_name;
        private TextView button;
        private ImageView profile;
        private RelativeLayout followers_followings_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_followers = (RelativeLayout) itemView.findViewById(R.id.followers_item_id);
            profile = (ImageView) itemView.findViewById(R.id.followers_following_profile);
            username = (TextView) itemView.findViewById(R.id.followers_following_username);
            button = (TextView) itemView.findViewById(R.id.button);
            username_name = (TextView) itemView.findViewById(R.id.followers_following_name);
            //followers_followings_item = (RelativeLayout) itemView.findViewById(R.id.followers_followings_item);

        }
    }
}
