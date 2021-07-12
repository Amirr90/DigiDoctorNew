package com.digidoctor.android.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digidoctor.android.model.MedicineModel;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMedicineData(MedicineModel.MedicineDetailModel medicineModel);

    @Query("select * from medicine_details_table")
    LiveData<List<MedicineModel.MedicineDetailModel>> getAllMedicineList();

    @Update
    void updateUser(MedicineModel.MedicineDetailModel user);

    @Delete
    void deleteUser(MedicineModel.MedicineDetailModel user);

    @Query("delete from medicine_details_table")
    void deleteAllUser();
}
