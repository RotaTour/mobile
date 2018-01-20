package br.ufrpe.projetao.rotatour;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public ImageView coverImageView;
    public EditText atividadeEditText;

    public MyViewHolder(View v) {
        super(v);
        titleTextView = v.findViewById(R.id.titleTextView);
        coverImageView = v.findViewById(R.id.coverImageView);
        atividadeEditText = v.findViewById(R.id.place_atividade);
    }
}