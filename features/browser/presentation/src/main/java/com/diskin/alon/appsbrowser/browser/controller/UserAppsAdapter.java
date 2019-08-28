package com.diskin.alon.appsbrowser.browser.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.model.UserApp;

import java.util.ArrayList;
import java.util.List;

public class UserAppsAdapter extends RecyclerView.Adapter<UserAppsAdapter.UserAppHolder>{
    @NonNull
    private List<UserApp> apps;
    @NonNull
    private final UserAppClickListener listener;

    public UserAppsAdapter(@NonNull UserAppClickListener listener) {
        this.listener = listener;
        this.apps = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserAppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.user_app,parent,false);

        return new UserAppHolder(binding,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAppHolder holder, int position) {
        holder.bind(apps.get(position));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public void updateApps(@NonNull List<UserApp> appsUpdate) {
        DiffUtil.Callback callback = new UserAppsDiffCallback(this.apps,appsUpdate);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);

        this.apps.clear();
        this.apps.addAll(appsUpdate);
        diffResult.dispatchUpdatesTo(this);
    }

    public static class UserAppHolder extends RecyclerView.ViewHolder {
        @NonNull
        private ViewDataBinding binding;

        public UserAppHolder(@NonNull ViewDataBinding binding, @NonNull UserAppClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setVariable(BR.clickListener,listener);
        }

        public void bind(@NonNull UserApp app) {
            binding.setVariable(BR.app,app);
            binding.executePendingBindings();
        }
    }

    public interface UserAppClickListener {
        void onClick(@NonNull UserApp app);
    }
}
