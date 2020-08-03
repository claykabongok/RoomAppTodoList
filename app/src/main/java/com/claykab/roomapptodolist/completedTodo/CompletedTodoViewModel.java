package com.claykab.roomapptodolist.completedTodo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.claykab.roomapptodolist.persistence.Todo;
import com.claykab.roomapptodolist.persistence.TodoRepository;

import java.util.List;

public class CompletedTodoViewModel extends AndroidViewModel {
    private LiveData<List<Todo>> todolist;
    private TodoRepository todoRepository;
    public CompletedTodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);

    }


    /**
     * get all item
     * @return
     */
    public LiveData<List<Todo>> getTodolist( boolean todo_completed){
        return todolist=todoRepository.getTodoList(todo_completed);
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
