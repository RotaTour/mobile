package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.activities.CriarRotaActivity;
import me.gujun.android.taggroup.TagGroup;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.LocaisViewHolder> {

    private List<Local> mList;
    private LayoutInflater mLayoutInflater;

    public RouteAdapter(Context c, List<Local> l){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public LocaisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.recycle_local, parent, false);
        LocaisViewHolder mvh = new LocaisViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final LocaisViewHolder holder, final int position) {

        holder.tvTitle.setText(mList.get(position).getCardName());
        holder.tvAtividade.setText(mList.get(position).getAtividade());
        holder.ivImage.setImageBitmap(mList.get(position).getImagem());
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
            tvAtividade = v.findViewById(R.id.place_atividade);
        }
    }
}