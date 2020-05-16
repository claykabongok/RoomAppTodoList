package com.claykab.roomapptodolist.todoList;



import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.claykab.roomapptodolist.R;
import com.claykab.roomapptodolist.TodoAdapter;
import com.claykab.roomapptodolist.databinding.FragmentTodoListBinding;
import com.claykab.roomapptodolist.persistence.Todo;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TodoListFragment extends Fragment {
    private FragmentTodoListBinding binding;
    private TodoAdapter todoAdapter;
    private TodoListViewModel todoListViewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //view binding
        binding= com.claykab.roomapptodolist.databinding.FragmentTodoListBinding.inflate(inflater, container, false);
        try {
            getActivity().setTitle("Todo list");
        } catch (Exception e) {
            e.printStackTrace();
        }



        binding.fabAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_ListFragment_to_NewItemFragment);
            }
        });

        /**
         *    binding.recyclerViewListCountries.setHasFixedSize(true);
         *         binding.recyclerViewListCountries.setLayoutManager(new LinearLayoutManager(getContext()));
         *         setHasOptionsMenu(true);
         *         loadDataUsingViewModel();
         *         SwipeDeleteMyFavoriteCountry();
         */
        binding.recyclerviewTodoList.setHasFixedSize(true);
        binding.recyclerviewTodoList.setLayoutManager(new LinearLayoutManager(getContext()));
        
        todoListViewModel= ViewModelProviders.of(this).get(TodoListViewModel.class);

        loadTodoItem();

        //swipe to delete
       swipeToleftToDdelete();
        return binding.getRoot();
    }


    private void loadTodoItem() {

        todoListViewModel.getTodolist().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todoList) {
                                if (!todoList.isEmpty()) {
                    binding.progressBarTodoList.setVisibility(View.GONE);
                    todoAdapter = new TodoAdapter(getContext(), todoList);
                    binding.recyclerviewTodoList.setAdapter(todoAdapter);
                } else {
                    binding.progressBarTodoList.setVisibility(View.GONE);
                    binding.tvMyTodoListEmpty.setVisibility(View.VISIBLE);
                    binding.ivTodoListEmpty.setVisibility(View.VISIBLE);
                }

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
                          builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {

                                  todoListViewModel.deleteItemFromList(todoAdapter.getTodoItemPosition(viewHolder.getAdapterPosition()));

                                  Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),"Item removed from todo list.",Snackbar.LENGTH_LONG)
                                                               .show();
                              }
                          });
                          builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.dismiss();

                                  todoAdapter.notifyDataSetChanged();
                              }
                          });
                          AlertDialog alertDialog = builder.create();
                          alertDialog.show();


                      }
                  }).attachToRecyclerView(binding.recyclerviewTodoList);

            }




    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }


}
