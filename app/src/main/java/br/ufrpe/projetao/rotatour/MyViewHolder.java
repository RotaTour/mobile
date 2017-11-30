package br.ufrpe.projetao.rotatour;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public ImageView coverImageView;
    public TextView atividadeTextView;

    public MyViewHolder(View v) {
        super(v);
        titleTextView = v.findViewById(R.id.titleTextView);
        coverImageView = v.findViewById(R.id.coverImageView);
        atividadeTextView = v.findViewById(R.id.atividade);
    }
}