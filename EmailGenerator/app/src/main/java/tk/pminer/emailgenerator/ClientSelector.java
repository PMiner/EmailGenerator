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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClientSelector extends AppCompatActivity
{
    private ListView mListView;
    private Menu mOptionsMenu;
    private String[] list;
    private Context newContext;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_client_selector, menu);
        return true;
    }

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
        View hint = findViewById(R.id.client_hint);
        if(clientList.size() == 0) { hint.setVisibility(View.VISIBLE); }
        else { hint.setVisibility(View.GONE); }
        for(int i = 0; i < clientList.size(); i++)
        {
            ClientList client = clientList.get(i);
            listItems[i] = client.name;
        }
        list = listItems;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
        newContext = this;
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.client_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ADD CLIENT
                fab.hide();
                mOptionsMenu.findItem(R.id.client_add).setVisible(true);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(newContext, android.R.layout.select_dialog_multichoice, list);
                mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                mListView.setAdapter(adapter);


                Snackbar.make(view, "TODO: Add client", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(mListView.getChoiceMode() != AbsListView.CHOICE_MODE_MULTIPLE)
                {
                    ClientList selectedClient = clientList.get(position);
                    Intent detailIntent = new Intent(context, EmailSelector.class);
                    detailIntent.putExtra("item", selectedClient.name);
                    startActivity(detailIntent);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //TODO SETTINGS
        Snackbar.make(findViewById(R.id.client_list_view), "TODO: Edit clients", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show();
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.findItem(R.id.client_add).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
