package com.iot.covid.duantotnghiep.doctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.covid.duantotnghiep.databinding.ItemListNoticeBinding;
import com.iot.covid.duantotnghiep.doctor.NoticeDetails;
import com.iot.covid.duantotnghiep.model.notification.NotificationDTO;

import java.util.List;

public class ListNotifyAdapter extends RecyclerView.Adapter<ListNotifyAdapter.ViewHolder> {
    private List<NotificationDTO> notificationDTOList;
private Context context;

    public ListNotifyAdapter(Context context) {
        this.context = context;
    }

    public void setNotificationDTOList(List<NotificationDTO> notificationDTOList) {
        this.notificationDTOList = notificationDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListNoticeBinding binding = ItemListNoticeBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.noticeBinding.setNotify(notificationDTOList.get(position));
        Log.e("adapter - notify",""+notificationDTOList.size());
holder.noticeBinding.executePendingBindings();
holder.noticeBinding.cvItemNotify.setOnClickListener(view->{
    Intent intent = new Intent(context, NoticeDetails.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable("notify",notificationDTOList.get(position));
    intent.putExtras(bundle);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
});
    }

    @Override
    public int getItemCount() {
        if (notificationDTOList!=null)return notificationDTOList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
ItemListNoticeBinding noticeBinding;
        public ViewHolder(@NonNull ItemListNoticeBinding noticeBinding) {
            super(noticeBinding.getRoot());
            this.noticeBinding = noticeBinding;
        }
    }
}
