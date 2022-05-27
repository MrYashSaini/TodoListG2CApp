package com.example.g2capp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteDialogBox extends AppCompatDialogFragment {
    Button deletebtn;
    private DeleteDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_dialogbox,null);
        deletebtn = view.findViewById(R.id.deletebtn);
        builder.setView(view);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deletetext("yes");
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (DeleteDialogListener) context;
            }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    public interface DeleteDialogListener{
        void deletetext(String dis);
    }
}
