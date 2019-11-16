package com.rvadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Object> dataList;
    private List<Object> dataSource;
    private ItemClickListener listener;
    private int itemLayout;
    private DataFilter mFilter;
    private int data = 0;
    private int clickListener = 0;

    public RecyclerViewAdapter(List<Object> dataSource, ItemClickListener listener, int itemLayout) {

        this.dataList = dataSource;
        this.dataSource = dataSource;
        this.listener = listener;
        this.itemLayout = itemLayout;

    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding viewDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemLayout, parent, false);

        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object dataModel = dataList.get(position);
        holder.bind(dataModel);
        if (listener != null)
            holder.viewDataBinding.setVariable(clickListener, listener);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public Filter getFilter() {

        if (mFilter == null) {

            mFilter = new DataFilter();
        }

        return mFilter;
    }

    public TextWatcher getWatcher() {


            return new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    getFilter().filter(arg0);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {

                }
            };



    }

    public void setVariables(int data, int clickListener, EditText search) {
        this.data = data;
        this.clickListener = clickListener;

        search.addTextChangedListener(this.getWatcher());

    }

    private String getMethodName(String searchField) {
        String field = searchField.substring(0, 1).toUpperCase() + searchField.substring(1);
        return "get" + field;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewDataBinding viewDataBinding;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public void bind(Object obj) {
            viewDataBinding.setVariable(data, obj);
            viewDataBinding.executePendingBindings();

        }

    }

    private class DataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results = new FilterResults();

            ArrayList<Object> filterList = new ArrayList<>();

            if (charSequence != null && charSequence.length() > 0) {

                for (int i = 0; i < dataSource.size(); i++) {

                    Object object = dataSource.get(i);

                    String searchedString = charSequence.toString();

                    Class cls = object.getClass();

                    try {

                        String methodName = getMethodName(SearchAdapter.searchFieldName);
                        Method method = cls.getDeclaredMethod(methodName, (Class<?>[]) null);
                        String fullString = (String) method.invoke(object, (Object[]) null);

                        if (fullString.toUpperCase().contains(searchedString.toUpperCase())) {

                            filterList.add(object);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }

                results.count = filterList.size();
                results.values = filterList;

            } else {

                results.count = dataSource.size();
                results.values = dataSource;

            }

            return results;

        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            SearchAdapter.mSearchString = constraint.toString();
            dataList = (ArrayList<Object>) results.values;
            notifyDataSetChanged();
        }
    }

}

