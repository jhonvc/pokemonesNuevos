package com.example.tecsup.pokemonexs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText txt;
    Button btn;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.image_pokemon);
        btn = findViewById(R.id.btn_button);
        txt = findViewById(R.id.txt_numero);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                InterfazPokemon ip = retrofit.create(InterfazPokemon.class);
                Call<Pokemon> llamada = ip.obtenerPokemon(Integer.parseInt(txt.getText().toString()));
                llamada.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        Pokemon poke = response.body();
                        Glide.with(MainActivity.this).load(poke.sprites.front_default).into(img);
                        Toast.makeText(MainActivity.this, poke.name, Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {


                    }
                });
            }
        });
    }
}


