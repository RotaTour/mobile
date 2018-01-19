package br.ufrpe.projetao.rotatour.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.Routes;
import br.ufrpe.projetao.rotatour.RoutesViewHolder;

/**
 * Created by Victor Alexandre on 1/19/2018.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>{

        private List<Routes> mList;
        private LayoutInflater mLayoutInflater;

    public RoutesAdapter(Context c, List<Routes> l){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RoutesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.recycle_route, parent, false);
       // View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_route, parent, false);
        RoutesViewHolder routesViewHolder = new RoutesViewHolder(v);
        return routesViewHolder;
    }


    public void onBindViewHolder(final RoutesViewHolder holder, final int position) {

        holder.routeName.setText(mList.get(position).getName());
        holder.routeDescription.setText(mList.get(position).getDescription());
        holder.createdDate.setText(mList.get(position).getCreated());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder {
        public TextView routeName;
        public  TextView routeDescription;
        public  TextView createdDate;

        public RoutesViewHolder(View itemView) {
            super(itemView);

            routeName = itemView.findViewById(R.id.textViewRouteName);
            routeDescription =  itemView.findViewById(R.id.textViewRouteDescription);
            createdDate = itemView.findViewById(R.id.textViewCreated);
        }
    }
}
