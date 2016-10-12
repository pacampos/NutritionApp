package com.example.nutrition.nutritionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class servingFragment extends Fragment {
    private FrameLayout mRelativeLayout;
    private PopupWindow mPopupWindow;
    private TextView tv;
    public servingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_serving, container, false);

        Button exampleButton = (Button) v.findViewById(R.id.exampleButton);
        mRelativeLayout = (FrameLayout) v.findViewById(R.id.rl);

        TextView grainsText = (TextView) v.findViewById(R.id.grainsText);
        TextView veggiesText = (TextView) v.findViewById(R.id.veggiesText);
        TextView fruitsText = (TextView) v.findViewById(R.id.fruitsText);
        TextView dairyText = (TextView) v.findViewById(R.id.dairyText);
        TextView meatText = (TextView) v.findViewById(R.id.meatText);


        grainsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView(0);
            }
        });
        veggiesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView(1);
            }
        });
        fruitsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView(2);
            }
        });
        dairyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView(3);
            }
        });
        meatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView(4);
            }
        });

        return v;
    }
    private void createView(int type) {


        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.custom_layout,null);

        tv = (TextView) customView.findViewById(R.id.tv);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        if (type == 0) {
            tv.setText("1 serving is:\n" +
                    "• 1 slice of brown sliced bread or\n" +
                    "wholegrain soda bread\n" +
                    "• 2-3 crackers or crispbreads\n" +
                    "• 4 dessertspoons flake type high\n" +
                    "fibre breakfast cereal, without\n" +
                    "sugar, honey or chocolate coating\n" +
                    "• 3 dessertspoons dry porridge oats\n" +
                    "• 2 breakfast cereal wheat or oat\n" +
                    "biscuits\n" +
                    "• 3 dessertspoons muesli, without\n" +
                    "sugar or honey coating\n" +
                    "• 1 medium or 2 small potatoes,\n" +
                    "• 2 dessertspoons of mashed\n" +
                    "potatoes\n" +
                    "• 3 dessertspoons or 1/2 cup boiled\n" +
                    "pasta, rice, noodles (25g/1 oz\n" +
                    "uncooked)\n" +
                    "• 1 cup of yam or plantain");
        } else if (type == 1) {
            tv.setText("1 serving is:\n" +
                    "• 4 dessertspoons of cooked\n" +
                    "vegetables – fresh or frozen\n" +
                    "• a bowl of salad – lettuce, tomato,\n" +
                    "cucumber\n" +
                    "• a bowl of homemade vegetable\n" +
                    "soup\n" +
                    "• 1 small corn on the cob or 4\n" +
                    "heaped dessertspoons of\n" +
                    "sweetcorn\n" +
                    "• a small glass (100ml) of\n" +
                    "smoothie made only from fruit\n" +
                    "or vegetables.");
        } else if (type == 2) {
            tv.setText("1 serving is:\n" +
                    "• 1 medium apple, orange,\n" +
                    "banana, pear or similar size fruit\n" +
                    "• 2 small fruits - plums, kiwis or\n" +
                    "similar size fruit\n" +
                    "• 10-12 berries, grapes or cherries\n" +
                    "• ½ a grapefruit\n" +
                    "• 1 heaped dessertspoon of raisins\n" +
                    "or sultanas\n" +
                    "• 4 dessertspoons of cooked fresh\n" +
                    "fruit, fruit tinned in own juice or\n" +
                    "frozen fruit\n" +
                    "• a small glass (100ml) of\n" +
                    "unsweetened fruit juice or a\n" +
                    "smoothie made only from fruit\n" +
                    "or vegetables.");
        } else if (type == 3) {
            tv.setText("1 serving is:\n" +
                    "• 1 large glass (200ml) low fat or\n" +
                    "low fat fortified milk\n" +
                    "• 1 large glass (200ml) calcium\n" +
                    "enriched Soya milk\n" +
                    "• 1 small carton yogurt (125ml)\n" +
                    "• 1 yogurt drink (200ml)\n" +
                    "• 1 small carton fromage frais\n" +
                    "• 25g/1oz (matchbox size piece)\n" +
                    "of low fat cheddar or semi-soft\n" +
                    "cheese\n" +
                    "• 50g/2oz low fat soft cheese\n" +
                    "• 2 processed cheese triangles\n" +
                    "• 75g/3oz cottage cheese\n" +
                    "• 1 portion of milk pudding made\n" +
                    "with a large glass low fat milk ");
        } else if (type == 4) {
            tv.setText("1 serving is:\n" +
                    "• 50-75g/2-3oz cooked lean beef,\n" +
                    "pork, lamb, lean mince, chicken\n" +
                    "(This is about 100g/4oz of raw\n" +
                    "meat or poultry and is about the\n" +
                    "size of a pack of cards)\n" +
                    "• 100g/4oz cooked oily fish (salmon,\n" +
                    "mackerel, sardines) or white fish\n" +
                    "(cod, haddock, plaice)\n" +
                    "• 2 eggs- limit to 7 eggs a week\n" +
                    "• 100g/4oz soya or tofu\n" +
                    "• 125g/5oz hummus\n" +
                    "• 6 dessertspoons of peas, beans\n" +
                    "(includes baked beans) or lentils\n" +
                    "• 40g/1.5oz unsalted nuts or peanut\n" +
                    "butter or seeds");
        }

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
    }


}
