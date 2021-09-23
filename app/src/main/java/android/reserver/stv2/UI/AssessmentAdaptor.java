package android.reserver.stv2.UI;

import android.content.Context;
import android.content.Intent;
import android.reserver.stv2.Entities.Assessment;
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
 * This class is an adaptor for the recycleView layout assessment list. It extends the RecyclerView.
 * Adaptor class.
 */
public class AssessmentAdaptor extends RecyclerView.Adapter
        <AssessmentAdaptor.AssessmentViewHolder> {
    /**
     * This class is used to represent the objects in the viewHolder and display them
     */
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        // creates a TextView variable to store the assessmentItemView
        private final TextView assessmentItemView;

        /**
         * This method is the ViewHolder to hold the recycleView
         * @param itemView the widget that is used
         */
        private AssessmentViewHolder (View itemView){
            super (itemView); // parent class reference
            // assigns the TextView to the class variable
            assessmentItemView = itemView.findViewById(R.id.assessmentListTextView);
            // sets the itemView with an OnClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gets the position of the adaptor and assigns to variable
                    int position = getAdapterPosition();
                    // Uses the position to retrieve the object and assigns to variable
                    final Assessment current = mAssessments.get(position);
                    // creates an intent to proceed to the AssessmentDetails class
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    // passes assessment object parameters through the intent
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("start", current.getAssessmentStart());
                    intent.putExtra("end", current.getAssessmentEnd());
                    // starts the intent
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessments; // creates a list variable to hold the assessments
    private final Context context; // creates a context variable to hold the context
    private final LayoutInflater mInflater; // creates an inflater variable to hold the inflater

    /**
     * This method is used to instantiate the adaptor
     * @param context the context used
     */
    public AssessmentAdaptor(Context context)   {
        // instantiates the inflater from the parameter and assigns it to variable
        mInflater = LayoutInflater.from(context);
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
    public AssessmentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        // creates a View variable to hold the layout used
        View itemView = mInflater.inflate(R.layout.layout_list_assessments, parent, false);
        return new AssessmentViewHolder(itemView); // the view returned

    }

    /**
     * This method is used to pass data and bind the viewHolder to the adaptor.
     * @param holder the data of the itemView
     * @param position the position of the itemView
     */
    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        // branch statement to verify contents
        if (mAssessments != null)   { // if mAssessments is not null
            // assigns the assessment to a class variable
            Assessment current = mAssessments.get(position);
            // sets the text of the ItemView with the assessmentName
            holder.assessmentItemView.setText(current.getAssessmentName());
        }
        else {
            // if null sets text message
            holder.assessmentItemView.setText("No Assessment Name");
        }

    }

    /**
     * This method is used to populate the recycleView with the assessment list
     * @param assessments the assessments used
     */
    public void setAssessments (List<Assessment> assessments) {
        mAssessments = assessments; // assigns the assessments to class variable
        notifyDataSetChanged(); // notify data change
    }

    /**
     * This method is used to get the get the number of itemViews
     * @return the size of the list
     */
    @Override
    public int getItemCount() {
        // branch statement
        if (mAssessments != null) // if assessments is not null
            return mAssessments.size(); // return size
        else return 0; // else return 0
    }


}
