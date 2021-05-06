package com.northerly.myfragmentsapp.View.Helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import kotlin.jvm.Synchronized;

abstract public class MySwipeHelper extends ItemTouchHelper.SimpleCallback {
    Context context;
    RecyclerView recyclerView;
    int buttonWidth;
    List<MyButton> buttonList = null;
    GestureDetector gestureDetector;
    int swipePosition = -1;
    float swipeTreshold = 0.5f;
    Map<Integer,List<MyButton>> buttonBuffer;
    Queue<Integer> removerQueue;

    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (MyButton button : buttonList){
                if(button.onClick(e.getX(), e.getY())){
                    break;
                }
            }
            return true;
        }
    };

   public View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(swipePosition < 0) return false;
            Point point = new Point((int) event.getRawX(), (int) event.getRawY());

            RecyclerView.ViewHolder swipeVeiwHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition);
            View swipedItem = swipeVeiwHolder.itemView;
            Rect rect = new Rect();
            swipedItem.getGlobalVisibleRect(rect);

            if(event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_MOVE ||
                    event.getAction() == MotionEvent.ACTION_UP
            ){
                if(rect.top < point.y && rect.bottom > point.y){
                    gestureDetector.onTouchEvent(event);
                }
                else {
                    removerQueue.add(swipePosition);
                    swipePosition = -1;
                    recoverSwipItem();
                }
            }
            return false;
        }
    };

    public MySwipeHelper(Context context, RecyclerView recyclerView, int buttonWidth) {
        super(0, ItemTouchHelper.LEFT);
        this.context = context;
        this.recyclerView = recyclerView;
        this.buttonWidth = buttonWidth;
        this.buttonList = new ArrayList<>();
        this.gestureDetector = new GestureDetector(context, gestureListener);
        this.recyclerView = recyclerView;
        this.buttonBuffer = new HashMap<>();
        this.removerQueue = new IntLinkedList();
        this.recyclerView.setOnTouchListener(onTouchListener);
        attachSwipe();

    }

    void attachSwipe(){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

   class IntLinkedList extends LinkedList<Integer>
    {
        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return indexOf(o);
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return indexOf(o);
        }

        @Override
        public boolean add(Integer integer) {
            if(contains(integer))
                return false;
            else
            return super.add(integer);
        }
    }
    public abstract void instanciateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer);

    private synchronized void recoverSwipItem(){
    while (!removerQueue.isEmpty()){
        int pos = removerQueue.poll();
        if(pos > -1){
            recyclerView.getAdapter().notifyItemChanged(pos);
        }
    }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        if(swipePosition != pos){
            removerQueue.add(swipePosition);
            swipePosition = pos;
        }
        if(buttonBuffer.containsKey(swipePosition))
            buttonList = buttonBuffer.get(swipePosition);
        else {
            buttonList.clear();
            buttonBuffer.clear();
            swipeTreshold = 0.5f*buttonList.size()*buttonWidth;
            recoverSwipItem();
        }
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return swipeTreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f*defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f*defaultValue;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos = viewHolder.getAdapterPosition();
        float translationX = dX;
        View itemView = viewHolder.itemView;
        if(pos < 0){
            swipePosition = pos;
            return;
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(dX < 0){
                List<MyButton> buffer = new ArrayList<>();
                if(!buttonBuffer.containsKey(pos)){
                    instanciateMyButton(viewHolder,buffer);
                }
                else {
                    buffer = buttonBuffer.get(pos);
                }
            translationX = dX*buffer.size()*buttonWidth  / itemView.getWidth();
                drawButton(c, itemView, buffer, pos, translationX);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
    }

    private void drawButton(Canvas c, View itemView, List<MyButton> buffer, int pos, Float translationX) {
        Float right = Float.valueOf(itemView.getRight());
        Float dButtonWidth = -1*translationX / buffer.size();

        for (MyButton button : buffer)
        {
            Float left = right - dButtonWidth;
            button.onDraw(c, new RectF(left, itemView.getTop(), right, itemView.getBottom()), pos);
            right = left;
        }
    }
}
