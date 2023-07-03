package com.sss.rentalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sss.rentalapp.Room.Rental;
import com.sss.rentalapp.Room.RentalDao;
import com.sss.rentalapp.Room.RentalDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterListener{

    EditText etrentsrvcname,etrentsrvcprice;
    Button btnrentsave,btngetrent;
    private RentalDao rentalDao;
    RentalDatabase rentalDatabase;
    String rentsrvcname,rentsrvcprice;
    private RentalAdapter rentalAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rentalDatabase=RentalDatabase.getInstance(this);
        rentalDao=rentalDatabase.getDao();

        rentalAdapter=new RentalAdapter(this,this);
        recyclerView=findViewById(R.id.rentalRecycler);
        recyclerView.setAdapter(rentalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchrental();

        etrentsrvcname=findViewById(R.id.rentsrvcnameET);
        etrentsrvcprice=findViewById(R.id.rentsrvcpriceET);
        btnrentsave=findViewById(R.id.rentsaveBTN);
        btngetrent=findViewById(R.id.getrentBTN);
        btngetrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rentalDao.getRental();
            }
        });
        btnrentsave.setOnClickListener(view -> {
            rentsrvcname=etrentsrvcname.getText().toString().trim();
            rentsrvcprice=etrentsrvcprice.getText().toString().trim();

            Rental rental=new Rental(0,rentsrvcname,rentsrvcprice);
            rentalAdapter.addRental(rental);
            rentalDao.insert(rental);

            Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
        });
    }

    private void fetchrental(){
        List<Rental> rentalList= rentalDao.getRental();
        for(int i=0;i<rentalList.size();i++){
            Rental rental=rentalList.get(i);
            rentalAdapter.addRental(rental);
        }
    }

    @Override
    public void OnUpdate(int id, int pos) {
      //  rentalDao.update(id);
    }

    @Override
    public void OnDelete(int id, int pos) {
        rentalDao.delete(id);
        rentalAdapter.removeRental(pos);
    }
}