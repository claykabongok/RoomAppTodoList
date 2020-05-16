package com.claykab.roomapptodolist.todoList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.claykab.roomapptodolist.persistence.Todo;
import com.claykab.roomapptodolist.persistence.TodoRepository;

import java.util.List;

public class TodoListViewModel  extends AndroidViewModel {
    private LiveData<List<Todo>> todolist;
    private TodoRepository todoRepository;
    public TodoListViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);

    }


    /**
     * get all item
     * @return
     */
    public LiveData<List<Todo>> getTodolist(){
        return todolist=todoRepository.getTodoList();
    }



    /**
     * delete item
     * @param todo
     */
      public void deleteItemFromList(Todo todo){
        todoRepository.deleteItem(todo);

      }

    /**
     * Delete All item
      */
    public void DeleteAllItem(){
        todoRepository.deleteAllTodo();
    }

}
