package tk.pminer.emailgenerator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmailSelector extends AppCompatActivity
{
    private Menu mOptionsMenu;
    private ListView mListView;
    private Context newContext;
    private ArrayList<EmailList> list;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        newContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String item = this.getIntent().getExtras().getString("item");
        mListView = (ListView) findViewById(R.id.email_list_view);
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
        list = emailList;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_multichoice, listItems);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mListView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO CREATE EMAIL
                EditModeOn();
            }
        });
        if(getActionBar() != null)
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_email_selector, menu);
        mOptionsMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        EditModeOff();
        return super.onPrepareOptionsMenu(menu);
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
            case R.id.email_add:
                final AlertDialog.Builder emailEnterBuilder = new AlertDialog.Builder(findViewById(R.id.email_list_view).getContext());
                mInflater = getLayoutInflater();
                View mView = mInflater.inflate(R.layout.dialog_email_enter, null);
                final EditText dialogText = (EditText) mView.findViewById(R.id.email_enter);
                emailEnterBuilder
                        .setView(mView)
                        .setTitle("Enter email address")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String text = dialogText.getText().toString();
                                Toast.makeText(findViewById(R.id.email_list_view).getContext(), text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(findViewById(R.id.email_list_view).getContext(), "CANCEL", Toast.LENGTH_SHORT).show();
                            }
                        });
                emailEnterBuilder.create().show();
                break;
            case R.id.email_delete:
                AlertDialog.Builder emailDeleteBuilder = new AlertDialog.Builder(findViewById(R.id.email_list_view).getContext());
                emailDeleteBuilder
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to permanently delete the selected emails?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO DELETE
                                EditModeOff();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                emailDeleteBuilder.create().show();
                break;
            case R.id.email_done:
                EditModeOff();
        }

        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(mOptionsMenu.findItem(R.id.email_add).isVisible())
        {
            EditModeOff();
        }
        else
        {
            finish();
        }
    }

    void EditModeOn()
    {
        for (int i = 0; i < list.size(); i++) {
            mListView.setItemChecked(i, false);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_edit);
        fab.hide();
        mOptionsMenu.findItem(R.id.email_add).setVisible(true);
        mOptionsMenu.findItem(R.id.email_delete).setVisible(true);
        mOptionsMenu.findItem(R.id.email_done).setVisible(true);
        mOptionsMenu.findItem(R.id.email_send).setVisible(false);
    }
    void EditModeOff()
    {
        for (int i = 0; i < list.size(); i++) {
            mListView.setItemChecked(i, true);
        }
        mOptionsMenu.findItem(R.id.email_add).setVisible(false);
        mOptionsMenu.findItem(R.id.email_done).setVisible(false);
        mOptionsMenu.findItem(R.id.email_delete).setVisible(false);
        mOptionsMenu.findItem(R.id.email_send).setVisible(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_edit);
        fab.show();
    }
}
