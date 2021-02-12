package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.digidoctor.android.interfaces.AddressInterface;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.AddAdressModel;
import com.digidoctor.android.model.pharmacyModel.DeleteAddress;
import com.digidoctor.android.model.pharmacyModel.getaddressModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressAdapterVH> {
    public List<getaddressModel.getaddressDetails> getadd;
    AddressInterface addressInterface;
    String Full_name, House, Mobile, City, ZipCode, area, State, locality, isDefault, setIsSundayOpen, setIsSaturdayOpen, setAddressType;
    String AddressId = null;
    private Activity activity;


    public AddressAdapter(List<getaddressModel.getaddressDetails> getadd, Activity activity) {
        this.getadd = getadd;
        this.activity = activity;
        this.addressInterface = addressInterface;
    }


    @NonNull
    @Override
    public AddressAdapter.AddressAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AddressviewBinding addressviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.addressview, parent, false);
        return new AddressAdapter.AddressAdapterVH(addressviewBinding);

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


        holder.addressviewBinding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddAdressModel addAdressModel1 = new AddAdressModel();
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

                addAdressModel1.setAddressId(addAdressModel.getAddressId());
                addAdressModel1.setMemberId("221261");
                addAdressModel1.setName(addAdressModel.getName());
                addAdressModel1.setHouseNo(addAdressModel.getHouseNo());
                addAdressModel1.setMobileno(addAdressModel.getMobileNo());
                addAdressModel1.setCity(addAdressModel.getCity());
                addAdressModel1.setPincode(addAdressModel.getPincode());
                addAdressModel1.setArea(addAdressModel.getArea());
                addAdressModel1.setState(addAdressModel.getState());
                addAdressModel1.setLocality(addAdressModel.getLocality());
                addAdressModel1.setIsDefault("1");
                addAdressModel1.setAddressType(addAdressModel.getAddressType());
                addAdressModel1.setIsSaturdayOpen(setIsSaturdayOpen);
                addAdressModel1.setIsSundayOpen(setIsSaturdayOpen);

                ApiUtils.update_Address(addAdressModel1, activity, new ApiCallbackInterface() {
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

            }
        });

        holder.addressviewBinding.imageView52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = '\n' + addAdressModel.getHouseNo() + ' ' + addAdressModel.getCity() + ' ' + addAdressModel.getPincode() + '\n';
                new AlertDialog.Builder(activity).setTitle("Delete Address")
                        .setMessage("Do you really want to delete this " + Name + " address?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteaddress(position, addAdressModel);
                                getadd.remove(position);
                                notifyDataSetChanged();
                            }


                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

            }
        });

        holder.addressviewBinding.imageView51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            }
        });


    }

    private void selectdefault() {


    }

    public void deleteaddress(final int position, getaddressModel.getaddressDetails addAdressModel) {

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

    public class AddressAdapterVH extends RecyclerView.ViewHolder {
        AddressviewBinding addressviewBinding;

        public AddressAdapterVH(@NonNull AddressviewBinding itemView) {

            super(itemView.getRoot());
            addressviewBinding = itemView;

        }
    }
}
