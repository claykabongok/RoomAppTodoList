package com.claykab.roomapptodolist.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private LiveData<List<Todo>> todoList;
    private TodoDao todoDao;
    private TodoAppDatabase todoAppDatabase;
    public TodoRepository(@NonNull Application application){
        todoAppDatabase= TodoAppDatabase.getDatabase(application);

        /**
         * initialise todo_Dao
         */
        todoDao=todoAppDatabase.todoDao();


    }


    public LiveData<List<Todo>> getTodoList(boolean todo_completed){
        return todoDao.getTodoList(todo_completed);
    }


    public LiveData<Todo>  getTodoItem(long todoItem){
        return todoDao.getTodoItemById(todoItem);
    }

    /**
     * add item
      * @param todo
     */
    public void AddTodoItem(Todo todo){
        new AddToToDoList().execute(todo);
    }
    private class AddToToDoList extends AsyncTask<Todo, Integer, Void>{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param todos The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Todo... todos) {
            //myFavoriteCountriesDao.insert(myFavoriteCountries[0]);
            todoDao.insert(todos[0]);
            return null;
        }
    }

    public void deleteItem(Todo todo){
        new DeleteItem().execute(todo);
    }
    private class DeleteItem extends AsyncTask<Todo, Void, Void>{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param todos The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.deleteItem(todos[0]);
            return null;
        }
    }

    public void deleteAllTodo(){
        new DeleteAllTodoList().execute();
    }
    private class DeleteAllTodoList extends AsyncTask<Void, Void, Void>{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param voids The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAllItem();
            return null;
        }
    }



    public void updateTodoItem(Todo todo){
        new UpdateTodoIten().execute(todo);
    }

    private class UpdateTodoIten extends AsyncTask<Todo,Void, Void>{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param todos The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.updateTodoItem(todos[0]);
            return null;
        }
    }

}
