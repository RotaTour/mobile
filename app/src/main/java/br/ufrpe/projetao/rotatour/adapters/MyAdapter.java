package br.ufrpe.projetao.rotatour.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.MyViewHolder;
import br.ufrpe.projetao.rotatour.R;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Local> list;
    public MyAdapter(ArrayList<Local> Data) {
        list = Data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_place, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.titleTextView.setText(list.get(position).getCardName());
        holder.atividadeEditText.setText((list.get(position).getAtividade()));
        holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
        holder.coverImageView.setTag(list.get(position).getImageResourceId());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}