package com.example.street_dancer_beta10.Segments.Profile.UserFollowersFollowingsComponents;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.street_dancer_beta10.HomeActivity;
import com.example.street_dancer_beta10.MainActivity;
import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.ProfileFollowersFollowingsModel;
import com.example.street_dancer_beta10.Segments.Profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileRecyclerViewFollowersFollowingAdapter extends RecyclerView.Adapter<ProfileRecyclerViewFollowersFollowingAdapter.MyViewHolder> {

    Context context;
    Dialog dialog;


    private boolean isFragment;
    private FirebaseUser firebaseUser;

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final ProfileFollowersFollowingsModel user = profileFollowersFollowingsModels.get(position);

        if (isFollowersFocused) {
            holder.button.setText("following");
        } else {
            holder.button.setText("remove");
        }
        holder.username.setText(profileFollowersFollowingsModels.get(position).getUserName());
        holder.fullname.setText(profileFollowersFollowingsModels.get(position).getName());

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

                if (isFragment) {
                    SharedPreferences.Editor editor = context.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", user.getId());
                    editor.apply();

                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                            new ProfileFragment()).commit();
                } else {
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("publisherid", user.getId());
                    context.startActivity(intent);
                }
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
        private TextView fullname;
        private TextView button;
        private ImageView profile;
        private RelativeLayout followers_followings_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_followers = (RelativeLayout) itemView.findViewById(R.id.followers_item_id);
            profile = (ImageView) itemView.findViewById(R.id.followers_following_profile);
            username = (TextView) itemView.findViewById(R.id.followers_following_username);
            fullname = (TextView) itemView.findViewById(R.id.followers_following_name);
            button = (TextView) itemView.findViewById(R.id.button);
            //followers_followings_item = (RelativeLayout) itemView.findViewById(R.id.followers_followings_item);

        }
    }
}
