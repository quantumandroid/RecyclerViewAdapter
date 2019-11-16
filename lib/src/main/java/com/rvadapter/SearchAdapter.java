package com.rvadapter;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class SearchAdapter {

    public static String mSearchString = "";
    public static String searchFieldName = "";

    public SearchAdapter() {
    }

    @BindingAdapter({"highlight"})
    public static void highlightText(TextView textView, String fullText) {

        Spannable spannable = highlight(fullText, mSearchString);
        textView.setText(spannable);

    }

    @BindingAdapter({"searchField"})
    public static void setSearchField(EditText editText, String name) {
        searchFieldName = name;

    }

    private static Spannable highlight(String fullText, String mSearchText) {

        Spannable spannable;

        if (mSearchText != null && !mSearchText.isEmpty()) {

            int startPos = fullText.toLowerCase(Locale.US).indexOf(mSearchText.toLowerCase(Locale.US));
            int endPos = startPos + mSearchText.length();

            if (startPos != -1) {
                spannable = new SpannableString(fullText);
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.NORMAL, -1, blueColor, null);
                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannable;
            } else {
                return new SpannableString(fullText);
            }

        } else {
            return new SpannableString(fullText);
        }

    }

}
