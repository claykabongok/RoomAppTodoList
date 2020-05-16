package com.claykab.roomapptodolist.updateTodoItem;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.claykab.roomapptodolist.R;
import com.claykab.roomapptodolist.databinding.FragmentNewTodoItemBinding;
import com.claykab.roomapptodolist.databinding.FragmentUpdateToDoItemBinding;
import com.claykab.roomapptodolist.newTodoItem.NewTodoItemFragment;
import com.claykab.roomapptodolist.newTodoItem.ViewModelNewItem;
import com.claykab.roomapptodolist.persistence.Todo;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class UpdateToDoItemFragment extends Fragment {
    private FragmentUpdateToDoItemBinding binding;
    private UpdateToDoItemViewModel updateToDoItemViewModel;


    public UpdateToDoItemFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //view binding
        binding= FragmentUpdateToDoItemBinding.inflate(inflater, container, false);
        try {
            getActivity().setTitle("Update todo Item");

        } catch (Exception e) {
            e.printStackTrace();
        }
        updateToDoItemViewModel= ViewModelProviders.of(this).get(UpdateToDoItemViewModel.class);


        Load_updateDetails();
        return binding.getRoot();
    }

    private void Load_updateDetails(){
        long itemId=0;

        try {
            itemId=getArguments().getLong("itemId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateToDoItemViewModel.getTodoItem(itemId).observe(getViewLifecycleOwner(), new Observer<Todo>() {
            @Override
            public void onChanged(Todo todo) {
                if(todo != null) {
                    binding.etUpdateTodoItemTitle.getEditText().setText(todo.getTodoTitle());
                    binding.etUpdateTodoItemDescription.getEditText().setText(todo.getTodoDescription());
                    binding.etUpdateTodoItemDate.getEditText().setText(todo.getTodoDate());
                }
            }
        });

    }
    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //select date
        binding.iBPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetTodoItemDate();
            }
        });

        //cancel action
        binding.btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to list
                Navigation.findNavController(v).navigate(R.id.action_NewItemFragment_to_ListFragment);
            }
        });
        //save item to list
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IsInputValid()){
                    return;
                }


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Update item ");
                builder.setMessage("Are you sure you want to update your list your list ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        long itemId=0;

                        try {
                            itemId=getArguments().getLong("itemId");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        long itemIdUpdate=itemId;
                        String todoUpdateTitle=binding.etUpdateTodoItemTitle.getEditText().getText().toString().trim();
                        String todoUpdateDescription=binding.etUpdateTodoItemDescription.getEditText().getText().toString().trim();
                        String todoDate=binding.etUpdateTodoItemDate.getEditText().getText().toString().trim();
//                        Todo newTodo= new Todo(todoUpdateTitle,todoUpdateTitle,todoUpdateDescription,todoDate);
                        Todo updateTodoItem = new Todo(itemIdUpdate, todoUpdateTitle, todoUpdateDescription, todoDate);

                        try {
                            updateToDoItemViewModel.UpdateToDoItem(updateTodoItem);

                            Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),"Item updated.",Snackbar.LENGTH_LONG)
                                    .show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        NavHostFragment.findNavController(UpdateToDoItemFragment.this)
                                .navigate(R.id.action_updateToDoItemFragment_to_ListFragment);



                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        NavHostFragment.findNavController(UpdateToDoItemFragment.this)
                                .navigate(R.id.action_updateToDoItemFragment_to_ListFragment);


                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();




            }
        });
    }
    //date picker wih min date set to current date
    public void openSetTodoItemDate() {
        DatePickerDialog datePickerDialog;
        int year;
        int month;
        int dayOfMonth;
        Calendar calendar;
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        String selectedDate= day+"-"+(month + 1)+"-"+year;

                        binding.etUpdateTodoItemDate.getEditText().setText(selectedDate);



                    }
                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());


        datePickerDialog.show();
    }


    private boolean IsInputValid() {
        boolean inputValid=true;
        String todoTitle=binding.etUpdateTodoItemTitle.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(todoTitle)){
            binding.etUpdateTodoItemTitle.setError("Title required.");
            inputValid=false;
        }
        else{
            binding.etUpdateTodoItemTitle.setError(null);
        }

        String todoDescription=binding.etUpdateTodoItemDescription.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(todoDescription)){
            binding.etUpdateTodoItemDescription.setError("Description required.");
            inputValid=false;
        }
        else{
            binding.etUpdateTodoItemDescription.setError(null);
        }

        String todoDate=binding.etUpdateTodoItemDate.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(todoDate)){
            binding.etUpdateTodoItemDate.setError("Date required.");
            inputValid=false;
        }
        else{
            binding.etUpdateTodoItemDate.setError(null);
        }

        return inputValid;
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
