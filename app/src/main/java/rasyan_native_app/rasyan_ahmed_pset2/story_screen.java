package rasyan_native_app.rasyan_ahmed_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class story_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button button;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_screen);

        //get the story that was passed along by intent and set the text of the textView to it.
        String story_txt = getIntent().getExtras().getString("story");
        TextView textView = (TextView) findViewById(R.id.story);
        textView.setText(story_txt);

        //go to the previous screen when the button is pressed.
        button = (Button)findViewById(R.id.another);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(story_screen.this,input_screen.class);
                startActivity(intent);
            }});
    }
}
