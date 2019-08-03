package com.diskin.alon.appsbrowser.browser;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.diskin.alon.appsbrowser.browser.databinding.UserAppBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAppsAdapter extends RecyclerView.Adapter<UserAppsAdapter.UserAppHolder>{

    @NonNull
    private List<UserApp> apps;

    public UserAppsAdapter() {
        this.apps = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserAppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserAppBinding binding = UserAppBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);

        return new UserAppHolder(binding);
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
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new UserAppsDiffCallback(this.apps,appsUpdate));

        diffResult.dispatchUpdatesTo(this);
        apps.clear();
        apps.addAll(new ArrayList<>(appsUpdate));
    }

    public static class UserAppHolder extends RecyclerView.ViewHolder {

        @NonNull
        private UserAppBinding binding;

        public UserAppHolder(@NonNull UserAppBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull UserApp app) {
            binding.setApp(app);
            binding.executePendingBindings();
        }
    }
}
