package tk.pminer.emailgenerator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;

public class EmailSelector extends AppCompatActivity
{
    private Menu mOptionsMenu;
    private ListView mListView;
    private String[] list;
    private Context newContext;
    private String add;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        newContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add = this.getIntent().getExtras().getString("item");
        mListView = (ListView) findViewById(R.id.email_list_view);
        reloadList();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditModeOn();
            }
        });
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(add);
        }
    }

    void reloadList()
    {
        final ArrayList<EmailList> emailList = EmailList.getEmailsFromFile(add, this);
        String[] listItems = new String[emailList.size()];
        View hint = findViewById(R.id.email_hint);
        if(emailList.size() == 0) { hint.setVisibility(View.VISIBLE); }
        else { hint.setVisibility(View.GONE); }
        for(int i = 0; i < emailList.size(); i++)
        {
            EmailList email = emailList.get(i);
            listItems[i] = email.email;
        }
        list = listItems;
        newContext = this;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(newContext, android.R.layout.select_dialog_multichoice, list);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mListView.setAdapter(adapter);
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

        switch(item.getItemId())

        {
            //TODO SEND EMAIL
            case R.id.email_send:
                if(mListView.getCheckedItemCount() != 0) {
                    final AlertDialog.Builder readyOETA = new AlertDialog.Builder(findViewById(R.id.email_list_view).getContext());
                    readyOETA
                            .setMessage("Choose appropriate option")
                            .setPositiveButton("READY", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final AlertDialog.Builder emailSendBuilder = new AlertDialog.Builder(findViewById(R.id.email_list_view).getContext());
                                    LayoutInflater emailSendInflater = getLayoutInflater();
                                    View emailSendView = emailSendInflater.inflate(R.layout.dialog_email_send, (ViewGroup) mListView.getRootView(), false);
                                    final EditText poText = (EditText) emailSendView.findViewById(R.id.po_enter);
                                    final EditText jobText = (EditText) emailSendView.findViewById(R.id.job_enter);
                                    final EditText dieText = (EditText) emailSendView.findViewById(R.id.die_enter);
                                    emailSendBuilder
                                            .setView(emailSendView)
                                            .setTitle("Enter relevant info")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String email;
                                                    
                                                }
                                            })
                                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                    emailSendBuilder.create().show();
                                }
                            })
                            .setNegativeButton("ETA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {}
                            });
                    readyOETA.create().show();
                }
                else
                {
                    Toast.makeText(newContext, "You must select at least one email.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.email_add:
                final AlertDialog.Builder emailEnterBuilder = new AlertDialog.Builder(findViewById(R.id.email_list_view).getContext());
                final LayoutInflater mInflater = getLayoutInflater();
                View mView = mInflater.inflate(R.layout.dialog_email_enter, (ViewGroup) mListView.getRootView(), false);
                final EditText dialogText = (EditText) mView.findViewById(R.id.email_enter);
                emailEnterBuilder
                        .setView(mView)
                        .setTitle("Enter email address")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String text = dialogText.getText().toString();
                                EmailJSONEdit.addJSONObjToFile(text, add, findViewById(R.id.email_list_view).getContext());
                                reloadList();
                                EditModeOn();

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                emailEnterBuilder.create().show();
                break;
            case R.id.email_delete:
                final int selectItem = mListView.getCheckedItemPosition();
                if(selectItem != -1)
                {
                    AlertDialog.Builder emailDeleteBuilder = new AlertDialog.Builder(findViewById(R.id.email_list_view).getContext());
                    emailDeleteBuilder
                            .setTitle("Warning")
                            .setMessage("Are you sure you want to permanently delete the selected emails?")
                            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (selectItem != -1) {
                                        EmailJSONEdit.removeJSONObjFromFile(add, selectItem, newContext);
                                        reloadList();
                                        EditModeOn();
                                    }
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    emailDeleteBuilder.create().show();
                }
                else
                {
                    Toast.makeText(newContext, "You must select an email.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.email_done:
                EditModeOff();
                break;
            default:
                if(mOptionsMenu.findItem(R.id.email_add).isVisible())
                {
                    EditModeOff();
                }
                else
                {
                    finish();
                }
        }

        return true;
    }

    public void sendEmail(String[] addresses, String subject, String text)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
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

    private void EditModeOn()
    {
        for (int i = 0; i < list.length; i++) {
            mListView.setItemChecked(i, false);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_edit);
        fab.hide();
        mOptionsMenu.findItem(R.id.email_add).setVisible(true);
        mOptionsMenu.findItem(R.id.email_delete).setVisible(true);
        mOptionsMenu.findItem(R.id.email_done).setVisible(true);
        mOptionsMenu.findItem(R.id.email_send).setVisible(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(newContext, android.R.layout.select_dialog_singlechoice, list);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setAdapter(adapter);
    }
    private void EditModeOff()
    {
        for (int i = 0; i < list.length; i++) {
            mListView.setItemChecked(i, true);
        }
        mOptionsMenu.findItem(R.id.email_add).setVisible(false);
        mOptionsMenu.findItem(R.id.email_done).setVisible(false);
        mOptionsMenu.findItem(R.id.email_delete).setVisible(false);
        mOptionsMenu.findItem(R.id.email_send).setVisible(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.email_edit);
        fab.show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(newContext, android.R.layout.select_dialog_multichoice, list);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mListView.setAdapter(adapter);
    }
}
