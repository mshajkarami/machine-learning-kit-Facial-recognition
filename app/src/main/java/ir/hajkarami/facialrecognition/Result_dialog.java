package ir.hajkarami.facialrecognition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Result_dialog extends DialogFragment {
    Button btn;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_dialog, container, false);
        String text = "";
        btn = view.findViewById(R.id.btn_ok);
        textView = view.findViewById(R.id.dialog);
        // Getting the Bundle
        Bundle bundle = getArguments();
        text = bundle.getString("RESULT_TEXT");
        textView.setText(text);
        // Adding Event Click Listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
