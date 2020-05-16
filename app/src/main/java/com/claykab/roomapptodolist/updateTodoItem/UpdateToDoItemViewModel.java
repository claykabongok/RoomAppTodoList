package com.claykab.roomapptodolist.updateTodoItem;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.claykab.roomapptodolist.persistence.Todo;
import com.claykab.roomapptodolist.persistence.TodoRepository;

import java.util.List;

public class UpdateToDoItemViewModel extends AndroidViewModel {

    private TodoRepository todoRepository;
    public UpdateToDoItemViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);

    }



    /**
     * update  item
     * @param todo
     */
    public void UpdateToDoItem(Todo todo){
        todoRepository.updateTodoItem(todo);
    }



    public LiveData<Todo> getTodoItem(long itemId){
        return todoRepository.getTodoItem(itemId);
    }


}
