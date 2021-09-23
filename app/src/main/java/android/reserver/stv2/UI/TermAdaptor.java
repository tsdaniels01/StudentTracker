package android.reserver.stv2.UI;

import android.content.Context;
import android.content.Intent;
import android.reserver.stv2.Entities.Term;
import android.reserver.stv2.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Project: C196 Mobile Application Development Performance Assessment
 * <p>
 * User: Timothy Daniels
 * ID: 001164972
 * Date: 7/5/2021
 * Time: 1:35 PM
 * <p>
 * Created with Android Studio
 */

/**
 * This class is an adaptor for the recycleView layout term list. It extends the RecyclerView.
 * Adaptor class.
 */
public class TermAdaptor extends RecyclerView.Adapter<TermAdaptor.TermViewHolder> {

    /**
     * This class is used to represent the objects in the viewHolder and display them
     */
    class TermViewHolder extends RecyclerView.ViewHolder{
        // creates a TextView variable to store the termItemView
        private final TextView termItemView;
        /**
         * This method is the ViewHolder to hold the recycleView
         * @param itemView the widget that is used
         */
        private TermViewHolder(View itemView)   {
            super (itemView);// parent class reference
            // assigns the TextView to the class variable
            termItemView = itemView.findViewById(R.id.termListTextView);
            // sets the itemView with an OnClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gets the position of the adaptor and assigns to variable
                    int position = getAdapterPosition();
                    // Uses the position to retrieve the object and assigns to variable
                    final Term current = mTerms.get(position);
                    // creates an intent to proceed to the Course List class
                    Intent intent = new Intent(context, CourseList.class );
                    // passes term object parameters through the intent
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("start", current.getTermStart());
                    intent.putExtra("end", current.getTermEnd());
                    // starts the intent
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term> mTerms; // creates a list variable to hold the terms
    private final Context context; // creates a context variable to hold the context
    private final LayoutInflater mInflater; // creates an inflater variable to hold the inflater

    /**
     * This method is used to instantiate the adaptor
     * @param context the context used
     */
    public TermAdaptor(Context context){
        // instantiates the inflater from the parameter and assigns it to variable
        mInflater=LayoutInflater.from(context);
        // assigns the parameter context to the class context
        this.context = context;
    }
    /**
     * This method is used to initialize the viewHolder
     * @param parent other contained views
     * @param viewType the id of the layout used
     * @return view
     */
    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // creates a View variable to hold the layout used
        View itemView = mInflater.inflate(R.layout.layout_list_terms, parent, false);
        return new TermViewHolder(itemView); // the view returned
    }
    /**
     * This method is used to pass data and bind the viewHolder to the adaptor.
     * @param holder the data of the itemView
     * @param position the position of the itemView
     */
    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        // branch statement to verify contents
        if (mTerms != null) { // if mTerms is not null
            // assigns the term to a class variable
            Term current = mTerms.get(position);
            // sets the text of the ItemView with the TermName
            holder.termItemView.setText(current.getTermName());

        }
        else { // if null sets text message
            holder.termItemView.setText("No Term Name");

        }
    }
    /**
     * This method is used to populate the recycleView with the assessment list
     * @param terms the term used
     */
    public void setTerms(List<Term> terms){
        mTerms = terms; // assigns the terms to class variable
        notifyDataSetChanged(); // notify data change
    }
    /**
     * This method is used to get the get the number of itemViews
     * @return the size of the list
     */
    @Override
    public int getItemCount() {
        // branch statement
        if (mTerms != null) // if mTerms is not null
            return mTerms.size(); // return size
        else return 0; // else return 0
    }
}
