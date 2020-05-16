package com.claykab.roomapptodolist.newTodoItem;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.claykab.roomapptodolist.R;
import com.claykab.roomapptodolist.databinding.FragmentNewTodoItemBinding;

public class NewTodoItemFragment extends Fragment {
    private FragmentNewTodoItemBinding binding;

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
        getActivity().setTitle("Todo list");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NewTodoItemFragment.this)
                        .navigate(R.id.action_NewItemFragment_to_ListFragment);
            }
        });
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
