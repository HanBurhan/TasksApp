package com.HanBurhan.Tasks;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.HanBurhan.Tasks.Model.ToDoModel;
import com.HanBurhan.Tasks.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG =  "AddNewTask";
    //Widgets
    private EditText myEditText;
    private Button mSaveButton;

    private DataBaseHelper myDB;

    public static com.HanBurhan.Tasks.AddNewTask NewInstance(){
        return  new com.HanBurhan.Tasks.AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_task, container, false);
        return  v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myEditText = view.findViewById(R.id.edittxt);
        mSaveButton = view.findViewById(R.id.button_save);

        myDB = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            myEditText.setText(task);

            if (task.length() > 0){
                mSaveButton.setEnabled(false);
            }
        }
        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().equals("")){
                    mSaveButton.setEnabled(false);
                }else {
                    mSaveButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    mSaveButton.setEnabled(false);
                }else {
                    mSaveButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    mSaveButton.setEnabled(false);
                }else {
                    mSaveButton.setEnabled(true);
                }
            }
        });


        final  boolean finalIsUpdate = isUpdate;
        boolean canSave = myEditText.toString().isEmpty();
        mSaveButton.setEnabled(canSave);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = myEditText.getText().toString();

                if (finalIsUpdate){
                    myDB.UpdateTask(bundle.getInt("id"), text);
                }else{
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    myDB.InsertTask(item);
                }
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof com.HanBurhan.Tasks.OnDialogCloseListener){
            ((com.HanBurhan.Tasks.OnDialogCloseListener)activity).OnDialogClose(dialog);
        }
    }
}
