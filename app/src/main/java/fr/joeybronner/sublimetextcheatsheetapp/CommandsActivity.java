package fr.joeybronner.sublimetextcheatsheetapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.joeybronner.sublimetextcheatsheetapp.cards.CommandsAdapter;
import fr.joeybronner.sublimetextcheatsheetapp.interfaces.OnCommandClickListener;
import fr.joeybronner.sublimetextcheatsheetapp.interfaces.OnShareClickListener;
import fr.joeybronner.sublimetextcheatsheetapp.objects.Command;
import fr.joeybronner.sublimetextcheatsheetapp.utils.Constants;
import fr.joeybronner.sublimetextcheatsheetapp.utils.Utils;

public class CommandsActivity extends AppCompatActivity {

    View activityView;
    private RecyclerView recyclerView;
    private List<Command> commands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_commands);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityView = this.findViewById(android.R.id.content);

        Bundle b = getIntent().getExtras();
        String category = b.getString("category");
        Snackbar.make(activityView, getResources().getString(R.string.activity_list_of_commands_launch) + " " + category, Snackbar.LENGTH_LONG).setAction("Action", null).show();

        // Action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(category);

        RecyclerView rv = (RecyclerView)findViewById(R.id.listCommands);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        String[] cmds = new String[0];
        String[] texts = new String[0];

        // Initialize commands
        switch (category) {
            case "General":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.global_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.global_commands_mac);
                }
                texts = getResources().getStringArray(R.array.global_text);
                break;
            case "Editing":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.editing_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.editing_commands_mac);
                }
                texts = getResources().getStringArray(R.array.editing_text);
                break;
            case "Navigation":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.navigation_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.navigation_commands_mac);
                }
                texts = getResources().getStringArray(R.array.navigation_text);
                break;
            case "Find/Replace":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.findreplace_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.findreplace_commands_mac);
                }
                texts = getResources().getStringArray(R.array.findreplace_text);
                break;
            case "Tabs":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.tabs_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.tabs_commands_mac);
                }
                texts = getResources().getStringArray(R.array.tabs_text);
                break;
            case "Window":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.window_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.window_commands_mac);
                }
                texts = getResources().getStringArray(R.array.window_text);
                break;
            case "Bookmarks":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.bookmarks_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.bookmarks_commands_mac);
                }
                texts = getResources().getStringArray(R.array.bookmarks_text);
                break;
            case "Text manipulation":
                if (Constants.PLATFORM.equals("win")) {
                    cmds = getResources().getStringArray(R.array.textmanip_commands_win);
                } else {
                    cmds = getResources().getStringArray(R.array.textmanip_commands_mac);
                }
                texts = getResources().getStringArray(R.array.textmanip_text);
                break;
        }

        for (int i = 0; i < cmds.length; i++) {
            commands.add(new Command(texts[i], cmds[i]));
        }

        // Grid view (2 elements per row) --> for only one row : recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView = (RecyclerView) findViewById(R.id.listCommands);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommandsAdapter(commands, new OnCommandClickListener() {
            @Override
            public void onItemClick(Command item) {
                // Nothing.
            }
        }, new OnShareClickListener() {
            @Override
            public void onItemClick(String title, String command) {
                ShareCompat.IntentBuilder
                        .from(CommandsActivity.this)
                        .setText(getResources().getString(R.string.share_sentence) + " " + title.toLowerCase() + " : " + command + " " + getResources().getString(R.string.share_sentence_app))
                        .setType("text/plain")
                        .setChooserTitle(getResources().getString(R.string.share_title))
                        .startChooser();
            }
        }));

        Utils.overrideFonts(getApplicationContext(), getWindow().getDecorView().getRootView());
    }

}
