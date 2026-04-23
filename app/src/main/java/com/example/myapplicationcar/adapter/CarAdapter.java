package com.example.myapplicationcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.model.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> cars;
    private final LayoutInflater inflater;

    public CarAdapter(Context context, List<Car> cars) {
        this.cars = cars;
        this.inflater = LayoutInflater.from(context);
    }
    public void updateData(List<Car> newCars) {//оновлення списку після пошуку
        this.cars = newCars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);

        holder.imageView.setImageResource(car.getImageResource());
        holder.tvTitle.setText(car.getBrand() + ", " + car.getModel() + " (" + car.getYear() + ")");
        holder.tvDescription.setText(car.getDescription());
        holder.tvCost.setText(car.getCost() + "$");
    }

    @Override
    public int getItemCount() { return cars.size(); }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView tvTitle;
        final TextView tvDescription;
        final TextView tvCost;

        CarViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.carImage);
            tvTitle = view.findViewById(R.id.carTitle);
            tvDescription = view.findViewById(R.id.carDescription);
            tvCost = view.findViewById(R.id.carCost);
        }
    }
}