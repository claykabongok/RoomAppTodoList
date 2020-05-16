package com.claykab.roomapptodolist.newTodoItem;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.claykab.roomapptodolist.persistence.Todo;
import com.claykab.roomapptodolist.persistence.TodoRepository;

import java.util.List;


public class ViewModelNewItem extends AndroidViewModel {
    private LiveData<List<Todo>> todolist;
    private TodoRepository todoRepository;
    public ViewModelNewItem(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);

    }


    /**
     * Add item to the list
     * @param todo
     */
    public  void AddItemToList(Todo todo){
        todoRepository.AddTodoItem(todo);
    }


}


