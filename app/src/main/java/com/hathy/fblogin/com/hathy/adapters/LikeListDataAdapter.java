package com.hathy.fblogin.com.hathy.adapters;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hathy.fblogin.ActionBarCallBack;
import com.hathy.fblogin.LikesMainPage;
import com.srv.fblogin.R;

public class LikeListDataAdapter extends
        RecyclerView.Adapter<LikeListDataAdapter.ViewHolder> implements AdapterView.OnItemClickListener {
    private List<LikeListItems> stList;
    Context mContext;
    private ActionMode mActionMode;

    public LikeListDataAdapter(List<LikeListItems> LikeList, Context mContext) {
        this.stList = LikeList;
        this.mContext = mContext;

    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.likelist_row_adapter, null);
        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

 /*       itemLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Test", "Parent clicked");

            }
        });*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final int pos = position;


        viewHolder.tvName.setText(stList.get(position).getName());

        //viewHolder.tvEmailId.setText(stList.get(position).getEmailId());

        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));
        if (stList.get(position).getCheckAll())
            viewHolder.chkSelected.setChecked(true);

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
/*                CheckBox cb = (CheckBox) view;
                LikeListItems contact = (LikeListItems) cb.getTag();
                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());*/

            }
        });

       viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                LikeListItems contact = (LikeListItems) cb.getTag();
                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());
                //LikeListDataAdapter.startActionMode(new ActionBarCallBack(Context));
                // ((LikesMainPage) mContext).startActionMode(new ActionBarCallBack(mContext));
    /*              if (mActionMode != null)
                    mActionMode.setTitle(String.valueOf("0") + " selected");*/

            }
        });
    }

    public class CheckBoxClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            Log.e("entee", "viki");
        }
    }


    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }


    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemClickListener clickListener;
        public TextView tvName;
        //public TextView tvEmailId;
        public CheckBox chkSelected;
        public LikeListItems singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);

            //tvEmailId = (TextView) itemLayoutView.findViewById(R.id.tvEmailId);
            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.chkSelected);
            itemView.setOnClickListener(this);

        }

        public void setClickListener(ItemClickListener itemClickListener) {

            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
    }

    // method to access in activity after updating selection
    public List<LikeListItems> getLikeList() {
        return stList;
    }

}
