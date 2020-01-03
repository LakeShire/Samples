package com.example.samples.view;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

// 标签的换行
public class RichEditText extends AppCompatEditText {
    public RichEditText(Context context) {
        super(context);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 替换默认EditableFactory
        // 因为在setText的时候 会转化为editable 其中NoCopySpan不会被复制 而SpanWatcher就是一个
        // 改变光标位置时 原有的Span会变化 因此可以限制光标不出现在Span的中间
        // 需要考虑单点和选中范围的情况
        setEditableFactory(new Editable.Factory() {
            @Override
            public Editable newEditable(CharSequence source) {
                Editable ss = new SpannableStringBuilder(source);
                ss.setSpan(new SpanWatcher() {
                    @Override
                    public void onSpanAdded(Spannable text, Object what, int start, int end) {
                    }

                    @Override
                    public void onSpanRemoved(Spannable text, Object what, int start, int end) {

                    }

                    @Override
                    public void onSpanChanged(Spannable text, Object what, int ostart, int oend, int nstart, int nend) {
                        if (what == Selection.SELECTION_END) {
                            ReplacementSpan[] spans = text.getSpans(nstart, nend-1, ReplacementSpan.class);
                            if (spans != null && spans.length == 1) {
                                int start = text.getSpanStart(spans[0]);
                                int end = text.getSpanEnd(spans[0]);
                                Selection.setSelection(text, Selection.getSelectionStart(text), start);
                            }
                        } else if (what == Selection.SELECTION_START) {
                            ForegroundColorSpan[] spans = text.getSpans(nstart+1, nend, ForegroundColorSpan.class);
                            if (spans != null && spans.length == 1) {
                                int start = text.getSpanStart(spans[0]);
                                int end = text.getSpanEnd(spans[0]);
                                Selection.setSelection(text, end, Selection.getSelectionEnd(text));
                            }
                        }
                    }
                }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                return ss;
            }
        });
        // 处理DEL键
        // 键盘事件都会触发两次：DOWN的时候时删除前，UP的时候就已经删除了，所以要拦截DOWN事件
        // ReplaceSpan删除的时候就会整体删掉，不用再额外处理了
//        setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_DEL) {
//                    // 有两个事件 拦截DOWN是在删除前
//                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                        Editable editable = getText();
//                        if (editable != null) {
//                            int start = Selection.getSelectionStart(editable);
//                            int end = Selection.getSelectionEnd(editable);
//                            ReplacementSpan[] spans = editable.getSpans(start, end, ReplacementSpan.class);
//                            if (spans != null && spans.length == 1) {
//                                int s = editable.getSpanStart(spans[0]);
//                                int e = editable.getSpanEnd(spans[0]);
//                                // 处理选区
//                                if (end > e || start < s) {
//                                    return false;
//                                }
//                                editable.removeSpan(spans[0]);
//                                editable.delete(s, e);
//                                return true;
//                            }
//                        }
//                    }
//                }
//                return false;
//            }
//        });
    }
}
