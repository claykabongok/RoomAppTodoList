package com.claykab.roomapptodolist.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    /**
     * If the @Insert method receives only 1 parameter, it can return a long, which is the new rowId for the inserted item.
     * @param todo
     * @return
     */
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Todo todo);


    /**
     * delete all item
     */
   @Query("DELETE FROM todo")
    void deleteAllItem();

    /**
     * Delete an item from the list
     * @param todo
     */
    @Delete
    int deleteItem(Todo todo);


    /**
     * @Query is the main annotation used in DAO classes.
     * It allows you to perform read/write operations on a database.
     * Each @Query method is verified at compile time, so if there is a problem with the query,
     * a compilation error occurs instead of a runtime failure.
     * @return
     */

    /**
     * Room also verifies the return value of the query such that if the name of the field in
     * the returned object doesn't match the corresponding column names in the query response,
     * Room alerts you in one of the following two ways:
     *
     * It gives a warning if only some field names match.
     * It gives an error if no field names match.
     *
     */


    /**
     * Return todoList
     * @return
     */
  @Query("SELECT * FROM todo")
    LiveData<List<Todo>> getTodoList();


    /**
     * retrieve an item
     * @param todo_id
     * @return
     */
    @Query("SELECT * FROM todo WHERE todo_id=:todo_id")
    LiveData<Todo> getTodoItemById(long todo_id);



    /**
     * Update and itm
     * @param todo
     */
    @Update
    int updateTodoItem(Todo... todo);

}
