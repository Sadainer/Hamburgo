package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hamburgo.tecnoparque.hamburgo.DTO.itemLista;

public class ConstruirTipoActivity extends AppCompatActivity {

    ListView listItemConstruirTipo;
    Context cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construir_tipo);
        cnt=this;
        itemLista[] piso =
                new itemLista[]{
                        new itemLista("Piso", "Todo tipo de pisos", R.drawable.piso),
                        new itemLista("Piso", "Todo tipo de pisos", R.drawable.piso),
                        new itemLista("Piso", "Todo tipo de pisos", R.drawable.piso),
                        new itemLista("Piso", "Todo tipo de pisos", R.drawable.piso),
                        new itemLista("Piso", "Todo tipo de pisos", R.drawable.piso)
                };

//        AdaptadorListviewClientes adaptador =
//                new AdaptadorListviewClientes(this,R.layout.layout_adaptador_listview,piso);

        listItemConstruirTipo = (ListView)findViewById(R.id.listViewConstruirTipo);

//        listItemConstruirTipo.setAdapter(adaptador);

        listItemConstruirTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemLista item = (itemLista) parent.getItemAtPosition(position);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.right_in,
                R.anim.right_out);
    }
}
