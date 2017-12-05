package br.ufrpe.projetao.rotatour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CriarRotaActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    private ImageButton mAddLocal;
    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_rota);

        mLinearLayout = findViewById(R.id.criarRota_layout_pai);
        mAddLocal = findViewById(R.id.criarRota_button_novoItem);

        mAddLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adicionar novos campos de atividade e local para a Rota
                LinearLayout ll = new LinearLayout(v.getContext());

                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.setOrientation(LinearLayout.HORIZONTAL);

                EditText etAtv = new EditText(v.getContext());
                etAtv.setId(i);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT, 2);
                etAtv.setLayoutParams(lp);
                etAtv.setHint(getString(R.string.criarRota_activity));

                EditText etLoc = new EditText(v.getContext());
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 3);
                etLoc.setLayoutParams(lp2);
                etLoc.setHint(getString(R.string.criarRota_place));

                i++;

                ll.addView(etAtv);
                ll.addView(etLoc);
                mLinearLayout.addView(ll, 1);
            }
        });
    }
}
