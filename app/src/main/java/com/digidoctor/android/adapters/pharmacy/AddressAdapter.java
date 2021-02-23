package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.AddressviewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.AddAddressModel;
import com.digidoctor.android.model.pharmacyModel.DeleteAddress;
import com.digidoctor.android.model.pharmacyModel.getaddressModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressAdapterVH> {
    public List<getaddressModel.getaddressDetails> getadd;

    String setIsSundayOpen, setIsSaturdayOpen;

    private final Activity activity;


    public AddressAdapter(List<getaddressModel.getaddressDetails> getadd, Activity activity) {
        this.getadd = getadd;
        this.activity = activity;
    }


    @NonNull
    @Override
    public AddressAdapter.AddressAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AddressviewBinding addressviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.addressview, parent, false);
        return new AddressAdapterVH(addressviewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final AddressAdapter.AddressAdapterVH holder, final int position) {


        final getaddressModel.getaddressDetails addAdressModel = getadd.get(position);
        holder.addressviewBinding.textView28.setText(addAdressModel.getName());


        holder.addressviewBinding.textView29.setText(addAdressModel.getHouseNo() + ' ' + addAdressModel.getArea() + ' ' + addAdressModel.getCity() + '\n' + addAdressModel.getState() + ' ' + addAdressModel.getPincode());
        holder.addressviewBinding.textView30.setText(addAdressModel.getMobileNo());


        if (addAdressModel.getIsDefault().equals("true")) {
            holder.addressviewBinding.textView132.setVisibility(View.VISIBLE);
        }


        holder.addressviewBinding.cardView.setOnClickListener(view -> {

            AddAddressModel addAddressModel1 = new AddAddressModel();
            if (addAdressModel.getIsSaturdayOpen().equals("true")) {
                setIsSaturdayOpen = "1";
            } else {
                setIsSaturdayOpen = "0";
            }

            if (addAdressModel.getIsSundayOpen().equals("true")) {
                setIsSundayOpen = "1";
            } else {
                setIsSundayOpen = "0";
            }

            addAddressModel1.setAddressId(addAdressModel.getAddressId());
            addAddressModel1.setMemberId("221261");
            addAddressModel1.setName(addAdressModel.getName());
            addAddressModel1.setHouseNo(addAdressModel.getHouseNo());
            addAddressModel1.setMobileno(addAdressModel.getMobileNo());
            addAddressModel1.setCity(addAdressModel.getCity());
            addAddressModel1.setPincode(addAdressModel.getPincode());
            addAddressModel1.setArea(addAdressModel.getArea());
            addAddressModel1.setState(addAdressModel.getState());
            addAddressModel1.setLocality(addAdressModel.getLocality());
            addAddressModel1.setIsDefault("1");
            addAddressModel1.setAddressType(addAdressModel.getAddressType());
            addAddressModel1.setIsSaturdayOpen(setIsSaturdayOpen);
            addAddressModel1.setIsSundayOpen(setIsSaturdayOpen);

            ApiUtils.update_Address(addAddressModel1, activity, new ApiCallbackInterface() {
                @Override
                public void onSuccess(List<?> o) {
                    AppUtils.hideDialog();

                    notifyDataSetChanged();

                }

                @Override
                public void onError(String s) {
                    AppUtils.hideDialog();
                    Toast.makeText(activity, "" + s, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    AppUtils.hideDialog();
                    Toast.makeText(activity, "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Toast.makeText(activity, "Address has been set Default", Toast.LENGTH_SHORT).show();

            PatientDashboard.getInstance().navigate(R.id.orderSummaryFragment);
            AppUtils.hideDialog();

        });

        holder.addressviewBinding.imageView52.setOnClickListener(view -> {
            String Name = '\n' + addAdressModel.getHouseNo() + ' ' + addAdressModel.getCity() + ' ' + addAdressModel.getPincode() + '\n';
            new AlertDialog.Builder(activity).setTitle("Delete Address")
                    .setMessage("Do you really want to delete this " + Name + " address?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        deleteaddress(addAdressModel);
                        getadd.remove(position);
                        notifyDataSetChanged();
                    }).setNegativeButton("No", (dialogInterface, i) -> {

            }).show();

        });

        holder.addressviewBinding.imageView51.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("AddressID", addAdressModel.getAddressId());
            bundle.putString("MemberId", String.valueOf(utils.getPrimaryUser(activity).getMemberId()));
            bundle.putString("name", addAdressModel.getName());
            bundle.putString("mobileNo", addAdressModel.getMobileNo());
            bundle.putString("houseNo", addAdressModel.getHouseNo());
            bundle.putString("area", addAdressModel.getArea());
            bundle.putString("pincode", addAdressModel.getPincode());
            bundle.putString("state", addAdressModel.getState());
            bundle.putString("city", addAdressModel.getCity());
            bundle.putString("locality", addAdressModel.getLocality());
            bundle.putString("isDefault", addAdressModel.getIsDefault());
            bundle.putString("isSundayOpen", addAdressModel.getIsSundayOpen());
            bundle.putString("adtype", addAdressModel.getAddressType());
            bundle.putString("isSaturdayOpen", addAdressModel.getIsSaturdayOpen());

            Log.d("TAG", "onClickid: " + addAdressModel.getAddressId());
            PatientDashboard.getInstance().navigate(R.id.action_allAddressFragment_to_updateAddressFragment, bundle);

        });


    }


    public void deleteaddress(getaddressModel.getaddressDetails addAdressModel) {

        AppUtils.showRequestDialog(activity);

        DeleteAddress deleteAddress = new DeleteAddress(addAdressModel.getAddressId());
        ApiUtils.DeleteAddress(deleteAddress, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                Toast.makeText(activity, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();


            }

            @Override
            public void onError(String s) {
                Toast.makeText(activity, "" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(activity, "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return getadd.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class AddressAdapterVH extends RecyclerView.ViewHolder {
        AddressviewBinding addressviewBinding;

        public AddressAdapterVH(@NonNull AddressviewBinding itemView) {

            super(itemView.getRoot());
            addressviewBinding = itemView;

        }
    }
}
