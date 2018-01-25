package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.Pub;
import br.ufrpe.projetao.rotatour.R;

public class PubsAdapter extends RecyclerView.Adapter<PubsAdapter.PubsViewHolder> {

    private List<Pub> mList;
    private LayoutInflater mLayoutInflater;

    public PubsAdapter(Context c, List<Pub> l){
        mList = l;
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
        holder.ivImage.setImageBitmap(mList.get(position).getImagem());
        holder.tvData.setText(mList.get(position).getData());
        holder.tvPub.setText(mList.get(position).getPub());
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

        public PubsViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.coverImageView);
            tvUser = v.findViewById(R.id.tvNomeUser);
            tvData = v.findViewById(R.id.tvDataPub);
            tvPub = v.findViewById(R.id.tvPub);
        }
    }
}