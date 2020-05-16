package com.claykab.roomapptodolist.persistence;
/**
 *  entity
 */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room allows you to return any Java-based object
 * from your queries as long as the set of result
 * columns can be mapped into the returned object

 */
@Entity(tableName = "todo")
public class Todo {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    private long todo_item_id;

    @ColumnInfo(name ="todo_title")
    private String todoTitle;

    public long getTodo_item_id() {
        return todo_item_id;
    }


    public void setTodo_item_id(long todo_item_id) {
        this.todo_item_id = todo_item_id;
    }


    @ColumnInfo(name ="todo_description")
    private String todoDescription;

    @ColumnInfo(name ="todo_date")
    private String todoDate;

    public Todo( String todoTitle, String todoDescription, String todoDate) {

        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.todoDate = todoDate;
    }



    public String getTodoTitle() {
        return todoTitle;
    }

    public Todo setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
        return this;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public Todo setTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
        return this;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public Todo setTodoDate(String todoDate) {
        this.todoDate = todoDate;
        return this;
    }
}
