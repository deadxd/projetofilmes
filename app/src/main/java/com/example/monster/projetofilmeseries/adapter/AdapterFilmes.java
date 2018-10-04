package com.example.monster.projetofilmeseries.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monster.projetofilmeseries.R;
import com.example.monster.projetofilmeseries.model.Filme;
import com.example.monster.projetofilmeseries.model.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterFilmes extends RecyclerView.Adapter<AdapterFilmes.MyViewHolder> {

    private List<Results> results = new ArrayList<>();
    private Context context;

    public AdapterFilmes(List<Results> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_filme, parent, false);
        return new AdapterFilmes.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Results result  = results.get( position );

        holder.title.setText( result.getTitle() );

        String url = result.getBackdrop_path();
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + url).into(holder.capa);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView capa;

        public MyViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.textTitulo);
            capa = itemView.findViewById(R.id.imageCapa);

        }

    }
}
