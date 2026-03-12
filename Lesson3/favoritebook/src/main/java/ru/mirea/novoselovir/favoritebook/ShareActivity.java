package ru.mirea.novoselovir.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private  TextView favBookDev;
    private  TextView favQuoteDev;
    private  EditText favBook;
    private  EditText favQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        favBookDev = findViewById(R.id.favBookDev);
        favQuoteDev = findViewById(R.id.favQuoteDev);
        favBook = findViewById(R.id.favBook);
        favQuote = findViewById(R.id.favQuote);

        Bundle extras = getIntent().getExtras();
        String book = extras.getString(MainActivity.BOOK_NAME_KEY);
        String quote = extras.getString(MainActivity.QUOTES_KEY);
        favBookDev.setText("Любимая книга разработчика - "+book);
        favQuoteDev.setText("Любимая цитата разработчика - "+quote);
    };


    public void onSend(View view){
        String bookText = favBook.getText().toString();
        String quoteText = favQuote.getText().toString();
        String result = "Название вашей любимой книги - " + bookText + ". Цитата - " + quoteText;
        Intent intent = new Intent();
        intent.putExtra(MainActivity.USER_MESSAGE, result);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}