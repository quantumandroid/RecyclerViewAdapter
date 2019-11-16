// how to use


1)TODO In row_item layout

<data>

  <variable
      name="mData"
      type="Your Data Model" />
  <!--
  android:text="@={mData.field}"
  -->

  <variable
      name="mClick"
      type="com.recyclerview.ItemClickListener" />

  <!--
  android:onClick="@{() -> itemClickListener.onItemClick(data)}"
  -->

</data>

  .....

<TextView
   app:highlight="@{mData.field}"
/>


2)TODO In activity layout

<data>

   <variable
      name="adapter"
      type="com.recyclerview.rvadapter.RecyclerViewAdapter" />

</data>

<EditText
   app:searchField='@{"fieldName"}'
/>

<android.support.v7.widget.RecyclerView
   android:adapter="@{adapter}"
   app:layoutManager="android.support.v7.widget.LinearLayoutManager"
/>

3)TODO In Activity Java

   RecyclerViewAdapter adapter = new RecyclerViewAdapter(objectList, null, R.layout.layout);
   adapter.setVariables(BR.mData, BR.itemClickListener, binding.search);
   mBinding.setAdapter(adapter);