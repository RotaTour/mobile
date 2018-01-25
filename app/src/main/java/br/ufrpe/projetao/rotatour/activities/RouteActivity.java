package br.ufrpe.projetao.rotatour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.adapters.RoutesAdapter;

public class RouteActivity extends AppCompatActivity {

    TextView name, description, created;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Intent intent = getIntent();
        final String routeName = intent.getStringExtra(RoutesAdapter.ROUTE_NAME);
        final String routeDescription = intent.getStringExtra(RoutesAdapter.ROUTE_DESCRIPTION);
        final String routeCreated = intent.getStringExtra(RoutesAdapter.ROUTE_CREATED);
        //final int routeID = Integer.parseInt(intent.getStringExtra(String.valueOf(RoutesAdapter.ROUTE_ID)));


        TextView name = (TextView)findViewById(R.id.textViewDetailsRouteName);
        TextView description = (TextView)findViewById(R.id.textViewDetailsDescription);
        TextView created = (TextView)findViewById(R.id.textViewDetailsDate);


        name.setText(routeName);
        description.setText(routeDescription);
        created.setText(routeCreated);

    }
}
