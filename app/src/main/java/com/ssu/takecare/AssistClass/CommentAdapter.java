package com.ssu.takecare.AssistClass;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssu.takecare.R;
import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private TextView name, comment;
    private ArrayList<Comment> commentList = new ArrayList<>();
    private ArrayList<Integer> commentIdList = new ArrayList<>();
    private ArrayList<String> AuthorNameList = new ArrayList<>();
    private LinearLayout messageContainer;

    public CommentAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public void remove(int position) {
        commentList.remove(position);
        commentIdList.remove(position);
        AuthorNameList.remove(position);
    }

    public void edit(int position, Comment comment) {
        commentList.set(position, comment);
    }

    public void addCommentId(int commentId) {
        commentIdList.add(commentId);
    }

    public int getCommentId(int position) {
        return commentIdList.get(position);
    }

    public void addAuthorName(String name) {
        AuthorNameList.add(name);
    }

    public String getAuthorName(int position) {
        return AuthorNameList.get(position);
    }

    @Override
    public void add(Comment object) {
        commentList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.commentList.size();
    }

    @Override
    public Comment getItem(int index) {
        return this.commentList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            // inflator를 생성하여 activity_chat.xml을 읽어서 View 객체로 생성한다.
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_comment, parent, false);
        }

        name = (TextView) row.findViewById(R.id.tv_comment_name);
        comment = (TextView) row.findViewById(R.id.tv_comment);
        messageContainer = (LinearLayout) row.findViewById(R.id.your_message_container);

        // ArrayList에 들어 있는 채팅 문자열을 읽어들여 inflater를 이용해서 생성한 View에 ChatMessage를 삽입한다.
        Comment commentObj = getItem(position);
        name.setText(AuthorNameList.get(position));
        comment.setText(commentObj.message);

        boolean flag = false;
        if (name.getText().equals("다른 보호자"))
            flag = true;

        // 경우에 따라 두 가지로 나눠 나타낼 수 있다.
        comment.setBackgroundResource(commentObj.left ? (flag ? R.drawable.bubble_other : R.drawable.bubble_you) : R.drawable.bubble_me);
        messageContainer.setGravity(commentObj.left ? Gravity.LEFT : Gravity.RIGHT);

        return row;
    }
}
