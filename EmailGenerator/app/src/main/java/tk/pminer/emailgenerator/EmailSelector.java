package tk.pminer.emailgenerator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EmailSelector extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String item = this.getIntent().getExtras().getString("item");
        ListView mListView = (ListView) findViewById(R.id.email_list_view);
        final ArrayList<EmailList> emailList = EmailList.getEmailsFromFile("clients.json", item, this);
        String[] listItems = new String[emailList.size()];
        View hint = findViewById(R.id.email_hint);
        if(emailList.size() == 0) { hint.setVisibility(View.VISIBLE); }
        else { hint.setVisibility(View.GONE); }
        for(int i = 0; i < emailList.size(); i++)
        {
            EmailList email = emailList.get(i);
            listItems[i] = email.email;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_multichoice, listItems);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mListView.setAdapter(adapter);
        for (int i = 0; i < emailList.size(); i++) {
            mListView.setItemChecked(i, true);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_create);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO CREATE EMAIL
                Snackbar.make(view, "TODO: Create email", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        if(getActionBar() != null)
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_email_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //TODO SEND EMAIL
        switch(item.getItemId())
        {
            case R.id.email_send:
                Snackbar.make(findViewById(R.id.email_list_view), "TODO: Send email", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null)
                        .show();
                break;
            default:
                finish();
                break;
        }

        return true;
    }
}
