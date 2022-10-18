package com.example.assignment_7_agm;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_7_agm.databinding.FragmentItemListBinding;
import com.example.assignment_7_agm.databinding.ItemListContentBinding;

import com.example.assignment_7_agm.placeholder.ModelContent;

import java.util.List;

/**
 * A fragment representing a list of Items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListFragment extends Fragment {

    //Method to intercept global key events in the item list fragment to trigger keyboard shortcuts Currently provides a toast when Ctrl + Z and Ctrl + F are triggered
    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };

    private FragmentItemListBinding binding;
    private static ModelContent displayMaker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);

        RecyclerView recyclerView = binding.itemList;

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        setupRecyclerView(recyclerView, itemDetailFragmentContainer);
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View itemDetailFragmentContainer
    ) {

        displayMaker = new ModelContent();
        displayMaker.jsonParse(getActivity());
        //Sets adapter based on the values in our MODELS list
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                ModelContent.MODELS,
                itemDetailFragmentContainer
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Model> modelValues;
        private final View mItemDetailFragmentContainer;

        SimpleItemRecyclerViewAdapter(List<Model> items,
                                      View itemDetailFragmentContainer) {
            modelValues = items;
            mItemDetailFragmentContainer = itemDetailFragmentContainer;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemListContentBinding binding =
                    ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);

        }

        //Method to set the values for the view holder
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.modelItem = modelValues.get(position);
            holder.mIdView.setText(modelValues.get(position).getName());
            holder.mContentView.setText(modelValues.get(position).getCivLeader());

            holder.itemView.setTag(modelValues.get(position));
            holder.itemView.setOnClickListener(itemView -> {
                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.ARG_ITEM_ID, modelValues.get(position).getName());
                if (mItemDetailFragmentContainer != null) {
                    Navigation.findNavController(mItemDetailFragmentContainer)
                            .navigate(R.id.fragment_item_detail, arguments);
                } else {
                    Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
                }
            });
        }

        @Override
        public int getItemCount() {
            return modelValues.size();
        }

        //Class that contains the definition for our view holder
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            public Model modelItem;

            ViewHolder(ItemListContentBinding binding) {
                super(binding.getRoot());
                mIdView = binding.idText;
                mContentView = binding.content;
            }

        }
    }
}