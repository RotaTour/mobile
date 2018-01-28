package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.projetao.rotatour.Friend;
import br.ufrpe.projetao.rotatour.R;

/**
 * Created by Victor Alexandre on 1/27/2018.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private List<Friend> mList;
    private LayoutInflater mLayoutInflater;

    public FriendsAdapter(Context context, List<Friend> f){
        mList = f;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.recycle_friend, parent, false);
        FriendsViewHolder mvh = new FriendsViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {

        holder.friendName.setText(mList.get(position).getFriendName());
        holder.friendUsername.setText(mList.get(position).getFriendUsername());
        holder.friendEmail.setText(mList.get(position).getFrienddEmail());
        holder.friendPhotos.setImageBitmap(mList.get(position).getFriendPhoto());

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
        //public ImageView photoReference;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.textViewfriendName);
            friendUsername = itemView.findViewById(R.id.textViewFriendUsername);
            friendEmail = itemView.findViewById(R.id.textViewFriendEmail);
            friendPhotos = itemView.findViewById(R.id.friendPhoto);
            //photoReference = itemView.findViewById(R.id.friendPhoto);

        }
    }
}
