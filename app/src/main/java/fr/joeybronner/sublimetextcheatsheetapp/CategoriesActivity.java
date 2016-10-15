package fr.joeybronner.sublimetextcheatsheetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import fr.joeybronner.sublimetextcheatsheetapp.cards.CategoryAdapter;
import fr.joeybronner.sublimetextcheatsheetapp.interfaces.OnItemClickListener;
import fr.joeybronner.sublimetextcheatsheetapp.objects.Category;
import fr.joeybronner.sublimetextcheatsheetapp.utils.Utils;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Category> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btSearch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.activity_about, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                ImageView btnDismiss = (ImageView) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }});

                popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
            }
        });

        // Create the list objects (1 object = 1 entry)
        cities.add(new Category("General",  R.drawable.general));
        cities.add(new Category("Editing",  R.drawable.editing));
        cities.add(new Category("Navigation",  R.drawable.navigation));
        cities.add(new Category("Find/Replace",  R.drawable.findreplace));
        cities.add(new Category("Tabs", R.drawable.tabs));
        cities.add(new Category("Window", R.drawable.window));
        cities.add(new Category("Bookmarks",  R.drawable.bookmarks));
        cities.add(new Category("Text manipulation",  R.drawable.textmanip));

        // Grid view (2 elements per row) --> for only one row : recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        // New Adapter of the list
        recyclerView.setAdapter(new CategoryAdapter(cities, new OnItemClickListener() {
            @Override
            public void onItemClick(Category item) {
                Intent i = new Intent(CategoriesActivity.this, CommandsActivity.class);
                Bundle b = new Bundle();
                b.putString("category", item.getText());
                i.putExtras(b);
                startActivity(i);
            }
        }));

        Utils.overrideFonts(getApplicationContext(), getWindow().getDecorView().getRootView());

        // Show info message
        Snackbar.make(recyclerView, getResources().getString(R.string.activity_categories_launch), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
