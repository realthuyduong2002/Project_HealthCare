package com.example.doctorapplication.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.doctorapplication.R;

public class ProgressDialog extends Dialog {
    Context mContext;
    ProgressDialog dialog;

    public ProgressDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    private ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public ProgressDialog show(boolean cancelable) {

        dialog = new ProgressDialog(mContext, R.style.CustomProgress);

        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCancelable(cancelable);
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
        }

        dialog.show();
        return dialog;
    }

    public void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}