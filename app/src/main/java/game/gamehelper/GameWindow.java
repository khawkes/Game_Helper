/**Window containing a visual representation of a hand, with options
 * to change arrangement
 *
 */

package game.gamehelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import game.gamehelper.javaFiles.Domino;
import game.gamehelper.javaFiles.Hand;


public class GameWindow extends ActionBarActivity {
    private Hand hand;
    private ListView listView;
    private ImageView image;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);
        Bundle bundle = getIntent().getExtras();

        DominoAdapter adapter;
        Domino[] data = new Domino[0];

        text = (TextView)findViewById(R.id.remPoint);
        listView = (ListView) findViewById(R.id.listView);
        image = (ImageView) findViewById(R.id.imageView2);

        text.setClickable(false);

        //use data passed from main window or camera to create a hand
        if(bundle != null) {
            if (bundle.getInt("dominoTotal") != 0) {
                hand = new Hand((int[][]) bundle.getSerializable("dominoList"),
                        bundle.getInt("dominoTotal"), bundle.getInt("maxDouble"));

                //create domino array for adapter, set text and image to corresponding values
                Domino temp[] = hand.toArray();

                data = new Domino[(temp.length < 10) ? temp.length : 10];

                //generate bitmaps for hand
                //ONLY for the first 10 dominoes.
                for (int i = 0; i < data.length; i++) {
                    data[i] = temp[i];
                    buildDomino(data[i]);
                }

                //for (Domino a : hand.toArray())
                //    buildDomino(a);


                text.setText(Integer.toString(hand.getTotalPointsHand()));
                image.setImageBitmap(getSide(hand.getLargestDouble()));
            }
        }

        //set ListView adapter to display list of dominos
        adapter = new DominoAdapter(this, R.layout.hand_display_grid, data);
        listView.setAdapter(adapter);

        //THIS IS A HACK. REMOVE. USED FOR DEBUGGING PURPOSES.
//        Domino [] longestRun = hand.getLongestRun().toArray();
//        adapter = new DominoAdapter(this, R.layout.hand_display_grid, longestRun);
//        listView.setAdapter(adapter);

        addButtonBehavior();
    }

    public void addButtonBehavior(){

        Button longestRun = (Button)findViewById(R.id.longestRunButton);
        Button highestScore = (Button)findViewById(R.id.highestScoreButton);
        Button draw = (Button)findViewById(R.id.drawButton);
        Button undo = (Button)findViewById(R.id.undoButton);

        //longest run click handler
        longestRun.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        // TODO Add behavior
                        //clear memory
                        for (Domino d : hand.toArray()) {
                            d.deletePic();
                        }
                        Domino temp[] = new Domino[1];
                        temp[0] = new Domino(0, 0);
                        buildDomino(temp[0]);
                        DominoAdapter adapter = new DominoAdapter(v.getContext(), R.layout.hand_display_grid, temp);
                        listView.setAdapter(adapter);

                        //set ListView adapter to display list of dominos
                        temp = hand.getLongestRun().toArray();

                        Domino viewLongestRun[] = new Domino[(temp.length < 10) ? temp.length : 10];

                        //generate bitmaps for hand (first 10 values, or memory crash).
                        for (int i = 0; i < viewLongestRun.length; i++) {
                            viewLongestRun[i] = temp[i];
                            buildDomino(viewLongestRun[i]);
                        }

                        adapter = new DominoAdapter(v.getContext(), R.layout.hand_display_grid, viewLongestRun);
                        listView.setAdapter(adapter);
                    }
                }
        );

        //highest score click handler
        highestScore.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        // TODO Add behavior
                        //clear memory
                        for (Domino d : hand.toArray()) {
                            d.deletePic();
                        }
                        Domino temp[] = new Domino[1];
                        temp[0] = new Domino(0, 0);
                        buildDomino(temp[0]);
                        DominoAdapter adapter = new DominoAdapter(v.getContext(), R.layout.hand_display_grid, temp);
                        listView.setAdapter(adapter);

                        //set ListView adapter to display list of dominos
                        temp = hand.getMostPointRun().toArray();

                        Domino viewMostPointRun[] = new Domino[(temp.length < 10) ? temp.length : 10];

                        //generate bitmaps for hand (first 10 values, or memory crash).
                        for (int i = 0; i < viewMostPointRun.length; i++) {
                            viewMostPointRun[i] = temp[i];
                            buildDomino(viewMostPointRun[i]);
                        }

                        adapter = new DominoAdapter(v.getContext(), R.layout.hand_display_grid, viewMostPointRun);
                        listView.setAdapter(adapter);
                    }
                }
        );

        //draw click handler
        draw.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        // TODO Add behavior
                    }
                }
        );

        //undo click handler
        undo.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        // TODO Add real behavior
                        //clear memory
                        for (Domino d : hand.toArray()) {
                            d.deletePic();
                        }
                        Domino temp[] = new Domino[1];
                        temp[0] = new Domino(0, 0);
                        buildDomino(temp[0]);
                        DominoAdapter adapter = new DominoAdapter(v.getContext(), R.layout.hand_display_grid, temp);
                        listView.setAdapter(adapter);

                        //set ListView adapter to display list of dominos
                        temp = hand.toArray();
                        Domino viewHand[] = new Domino[(temp.length < 10) ? temp.length : 10];

                        //generate bitmaps for hand
                        for (int i = 0; i < viewHand.length; i++) {
                            viewHand[i] = temp[i];
                            buildDomino(viewHand[i]);
                        }

                        adapter = new DominoAdapter(v.getContext(), R.layout.hand_display_grid, viewHand);
                        listView.setAdapter(adapter);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_window, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Load background and write each side on top
    public void buildDomino(Domino a){

        Bitmap side1;
        Bitmap side2;
        Bitmap bg;

        bg = BitmapFactory.decodeResource(getResources(), R.drawable.dom_bg);
        side1 = getSide(a.getVal1());
        side2 = getSide(a.getVal2());

        //copy immutable bitmap generated previously to a mutable bitmap and impose the sides
        bg = bg.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bg);
        canvas.drawBitmap(side1, 0, 0, null);
        canvas.drawBitmap(side2, side2.getWidth(), 0, null);

        a.setDominoPic(bg);
    }

    //Load image for domino side value
    private Bitmap getSide(int value){

        Bitmap side;

        switch(value){
            case 1:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_one);
                break;
            case 2:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_two);
                break;
            case 3:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_three);
                break;
            case 4:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_four);
                break;
            case 5:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_five);
                break;
            case 6:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_six);
                break;
            case 7:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_seven);
                break;
            case 8:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_eight);
                break;
            case 9:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_nine);
                break;
            case 10:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_ten);
                break;
            case 11:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_eleven);
                break;
            case 12:
                side = BitmapFactory.decodeResource(getResources(), R.drawable.dom_twelve);
                break;
            case 0:
            default:
                side = Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888);
                break;
        }
        return side;
    }
}
