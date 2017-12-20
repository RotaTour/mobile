package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;

public class LocaisAdapter extends RecyclerView.Adapter<LocaisAdapter.LocaisViewHolder> {

    private List<Local> mList;
    private LayoutInflater mLayoutInflater;

    public LocaisAdapter(Context c, List<Local> l){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public LocaisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.recycle_place, parent, false);
        LocaisViewHolder mvh = new LocaisViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(LocaisViewHolder holder, int position) {
        int  Images[] = {R.drawable.ic_like
                ,R.drawable.ic_home_black_24dp
                ,R.drawable.ic_map_black_24dp
                ,R.drawable.ic_person_black_24dp
                ,R.drawable.ic_share_black_24dp};

        holder.ivImage.setImageResource(Images[mList.get(position).getImageResourceId()]);
        holder.tvTitle.setText(mList.get(position).getCardName());
        holder.tvAtividade.setText(mList.get(position).getAtividade());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LocaisViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivImage;
        public TextView tvAtividade;

        public LocaisViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.titleTextView);
            ivImage = v.findViewById(R.id.coverImageView);
            tvAtividade = v.findViewById(R.id.atividade);
        }
    }
}