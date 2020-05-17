package com.claykab.roomapptodolist.todoList;



import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
            setHasOptionsMenu(true);
        } catch (Exception e) {
            e.printStackTrace();
        }



        binding.fabAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_ListFragment_to_NewItemFragment);
            }
        });


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
                                  todoAdapter.notifyDataSetChanged();
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


    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
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
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    todoListViewModel.DeleteAllItem();


                    binding.recyclerviewTodoList.setVisibility(View.GONE);

                    loadTodoItem();
                    Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),"Item added to the  list.",Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }






            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();



            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
