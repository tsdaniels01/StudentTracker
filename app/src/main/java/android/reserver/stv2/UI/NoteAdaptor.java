package android.reserver.stv2.UI;

import android.content.Context;
import android.content.Intent;
import android.reserver.stv2.Entities.Note;
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
 * This class is an adaptor for the recycleView layout course notes. It extends the RecyclerView.
 * Adaptor class.
 */
public class NoteAdaptor extends RecyclerView.Adapter<NoteAdaptor.NoteViewHolder> {
    /**
     * This class is used to represent the objects in the viewHolder and display them
     */
    class NoteViewHolder extends RecyclerView.ViewHolder{
        // creates a TextView variable to store the noteItemView
        private final TextView noteItemView;
        /**
         * This method is the ViewHolder to hold the recycleView
         * @param itemView the widget that is used
         */
        private NoteViewHolder (View itemView){
            super (itemView); // parent class reference
            // assigns the TextView to the class variable
            noteItemView = itemView.findViewById(R.id.courseNoteTextView);
            // sets the itemView with an OnClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gets the position of the adaptor and assigns to variable
                    int position = getAdapterPosition();
                    // Uses the position to retrieve the object and assigns to variable
                    final Note current = mNotes.get(position);
                    // creates an intent to proceed to the NoteDetails class
                    Intent intent = new Intent(context, android.reserver.stv2.UI.NoteDetails.class);
                    // passes note object parameters through the intent
                    intent.putExtra("id", current.getNoteID());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("courseID", current.getCourseID());
                    // starts the intent
                    context.startActivity(intent);

                }
            });
        }
    }
    private List<Note> mNotes; // creates a list variable to hold the notes
    private final Context context; // creates a context variable to hold the context
    private final LayoutInflater mInflater; // creates an inflater variable to hold the inflater

    /**
     * This method is used to instantiate the adaptor
     * @param context the context used
     */
    public NoteAdaptor (Context context)    {
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
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // creates a View variable to hold the layout used
        View itemView = mInflater.inflate(R.layout.layout_course_notes, parent, false);
        return new NoteViewHolder(itemView); // the view returned
    }
    /**
     * This method is used to pass data and bind the viewHolder to the adaptor.
     * @param holder the data of the itemView
     * @param position the position of the itemView
     */
    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        // branch statement to verify contents
        if(mNotes != null)  { // if mNotes is not null
            // assigns the note to a class variable
            Note current = mNotes.get(position);
            // sets the text of the ItemView with the noteName
            holder.noteItemView.setText(current.getNote());
        }
        else{
            // if null sets text message
            holder.noteItemView.setText("No Note");
        }
    }
    /**
     * This method is used to populate the recycleView with the assessment list
     * @param notes the notes used
     */
    public void setNotes (List<Note> notes) {
        mNotes = notes; // assigns the notes to class variable
        notifyDataSetChanged(); // notify data change
    }
    /**
     * This method is used to get the get the number of itemViews
     * @return the size of the list
     */
    @Override
    public int getItemCount() {
        // branch statement
        if (mNotes != null) // if notes is not null
            return mNotes.size(); // return size
        else return 0; // else return 0
    }


}
