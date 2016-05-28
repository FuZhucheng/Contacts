package com.android.administrator.contacts_fuzhucheng.utils.db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.administrator.contacts_fuzhucheng.R;
import com.android.administrator.contacts_fuzhucheng.ui.ContactsBean;
import com.android.administrator.contacts_fuzhucheng.utils.ViewHolder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/4/23.
 */
public class MyAdapter extends BaseAdapter  {
    private List<ContactsBean> listContacts;

    public void setData( List<ContactsBean> listContacts){
        this.listContacts= listContacts;
        notifyDataSetChanged();
    }



    private LayoutInflater mInflater = null;

    public MyAdapter(Context context)
    {
        //根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.mInflater = LayoutInflater.from(context);
    }
//    /**
//     * 当ListView数据发生变化时,调用此方法来更新ListView
//     * @param listContacts
//     */
//    public void updateListView(List<ContactsBean> listContacts){
//        this.listContacts = listContacts;
//        notifyDataSetChanged();
//    }
    @Override
    public int getCount() {
        //How many items are in the data set represented by this Adapter.
        //在此适配器中所代表的数据集中的条目数
        return listContacts.size();
    }
    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.
        //获取数据集中与指定索引对应的数据项,,给定索引值，得到索引值对应的对象
        return listContacts.get(position);
    }
    @Override
    public long getItemId(int position) {
        //Get the row id associated with the specified position in the list.
        //获取在列表中与指定索引对应的行id,,,,, 获取条目id
        return position;
    }

    public void remove(int position){
        listContacts.remove(position);
    }
    //Get a View that displays the data at the specified position in the data set.
    //获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        final ContactsBean mContent=listContacts.get(position);

        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.item, null);
            holder.img = (ImageView)convertView.findViewById(R.id.image);
            holder.title = (TextView)convertView.findViewById(R.id.name);
            holder.info = (TextView)convertView.findViewById(R.id.mobilephone);
            holder.remark=(TextView)convertView.findViewById(R.id.remark);

//            convertView.setOnClickListener(this);

            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
//        Log.e("title",(String) data.get(position).get("title"));


        //根据position获取分类的首字母的Char ascii值
//        int section = getSectionForPosition(position);

        if (listContacts.get(position).getPhoto()==null) {
//            holder.img.setBackgroundResource(listContacts.get(position).getPhoto());
            holder.img.setImageResource(R.drawable.image_contacts);
        }
        else {
            holder.img.setImageBitmap(listContacts.get(position).getPhoto());
//        holder.img.setImageResource(R.drawable.image_contacts);
        }
        holder.title.setText(listContacts.get(position).getDisplayName());
        holder.info.setText(listContacts.get(position).getPhoneNumber());
        holder.remark.setText(listContacts.get(position).getRemark());


//        convertView.setTag(123,position);
        return convertView;
    }

//    @Override
//    public void onClick(View v) {
//        Object obj=v.getTag(123);
//        final int index=Integer.valueOf(obj.toString());
//    }

//    /**
//     * 根据ListView的当前位置获取分类的首字母的Char ascii值
//     */
//    public int getSectionForPosition(int position) {
//        return listContacts.get(position).getSortLetters().charAt(0);
//    }
//
//    /**
//     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
//     */
//    public int getPositionForSection(int section) {
//        for (int i = 0; i < getCount(); i++) {
//            String sortStr = listContacts.get(i).getSortLetters();
//            char firstChar = sortStr.toUpperCase().charAt(0);
//            if (firstChar == section) {
//                return i;
//            }
//        }
//
//        return -1;
//    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
//    private String getAlpha(String str) {
//        String  sortStr = str.trim().substring(0, 1).toUpperCase();
//        // 正则表达式，判断首字母是否是英文字母
//        if (sortStr.matches("[A-Z]")) {
//            return sortStr;
//        } else {
//            return "#";
//        }
//    }

//    @Override
//    public Object[] getSections() {
//        return null;
//    }

}