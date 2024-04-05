// Generated by view binder compiler. Do not edit!
package http://schemas.android.com/apk/res-auto.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import http://schemas.android.com/apk/res-auto.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class QuizDialogBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Spinner spnArea;

  @NonNull
  public final Spinner spnLevel;

  private QuizDialogBinding(@NonNull LinearLayout rootView, @NonNull Spinner spnArea,
      @NonNull Spinner spnLevel) {
    this.rootView = rootView;
    this.spnArea = spnArea;
    this.spnLevel = spnLevel;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static QuizDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static QuizDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.quiz_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static QuizDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.spn_area;
      Spinner spnArea = ViewBindings.findChildViewById(rootView, id);
      if (spnArea == null) {
        break missingId;
      }

      id = R.id.spn_level;
      Spinner spnLevel = ViewBindings.findChildViewById(rootView, id);
      if (spnLevel == null) {
        break missingId;
      }

      return new QuizDialogBinding((LinearLayout) rootView, spnArea, spnLevel);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}