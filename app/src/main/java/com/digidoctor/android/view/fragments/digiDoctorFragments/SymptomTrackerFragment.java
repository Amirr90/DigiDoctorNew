package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentSymptomTrackerBinding;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.patientModel.AddMemberProblemModel;
import com.digidoctor.android.model.patientModel.AttributeModel;
import com.digidoctor.android.model.patientModel.GetAllProblemModel;
import com.digidoctor.android.model.patientModel.GetAllProblemRes;
import com.digidoctor.android.model.patientModel.GetAllSuggestedProblemModel;
import com.digidoctor.android.model.patientModel.GetAllSuggestedProblemRes;
import com.digidoctor.android.model.patientModel.GetAttributeListDataResp;
import com.digidoctor.android.model.patientModel.GetAttributeListModel;
import com.digidoctor.android.model.patientModel.GetAttributeListResp;
import com.digidoctor.android.model.patientModel.GetProblemsWithIconModel;
import com.digidoctor.android.model.patientModel.GetProblemsWithIconRes;
import com.digidoctor.android.utility.URLUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.AppUtils.capitalizeFirstLetter;
import static com.digidoctor.android.utility.AppUtils.parseDate;
import static com.digidoctor.android.utility.AppUtils.showToastSort;
import static com.digidoctor.android.utility.NewDashboardUtils.getNextWeekDays;
import static com.digidoctor.android.utility.NewDashboardUtils.isNetworkConnected;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.getUserForBooking;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;


public class SymptomTrackerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SymptomTrackerFragment";

    FragmentSymptomTrackerBinding binding;
    NavController navController;


    private Dialog dialog;

    private String problemDate = "";

    private List<GetAttributeListDataResp> getAttributeListDataRespList = new ArrayList<>();

    private List<GetAllSuggestedProblemModel> getAllSuggestedProblemModelList = new ArrayList<>();
    private List<GetAllProblemModel> getAllProblemModelList = new ArrayList<>();
    private AdapterProblems adapterProblems;

    private static JSONArray jsonArraySuggestedProblemProvable;

    private JSONArray tempArray = new JSONArray();

    private int hour = 0;
    private int minutes = 0;

    String customToken = "";
    String mobileNumber;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSymptomTrackerBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        init();
        setListeners();
    }


    private void init() {


        mobileNumber = getPrimaryUser(requireActivity()).getMobileNo();
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);
        ArrayList<HashMap<String, String>> daysList = new ArrayList<>();
        daysList.addAll(getNextWeekDays());

        jsonArraySuggestedProblemProvable = new JSONArray();

        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        binding.recyclerView.setNestedScrollingEnabled(false);

        if (isNetworkConnected(requireActivity())) {
            hitGetProblemsWithIcon();
        } else {
            showToastSort(requireActivity(), getString(R.string.noInternetConnection));
        }


        binding.tvDay1.setText(daysList.get(0).get("date"));
        binding.tvMonday.setText(daysList.get(0).get("day"));
        binding.iv1.setTag(daysList.get(0).get("dateSend"));
        problemDate = daysList.get(0).get("dateSend");

        binding.tvDay2.setText(daysList.get(1).get("date"));
        binding.tvTuesday.setText(daysList.get(1).get("day"));
        binding.iv2.setTag(daysList.get(1).get("dateSend"));

        binding.tvDay3.setText(daysList.get(2).get("date"));
        binding.tvWednesday.setText(daysList.get(2).get("day"));
        binding.iv3.setTag(daysList.get(2).get("dateSend"));

        binding.tvDay4.setText(daysList.get(3).get("date"));
        binding.tvThursday.setText(daysList.get(3).get("day"));
        binding.iv4.setTag(daysList.get(3).get("dateSend"));

        binding.tvDay5.setText(daysList.get(4).get("date"));
        binding.tvFriday.setText(daysList.get(4).get("day"));
        binding.iv5.setTag(daysList.get(4).get("dateSend"));

        binding.tvDay6.setText(daysList.get(5).get("date"));
        binding.tvSaturday.setText(daysList.get(5).get("day"));
        binding.iv6.setTag(daysList.get(5).get("dateSend"));

        binding.tvDay7.setText(daysList.get(6).get("date"));
        binding.tvSunday.setText(daysList.get(6).get("day"));
        binding.iv7.setTag(daysList.get(6).get("dateSend"));

    }

    private void setListeners() {
        binding.tvAddMoreSymptoms.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
        binding.iv1.setOnClickListener(this);
        binding.iv2.setOnClickListener(this);
        binding.iv3.setOnClickListener(this);
        binding.iv4.setOnClickListener(this);
        binding.iv5.setOnClickListener(this);
        binding.iv6.setOnClickListener(this);
        binding.iv7.setOnClickListener(this);
        binding.tvHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvAddMoreSymptoms:
                AlertAddMoreSymptoms();
                break;
            case R.id.tvSave:

                if (jsonArraySuggestedProblemProvable.length() == 0) {
                    showToastSort(requireActivity(), getString(R.string.select_problems));
                } else {
                    if (isNetworkConnected(requireActivity())) {
                        try {

                            JSONArray tempArray = new JSONArray();
                            Set<String> strings = new HashSet<>();

                            for (int i = 0; i < jsonArraySuggestedProblemProvable.length(); i++) {

                                String problemId = jsonArraySuggestedProblemProvable.getJSONObject(i).getString("problemId");
                                String attributeId = jsonArraySuggestedProblemProvable.getJSONObject(i).getString("attributeId");
                                String attributeValueId = jsonArraySuggestedProblemProvable.getJSONObject(i).getString("attributeValueId");

                                if (strings.contains(problemId) && strings.contains(attributeId) && strings.contains(attributeValueId)) {
                                    continue;
                                } else {
                                    strings.add(problemId);
                                    strings.add(attributeId);
                                    strings.add(attributeValueId);

                                    tempArray.put(jsonArraySuggestedProblemProvable.get(i));
                                }
                            }

                            jsonArraySuggestedProblemProvable = tempArray;

                            hitAddMemberProblem();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        showToastSort(requireActivity(), getString(R.string.noInternetConnection));
                    }
                }
                break;

            case R.id.iv1:
                setDefaultDaysView();
                setSelected(binding.iv1, binding.tvDay1, binding.tvMonday);
                problemDate = binding.iv1.getTag().toString();
                break;

            case R.id.iv2:
                setDefaultDaysView();
                setSelected(binding.iv2, binding.tvDay2, binding.tvTuesday);
                problemDate = binding.iv2.getTag().toString();
                break;

            case R.id.iv3:
                setDefaultDaysView();
                setSelected(binding.iv3, binding.tvDay3, binding.tvWednesday);
                problemDate = binding.iv3.getTag().toString();
                break;

            case R.id.iv4:
                setDefaultDaysView();
                setSelected(binding.iv4, binding.tvDay4, binding.tvThursday);
                problemDate = binding.iv4.getTag().toString();
                break;

            case R.id.iv5:
                setDefaultDaysView();
                setSelected(binding.iv5, binding.tvDay5, binding.tvFriday);
                problemDate = binding.iv5.getTag().toString();
                break;

            case R.id.iv6:
                setDefaultDaysView();
                setSelected(binding.iv6, binding.tvDay6, binding.tvSaturday);
                problemDate = binding.iv6.getTag().toString();
                break;

            case R.id.iv7:
                setDefaultDaysView();
                setSelected(binding.iv7, binding.tvDay7, binding.tvSunday);
                problemDate = binding.iv7.getTag().toString();
                break;

            case R.id.tvHistory:
                Log.d(TAG, "onClick: tvHistory");
                break;
        }
    }

    private void setSelected(ImageView view, TextView tvDay, TextView tvDate) {
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tvDay.setTextColor(getResources().getColor(R.color.white));
        tvDate.setTextColor(getResources().getColor(R.color.white));
    }

    private void setDefaultDaysView() {
        binding.iv1.setBackground(null);
        binding.iv2.setBackground(null);
        binding.iv3.setBackground(null);
        binding.iv4.setBackground(null);
        binding.iv5.setBackground(null);
        binding.iv6.setBackground(null);
        binding.iv7.setBackground(null);

        binding.tvDay1.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.tvMonday.setTextColor(getResources().getColor(R.color.black));
        binding.tvDay2.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.tvTuesday.setTextColor(getResources().getColor(R.color.black));
        binding.tvDay3.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.tvWednesday.setTextColor(getResources().getColor(R.color.black));
        binding.tvDay4.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.tvThursday.setTextColor(getResources().getColor(R.color.black));
        binding.tvDay5.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.tvFriday.setTextColor(getResources().getColor(R.color.black));
        binding.tvDay6.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.tvSaturday.setTextColor(getResources().getColor(R.color.black));
        binding.tvDay7.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.tvSunday.setTextColor(getResources().getColor(R.color.black));
    }

    private void hitGetProblemsWithIcon() {

        SymptomModel symptomModel = new SymptomModel();


        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<GetProblemsWithIconRes> call = iRestInterfaces.getProblemsWithIcon2(
                symptomModel);

        call.enqueue(new Callback<GetProblemsWithIconRes>() {
            @Override
            public void onResponse(@NotNull Call<GetProblemsWithIconRes> call, @NotNull Response<GetProblemsWithIconRes> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {

                    if (response.body().getResponseCode() == 1) {

                        binding.recyclerView.setAdapter(new AdapterProblemsIcon(response.body().getResponseValue()));

                    } else {

                        showToastSort(requireActivity(), response.body().getResponseMessage());
                    }

                }

                // AppUtils.hideDialog();

            }

            @Override
            public void onFailure(@NotNull Call<GetProblemsWithIconRes> call, @NotNull Throwable t) {

                //  AppUtils.hideDialog();
                showToastSort(requireActivity(), t.getMessage());
            }
        });
    }

    private class AdapterProblemsIcon extends RecyclerView.Adapter<AdapterProblemsIcon.HolderProblemsIcon> {
        List<GetProblemsWithIconModel> data;

        public AdapterProblemsIcon(List<GetProblemsWithIconModel> favList) {
            data = favList;
        }

        public HolderProblemsIcon onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderProblemsIcon(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_problems_with_icon, parent, false));
        }

        @SuppressLint({"SetTextI18n", "NewApi"})
        public void onBindViewHolder(final HolderProblemsIcon holder, final int position) {

            final GetProblemsWithIconModel getProblemsWithIconModel = data.get(position);

            holder.clMain.setBackground(getProblemsWithIconModel.isSelected() ? getResources().getDrawable(R.drawable.rectangle) : null);
            holder.clMain.setBackgroundTintList(getProblemsWithIconModel.isSelected() ? ContextCompat.getColorStateList(requireActivity(), R.color.colorAccent) : null);
            holder.tvName.setTextColor(getProblemsWithIconModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));

            holder.tvName.setText(capitalizeFirstLetter(data.get(position).getProblemName()));

            if (!data.get(position).getDisplayIcon().trim().equals("")) {
                Picasso.get()
                        .load(data.get(position).getDisplayIcon().trim())
                        .into(holder.ivImage);
            } else {
                holder.ivImage.setImageResource((R.drawable.circle));
                holder.ivImage.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.YellowColo), PorterDuff.Mode.SRC_IN);
            }

            holder.clMain.setOnClickListener(view -> {
                getProblemsWithIconModel.setSelected(!getProblemsWithIconModel.isSelected());

                holder.clMain.setBackground(getProblemsWithIconModel.isSelected() ? requireActivity().getResources().getDrawable(R.drawable.rectangle) : null);
                holder.clMain.setBackgroundTintList(getProblemsWithIconModel.isSelected() ? ContextCompat.getColorStateList(requireActivity(), R.color.colorAccent) : null);
                holder.tvName.setTextColor(getProblemsWithIconModel.isSelected() ? requireActivity().getResources().getColor(R.color.white) : requireActivity().getResources().getColor(R.color.black));

                if (data.get(position).getIsVisible().equals(1)
                        && getProblemsWithIconModel.isSelected()) {
                    AlertAttributeList(data.get(position).getProblemId().toString());
                } else {
                    try {

                        JSONArray tempArray = new JSONArray();

                        for (int i = 0; i < jsonArraySuggestedProblemProvable.length(); i++) {

                            String id = jsonArraySuggestedProblemProvable.getJSONObject(i).getString("problemId");

                            if (id.contains(data.get(position).getProblemId().toString())) {
                                continue;
                            } else {
                                tempArray.put(jsonArraySuggestedProblemProvable.getJSONObject(i));
                            }

                        }

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("problemId", data.get(position).getProblemId().toString());
                        jsonObject.put("attributeId", "0");
                        jsonObject.put("attributeValueId", "0");

                        if (getProblemsWithIconModel.isSelected()) {
                            tempArray.put(jsonObject);
                        } else {

                            for (int j = 0; j < tempArray.length(); j++) {
                                JSONObject obj = tempArray.getJSONObject(j);
                                if (obj.getString("problemId").equals(data.get(position).getProblemId().toString())) {
                                    // add this item in some collection i.e PublishedList, and later use this collection
                                    tempArray.remove(j);
                                }
                            }
                        }

                        jsonArraySuggestedProblemProvable = tempArray;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            });

        }

        public int getItemCount() {
            return data.size();
        }

        private class HolderProblemsIcon extends RecyclerView.ViewHolder {

            TextView tvName;

            ImageView ivImage;

            ConstraintLayout clMain;

            public HolderProblemsIcon(View itemView) {
                super(itemView);

                tvName = itemView.findViewById(R.id.tvName);
                ivImage = itemView.findViewById(R.id.ivImage);
                clMain = itemView.findViewById(R.id.clMain);

            }
        }

    }

    //GetAttributeList
    private void hitGetAttributeList(String problemId, final RecyclerView recyclerView, final ProgressBar progressBar) {

        AttributeModel model = new AttributeModel();
        model.setProblemId(problemId);
        model.setUserMobileNo(mobileNumber);
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<GetAttributeListResp> call = iRestInterfaces.getAttributeByProblem(
                model);

        call.enqueue(new Callback<GetAttributeListResp>() {
            @Override
            public void onResponse(@NotNull Call<GetAttributeListResp> call, @NotNull Response<GetAttributeListResp> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {

                    getAttributeListDataRespList = response.body().getResponseValue();

                    recyclerView.setAdapter(new AdapterAttribute(getAttributeListDataRespList));

                    progressBar.setVisibility(View.GONE);

                }

                //AppUtils.hideDialog();

            }

            @Override
            public void onFailure(@NotNull Call<GetAttributeListResp> call, @NotNull Throwable t) {

                //AppUtils.hideDialog();

                showToastSort(requireActivity(), t.getLocalizedMessage());
            }
        });
    }

    @SuppressLint("NewApi")
    public void AlertAttributeList(final String problemId) {
        dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.alert_attribute_list);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        ImageView ivClose;
        RecyclerView recyclerView;
        ProgressBar progressBar;
        final TextView tvOk;

        progressBar = dialog.findViewById(R.id.progressBar);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        tvOk = dialog.findViewById(R.id.tvOk);
        ivClose = dialog.findViewById(R.id.ivClose);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setHasFixedSize(true);

        if (isNetworkConnected(requireActivity())) {
            hitGetAttributeList(problemId, recyclerView, progressBar);
        } else {
            showToastSort(requireActivity(), getString(R.string.noInternetConnection));
            dialog.dismiss();
        }

        tvOk.setOnClickListener(view -> {

            try {

                if (tempArray.length() == 0) {
                    showToastSort(requireActivity(), requireActivity().getString(R.string.please_select_attributes));
                } else {
                    for (int i = 0; i < tempArray.length(); i++) {
                        JSONObject jsonObject = tempArray.getJSONObject(i);

                        jsonObject.put("problemId", jsonObject.get("problemId"));
                        jsonObject.put("attributeId", jsonObject.get("attributeId"));
                        jsonObject.put("attributeValueId", jsonObject.get("attributeValueId"));

                        jsonArraySuggestedProblemProvable.put(jsonObject);

                    }

                    try {
                        for (int i = tempArray.length() - 1; i >= 0; i--) {
                            tempArray.remove(i);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        ivClose.setOnClickListener(view -> {

            try {

                if (tempArray.length() == 0) {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("problemId", problemId);
                    jsonObject.put("attributeId", "0");
                    jsonObject.put("attributeValueId", "0");

                    jsonArraySuggestedProblemProvable.put(jsonObject);
                }


                for (int i = tempArray.length() - 1; i >= 0; i--) {
                    tempArray.remove(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            dialog.dismiss();


        });

        dialog.show();
    }

    private class AdapterAttribute extends RecyclerView.Adapter<AdapterAttribute.HolderAttribute> {

        List<GetAttributeListDataResp> data;

        public AdapterAttribute(List<GetAttributeListDataResp> favList) {
            data = favList;
        }

        public HolderAttribute onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderAttribute(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_attribute, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderAttribute holder, final int position) {

            try {

                List<GetAttributeListModel> getAttributeListModelList = data.get(position).getAttribute();

                holder.tvHeading.setText(data.get(position).getAttributeName());

                holder.recyclerView.setAdapter(new AdapterAttributeList(getAttributeListModelList));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class HolderAttribute extends RecyclerView.ViewHolder {
            TextView tvHeading;
            RecyclerView recyclerView;

            public HolderAttribute(View itemView) {
                super(itemView);
                tvHeading = itemView.findViewById(R.id.tvHeading);
                recyclerView = itemView.findViewById(R.id.recyclerView);

                recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setHasFixedSize(true);

            }
        }

        public int getItemCount() {

            return data.size();
        }


    }

    private class AdapterAttributeList extends RecyclerView.Adapter<AdapterAttributeList.HolderAttributeList> {

        List<GetAttributeListModel> data;

        public AdapterAttributeList(List<GetAttributeListModel> favList) {
            data = favList;
        }

        public HolderAttributeList onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderAttributeList(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_attribute_list, parent, false));
        }

        @SuppressLint({"SetTextI18n", "NewApi"})
        public void onBindViewHolder(final HolderAttributeList holder, final int position) {

            try {

                final GetAttributeListModel getAttributeListModel = data.get(position);

                holder.checkbox.setText(capitalizeFirstLetter(data.get(position).getAttributeValue().trim()));

                holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    if (!isChecked) {
                        for (int i = 0; i < tempArray.length(); i++) {
                            try {
                                if (tempArray.getJSONObject(i).getString("attributeValueId")
                                        .equalsIgnoreCase(data.get(position).getAttributeValueId().toString())) {
                                    tempArray.remove(i);
                                    break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        getAttributeListModel.setSelected(false);

                    } else {

                        getAttributeListModel.setSelected(true);

                        try {
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("problemId", data.get(position).getProblemId());
                            jsonObject.put("attributeId", data.get(position).getAttributeId().toString());
                            jsonObject.put("attributeValueId", data.get(position).getAttributeValueId().toString());

                            tempArray.put(jsonObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                holder.checkbox.setChecked(getAttributeListModel.isSelected());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public int getItemCount() {

            return data.size();
        }

        private class HolderAttributeList extends RecyclerView.ViewHolder {

            CheckBox checkbox;

            public HolderAttributeList(View itemView) {
                super(itemView);

                checkbox = itemView.findViewById(R.id.checkbox);

            }
        }

    }

    private void hitGetAllProblem(String alphabet, final AutoCompleteTextView editText, final RecyclerView recyclerView) {

        AddMemberProblemModel model = new AddMemberProblemModel();
        model.setUserMobileNo(mobileNumber);
        model.setAlphabet(alphabet);

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<GetAllProblemRes> call = iRestInterfaces.getAllProblems(
                model);

        call.enqueue(new Callback<GetAllProblemRes>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<GetAllProblemRes> call, Response<GetAllProblemRes> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {

                    getAllProblemModelList = response.body().getResponseValue();

                    if (getAllProblemModelList.size() > 0) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter<>(requireActivity(), R.layout.inflate_auto_complete_text, getAllProblemModelList);
                        arrayAdapter.setDropDownViewResource(R.layout.inflate_auto_complete_text);
                        editText.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

                        editText.setOnItemClickListener((parent, view, position, id) -> {

                            try {

                                if (!containsSuggestedProblem(getAllSuggestedProblemModelList, getAllProblemModelList.get(position).getProblemName())) {

                                    getAllSuggestedProblemModelList.add(0, new GetAllSuggestedProblemModel());
                                    getAllSuggestedProblemModelList.get(0).setId(getAllProblemModelList.get(position).getId()); // get item
                                    getAllSuggestedProblemModelList.get(0).setProblemName(getAllProblemModelList.get(position).getProblemName());
                                    getAllSuggestedProblemModelList.get(0).setIsVisible(getAllProblemModelList.get(position).getIsVisible());
                                    getAllSuggestedProblemModelList.get(0).setSelected(true);

                                    editText.setText("");

                                    if (adapterProblems != null) {
                                        adapterProblems.notifyItemInserted(0);
                                        adapterProblems.notifyItemRangeChanged(0, getAllSuggestedProblemModelList.size());
                                        recyclerView.smoothScrollToPosition(0);
                                    } else {
                                        adapterProblems = new AdapterProblems(getAllSuggestedProblemModelList);
                                        recyclerView.setAdapter(adapterProblems);
                                    }


                                    if (getAllProblemModelList.get(position).getIsVisible().equals(1)) {
                                        AlertAttributeList(getAllProblemModelList.get(position).getId().toString());
                                    } else {
                                        try {

                                            JSONArray tempArray = new JSONArray();

                                            for (int i = 0; i < jsonArraySuggestedProblemProvable.length(); i++) {

                                                String idd = jsonArraySuggestedProblemProvable.getJSONObject(i).getString("problemId");

                                                if (idd.contains(getAllProblemModelList.get(position).getId().toString())) {
                                                    continue;
                                                } else {
                                                    tempArray.put(jsonArraySuggestedProblemProvable.getJSONObject(i));
                                                }

                                            }

                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("problemId", getAllProblemModelList.get(position).getId().toString());
                                            jsonObject.put("attributeId", "0");
                                            jsonObject.put("attributeValueId", "0");

                                            if (getAllProblemModelList.get(position).isSelected()) {
                                                tempArray.put(jsonObject);
                                            } else {

                                                for (int j = 0; j < tempArray.length(); j++) {
                                                    JSONObject obj = tempArray.getJSONObject(j);
                                                    if (obj.getString("problemId").equals(getAllProblemModelList.get(position).getId().toString())) {
                                                        // add this item in some collection i.e PublishedList, and later use this collection
                                                        tempArray.remove(j);
                                                    }
                                                }
                                            }

                                            jsonArraySuggestedProblemProvable = tempArray;

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }


                                } else {
                                    hideSoftKeyboard(requireActivity());
                                    showToastSort(requireActivity(), getString(R.string.symptom_already_exist));

                                    editText.setText("");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });

                    } else {
                        Log.d(TAG, "onResponse: ");
                    }

                }


            }

            @Override
            public void onFailure(@NotNull Call<GetAllProblemRes> call, @NotNull Throwable t) {


                showToastSort(requireActivity(), t.getMessage());
            }
        });
    }

    private void AlertAddMoreSymptoms() {
        final Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.alert_add_more_symptoms);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final AutoCompleteTextView etSearch;

        ImageView ivClose;

        final RecyclerView recyclerView;

        ivClose = dialog.findViewById(R.id.ivClose);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        etSearch = dialog.findViewById(R.id.etSearch);


        ivClose.setOnClickListener(view -> dialog.dismiss());

        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        if (isNetworkConnected(requireActivity())) {
            hitGetAllSuggestedProblem(recyclerView);
        } else {
            showToastSort(requireActivity(), getString(R.string.noInternetConnection));
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    if (isNetworkConnected(requireActivity())) {
                        hitGetAllProblem(s.toString(), etSearch, recyclerView);
                    } else {
                        showToastSort(requireActivity(), getString(R.string.noInternetConnection));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void hitGetAllSuggestedProblem(final RecyclerView recyclerView) {

        String memberId = null;
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        AddMemberProblemModel model = new AddMemberProblemModel();
        model.setUserMobileNo(mobileNumber);
        model.setMemberId(String.valueOf(getUserForBooking(requireActivity()).getMemberId()));
        Call<GetAllSuggestedProblemRes> call = iRestInterfaces.getAllSuggestedProblem(
                model
        );

        call.enqueue(new Callback<GetAllSuggestedProblemRes>() {
            @Override
            public void onResponse(@NotNull Call<GetAllSuggestedProblemRes> call, @NotNull Response<GetAllSuggestedProblemRes> response) {


                if (response.isSuccessful() && response.body().getResponseCode() == 1) {

                    getAllSuggestedProblemModelList = response.body().getResponseValue();

                    adapterProblems = new AdapterProblems(getAllSuggestedProblemModelList);

                    recyclerView.setAdapter(adapterProblems);

                }

            }

            @Override
            public void onFailure(@NotNull Call<GetAllSuggestedProblemRes> call, @NotNull Throwable t) {


                showToastSort(requireActivity(), t.getMessage());

            }
        });
    }

    private static boolean containsSuggestedProblem(List<GetAllSuggestedProblemModel> list, String input) {
        for (GetAllSuggestedProblemModel item : list) {
            if (item.getProblemName().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    private class AdapterProblems extends RecyclerView.Adapter<AdapterProblems.HolderSelected> {
        List<GetAllSuggestedProblemModel> data;

        public AdapterProblems(List<GetAllSuggestedProblemModel> favList) {
            data = favList;
        }

        public HolderSelected onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderSelected(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_problems, parent, false));
        }

        @SuppressLint({"SetTextI18n", "NewApi"})
        public void onBindViewHolder(final HolderSelected holder, final int position) {

            holder.tvProblem.setText(capitalizeFirstLetter(data.get(position).getProblemName()));
            holder.cvMain.setCardBackgroundColor(data.get(position).isSelected() ? getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.YellowColo));
            holder.tvProblem.setTextColor(data.get(position).isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));

            holder.tvProblem.setOnClickListener(v -> {


                data.get(position).setSelected(!data.get(position).isSelected());
                holder.cvMain.setCardBackgroundColor(data.get(position).isSelected() ? requireActivity().getResources().getColor(R.color.colorAccent) : requireActivity().getResources().getColor(R.color.YellowColo));
                holder.tvProblem.setTextColor(data.get(position).isSelected() ? requireActivity().getResources().getColor(R.color.white) : requireActivity().getResources().getColor(R.color.black));

                if (data.get(position).getIsVisible().equals(1)
                        && data.get(position).isSelected()) {
                    AlertAttributeList(data.get(position).getId().toString());
                } else {
                    try {

                        JSONArray tempArray = new JSONArray();

                        for (int i = 0; i < jsonArraySuggestedProblemProvable.length(); i++) {

                            String id = jsonArraySuggestedProblemProvable.getJSONObject(i).getString("problemId");

                            if (id.contains(data.get(position).getId().toString())) {
                                continue;
                            } else {
                                tempArray.put(jsonArraySuggestedProblemProvable.getJSONObject(i));
                            }

                        }

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("problemId", data.get(position).getId().toString());
                        jsonObject.put("attributeId", "0");
                        jsonObject.put("attributeValueId", "0");

                        if (data.get(position).isSelected()) {
                            tempArray.put(jsonObject);
                        } else {

                            for (int j = 0; j < tempArray.length(); j++) {
                                JSONObject obj = tempArray.getJSONObject(j);
                                if (obj.getString("problemId").equals(data.get(position).getId().toString())) {
                                    tempArray.remove(j);
                                }
                            }
                        }

                        jsonArraySuggestedProblemProvable = tempArray;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });

        }

        public int getItemCount() {
            return data.size();
        }

        private class HolderSelected extends RecyclerView.ViewHolder {

            TextView tvProblem;

            CardView cvMain;

            public HolderSelected(View itemView) {
                super(itemView);
                tvProblem = itemView.findViewById(R.id.tvProblem);
                cvMain = itemView.findViewById(R.id.cvMain);
            }
        }

    }


    private void hitAddMemberProblem() {

        String time = hour + ":" + minutes;
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();

        AddMemberProblemModel model = new AddMemberProblemModel();
        model.setUserMobileNo(mobileNumber);
        model.setMemberId(String.valueOf(getPrimaryUser(requireActivity()).getMemberId()));
        model.setProblemDate(parseDate(problemDate, "yyyy-MM-dd", "yyyy/MM/dd"));
        model.setProblemTime(time);
        model.setIsUpCovid("0");
        model.setDtDataTable(jsonArraySuggestedProblemProvable.toString());

        Call<RegistrationRes> call = iRestInterfaces.addMemberProblem(
                model);

        call.enqueue(new Callback<RegistrationRes>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NotNull Call<RegistrationRes> call, @NotNull Response<RegistrationRes> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {

                    Toast.makeText(requireActivity(), R.string.problem_submitted, Toast.LENGTH_SHORT).show();
                    navController.navigateUp();

                } else
                    Toast.makeText(requireActivity(), "" + response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(@NotNull Call<RegistrationRes> call, @NotNull Throwable t) {


                showToastSort(requireActivity(), t.getMessage());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}