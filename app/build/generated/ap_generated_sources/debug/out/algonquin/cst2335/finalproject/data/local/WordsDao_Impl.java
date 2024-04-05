package algonquin.cst2335.finalproject.data.local;

import algonquin.cst2335.finalproject.data.model.wordsapi.Pronunciation;
import algonquin.cst2335.finalproject.data.model.wordsapi.Result;
import algonquin.cst2335.finalproject.data.model.wordsapi.Word;
import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.rxjava3.EmptyResultSetException;
import androidx.room.rxjava3.RxRoom;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import io.reactivex.rxjava3.core.Single;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WordsDao_Impl implements WordsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Word> __insertionAdapterOfWord;

  private final EntityDeletionOrUpdateAdapter<Word> __deletionAdapterOfWord;

  public WordsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWord = new EntityInsertionAdapter<Word>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `FavouriteWord` (`id`,`word`,`results`,`pronunciation`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Word value) {
        stmt.bindLong(1, value.getId());
        if (value.getWord() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getWord());
        }
        final String _tmp = Converters.fromList(value.getResults());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        final String _tmp_1 = Converters.fromPron(value.getPronunciation());
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfWord = new EntityDeletionOrUpdateAdapter<Word>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `FavouriteWord` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Word value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insertWord(final Word w) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWord.insert(w);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void removeWord(final Word w) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfWord.handle(w);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void removeListofWords(final List<Word> w) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfWord.handleMultiple(w);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Word>> getAllFavouriteWords() {
    final String _sql = "SELECT * FROM FavouriteWord";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"FavouriteWord"}, false, new Callable<List<Word>>() {
      @Override
      public List<Word> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfResults = CursorUtil.getColumnIndexOrThrow(_cursor, "results");
          final int _cursorIndexOfPronunciation = CursorUtil.getColumnIndexOrThrow(_cursor, "pronunciation");
          final List<Word> _result = new ArrayList<Word>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Word _item;
            _item = new Word();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpWord;
            if (_cursor.isNull(_cursorIndexOfWord)) {
              _tmpWord = null;
            } else {
              _tmpWord = _cursor.getString(_cursorIndexOfWord);
            }
            _item.setWord(_tmpWord);
            final List<Result> _tmpResults;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfResults)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfResults);
            }
            _tmpResults = Converters.toList(_tmp);
            _item.setResults(_tmpResults);
            final Pronunciation _tmpPronunciation;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfPronunciation)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfPronunciation);
            }
            _tmpPronunciation = Converters.toPron(_tmp_1);
            _item.setPronunciation(_tmpPronunciation);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Single<Word> findWord(final String w) {
    final String _sql = "SELECT * FROM FavouriteWord WHERE word LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (w == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, w);
    }
    return RxRoom.createSingle(new Callable<Word>() {
      @Override
      public Word call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfResults = CursorUtil.getColumnIndexOrThrow(_cursor, "results");
          final int _cursorIndexOfPronunciation = CursorUtil.getColumnIndexOrThrow(_cursor, "pronunciation");
          final Word _result;
          if(_cursor.moveToFirst()) {
            _result = new Word();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpWord;
            if (_cursor.isNull(_cursorIndexOfWord)) {
              _tmpWord = null;
            } else {
              _tmpWord = _cursor.getString(_cursorIndexOfWord);
            }
            _result.setWord(_tmpWord);
            final List<Result> _tmpResults;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfResults)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfResults);
            }
            _tmpResults = Converters.toList(_tmp);
            _result.setResults(_tmpResults);
            final Pronunciation _tmpPronunciation;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfPronunciation)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfPronunciation);
            }
            _tmpPronunciation = Converters.toPron(_tmp_1);
            _result.setPronunciation(_tmpPronunciation);
          } else {
            _result = null;
          }
          if(_result == null) {
            throw new EmptyResultSetException("Query returned empty result set: " + _statement.getSql());
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
