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
    @PrimaryKey
    @ColumnInfo(name = "todo_id")
    private long todo_id;

    @ColumnInfo(name ="todo_title")
    private String todoTitle;


    @ColumnInfo(name ="todo_description")
    private String todoDescription;

    @ColumnInfo(name ="todo_date")
    private String todoDate;

    public Todo(long todo_id, String todoTitle, String todoDescription, String todoDate) {
        this.todo_id = todo_id;
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.todoDate = todoDate;
    }

    public long getTodo_id() {
        return todo_id;
    }

    public Todo setTodo_id(long todo_id) {
        this.todo_id = todo_id;
        return this;
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
