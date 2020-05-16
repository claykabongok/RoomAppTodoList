package com.claykab.roomapptodolist.todoList;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.claykab.roomapptodolist.R;
import com.claykab.roomapptodolist.persistence.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
     private Context context;
     List<Todo> todoList;
     public TodoAdapter(Context context, List<Todo> todoList) {
          this.context=context;
          this.todoList=todoList;
     }





          @NonNull
          @Override
          public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
              View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_todo_list_item,viewGroup,false);

              return new TodoViewHolder(view);

          }

     /**
      * Called by RecyclerView to display the data at the specified position. This method
      * should update the contents of the {ViewHolder # itemView} to reflect the item at
      * the given position.
      * <p>
      * Note that unlike {@link ListView}, RecyclerView will not call this method
      * again if the position of the item changes in the data set unless the item itself is
      * invalidated or the new position cannot be determined. For this reason, you should only
      * use the <code>position</code> parameter while acquiring the related data item inside
      * this method and should not keep a copy of it. If you need the position of an item later
      * on (e.g. in a click listener), use {ViewHolder #getAdapterPosition()} which will
      * have the updated adapter position.
      * <p>
      * Partial bind vs full bind:
      * <p>
      * The payloads parameter is a merge list from {@link #notifyItemChanged(int, Object)} or
      * {@link #notifyItemRangeChanged(int, int, Object)}.  If the payloads list is not empty,
      * the ViewHolder is currently bound to old data and Adapter may run an efficient partial
      * update using the payload info.  If the payload is empty,  Adapter must run a full bind.
      * Adapter should not assume that the payload passed in notify methods will be received by
      * onBindViewHolder().  For example when the view is not attached to the screen, the
      * payload in notifyItemChange() will be simply dropped.
      *
      * @param todoViewHolder   The ViewHolder which should be updated to represent the contents of the
      *                 item at the given position in the data set.
      * @param position The position of the item within the adapter's data set.
      *
      */
     @Override
     public void onBindViewHolder(@NonNull TodoViewHolder todoViewHolder, int position) {
          Todo todo=todoList.get(position);
          todoViewHolder.tv_itemId.setText(String.valueOf(position+1));
          todoViewHolder.tv_item_title.setText(todo.getTodoTitle());
          todoViewHolder.tv_item_description.setText(todo.getTodoDescription());
          todoViewHolder.tv_item_date.setText(todo.getTodoDate());

     }

     /**
      * Returns the total number of items in the data set held by the adapter.
      *
      * @return The total number of items in this adapter.
      */
     @Override
     public int getItemCount() {
          return todoList.size();
     }



       class TodoViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView tv_itemId, tv_item_title, tv_item_description, tv_item_date;
            public TodoViewHolder(@NonNull View itemView) {
                 super(itemView);

                 itemView.setOnClickListener(this);
                 tv_itemId=itemView.findViewById(R.id.tv_todo_item_id);
                 tv_item_title=itemView.findViewById(R.id.tv_todo_title);
                 tv_item_description=itemView.findViewById(R.id.tv_todo_item_description);
                 tv_item_date=itemView.findViewById(R.id.tv_todo_item_date);

            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                 Todo todo=todoList.get(getAdapterPosition());
                 Bundle bundle= new Bundle();
                 long  itemId=todo.getTodo_item_id();
                 bundle.putLong("itemId", itemId);
                 //navigate to details view

                 Navigation.findNavController(v).navigate(R.id.action_ListFragment_to_updateToDoItemFragment, bundle);


            }
       }

     public void todoList(List<Todo> todoList){
          this.todoList=todoList;
          notifyDataSetChanged();
     }

     public Todo getTodoItemPosition(int position){
          return todoList.get(position);
     }

}
