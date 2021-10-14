package com.HanBurhan.Tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.View;
import android.os.Bundle;

import com.HanBurhan.Tasks.R;
import com.HanBurhan.Tasks.Adapter.ToDoAdapter;
import com.HanBurhan.Tasks.Model.ToDoModel;
import com.HanBurhan.Tasks.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DataBaseHelper mydb;
    private List<ToDoModel> mLİST;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rycleview);
        fab = findViewById(R.id.fab);
        mydb = new DataBaseHelper(MainActivity.this);
        mLİST = new ArrayList<>();
        adapter = new ToDoAdapter(mydb, MainActivity.this);

        mLİST = mydb.GetAllTasks();
        Collections.reverse(mLİST);
        adapter.SetTasks(mLİST);
        adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.NewInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void OnDialogClose(DialogInterface dialogInterface) {
        mLİST = mydb.GetAllTasks();
        Collections.reverse(mLİST);
        adapter.SetTasks(mLİST);
        adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}