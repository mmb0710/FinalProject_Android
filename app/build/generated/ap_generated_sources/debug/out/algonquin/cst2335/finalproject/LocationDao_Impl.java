package algonquin.cst2335.finalproject;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class LocationDao_Impl implements LocationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Location> __insertionAdapterOfLocation;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public LocationDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocation = new EntityInsertionAdapter<Location>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `location_table` (`id`,`latitude`,`longitude`,`sunrise`,`sunset`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Location value) {
        stmt.bindLong(1, value.getId());
        if (value.getLatitude() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLatitude());
        }
        if (value.getLongitude() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLongitude());
        }
        if (value.getSunrise() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSunrise());
        }
        if (value.getSunset() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSunset());
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM location_table WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Location location) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocation.insert(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public List<Location> getAllLocations() {
    final String _sql = "SELECT * FROM location_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
      final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
      final List<Location> _result = new ArrayList<Location>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Location _item;
        final String _tmpLatitude;
        if (_cursor.isNull(_cursorIndexOfLatitude)) {
          _tmpLatitude = null;
        } else {
          _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
        }
        final String _tmpLongitude;
        if (_cursor.isNull(_cursorIndexOfLongitude)) {
          _tmpLongitude = null;
        } else {
          _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        }
        final String _tmpSunrise;
        if (_cursor.isNull(_cursorIndexOfSunrise)) {
          _tmpSunrise = null;
        } else {
          _tmpSunrise = _cursor.getString(_cursorIndexOfSunrise);
        }
        final String _tmpSunset;
        if (_cursor.isNull(_cursorIndexOfSunset)) {
          _tmpSunset = null;
        } else {
          _tmpSunset = _cursor.getString(_cursorIndexOfSunset);
        }
        _item = new Location(_tmpLatitude,_tmpLongitude,_tmpSunrise,_tmpSunset);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
