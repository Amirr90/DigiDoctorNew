package com.digidoctor.android.view.fragments.pharmacy;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.MedicineDetailsFragmentBinding;
import com.digidoctor.android.interfaces.Api;

import com.digidoctor.android.model.pharmacyModel.GetMedicineReportModel;
import com.digidoctor.android.model.pharmacyModel.GetMedicineReportRes;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.CustomTypefaceSpan;
import com.digidoctor.android.utility.URLUtils;
import com.digidoctor.android.utility.utils;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.view.fragments.pharmacy.ProductDetailsFragment.MedicineID;
import static com.digidoctor.android.view.fragments.pharmacy.ProductDetailsFragment.MedicineName;


public class MedicineDetailsFragment extends Fragment implements View.OnClickListener {


    NestedScrollView scrollViewMain;

    RecyclerView recyclerViewMain;

    LinearLayoutManager linearLayoutManager;

    List<GetMedicineReportModel> getMedicineReportModelList = new ArrayList<>();

    AdapterSelections adapterSelections;
    AdapterMain adapterMain;

    TextView tvDiseaseDefinition, tvHeading, tvName;

    Typeface fontRegular, fontBold;


    FloatingActionButton fabScrollUp;

    Spinner spinnerDepartment;


    MedicineDetailsFragmentBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        init(container);
        setListeners();
        binding = MedicineDetailsFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    private void init(View v) {

        scrollViewMain = v.findViewById(R.id.scrollViewMain);


        tvDiseaseDefinition = v.findViewById(R.id.tvDiseaseDefinition);
        tvHeading = v.findViewById(R.id.tvHeading);
        tvName = v.findViewById(R.id.tvName);

        // recyclerViewSelection = v.findViewById(R.id.recyclerViewSelection);
        recyclerViewMain = v.findViewById(R.id.recyclerViewMain);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireActivity());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.STRETCH);
//        recyclerViewSelection.setLayoutManager(layoutManager);
        // recyclerViewSelection.setNestedScrollingEnabled(false);

        linearLayoutManager = new LinearLayoutManager(requireActivity());
//        recyclerViewMain.setLayoutManager(linearLayoutManager);
        //   recyclerViewMain.setNestedScrollingEnabled(false);

        // adapterSelections = new AdapterSelections(selectionList);
        //  recyclerViewSelection.setAdapter(adapterSelections);

//        fontRegular = Typeface.createFromAsset(requireActivity().getAssets(), "calibri.ttf");
//        fontBold = Typeface.createFromAsset(requireActivity().getAssets(), "calibri_bold.ttf");

//        setSpannable(tv2);
//        setSpannable(tv3);
//        setSpannable(tv4);
        if (utils.isNetworkConnected(requireActivity())) {
            hitGetMedicineReport();
        } else {
            Toast.makeText(requireActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();

        }

    }

    private void setSpannable(TextView textView) {
        Spannable spannable = new SpannableString(textView.getText().toString());
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.payu_color_d2d2d2)),
                0,
                textView.getText().toString().indexOf("."),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new CustomTypefaceSpan("", fontBold),
                0,
                textView.getText().toString().indexOf("."),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);
    }

    private void setListeners() {

//        fabScrollUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scrollViewMain.fullScroll(ScrollView.FOCUS_UP);
//            }
//        });

//        scrollViewMain.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (scrollViewMain != null) {
//                    if (scrollViewMain.getScrollY() == 0) {
//                        fabScrollUp.hide();
//                    } else {
//                        fabScrollUp.show();
//                    }
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {

    }

    private void hitGetMedicineReport() {

        getMedicineReportModelList.clear();

        AppUtils.showRequestDialog(requireActivity());

        Api iRestInterfaces = URLUtils.getMedicineDetails();
        Call<GetMedicineReportRes> call = iRestInterfaces.getMedicineReport(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZmlyc3ROYW1lIjoic2FkZGFtIiwibGFzdE5hbWUiOm51bGwsImVtYWlsSWQiOm51bGwsIm1vYmlsZU5vIjoiODk2MDI1MzEzMyIsImNvdW50cnkiOiJJTkRJQSIsInppcENvZGUiOiIyMjYwMjAiLCJvY2N1cGF0aW9uSWQiOjEsImFnZSI6bnVsbCwiZ2VuZGVyIjpudWxsLCJoZWlnaHRJbkZlZXQiOm51bGwsImhlaWdodEluSW5jaCI6bnVsbCwid2VpZ2h0IjpudWxsLCJwYWNrYWdlTmFtZSI6IkZyZWUiLCJpYXQiOjE1NjMwMTM4MDUsImV4cCI6MTU5NDU0OTgwNX0.l220lljQyTXmDPD-gyU53H4vV-I1GDPociKcp2qrWe8",
                "   2",
                MedicineID);

        call.enqueue(new Callback<GetMedicineReportRes>() {
            @Override
            public void onResponse(Call<GetMedicineReportRes> call, Response<GetMedicineReportRes> response) {

                if (response != null && response.body().getResponseCode() == 1) {

                    binding.tvName.setText(MedicineName);

                    for (int i = 0; i < response.body().getResponseValue().size(); i++) {

                        if (response.body().getResponseValue().get(i).getHeading().equalsIgnoreCase("Overview")) {

                            binding.tvHeading.setText(response.body().getResponseValue().get(i).getHeading().trim());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                binding.tvDiseaseDefinition.setText(Html.fromHtml(response.body().getResponseValue().get(i).getBody().toString().trim(), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                binding.tvDiseaseDefinition.setText(Html.fromHtml(response.body().getResponseValue().get(i).getBody().toString().trim()));
                            }

                        }
                    }

                    getMedicineReportModelList = response.body().getResponseValue();

                    getMedicineReportModelList.remove(0);

                    adapterSelections = new AdapterSelections(getMedicineReportModelList);
                    if (null == adapterSelections)
                        Toast.makeText(requireActivity(), "Adapter Is Null", Toast.LENGTH_SHORT).show();
                    binding.recyclerViewSelection.setAdapter(adapterSelections);

                    adapterMain = new AdapterMain(getMedicineReportModelList);
                    binding.recyclerViewMain.setAdapter(adapterMain);

                    //  firstTime = false;

                    AppUtils.hideDialog();

                }


            }

            @Override
            public void onFailure(Call<GetMedicineReportRes> call, Throwable t) {

                AppUtils.hideDialog();

                Toast.makeText(getActivity(), "Something Wrong" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private class AdapterSelections extends RecyclerView.Adapter<HolderSelections> {
        List<GetMedicineReportModel> data = new ArrayList<>();
        private int lastPosition = -1;

        public AdapterSelections(List<GetMedicineReportModel> favList) {
            data = favList;
        }

        public HolderSelections onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderSelections(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_selections, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderSelections holder, final int position) {

            holder.tvSelection.setText(data.get(position).getHeading().trim());

            holder.tvSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    float y = binding.recyclerViewMain.getY() + binding.recyclerViewMain.getChildAt(position).getY();

                    ObjectAnimator.ofInt(binding.scrollViewMain, "scrollY", (int) y).setDuration(1500).start();

                    // scrollViewMain.smoothScrollTo(0, (int) y);

                }
            });

            holder.tvSelection.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen._5sdp));

            switch (data.get(position).getHeadingId()) {
                case 2:

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cause, 0, 0, 0);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 4:

                    try {
                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sign_symptom, 0, 0, 0);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 7:

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_prevention, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 9:

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_diet, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

            /*    case "Caution":

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_prevention, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;*/

                case 8:

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_treatment, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 6:

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_treatment, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 5:

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_treatment, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 3:

                    try {

                        holder.tvSelection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_treatment, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                default:
                    break;

            }
            setRecyclerViewAnimation(requireActivity(), holder.itemView, position, lastPosition);
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private void setRecyclerViewAnimation(FragmentActivity requireActivity, View itemView, int position, int lastPosition) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.layout_animations);
            itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    private class HolderSelections extends RecyclerView.ViewHolder {

        TextView tvSelection;

        ConstraintLayout clMain;

        public HolderSelections(View itemView) {
            super(itemView);
            tvSelection = itemView.findViewById(R.id.tvSelection);
            clMain = itemView.findViewById(R.id.clMain);
        }
    }

    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);
        }
        return list;
    }

    private class AdapterMain extends RecyclerView.Adapter<HolderMain> {
        List<GetMedicineReportModel> data = new ArrayList<>();
        private int lastPosition = -1;

        public AdapterMain(List<GetMedicineReportModel> favList) {
            data = favList;
        }

        public HolderMain onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderMain(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_medicine_detail, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderMain holder, final int position) {

            holder.tvHeading.setText(data.get(position).getHeading().trim());

            if (data.get(position).getReference() == null || data.get(position).getReference().equalsIgnoreCase("")) {
                holder.tvReference.setVisibility(View.GONE);
                holder.tvReferenceInteraction.setVisibility(View.GONE);
                holder.tvReferenceSideEffects.setVisibility(View.GONE);
            } else {
                holder.tvReference.setVisibility(View.VISIBLE);
                holder.tvReference.setText("Ref: " + data.get(position).getReference().trim());

                holder.tvReferenceInteraction.setVisibility(View.GONE);
                holder.tvReferenceInteraction.setText("Ref: " + data.get(position).getReference().trim());

                holder.tvReferenceSideEffects.setVisibility(View.GONE);
                holder.tvReferenceSideEffects.setText("Ref: " + data.get(position).getReference().trim());
            }

            switch (data.get(position).getHeadingId()) {

                //Ids
                //1. Overview
                //2. When to use
                //3. Dosing
                //4. Side Effect
                //5. Interactions
                //6. Concerns
                //7. Mechanism if action
                //8. Precautions
                //9. Nutritional Therapy

                case 2:

                    try {

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        Gson gson = new Gson();
                        String json = gson.toJson(data.get(position).getBody());

                        JSONArray jsonArray = new JSONArray(json);

                        String text = "";

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            text = text + "\u2022 " + AppUtils.capitalizeFirstLetter(jsonObject.getString("whenToUse").trim()) + "\n";

                        }

                        holder.tvDetail.setText(text.trim());

                        holder.tvDetail.setPadding((int) getResources().getDimension(R.dimen._20sdp), 0, 0, 0);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 4:

                    try {

                        holder.tvReferenceSideEffects.setVisibility(View.VISIBLE);
                        holder.recyclerViewSideEffects.setVisibility(View.VISIBLE);
                        holder.tvDetail.setVisibility(View.GONE);
                        holder.tvReference.setVisibility(View.GONE);

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        Gson gson = new Gson();
                        String json = gson.toJson(data.get(position).getBody());

                        JSONArray jsonArray = new JSONArray(json);

                        holder.recyclerViewSideEffects.setAdapter(new AdapterSideEffects(jsonArray));

                       /* String text = "";

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            text = text + "\u2022 " + AppUtils.capitalizeFirstLetter(jsonObject.getString("sideEffects").trim()) + "<br />";

                        }


                        //  holder.tvDetail.setText(text.trim());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holder.tvDetail.setText(Html.fromHtml(text.trim(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holder.tvDetail.setText(Html.fromHtml(text.trim()));
                        }

                        holder.tvDetail.setPadding((int)getResources().getDimension(R.dimen._20sdp),0,0,0);*/
                        // holder.tvDetail.setText(text.trim());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 7:

                    try {

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holder.tvDetail.setText(Html.fromHtml(data.get(position).getBody().toString().trim(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holder.tvDetail.setText(Html.fromHtml(data.get(position).getBody().toString().trim()));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 9:

                    try {

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holder.tvDetail.setText(Html.fromHtml(data.get(position).getBody().toString().trim(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holder.tvDetail.setText(Html.fromHtml(data.get(position).getBody().toString().trim()));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

               /* case "Caution":

                    try {

                        holder.ivImage.setImageResource(R.drawable.ic_prevention);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holder.tvDetail.setText(Html.fromHtml(data.get(position).getBody().toString().trim(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holder.tvDetail.setText(Html.fromHtml(data.get(position).getBody().toString().trim()));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;*/

                case 8:

                    try {

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        Gson gson = new Gson();
                        String json = gson.toJson(data.get(position).getBody());

                        JSONArray jsonArray = new JSONArray(json);

                        String text = "";

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            text = text + "\u2022 " + AppUtils.capitalizeFirstLetter(jsonObject.getString("precautions").trim()) + "<br />";

                        }

                        //  holder.tvDetail.setText(text.trim());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holder.tvDetail.setText(Html.fromHtml(text.trim(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holder.tvDetail.setText(Html.fromHtml(text.trim()));
                        }

                        holder.tvDetail.setPadding((int) getResources().getDimension(R.dimen._20sdp), 0, 0, 0);
                        // holder.tvDetail.setText(text.trim());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 6:

                    try {

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        Gson gson = new Gson();
                        String json = gson.toJson(data.get(position).getBody());

                        JSONArray jsonArray = new JSONArray(json);

                        String text = "";

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            text = text + "\u2022 " + "<b>" + AppUtils.capitalizeFirstLetter(jsonObject.getString("text").trim()) + " -</b> "
                                    + jsonObject.getString("value").trim() + "<br />";

                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holder.tvDetail.setText(Html.fromHtml(text.trim(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holder.tvDetail.setText(Html.fromHtml(text.trim()));
                        }

                        holder.tvDetail.setPadding((int) getResources().getDimension(R.dimen._20sdp), 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 5:

                    try {

                        holder.tvReferenceInteraction.setVisibility(View.VISIBLE);
                        holder.recyclerViewInteractions.setVisibility(View.VISIBLE);
                        holder.tvDetail.setVisibility(View.GONE);
                        holder.tvReference.setVisibility(View.GONE);

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        Gson gson = new Gson();
                        String json = gson.toJson(data.get(position).getBody());

                        JSONArray jsonArray = new JSONArray(json);

                        JSONObject jsonObject = new JSONObject();

                        JSONArray jsonArrayTemp = new JSONArray();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);

                            if (jsonObject.has("interactionWithMedicine")) {
                                jsonArrayTemp = jsonObject.getJSONArray("interactionWithMedicine");

                                if (jsonArrayTemp.length() == 0) {
                                    jsonObject.remove("interactionWithMedicine");
                                }
                            }
                            if (jsonObject.has("interactionWithMedicineGroup")) {
                                jsonArrayTemp = jsonObject.getJSONArray("interactionWithMedicineGroup");

                                if (jsonArrayTemp.length() == 0) {
                                    jsonObject.remove("interactionWithMedicineGroup");
                                }
                            }
                            if (jsonObject.has("interactionWithNurient")) {
                                jsonArrayTemp = jsonObject.getJSONArray("interactionWithNurient");

                                if (jsonArrayTemp.length() == 0) {
                                    jsonObject.remove("interactionWithNurient");
                                }
                            }
                            if (jsonObject.has("interactionWithAddiquate")) {
                                jsonArrayTemp = jsonObject.getJSONArray("interactionWithAddiquate");

                                if (jsonArrayTemp.length() == 0) {
                                    jsonObject.remove("interactionWithAddiquate");
                                }
                            }
                            if (jsonObject.has("interactionWithProblem")) {
                                jsonArrayTemp = jsonObject.getJSONArray("interactionWithProblem");

                                if (jsonArrayTemp.length() == 0) {
                                    jsonObject.remove("interactionWithProblem");
                                }
                            }

                        }

                        holder.recyclerViewInteractions.setAdapter(new AdapterInteraction(AppUtils.jsonObjectToList(jsonObject)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 3:

                    try {

                        holder.ivImage.setImageResource(R.drawable.aar_ic_check);

                        holder.clDosing.setVisibility(View.VISIBLE);
                        holder.tvDetailHeading.setVisibility(View.VISIBLE);

                        Gson gson = new Gson();
                        String json = gson.toJson(data.get(position).getBody());

                        JSONArray jsonArray = new JSONArray(json);

                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        holder.tvDetailHeading.setText(jsonObject.getString("doseHeading").trim());
                        holder.tvDetail.setText(jsonObject.getString("doseSubHeading").trim());

                        JSONArray jsonArrayAdult = jsonObject.getJSONArray("adultDose");
                        JSONArray jsonArrayPediatric = jsonObject.getJSONArray("pediatricDose");

                        holder.recyclerViewAdultDose.setAdapter(new AdapterDosing(jsonArrayAdult));
                        holder.recyclerViewPediatricDose.setAdapter(new AdapterDosing(jsonArrayPediatric));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                default:
                    break;

            }


            //todo for flex view
/*
            if (data.get(position).getHeading().equalsIgnoreCase("side effect")){
                holder.tvDetail.setVisibility(View.GONE);
                holder.flexSignSymptoms.setVisibility(View.VISIBLE);

                //view = inflater.inflate(R.layout.fragment_medicine_detail, container, false);

                String sign[] = data.get(position).getBody().split(",");

                for (int i = 0; i < sign.length; i++) {

                    Typeface face = Typeface.createFromAsset(mActivity.getAssets(),
                            "calibri_bold.ttf");

                    TextView textView = new TextView(mActivity);
                    textView.setTypeface(face);
                    textView.setTextColor(getResources().getColor(R.color.orange_text));
                    textView.setTextSize(15);
                    textView.setPadding(0,0,100,0);
                    textView.setText(AppUtils.capitalizeFirstLetter(sign[i]));

                    holder.flexSignSymptoms.addView(textView);
                }
            }
*/
            setRecyclerViewAnimation(requireActivity(), holder.itemView, position, lastPosition);

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class HolderMain extends RecyclerView.ViewHolder {

        TextView tvHeading, tvDetail, tvReference, tvReferenceInteraction, tvReferenceSideEffects, tvDetailHeading;

        ImageView ivImage;

        RecyclerView recyclerViewInteractions, recyclerViewAdultDose, recyclerViewPediatricDose, recyclerViewSideEffects;

        ConstraintLayout clMain, clDosing;

        //FlexboxLayout flexSignSymptoms;

        public HolderMain(View itemView) {
            super(itemView);
            tvDetailHeading = itemView.findViewById(R.id.tvDetailHeading);
            tvHeading = itemView.findViewById(R.id.tvHeading);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            tvReference = itemView.findViewById(R.id.tvReference);
            tvReferenceInteraction = itemView.findViewById(R.id.tvReferenceInteraction);
            tvReferenceSideEffects = itemView.findViewById(R.id.tvReferenceSideEffects);
            ivImage = itemView.findViewById(R.id.ivImage);
            // clMain = itemView.findViewById(R.id.clMain);
            clDosing = itemView.findViewById(R.id.clDosing);
            //    flexSignSymptoms = itemView.findViewById(R.id.flexSignSymptoms);
            recyclerViewInteractions = itemView.findViewById(R.id.recyclerViewInteractions);
            recyclerViewAdultDose = itemView.findViewById(R.id.recyclerViewAdultDose);
            recyclerViewPediatricDose = itemView.findViewById(R.id.recyclerViewPediatricDose);
            recyclerViewSideEffects = itemView.findViewById(R.id.recyclerViewSideEffects);

            recyclerViewInteractions.setLayoutManager(new LinearLayoutManager(requireActivity()));
            recyclerViewInteractions.setNestedScrollingEnabled(false);
            recyclerViewSideEffects.setLayoutManager(new LinearLayoutManager(requireActivity()));
            recyclerViewSideEffects.setNestedScrollingEnabled(false);

            recyclerViewAdultDose.setLayoutManager(new LinearLayoutManager(requireActivity()));
            recyclerViewAdultDose.setNestedScrollingEnabled(false);

            recyclerViewPediatricDose.setLayoutManager(new LinearLayoutManager(requireActivity()));
            recyclerViewPediatricDose.setNestedScrollingEnabled(false);

            recyclerViewPediatricDose.addItemDecoration(new DividerItemDecoration(requireActivity(),
                    DividerItemDecoration.VERTICAL));

            recyclerViewAdultDose.addItemDecoration(new DividerItemDecoration(requireActivity(),
                    DividerItemDecoration.VERTICAL));

            /*DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));*/

        }
    }

    private class AdapterInteraction extends RecyclerView.Adapter<HolderInteraction> {
        // JSONObject data;
        List<HashMap<String, String>> data;
        private int lastPosition = -1;

        public AdapterInteraction(List<HashMap<String, String>> favList) {
            data = favList;
        }

        public HolderInteraction onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderInteraction(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_interaction, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderInteraction holder, final int position) {

            try {
                if (data.get(position).containsKey("interactionWithMedicine")) {
                    holder.tvHeading.setText("With Medicine");

                    setDetailText(position, "interactionWithMedicine", holder.tvDetail);

                } else if (data.get(position).containsKey("interactionWithMedicineGroup")) {
                    holder.tvHeading.setText("With Medicine Group");

                    setDetailText(position, "interactionWithMedicineGroup", holder.tvDetail);

                } else if (data.get(position).containsKey("interactionWithNurient")) {
                    holder.tvHeading.setText("With Nutrient");

                    setDetailText(position, "interactionWithNurient", holder.tvDetail);

                } else if (data.get(position).containsKey("interactionWithAddiquate")) {
                    holder.tvHeading.setText("With Addiquate");

                    setDetailText(position, "interactionWithAddiquate", holder.tvDetail);

                } else if (data.get(position).containsKey("interactionWithProblem")) {
                    holder.tvHeading.setText("With Problem");

                    setDetailText(position, "interactionWithProblem", holder.tvDetail);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            setRecyclerViewAnimation(requireActivity(), holder.itemView, position, lastPosition);
        }

        private void setDetailText(int position, String key, TextView textView) {
            try {
                JSONArray jsonArray = new JSONArray(data.get(position).get(key));

                String text = "";

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject obj = jsonArray.getJSONObject(i);

                    text = text + "\u2022 " + AppUtils.capitalizeFirstLetter(obj.getString(key).trim()) + "\n";

                }

                textView.setText(text.trim());

                textView.setPadding((int) getResources().getDimension(R.dimen._15sdp), 0, 0, 0);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        public int getItemCount() {

            return data.size();
        }
    }

    private class HolderInteraction extends RecyclerView.ViewHolder {

        TextView tvHeading, tvDetail;

        public HolderInteraction(View itemView) {
            super(itemView);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            tvHeading = itemView.findViewById(R.id.tvHeading);
        }
    }

    private class AdapterSideEffects extends RecyclerView.Adapter<HolderSideEffects> {
        // JSONObject data;
        //List<HashMap<String, String>> data;
        private int lastPosition = -1;
        JSONArray data;

        public AdapterSideEffects(JSONArray favList) {
            data = favList;
        }

        public HolderSideEffects onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderSideEffects(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_side_effects, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderSideEffects holder, final int position) {

            try {
                JSONObject jsonObject = data.getJSONObject(position);

                holder.tvSymptomName.setText("\u2022 " + AppUtils.capitalizeFirstLetter(jsonObject.getString("sideEffects").trim()));

                if (jsonObject.getString("isWatchable").equalsIgnoreCase("yes")) {
                    holder.tvWatchable.setVisibility(View.VISIBLE);
                }

                if (jsonObject.getString("isLifeThreatening").equalsIgnoreCase("yes")) {
                    holder.tvLifeThreatening.setVisibility(View.VISIBLE);
                }

                if (!jsonObject.getString("sideEffectType").equalsIgnoreCase("")) {
                    holder.tvType.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            setRecyclerViewAnimation(requireActivity(), holder.itemView, position, lastPosition);
        }

        public int getItemCount() {
            return data.length();
        }
    }

    private class HolderSideEffects extends RecyclerView.ViewHolder {

        TextView tvSymptomName, tvWatchable, tvType, tvLifeThreatening;

        public HolderSideEffects(View itemView) {
            super(itemView);
            tvSymptomName = itemView.findViewById(R.id.tvSymptomName);
            tvWatchable = itemView.findViewById(R.id.tvWatchable);
            tvType = itemView.findViewById(R.id.tvType);
            tvLifeThreatening = itemView.findViewById(R.id.tvLifeThreatening);

        }
    }

    private class AdapterDosing extends RecyclerView.Adapter<HolderDosing> {
        JSONArray data;
        private int lastPosition = -1;

        public AdapterDosing(JSONArray favList) {
            data = favList;
        }

        public HolderDosing onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderDosing(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_dosing, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderDosing holder, final int position) {

            try {

                JSONObject jsonObject = data.getJSONObject(position);

                if (jsonObject.isNull("ailment")) {
                    holder.tvAilment.setText("-");
                } else {
                    holder.tvAilment.setText(AppUtils.capitalizeFirstLetter(jsonObject.getString("ailment").trim()));
                }

                if (jsonObject.isNull("route")) {
                    holder.tvRoute.setText("-");
                } else {
                    holder.tvRoute.setText(jsonObject.getString("route").trim());
                }

                if (jsonObject.isNull("dose")) {
                    holder.tvDose.setText("-");
                } else {
                    holder.tvDose.setText(jsonObject.getString("dose").trim());
                }

                if (jsonObject.isNull("formName")) {
                    holder.tvForm.setText("-");
                } else {
                    holder.tvForm.setText(jsonObject.getString("formName").trim());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            setRecyclerViewAnimation(requireActivity(), holder.itemView, position, lastPosition);
        }

        public int getItemCount() {

            return data.length();
        }
    }

    private class HolderDosing extends RecyclerView.ViewHolder {

        TextView tvAilment, tvRoute, tvForm, tvDose;

        public HolderDosing(View itemView) {
            super(itemView);
            tvAilment = itemView.findViewById(R.id.tvAilment);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvForm = itemView.findViewById(R.id.tvForm);
            tvDose = itemView.findViewById(R.id.tvDose);
        }
    }

}