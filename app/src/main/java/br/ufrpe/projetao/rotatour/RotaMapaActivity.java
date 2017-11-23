package br.ufrpe.projetao.rotatour;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RotaMapaActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota_mapa);

        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rotaMapa_frame_container, new MapsFragment(), "Maps Fragment");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
