package com.charge.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class AddDialogBox extends AppCompatDialogFragment {
    Button add;
    EditText entrybox;
    private AddDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialogbox,null);
        add = view.findViewById(R.id.add_check_item);
        entrybox = view.findViewById(R.id.check_item_entrybox);
        builder.setView(view);
        add.setOnClickListener(view1 -> {
            String checkitem = entrybox.getText().toString();
            entrybox.getText().clear();
            listener.applytext(checkitem);
            dismiss();
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
        listener = (AddDialogListener) context;}
        catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    public interface AddDialogListener{
        void applytext(String checkitem);
    }
}
