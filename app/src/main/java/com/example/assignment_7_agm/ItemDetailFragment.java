package com.example.assignment_7_agm;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.assignment_7_agm.placeholder.ModelContent;
import com.example.assignment_7_agm.databinding.FragmentItemDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";


    //Create variable of our Model item
    private Model modelItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;
    private FloatingActionButton testingFab;

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            //Gets the item from our map and stores it in mItem
            modelItem = ModelContent.MODELS_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentItemDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            //Gets the model from the model map
            modelItem = ModelContent.MODELS_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        testingFab = rootView.findViewById(R.id.fab);
        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.itemDetail;

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();

        rootView.setOnDragListener(dragListener);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (modelItem != null) {
            mTextView.setText(modelItem.getCivDescription());
            if (mToolbarLayout != null) {
                mToolbarLayout.setTitle(modelItem.getName());
            }
        }
        if (testingFab != null)
        {
            testingFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {



                }
            });
        }

    }


//    private void jsonParse()
//    {
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String url = "e.com";
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the response string in our convenient existing text view
//                        mTextView.setText("Response is: "+ response);
//                        // NEXT, we need to use GSON to turn that JSON into a model
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // you should drop a breakpoint RIGHT HERE if you need to see the error coming back
//                mTextView.setText("That didn't work!");
//            }
//        });
//
//        queue.add(stringRequest);
//    }

}