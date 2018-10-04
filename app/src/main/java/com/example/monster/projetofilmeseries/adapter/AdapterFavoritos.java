package com.example.monster.projetofilmeseries.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monster.projetofilmeseries.R;
import com.example.monster.projetofilmeseries.model.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterFavoritos extends BaseAdapter {

    private List<Results> results;
    //private Activity activity;
    private Context context;

    public AdapterFavoritos(Context context, List<Results> results) {
        //super(context, 0, results);
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        //aqui eu posso implementaar pra ele da um getID da pessoa que vim do banco
        return 0;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;
        //TextView nome;
        ViewHolder holder ;

        if( convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_filme, parent, false);

            Results result  = results.get( position );
            //nome = (TextView)  view.findViewById(R.id.textServico);
            // nome.setText(pessoa.getNome());

            holder = new ViewHolder(view);
            view.setTag(holder);


        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        Results result  = results.get( position );
        holder.title.setText( result.getTitle() );

        String url = result.getBackdrop_path();
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + url).into(holder.capa);

        return view; //erro -

    }

    public class ViewHolder {

        TextView title;
        ImageView capa;

        public ViewHolder(View view) {

            title = view.findViewById(R.id.textTitulo);
            capa = view.findViewById(R.id.imageCapa);
        }
    }

}
