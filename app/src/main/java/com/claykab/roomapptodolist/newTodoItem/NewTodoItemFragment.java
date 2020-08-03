package com.claykab.roomapptodolist.newTodoItem;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.claykab.roomapptodolist.R;
import com.claykab.roomapptodolist.databinding.FragmentNewTodoItemBinding;
import com.claykab.roomapptodolist.persistence.Todo;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewTodoItemFragment extends Fragment {
    private FragmentNewTodoItemBinding binding;
    private ViewModelNewItem viewModelNewItem;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        try {
            getActivity().setTitle("Add new Item");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //view binding
        binding= FragmentNewTodoItemBinding.inflate(inflater, container, false);

        viewModelNewItem= ViewModelProviders.of(this).get(ViewModelNewItem.class);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //select date
        binding.iBPickDate.setOnClickListener(v -> openSetTodoItemDate());

        //cancel action
        binding.btnCancel.setOnClickListener(v -> {
            //Navigate to list
            Navigation.findNavController(v).navigate(R.id.action_NewItemFragment_to_ListFragment);
        });
        //save item to list
        binding.btnSave.setOnClickListener(v -> {
            if(!IsInputValid()){
                return;
            }


            AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
            builder.setTitle("Add new item ");
            builder.setMessage("Are you sure you want to add this to your list ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                String todoTitle=binding.etTodoItemTitle.getEditText().getText().toString().trim();
                String todoDescription=binding.etTodoItemDescription.getEditText().getText().toString().trim();
                String todoDate=binding.etTodoItemDate.getEditText().getText().toString().trim();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date mTodoate= null;

                try {
                    mTodoate = simpleDateFormat.parse(todoDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//
                Todo newTodo= new Todo(todoTitle,false,todoDescription,mTodoate);

                try {
                    viewModelNewItem.AddItemToList(newTodo);
                    Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),"Item added to the  list.",Snackbar.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                NavHostFragment.findNavController(NewTodoItemFragment.this)
                        .navigate(R.id.action_NewItemFragment_to_ListFragment);



            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
                NavHostFragment.findNavController(NewTodoItemFragment.this)
                        .navigate(R.id.action_NewItemFragment_to_ListFragment);


            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();




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
                (datePicker, year1, month1, day) -> {

                    String selectedDate= day+"-"+(month1 + 1)+"-"+ year1;

                    binding.etTodoItemDate.getEditText().setText(selectedDate);



                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());


        datePickerDialog.show();
    }


    private boolean IsInputValid() {
        boolean inputValid=true;
        String todoTitle=binding.etTodoItemTitle.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(todoTitle)){
            binding.etTodoItemTitle.setError("Title required.");
            inputValid=false;
        }
        else{
            binding.etTodoItemTitle.setError(null);
        }

        String todoDescription=binding.etTodoItemDescription.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(todoDescription)){
            binding.etTodoItemDescription.setError("Description required.");
            inputValid=false;
        }
        else{
            binding.etTodoItemDescription.setError(null);
        }

        String todoDate=binding.etTodoItemDate.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(todoDate)){
            binding.etTodoItemDate.setError("Date required.");
            inputValid=false;
        }
        else{
            binding.etTodoItemDate.setError(null);
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
