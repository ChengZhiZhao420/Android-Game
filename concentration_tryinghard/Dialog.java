package com.example.concentration_tryinghard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class Dialog extends AppCompatDialogFragment {
    private Button button1, button2, button3, button4, button5,
            button6, button7, button8, button9;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);

        builder.setView(view)
                .setTitle("Selection")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        button1 = (Button)view.findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 2);
                startActivity(i);
            }
        });

        button2 = (Button)view.findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 3);
                startActivity(i);
            }
        });

        button3 = (Button)view.findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 4);
                startActivity(i);
            }
        });

        button4 = (Button)view.findViewById(R.id.button_4);
        button4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 5);
                startActivity(i);
            }
        });

        button5 = (Button)view.findViewById(R.id.button_5);
        button5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 6);
                startActivity(i);
            }
        });

        button6 = (Button)view.findViewById(R.id.button_6);
        button6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 7);
                startActivity(i);
            }
        });

        button7 = (Button)view.findViewById(R.id.button_7);
        button7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 8);
                startActivity(i);
            }
        });

        button8 = (Button)view.findViewById(R.id.button_8);
        button8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 9);
                startActivity(i);
            }
        });

        button9 = (Button)view.findViewById(R.id.button_9);
        button9.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Game.class);
                i.putExtra("NumberOfTiles", 10);
                startActivity(i);
            }
        });

        return builder.create();
    }

}