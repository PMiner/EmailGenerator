package tk.pminer.emailgenerator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ClientSelector extends AppCompatActivity
{
    private ListView mListView;
    private Menu mOptionsMenu;
    private String[] list;
    private Context newContext;
    private InputStream is;

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

        newContext = this;

        reloadList(newContext);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.client_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditModeOn();
            }
        });
    }

    private void reloadList(final Context context)
    {

        mListView = (ListView) findViewById(R.id.client_list_view);
        try {
            is = this.getAssets().open("template.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ArrayList<ClientList> clientList  = ClientList.getClientsFromFile(this, is);
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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(mListView.getChoiceMode() != AbsListView.CHOICE_MODE_SINGLE)
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
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.client_add:
                final AlertDialog.Builder clientEnterBuilder = new AlertDialog.Builder(findViewById(R.id.client_list_view).getContext());
                LayoutInflater mInflater = getLayoutInflater();
                final View mView = mInflater.inflate(R.layout.dialog_client_enter, (ViewGroup) mListView.getRootView(), false);
                final EditText dialogText = (EditText) mView.findViewById(R.id.client_enter);
                clientEnterBuilder
                        .setView(mView)
                        .setTitle("Enter client name")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String text = dialogText.getText().toString();
                                ClientJSONEdit.addJSONObjToFile(text, newContext);
                                reloadList(newContext);
                                EditModeOn();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                clientEnterBuilder.create().show();
                break;
            case R.id.client_done:
                EditModeOff();
                break;
            case R.id.client_delete:
                final int selectItem = mListView.getCheckedItemPosition();
                if(selectItem != -1) {
                    AlertDialog.Builder clientDeleteBuilder = new AlertDialog.Builder(findViewById(R.id.client_list_view).getContext());
                    clientDeleteBuilder
                            .setTitle("Warning")
                            .setMessage("Are you sure you want to permanently delete the selected clients?")
                            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int myInt;
                                    String string;
                                    myInt = selectItem;
                                    string = mListView.getItemAtPosition(myInt).toString();
                                    ClientJSONEdit.removeJSONObjFromFile(string, myInt, newContext);
                                    reloadList(newContext);
                                    EditModeOn();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    clientDeleteBuilder.create().show();
                }
                else
                {
                    Toast.makeText(newContext, "You must select a client.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        EditModeOff();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed()
    {
        if(mOptionsMenu.findItem(R.id.client_add).isVisible())
        {
            EditModeOff();
        }
        else
        {
            finish();
        }
    }
    private void EditModeOn()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.client_edit);
        fab.hide();
        mOptionsMenu.findItem(R.id.client_add).setVisible(true);
        mOptionsMenu.findItem(R.id.client_delete).setVisible(true);
        mOptionsMenu.findItem(R.id.client_done).setVisible(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(newContext, android.R.layout.select_dialog_singlechoice, list);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setAdapter(adapter);
    }
    private void EditModeOff()
    {
        mOptionsMenu.findItem(R.id.client_add).setVisible(false);
        mOptionsMenu.findItem(R.id.client_done).setVisible(false);
        mOptionsMenu.findItem(R.id.client_delete).setVisible(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.client_edit);
        fab.show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(newContext, R.layout.simple_list_item_1, list);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        mListView.setAdapter(adapter);
    }
}
