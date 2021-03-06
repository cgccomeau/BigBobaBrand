package edu.gatech.cs2340.m5bigbobabrand.views;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.cs2340.m5bigbobabrand.Model.GameState;
import edu.gatech.cs2340.m5bigbobabrand.R;
import edu.gatech.cs2340.m5bigbobabrand.entity.Coordinates;
import edu.gatech.cs2340.m5bigbobabrand.entity.Difficulty;
import edu.gatech.cs2340.m5bigbobabrand.entity.Player;
import edu.gatech.cs2340.m5bigbobabrand.entity.Ship;
import edu.gatech.cs2340.m5bigbobabrand.entity.SolarSystem;
import edu.gatech.cs2340.m5bigbobabrand.entity.Universe;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * Takes care of the view of the main activities in the game
 */
public class MainActivity extends AppCompatActivity {


    /**
     * UI Items
     */
    private Spinner difficultySpinner;
    private EditText nameField;
    private EditText pilotField;
    private EditText fighterField;
    private EditText traderField;
    private EditText engineerField;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.twigs);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameField = findViewById(R.id.player_name_input);
        pilotField = findViewById(R.id.pilot_field);
        fighterField = findViewById(R.id.fighter_field);
        traderField = findViewById(R.id.trader_field);
        engineerField = findViewById(R.id.engineer_field);
        difficultySpinner = findViewById(R.id.difficulty_spinner);
        Difficulty[] difficultyList = Difficulty.values();
        String[] difficultyStrings = new String[difficultyList.length];
        for (int i = 0; i < difficultyStrings.length; i++) {
            difficultyStrings[i] = difficultyList[i].getString();
        }
        ArrayAdapter<String> difficultyArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, difficultyStrings);
        difficultyArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyArrayAdapter);
        /* Check for student being passed in */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
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
    */

    /**
     * Button handler for the add new student button
     *
     * @param view the button that was pressed
     */
    public void onCreatePressed(View view) {

        try {
            Log.d("Edit", "Create Player Pressed");
            Toast toast = Toast.makeText(this, "" +
                    "player created", Toast.LENGTH_LONG);
            toast.show();

            /*
             Data for player being edited
             */
            Player player = new Player();
            Editable name = nameField.getText();
            Editable engineer = engineerField.getText();
            Editable fighter = fighterField.getText();
            Editable pilot = pilotField.getText();
            Editable trader = traderField.getText();

            player.setName(name.toString());
            player.setEngineerPts(Integer.parseInt(engineer.toString()));
            player.setFighterPts(Integer.parseInt(fighter.toString()));
            player.setPilotPts(Integer.parseInt(pilot.toString()));
            player.setTraderPts(Integer.parseInt(trader.toString()));
            String chosenDiffString = (String) difficultySpinner.getSelectedItem();
            Difficulty[] difficultyList = Difficulty.values();
            for (Difficulty d: difficultyList) {
                if(chosenDiffString.equals(d.getString())) {
                    player.setDifficulty(d);
                    break;
                }
            }
            try {
                if (!player.verifySum()) {
                    throw new IllegalArgumentException("Stats must be positive and sum to 16");
                }
                Difficulty playerDifficulty = player.getDifficulty();
                Ship playerShip = player.getShip();
                Log.d("Edit", "\nName: " + player.getName() + "\nPilot Points: "
                        + player.getPilotPts() + "\nFighter Points: " + player.getFighterPts()
                        +  "\nTrader Points: " + player.getTraderPts() +
                        "\nEngineer Points: " + player.getEngineerPts()
                        + "\nDifficulty: " + playerDifficulty.getString()
                        + "\nCredits: " + player.getCredits()
                        + "\nShip Type: " + playerShip.toString());
                Universe gameUniverse = new Universe();
                ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
                while (gameUniverse.size() < 10) {
                    Coordinates coordinates = new Coordinates();
                    int counter = 0;
                    int differenceThreshold = 5;
                    while (counter < coordinatesArrayList.size()) {
                        Coordinates coordinate = coordinatesArrayList.get(counter);
                        if ((Math.abs(coordinate.getX() - coordinates.getX())
                                <= differenceThreshold)
                                || (Math.abs(coordinate.getY() - coordinates.getY())
                                <= differenceThreshold)) {
                            coordinates = new Coordinates();
                            counter = -1;
                        }
                        counter++;
                    }
                    coordinatesArrayList.add(coordinates);
                    gameUniverse.addSolarSystem(new SolarSystem(coordinates));
                }

                Map<Coordinates, SolarSystem> gameUniverseMap = gameUniverse.getUniverse();
                Collection<SolarSystem> gameUniverseMapValues = gameUniverseMap.values();
                Object[] printMapArr = gameUniverseMapValues.toArray();

                Log.d("Edit", "Solar Systems:\nPlanet 1: " + printMapArr[0].toString()
                        + "\nPlanet 2: " + printMapArr[1].toString()
                        + "\nPlanet 3: " + printMapArr[2].toString()
                        + "\nPlanet 4: " + printMapArr[3].toString()
                        + "\nPlanet 5: " + printMapArr[4].toString()
                        + "\nPlanet 6: " + printMapArr[5].toString()
                        + "\nPlanet 7: " + printMapArr[6].toString()
                        + "\nPlanet 8: " + printMapArr[7].toString()
                        + "\nPlanet 9: " + printMapArr[8].toString()
                        + "\nPlanet 10: " + printMapArr[9].toString());
                Toast toast2 = Toast.makeText(this,
                        "Universe and Player Created", Toast.LENGTH_LONG);
                toast2.show();
                player.setSolarSystem(
                        gameUniverseMapValues.toArray(new SolarSystem[0])[0]);
                GameState.startGame(gameUniverse, player);
                Log.d("hello", "hello");
                mediaPlayer.release();
                mediaPlayer = null;
                Intent intent = new Intent(MainActivity.this, MarketActivity.class);
                this.startActivity(intent);
            } catch (IllegalArgumentException e) {
                Log.d("Error", e.getMessage());
                Toast toast3 = Toast.makeText(this, "Skill points must be positive and sum to 16!",
                        Toast.LENGTH_LONG);
                toast3.show();
            }
        } catch (Throwable T) {
            Log.d("Error", T.getMessage(), T);
            Toast toast4 = Toast.makeText(this,"Enter all required fields", Toast.LENGTH_LONG);
            toast4.show();

        }


    }



}
