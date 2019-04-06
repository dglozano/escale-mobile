package com.dglozano.escale.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dglozano.escale.db.entity.AppUser;

import java.util.List;
import java.util.Optional;

@Dao
public abstract class UserDao extends BaseDao<AppUser>{
    @Query("SELECT * FROM AppUser")
    public abstract LiveData<List<AppUser>> getAllUsersAsLiveData();

    @Query("SELECT * FROM AppUser WHERE id == :id")
    public abstract LiveData<AppUser> getUserByIdAsLiveData(Long id);

    @Query("SELECT * FROM AppUser WHERE id == :id")
    public abstract Optional<AppUser> getUserById(Long id);

    @Query("DELETE FROM AppUser")
    public abstract void deleteAll();
}
