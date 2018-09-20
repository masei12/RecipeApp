package com.mfalle.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        result = findViewById(R.id.result);

         final ArrayList<String> animalNames = getIntent().getExtras().getStringArrayList("food");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gimmeFood();
            }
        });}

        public void gimmeFood()  {
        new Thread(new Runnable() {
           @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
            try {
                Document doc = Jsoup.connect("https://www.allrecipes.com/?page=3").get();
                Elements recipe = doc.select("article");


                for (Element r : recipe) {
                    if (!r.select("h3").text().equals("") && !r.select("div.fixed-recipe-card__info").text().equals("")) {// gets
                        if (r.select("a").first().attr("href").contains("/recipe/")) {

                            String title = r.select("h3").text();
                            String titleParse = (title);

                            Element recipeLink = r.select("a").first();
                            String link = recipeLink.attr("href");

                            String recipeLinkParse = (link);


                                builder.append("\n").append("Recipe : ").append(title).append("\n").append("Link : ").append(link);
                            }


                            /////////////////////////////////////////////////////////////////////////////////////////////////////


                        }


                    }}
             catch (IOException e) {
                builder.append("Error : ").append(e.getMessage()).append("\n");
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                result.setText(builder.toString());
            }
        });
        }
}).start();}}



