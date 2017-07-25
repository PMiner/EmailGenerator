package tk.pminer.emailgenerator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EmailSelector extends AppCompatActivity
{
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String item = this.getIntent().getExtras().getString("item");
        mListView = (ListView) findViewById(R.id.email_list_view);
        final ArrayList<EmailList> emailList  = EmailList.getClientsFromFile("clients.json", item, this);
        String[] listItems = new String[emailList.size()];
        EmailList.getArrayFromFile("clients.json", item, this);
        for(int i = 0; i < emailList.size(); i++)
        {
            EmailList client = emailList.get(i);
            listItems[i] = client.name;
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, EmailList.getArrayFromFile("clients.json", item, this));
        mListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_create);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO CREATE EMAIL
                Snackbar.make(view, "TODO: Create email", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(item);
    }

}
