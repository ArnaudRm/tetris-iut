package com.tetris.arnaud.tetris;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tetris.arnaud.tetris.Models.*;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,Runnable, View.OnClickListener  {

    public int W = 10;
    public int H = 15;
    public int timer = 500;
    public int map[][] = new int[H][W];
    public Block current;
    public int score = 0;
    public GridView grid;
    private boolean gameOver = false;
    private boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().hide();

        this.grid = (GridView) findViewById(R.id.map);
        LinearLayout onleft = (LinearLayout) findViewById(R.id.left);
        LinearLayout onright = (LinearLayout) findViewById(R.id.right);
        ImageView ondown = (ImageView) findViewById(R.id.down);
        ImageView onstart = (ImageView) findViewById(R.id.start);
        onleft.setOnClickListener(this);
        onright.setOnClickListener(this);
        onstart.setOnClickListener(this);
        ondown.setOnClickListener(this);
        this.grid.setOnItemClickListener(this);
        this.current = this.newBlock();
        this.resetScore();
        this.run();
    }

    public void resetScore(){
        this.score = 0;
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Score : " + this.score);
    }

    /**
     * Manage click events
     * this function
     * @param v
     */
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.left:
                if(!this.paused){
                    this.current.left(this.map);
                    this.draw();
                }
                break;
            case R.id.right:
                if(!this.paused) {

                    this.current.right(this.map);
                    this.draw();
                }
                break;
            case R.id.map:
                this.current.rotate(this.map, true);
                this.draw();
                break;
            case R.id.down:
                if(!this.paused) {
                    this.current.down(this.map);
                }
                break;
            case R.id.start:
                this.paused = !this.paused;
                this.displayPauseDialog();
            break;
            default:

                break;
        }
        this.draw();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        this.current.rotate(this.map, true);
        this.draw();
    }

    /**
     * Create a random Block with random color
     * @return Block
     */

    public Block newBlock(){
        Random r = new Random();
        String[] list = {"I","O","Z","S","J","L","T"};
        int n = r.nextInt(list.length);
        Block b;
        switch (list[n])
        {
            case "O": b= new O(this.W / 2); break;
            case "Z": b= new Z(this.W / 2); break;
            case "S": b= new S(this.W / 2); break;
            case "L": b= new L(this.W / 2); break;
            case "J": b= new J(this.W / 2); break;
            case "T": b= new T(this.W / 2); break;
            case "I": b= new I(this.W / 2); break;
            default:
                b= new I(this.W / 2);
                break;
        }
        b.color = r.nextInt(5) + 1;
        return b;
    }

    public void draw(){
        int[][] mapDraw = new int[this.H][this.W];
        for(int i = 0; i < this.map.length; i++)
            mapDraw[i] = this.map[i].clone();

        int[][] form = this.current.getForm();

        for(int i = 0; i < form.length; i++)
        {
            for (int j = 0; j < form[i].length; j++)
            {
                if(this.map[this.current.getY() + i][this.current.getX() + j] == 0)
                    mapDraw[this.current.getY() + i][this.current.getX() + j] = form[i][j] == 0 ? 0 : this.current.color;
            }
        }
        int[] arr = new int[mapDraw.length * mapDraw[0].length];
        for(int i = 0; i < mapDraw.length; i++) {
            for (int j = 0; j < mapDraw[i].length; j++) {

                int n = i * mapDraw[0].length + j;

                switch (mapDraw[i][j])
                {
                    case 0: arr[n] = R.drawable.sample_2; break;
                    case 1: arr[n] = R.drawable.sample_3; break;
                    case 2: arr[n] =  R.drawable.purple; break;
                    case 3: arr[n] =  R.drawable.green; break;
                    case 4: arr[n] =  R.drawable.red; break;
                    case 5: arr[n] =  R.drawable.yellow; break;
                    case 6: arr[n] = R.drawable.sample_3; break;
                    default:
                        arr[n] = R.drawable.sample_3;
                        Log.i("COLOR", "unknow color ");
                        break;
                }

            }
        }
        this.grid.setAdapter(new ImageAdapter(this, arr));
    }

    /**
     * this function is called by the timer
     */
    public void refresh() {
        if(this.current.canMove(this.map, "down"))
        {
            this.current.down();
        }
        else {
            this.current.fix(this.map);
            this.clean();
            if (this.current.getY() == 0) {
                this.gameOver = true;
                this.displayGameOverDialog();
            } else {
                this.current = this.newBlock();
            }
        }
        this.draw();
    }


    public void run() {
        final Handler h = new Handler();
        Runnable run = new Runnable() {
            public void run() {
                if(!gameOver && !paused)
                {
                    refresh();
                }
                h.postDelayed(MainActivity.this, MainActivity.this.timer);
            }
        };
        h.post(run);
    }

    public String getMessageScore(){
        String message = "" ;

        if (this.score < 40){
            message = "As tu vraiment essayé ?";
        }else if (this.score < 250){
            message =  "Ca passe pour cette fois...";
        }else{
            message = "Tu as suffisament joué ;)";
        }
        return message;
    };

    /**
     * check if some line are full, and clean them
     */
    private void clean() {
        boolean b;
        int n = 0;
        for(int i = 0; i < this.H; i++)
        {
            b = true;
            for(int j = 0; j < this.W; j++)
            {
                if(this.map[i][j] == 0)
                {
                    b = false;
                    break;
                }
            }
            if(b)
            {
                n++;
                this.map[0] = new int[this.W];
                for(int k = i; k > 0; k--)
                {
                    System.arraycopy(this.map[k - 1], 0, this.map[k], 0, this.W);
                }
            }
        }
        // official scoring for level 1
        switch (n)
        {
            case 1: this.score += 40; break;
            case 2: this.score += 100; break;
            case 3: this.score += 300; break;
            case 4: this.score += 1200; break;
            default:
        }

         TextView scoreView = (TextView) findViewById(R.id.score);
         scoreView.setText("Score : " + this.score);
         Log.i("Score : " , "test :" + this.score);
    }

    /**
     * fill the map with empty case ( value 0 )
     * and init to default value all variables
     */
    private void clear() {
        for (int i = 0; i < this.map.length; i++)
        {
            Arrays.fill(this.map[i], 0);
        }
        this.current = this.newBlock();
        this.paused = false;
        this.gameOver = false;
        this.resetScore();
    }

    /**
     * prints a dialog when users reaches game over
     */
    public void displayGameOverDialog() {
        String scoreMessage = this.getMessageScore();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game over ! " + scoreMessage)
                .setPositiveButton("rejouer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clear();
                    }
                });
        AlertDialog dialog =  builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    /**
     * prints a dialog when users hits start
     */
    public void displayPauseDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Jeu en pause. Souhaitez-vous continuer?")
                .setPositiveButton("Reprendre", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User disabled start
                        MainActivity.this.paused = !MainActivity.this.paused;
                    }
                })
                .setNegativeButton("Recommencer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User disabled start
                        MainActivity.this.paused = !MainActivity.this.paused;
                        clear();
                    }
                });
        AlertDialog dialog =  builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}