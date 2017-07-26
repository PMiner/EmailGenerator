package tk.pminer.emailgenerator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ClientSelector extends AppCompatActivity
{
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.client_list_view);
        final ArrayList<ClientList> clientList  = ClientList.getClientsFromFile("clients.json", this);
        String[] listItems = new String[clientList.size()];

        for(int i = 0; i < clientList.size(); i++)
        {
            ClientList client = clientList.get(i);
            listItems[i] = client.name;
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.client_create);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ADD CLIENT
                Snackbar.make(view, "TODO: Add client", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientList selectedClient = clientList.get(position);
                Intent detailIntent = new Intent(context, EmailSelector.class);
                detailIntent.putExtra("item", selectedClient.name);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO SETTINGS
        Snackbar.make(findViewById(R.id.client_list_view), "TODO: Add client", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show();
        return true;
    }
}
