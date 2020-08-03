package com.claykab.roomapptodolist.completedTodo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.claykab.roomapptodolist.R;
import com.claykab.roomapptodolist.adapter.TodoAdapter;
import com.claykab.roomapptodolist.databinding.FragmentCompletedTodoBinding;
import com.claykab.roomapptodolist.databinding.FragmentTodoListBinding;
import com.claykab.roomapptodolist.todoList.TodoListViewModel;
import com.google.android.material.snackbar.Snackbar;


public class CompletedTodoFragment extends Fragment {
    private FragmentCompletedTodoBinding binding;
    private TodoAdapter todoAdapter;
    private CompletedTodoViewModel completedTodoViewModel;

    public CompletedTodoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        //view binding

        binding=FragmentCompletedTodoBinding.inflate(inflater, container, false);

        try {
            getActivity().setTitle("Completed todo ");
            setHasOptionsMenu(true);
        } catch (Exception e) {
            e.printStackTrace();
        }



        binding.fabAddNewItem.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_completedTodoFragment_to_NewItemFragment));


        binding.recyclerviewTodoList.setHasFixedSize(true);
        binding.recyclerviewTodoList.setLayoutManager(new LinearLayoutManager(getContext()));

        completedTodoViewModel= ViewModelProviders.of(this).get(CompletedTodoViewModel.class);

        loadTodoItem();

        //swipe to delete
        swipeToleftToDdelete();
        return binding.getRoot();
    }

    private void loadTodoItem() {

        completedTodoViewModel.getTodolist(true).observe(getViewLifecycleOwner(), todoList -> {
            if (!todoList.isEmpty()) {
                binding.progressBarTodoList.setVisibility(View.GONE);
                todoAdapter = new TodoAdapter(getContext(), todoList,"completedtodo");
                binding.recyclerviewTodoList.setAdapter(todoAdapter);
            } else {
                binding.progressBarTodoList.setVisibility(View.GONE);
                binding.tvMyTodoListEmpty.setVisibility(View.VISIBLE);
                binding.ivTodoListEmpty.setVisibility(View.VISIBLE);
            }

        });

    }



    private void swipeToleftToDdelete() {
        final String deletedItem=null;
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Records");
                builder.setMessage("Are you sure you want to delete this?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (dialog, which) -> {

                    completedTodoViewModel.deleteItemFromList(todoAdapter.getTodoItemPosition(viewHolder.getAdapterPosition()));
                    todoAdapter.notifyDataSetChanged();
                    Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),"Item removed from todo list.",Snackbar.LENGTH_LONG)
                            .show();

                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();

                    todoAdapter.notifyDataSetChanged();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        }).attachToRecyclerView(binding.recyclerviewTodoList);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_clearList:

                DeleteAllItem();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }


    }

    private void DeleteAllItem() {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Delete All Records");
        builder.setMessage("Are you sure you want to delete All item ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> {

            try {
                completedTodoViewModel.DeleteAllItem();


                binding.recyclerviewTodoList.setVisibility(View.GONE);

                loadTodoItem();
                Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),"Item added to the  list.",Snackbar.LENGTH_LONG)
                        .show();
            } catch (Exception e) {
                e.printStackTrace();
            }






        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}