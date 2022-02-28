package com.app.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yalahwy.R;
import com.app.yalahwy.databinding.OrderRowBinding;
import com.app.yalahwy.databinding.SearchRowBinding;
import com.app.yalahwy.models.BankDataModel;
import com.app.yalahwy.models.OrderModel;
import com.app.yalahwy.ui.activity_home.fragments.Fragment_Orders;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyHolder> {

    private List<OrderModel> list;
    private Context context;
    private Fragment_Orders fragment_orders;

    public OrdersAdapter(List<OrderModel> list, Context context, Fragment_Orders fragment_orders) {
        this.list = list;
        this.context = context;
        this.fragment_orders = fragment_orders;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        OrderModel model = list.get(position);
        holder.binding.setModel(model);

        holder.itemView.setOnClickListener(view -> {
            OrderModel model2 = list.get(holder.getAdapterPosition());
            fragment_orders.setItemData(model2);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private OrderRowBinding binding;

        public MyHolder(OrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
