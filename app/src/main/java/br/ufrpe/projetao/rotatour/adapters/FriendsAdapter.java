package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufrpe.projetao.rotatour.Friend;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.activities.FriendActivity;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    public static  final String FRIEND_NAME = "friendName";
    public static final String FRIEND_USERNAME = "friendUsername";
    public static final String FRIEND_EMAIL = "friendEmail";
    public static final String FRIEND_AVATAR = "friendAvatar";

    private List<Friend> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public FriendsAdapter(Context c, List<Friend> l){
        mList = l;
        mContext = c;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.recycle_friend, parent, false);
        FriendsViewHolder mvh = new FriendsViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, final int position) {

        holder.friendName.setText(mList.get(position).getFriendName());
        holder.friendUsername.setText(mList.get(position).getFriendUsername());
        holder.friendEmail.setText(mList.get(position).getFriendEmail());
        Picasso.with(mContext).load(mList.get(position).getFriendPhoto()).into(holder.friendPhotos);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friends = mList.get(position);
                Intent friend_data= new Intent(v.getContext(), FriendActivity.class);
                friend_data.putExtra(FRIEND_NAME,friends.getFriendName());
                friend_data.putExtra(FRIEND_USERNAME,friends.getFriendUsername());
                friend_data.putExtra(FRIEND_EMAIL,friends.getFriendEmail());
                friend_data.putExtra(FRIEND_AVATAR, String.valueOf(friends.getFriendPhoto()));

                //ADICIONAR O ID DO USUARIO AMIGO

                //route_data.putExtra(ROUTE_ID, String.valueOf(routes.getId()));
                v.getContext().startActivity(friend_data);
            }
        });
        //holder.friendPhotos.setImageBitmap(mList.get(position).getFriendPhoto());

       /* Friend friend = (Friend) mList.get(position);
        if(holder.friendPhotos == null){
            //new ImageDownloaderTask(holder.friendPhotos).execute(friend.getPhotoreference());

        }*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {

        public TextView friendName;
        public TextView friendUsername;
        public TextView friendEmail;
        public ImageView friendPhotos;
        public LinearLayout linearLayout;
        //public ImageView photoReference;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.textViewfriendName);
            friendUsername = itemView.findViewById(R.id.textViewFriendUsername);
            friendEmail = itemView.findViewById(R.id.textViewFriendEmail);
            friendPhotos = itemView.findViewById(R.id.friendPhoto);
            linearLayout = itemView.findViewById(R.id.linearLayoutFriendCardview);
            //photoReference = itemView.findViewById(R.id.friendPhoto);

        }
    }
}
