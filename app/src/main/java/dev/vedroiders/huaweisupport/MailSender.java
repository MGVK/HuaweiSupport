package dev.vedroiders.huaweisupport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

/**
 * Created by mike on 27.03.17.
 */
public class MailSender extends AlertDialog.Builder implements Spinner.OnItemSelectedListener {

    private static String supportEmail = "huawei.task@best-bmstu.ru";
    private Consumer consumer;
    private Report report;
    private String[] spinner1List = {"Вопросы", "Сбои", "Жалобы", "Рекомендации", "Личные "
            + "настройки"};
    private String[][] spinner2List =
            {
                    {"Аксессуары", "Другие", "Использование продукта",
                            "Необоснованное завышение цены",
                            "Обновление ПО или возврат к предыдущей версии", "Особенности продукта",
                            "Подтверждение", "Спецификация продукта"},
                    {"Автоматическое выключение", "Зависание экрана при загрузке",
                            "Звонок без вибро и мелодии", "Невозможно разблокировать",
                            "Не работает GPS", "Нет вызова и/или слабый сигнал", "Перезагрузка",
                            "Прерывание звонка", "Проблема с зарядкой аппарата",
                            "Проблемы при запуске",
                            "Проблемы с Wi-Fi", "Проблемы с камерой",
                            "Пропадание звука при разговоре", "Пропущенные вызовы"},
                    {"Время ремонта", "Другие", "Качество продукта", "Качество ремонта",
                            "Качество сервиса", "Незаконные действия",
                            "Профессиональная компетентность", "Характерная черта сервиса"},
                    {"Другие", "Интерфейс пользователя", "Конфигурация продукта",
                            "Отношение к сервису", "Точка сервиса", "Улучшение аксессуаров"},
                    {"Вопросы", "Жалобы", "Рекомендации"}
            };


    private Spinner spinner1, spinner2;

    public MailSender(final Context context, Consumer consumer) {
        super(context);

        this.consumer = consumer;

        setView(loadLayout());

        setPositiveButton("Отправить отчет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO: 28.03.17 report

                Intent intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("mailto:" + supportEmail))
//                                .setType("text/plain")
                        .putExtra(Intent.EXTRA_SUBJECT, report.getSubject())
                        .putExtra(Intent.EXTRA_TEXT, report.getContent());
                context.startActivity(intent);
                dialog.cancel();
            }
        });

        create();

    }

    void prepareReport() {

    }

    LinearLayout loadLayout() {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.layout_mailsender, null);

        ((EditText) linearLayout.findViewById(R.id.email)).setText(
                consumer.getEmail());

        ((EditText) linearLayout.findViewById(R.id.model)).setText(
                consumer.getModel());


        (spinner1 =
                ((Spinner) linearLayout.findViewById(R.id.spinner_1))).setAdapter(
                new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item,
                        spinner1List)
        );
        spinner1.setOnItemSelectedListener(this);
        (spinner2 =
                ((Spinner) linearLayout.findViewById(R.id.spinner_2))).setAdapter(
                new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item,
                        spinner2List[0])
        );

        return linearLayout;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spinner1) {
            spinner2.setAdapter(new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item,
                    spinner2List[i]
            ));
        }
        else {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    static class Report {

        public String subject = "test";
        public String content = "test";

        public Report(Context context) {

//            subject =

        }


        public String getContent() {
            return content;
        }

        public String getSubject() {
            return subject;
        }
    }

}
