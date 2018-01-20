package br.ufrpe.projetao.rotatour;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Victor Alexandre on 1/19/2018.
 */

    public class RoutesViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView createdTextView;

        public RoutesViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.textViewRouteName);
            descriptionTextView = v.findViewById(R.id.textViewRouteDescription);
            createdTextView = v.findViewById(R.id.textViewCreated);
        }
}
