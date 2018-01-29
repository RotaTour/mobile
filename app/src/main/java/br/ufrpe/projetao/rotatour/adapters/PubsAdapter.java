package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.Pub;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;

public class PubsAdapter extends RecyclerView.Adapter<PubsAdapter.PubsViewHolder> {

    private List<Pub> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PubsAdapter(Context c, List<Pub> l){
        mList = l;
        mContext = c;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PubsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.recycle_pubs, parent, false);
        PubsViewHolder mvh = new PubsViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final PubsViewHolder holder, final int position) {
        holder.tvUser.setText(mList.get(position).getUser());
        Picasso.with(mContext).load(mList.get(position).getImagem()).into(holder.ivImage);
        //holder.ivImage.setImageBitmap(mList.get(position).getImagem());
        holder.tvData.setText(mList.get(position).getData());
        holder.tvPub.setText(mList.get(position).getPub());
        holder.btnLike.setLiked(mList.get(position).isLiked());
        holder.btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                VolleySingleton.getInstance(mContext).togglePubLike(mContext, mList.get(position).getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("vtnc", "response pubLike" + response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error!= null)
                                    Log.d("vtnc", "erro pubLike" + error.toString());
                            }
                        });
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                VolleySingleton.getInstance(mContext).togglePubLike(mContext, mList.get(position).getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("vtnc", "response pubUnLike" + response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error != null)
                                    Log.d("vtnc", "erro pubUnLike" + error.toString());
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PubsViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TextView tvUser;
        public TextView tvData;
        public TextView tvPub;
        public LikeButton btnLike;

        public PubsViewHolder(View v) {
            super(v);
            btnLike = v.findViewById(R.id.btnPubLike);
            ivImage = v.findViewById(R.id.coverImageView);
            tvUser = v.findViewById(R.id.tvNomeUser);
            tvData = v.findViewById(R.id.tvDataPub);
            tvPub = v.findViewById(R.id.tvPub);
        }
    }
}