package com.sss.rentalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sss.rentalapp.Room.Rental;

import java.util.ArrayList;
import java.util.List;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.MyViewHolder> {

    private Context context;
    private List<Rental> rentalList;
    private AdapterListener adapterListener;
    public RentalAdapter(Context context,AdapterListener listener){

        this.context=context;
        rentalList=new ArrayList<>();
        this.adapterListener=listener;
    }

    public void addRental(Rental rental){
        rentalList.add(rental);
        notifyDataSetChanged();
    }
    public void removeRental(int position){
       rentalList.remove(position);
       notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rental_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Rental rental=rentalList.get(position);
        holder.tvrentalservicename.setText(rental.getRentalservicename());
        holder.tvrentalprice.setText(rental.getRentalserviceprice());

        holder.Ivupdate.setOnClickListener(view -> {
            adapterListener.OnUpdate(rental.getId(),position);
        });

        holder.IVdelete.setOnClickListener(view -> {
            adapterListener.OnDelete(rental.getId(),position);
        });



    }

    @Override
    public int getItemCount() {
        return rentalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvrentalprice,tvrentalservicename,tvprintpdf;
        private ImageView IVdelete,Ivupdate;
        Bundle bundle=new Bundle();

        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            tvrentalservicename=itemView.findViewById(R.id.rentalservicenameTV);
            tvrentalprice=itemView.findViewById(R.id.rentalpriceTV);
            tvprintpdf=itemView.findViewById(R.id.displaypdf);
            IVdelete=itemView.findViewById(R.id.deleteIV);
            Ivupdate=itemView.findViewById(R.id.updateIV);

            tvprintpdf.setOnClickListener(view -> {
                String GETSERVICENAME=rentalList.get(getAdapterPosition()).getRentalservicename();
                String GETPRICE=rentalList.get(getAdapterPosition()).getRentalserviceprice();
                bundle.putString("name",GETSERVICENAME);
                bundle.putString("price",GETPRICE);
                Intent in=new Intent(context,PdfActivity.class);
                in.putExtras(bundle);
                context.startActivity(in);


            });

        }
    }
}
