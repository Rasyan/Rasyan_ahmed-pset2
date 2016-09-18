package rasyan_native_app.rasyan_ahmed_pset2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

public class input_screen extends AppCompatActivity {

    Button button;
    Story story = null;
    InputStream stream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);

        // if no story is set, make a new one.
        // this is done by loading the text files using the asset manager,
        // and selecting one of them at random as the input for the story class.
        if (story == null) {
            AssetManager assets = getAssets();
            Random rand = new Random();
            int n = rand.nextInt(4);
            String[] storys = {"madlib0_simple.txt", "madlib1_tarzan.txt", "madlib2_university.txt", "madlib3_clothes.txt", "madlib4_dance.txt"};

            try {
                stream = (assets.open(storys[n]));
            } catch (Exception e) {

                // if any I/O error occurs
                e.printStackTrace();
            }

            story = new Story(stream);
        }

        //updates the text and hints on the screen
        updateInfo();

        //executes when the button is pressed
        button = (Button)findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inputs the entered text in the EditText view into the story class
                //and clears it afterwards
                EditText edittext = (EditText) findViewById(R.id.editText);
                String newtxt = edittext.getText().toString();
                story.fillInPlaceholder(newtxt);
                edittext.getText().clear();

                // checks if everything is filled in already,
                // if so we go to the story screen by intent
                // and we pass along the story text as a string
                if (story.isFilledIn()) {
                    Intent intent= new Intent(input_screen.this,story_screen.class);
                    intent.putExtra("story",story.toString());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Your story is done!", Toast.LENGTH_SHORT).show();
                } else {
                    //updates the screen again with the new values given by the story class
                    updateInfo();
                    Toast.makeText(getApplicationContext(), "Great! keep going!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateInfo() {
        Resources res = getResources();
        int left = story.getPlaceholderRemainingCount();
        String next_type = story.getNextPlaceholder();
        String words_left = String.format(res.getString(R.string.words_left), left);
        String please = String.format(res.getString(R.string.please), next_type);

        TextView textView = (TextView) findViewById(R.id.left);
        textView.setText(words_left);
        EditText edittext = (EditText) findViewById(R.id.editText);
        edittext.setHint(next_type);
        TextView textView2 = (TextView) findViewById(R.id.please);
        textView2.setText(please);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putSerializable("story_state",story);

        // etc.
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        story = (Story) savedInstanceState.getSerializable("story_state");
        updateInfo();
    }
}
