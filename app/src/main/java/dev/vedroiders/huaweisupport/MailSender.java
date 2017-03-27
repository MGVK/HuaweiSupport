package dev.vedroiders.huaweisupport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by mike on 27.03.17.
 */
public class MailSender extends AlertDialog.Builder {

    private static String supportEmail = "huawei.task@best-bmstu.ru";
    private String userEmail;

    public MailSender(final Context context, final String userEmail) {
        super(context);

        this.userEmail = userEmail;


        setView(loadLayout());

        setPositiveButton("Отправить отчет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("mailto:" + supportEmail))
//                                .setType("text/plain")
                        .putExtra(Intent.EXTRA_SUBJECT, "ТЕмА ПИСЬМА")
                        .putExtra(Intent.EXTRA_TEXT, "СОДЕРЖИМОЕ ПИСЬМА");
                context.startActivity(intent);
                dialog.cancel();
            }
        });

        create();

    }


    LinearLayout loadLayout() {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.layout_mailsender, null);

        ((EditText) linearLayout.findViewById(R.id.mail)).setText(userEmail);


        return linearLayout;
    }

}
