package com.example.chatroom.ui.main;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatroom.R;
import com.example.chatroom.base.BaseActivity;
import com.example.chatroom.base.BaseRecyclerViewAdapter;
import com.example.chatroom.base.BaseViewHolder;
import com.example.chatroom.data.entity.ChatMessage;
import com.example.chatroom.utils.ColorUtils;
import com.example.chatroom.utils.DateTimeUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.rvMessage)
    RecyclerView rvMessage;

    @BindView(R.id.etMessage)
    EditText etMessage;

    private MainViewModel mMainViewModel;

    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        tvTitle.setText(R.string.app_name);

        initView();
        initViewModel();
    }

    private void initView() {
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        rvMessage.setAdapter(adapter = new MessageAdapter(this, R.layout.chat_message_item));
    }

    private void initViewModel() {
        mMainViewModel = obtainViewModel(MainViewModel.class);
        observeBaseLiveData(mMainViewModel);

        mMainViewModel.chatMessageList().observe(this, chatMessageList -> {
            // show
            adapter.setData(chatMessageList);

            // scroll to bottom
            if (adapter.getItemCount() > 0) {
                rvMessage.smoothScrollToPosition(adapter.getItemCount());
            }
        });
        mMainViewModel.isClose().observe(this, isClose -> {
            toast(R.string.close);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainViewModel.onResume();
    }

    @Override
    protected void onPause() {
        mMainViewModel.onPause();
        super.onPause();
    }

    @OnClick(R.id.btnSend)
    public void onSendClick(View view) {
        mMainViewModel.sendMessage(etMessage.getText().toString());
        etMessage.setText("");
    }

    private class MessageAdapter extends BaseRecyclerViewAdapter<ChatMessage> {

        public MessageAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(Context context, BaseViewHolder holder, ChatMessage chatMessage) {
            TextView tvMessage = holder.getView(R.id.tvMessage);
            tvMessage.setText(getMessage(chatMessage, holder.getAdapterPosition()));
        }

        private SpannableString getMessage(ChatMessage chatMessage, int position) {
            SpannableString span = new SpannableString(String.format("%s : %s", chatMessage.getUser_name(), chatMessage.getMessage()));
            span.setSpan(new ForegroundColorSpan(ColorUtils.getColor(MainActivity.this, position)), 0, chatMessage.getUser_name().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return span;
        }
    }
}
